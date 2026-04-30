package agents;

import tools.Billet;
import tools.Offre;
import tools.Strategie;

import java.util.concurrent.ThreadLocalRandom;

public class Personne extends Acheteur {


    int prefere;
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

    protected Offre soumettreOffre(Agent fournisseur, int pr) {
        return null;
    }

    public void salaire() {
        budget += 300;
    }

    public void offreAcceptee(Billet billet) {
        budget -= billet.getPrix();
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



}
