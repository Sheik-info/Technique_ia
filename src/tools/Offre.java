package tools;
import agents.Fournisseur;
import agents.Organisme;

import java.util.List;

public class Offre {

    protected Organisme expediteur;
    protected List<Organisme> coalition;
    int price;

    public Offre(Organisme exp, int pr) {
        this.expediteur = exp;
        this.price = pr;
        this.coalition = null;
    }
    public Offre(List<Organisme> exp, int pr) {
        this.expediteur = null;
        this.coalition = exp;
        this.price = pr;
    }

    public int etat() {
        return price;
    }

    public Organisme getExpediteur() {
        return expediteur;
    }

    public List<Organisme> getCoalition() {
        return coalition;
    }
}
