package agents;

import tools.Billet;
import tools.Offre;
import tools.Strategie;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Personne extends Acheteur {


    int prefere;
    private int nbBillets;

    public Personne(int id, int budget) {
        super(id, budget+ThreadLocalRandom.current().nextInt(0, budget+1));
        this.strat = new Strategie();
        prefere = ThreadLocalRandom.current().nextInt(0, 10 + 1);
    }

    @Override
    public void recevoirOffre(Offre m) {
    }

    @Override
    public Offre proposerOffre(Fournisseur exp, int price) {
        return null;
    }

    @Override
    public Offre proposerOffre(Fournisseur exp, int price, List<Organisme> orga) {
        return null;
    }

    protected Offre soumettreOffre(Agent fournisseur, int pr) {
        return null;
    }

    public void salaire() {
        budget += 300;
    }

    public void offreAcceptee(Billet billet) {
        budget -= billet.getPrix();
        nbBillets++;
    }
    public int decision(int offre) {
        return strat.decision(offre,budget);
    }
    public int contribution(int prixBillet) {
        int vd = strat.valeurDepart(budget, prixBillet);
        if (vd == -1) return 0;

        int offre = vd;
        int nouvelleOffre;

        while ((nouvelleOffre = strat.valeurOffre(offre, vd)) != -1) {
            offre = nouvelleOffre;
        }

        return offre;
    }
    public static int cooperation(Personne[] groupe, int prixBillet) {
        int total = 0;

        for (Personne p : groupe) {
            total += p.contribution(prixBillet);
        }

        return total;
    }
    public int getPrefer() {
        return prefere;
    }

    public int surencherir(int prixActuel) {

        int decision = strat.decision(prixActuel, budget);

        if (decision == 0) return -1;

        int increment;

        if (decision == 1) {
            increment = 10;
        } else {
            increment = budget / 15;
        }

        int nouvelleOffre = prixActuel + increment;

        if (nouvelleOffre > budget) return -1;

        return nouvelleOffre;
    }

    public int getNbBillets() {
        return nbBillets;
    }
}
