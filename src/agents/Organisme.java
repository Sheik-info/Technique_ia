package agents;

import tools.Coalition;
import tools.Offre;
import tools.Strategie;

import java.util.ArrayList;
import java.util.List;

public class Organisme extends Acheteur{

    private Coalition alliance;
    public static List<Coalition> coalitions = new ArrayList<>();
    public static int nextCoalitionId = 0;

    public Organisme(int id, int budget) {
        super(id,budget);

        this.alliance = null;

        this.strat = new Strategie();
    }

    @Override
    public void recevoirOffre(Offre m) {
    }


    @Override
    public Offre proposerOffre(Fournisseur exp, int price, List<Organisme> orga) {
        if(price <= budget) {
            return new Offre(this, price);
        }
        System.out.println("je suis passé par là");
        if(alliance != null) {
            //alliance.
        }
        return null;
    }

    public void rente(int amount) {
        budget += amount;
    }

    public void offreAcceptee(int offre) {
        budget -= offre;
    }

    public Coalition getCoalitionObj() {
        return alliance;
    }

    public void setCoalition(Coalition coalition) {
        this.alliance = coalition;
    }

    public boolean getCoalition() {
        return alliance != null;
    }

    public void proposerCoalition(Organisme dest) {
        if(dest.getBudget() < 2000 && !dest.getCoalition()) {
            Organisme.idCoalition++;
            dest.setIdCoalition(Organisme.idCoalition);
            dest.setCoalition(true);
        }
        else if(dest.getBudget() > 2000 && !dest.getCoalition())
        {
            Organisme.idCoalition++;
            dest.setIdCoalition(Organisme.idCoalition);
            dest.setCoalition(true);
        }
    }

    public static int valeurCoalition(List<Organisme> coalition) {
        int somme = 0;
        for(Organisme o : coalition) {
            somme += o.getBudget();
        }
        return somme;
    }

    public int surencherir(int prixActuel) {

        int decision = strat.decision(prixActuel, budget);

        // abandon
        if (decision == 0) {
            return -1;
        }

        int offre;

        // première vraie offre
        int vd = strat.valeurDepart(budget, prixActuel);

        if (vd == -1) {
            return -1;
        }

        // petite stratégie prudente
        if (decision == 1) {

            offre = strat.valeurOffre(prixActuel, vd);

            if (offre == -1) {
                return -1;
            }
        }

        // stratégie agressive
        else {

            offre = vd;

            int nouvelle;

            while ((nouvelle =
                    strat.valeurOffre(offre, vd)) != -1) {

                offre = nouvelle;
            }
        }

        if (offre > budget) {
            return -1;
        }

        return offre;
    }

}
