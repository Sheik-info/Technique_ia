package tools;

import agents.Organisme;

import java.util.Date;

public class Billet {
    Date depart;
    Date arrivee;
    int prix;
    int nbPersonnesMax=30;
    private Organisme organisme;

    public Billet(Date d, Date a, int pr, Organisme or) {
        this.depart = d;
        this.arrivee = a;
        this.prix = pr;
        this.organisme = or;

    }

    Billet setBillet(Date d, Date a, int pr, Organisme or) {
        return new Billet(d,a,pr,or);
    }

    public int getNbPersonnesMax() {
        return nbPersonnesMax;
    }

    public void placeReservee() {
        nbPersonnesMax--;
    }

    public int getPrix() {
        return prix;
    }
    public Organisme getOrganisme() {
        return organisme;
    }
}
