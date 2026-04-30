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

    @Override
    public abstract Offre proposerOffre(Fournisseur exp, int price);

    public abstract Offre proposerOffre(Fournisseur exp, int price, List<Organisme> orga);

    protected abstract Offre soumettreOffre(Agent fournisseur, int pr);

    public int getBudget() {
        return budget;
    }

}