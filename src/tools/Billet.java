package tools;

import agents.Organisme;

import java.util.Date;

public class Billet {
    Date depart;
    Date arrivee;
    int prix;
    int nbPersonnesMax=30;
    private Organisme organisme;
    private Coalition coalition;


    public Billet(Date d, Date a, int pr, Organisme or) {
        this.depart = d;
        this.arrivee = a;
        this.prix = pr;
        this.organisme = or;
        this.coalition = null;

    }
    public Billet(Date d,
                  Date a,
                  int pr,
                  Coalition coalition) {

        this.depart = d;
        this.arrivee = a;
        this.prix = pr;

        this.coalition = coalition;

        this.organisme = null;
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

    public Coalition getCoalition() {
        return coalition;
    }

    public boolean appartientCoalition() {
        return coalition != null;
    }
}
