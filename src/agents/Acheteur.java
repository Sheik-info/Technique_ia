package agents;

import java.util.Date;
import java.util.List;

import tools.Billet;
import tools.Offre;
import tools.Strategie;

public abstract class Acheteur extends Agent {

    protected int budget = 1500;
    protected Strategie strat;
    protected boolean coalition = false;

    public Acheteur(int id, int budget) {
        this.budget = budget;
        this.id = id;
        this.coalition = false;
    }

    Billet setBillet(Date d, Date a, int pr) {
        return new Billet(d,a,pr,(Organisme) this);
    }

    @Override
    public abstract void recevoirOffre(Offre m);

    public int getBudget() {
        return budget;
    }

    public int decision(int price) {
        return strat.decision(price,budget);
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

}