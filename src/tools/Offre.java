package tools;
import agents.Fournisseur;
import agents.Organisme;

public class Offre {

    protected Organisme expediteur;
    int price;

    public Offre(Organisme exp, int pr) {
        this.expediteur = exp;
        this.price = pr;
    }

    public Offre() {

    }

    public int etat() {
        return price;
    }

    public Organisme getExpediteur() {
        return expediteur;
    }
}
