package agents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tools.*;

public class Fournisseur extends Agent {

    List<Billet> billets;
    List<Offre> offres;
    private int budget = 3000;

    public Fournisseur(int idFournisseur) {
        this.id = idFournisseur;
        this.billets = new ArrayList<>();
        this.offres = new ArrayList<>();
    }

    @Override
    public void recevoirOffre(Offre m) {
        offres.add(m);
    }


    @Override
    public Offre proposerOffre(Fournisseur exp, int price) {
        //billets.add(new Billet())
        return null;
    }

    public void ajouterOffre(Billet offre) {
        billets.add(offre);
    }

    public void accepterOffre() {
        if (offres == null || offres.isEmpty()) {
            return;
        }

        Offre maximum = offres.getFirst();

        for (Offre offre : offres) {
            if (offre.etat() > maximum.etat()) {
                maximum = offre;
            }
        }
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 7);
        dt = c.getTime();
        ajouterOffre(new Billet(new Date(),dt,maximum.etat(),maximum.getExpediteur()));

        maximum.getExpediteur().offreAcceptee(maximum);

        System.out.println("une offre a ete acceptee");
        //une contribution 10% à chaque vente de son billet
        offres.clear();
    }

    public Offre etatOffres() {
        if (offres == null || offres.isEmpty()) {
            return null;
        }

        Offre maximum = offres.getFirst();

        for (Offre offre : offres) {
            if (offre.etat() > maximum.etat()) {
                maximum = offre;
            }
        }

        return maximum;
    }

    public void proposerBillet(List<Personne> interesse) {
        while(!interesse.isEmpty() && billets.getFirst().getNbPersonnesMax() != 0
                && interesse.getFirst().getPrefer() == this.id) {
            if(interesse.getFirst().decision(billets.getFirst().getPrix())==1
                    && interesse.getFirst().getBudget() > 0) {
                interesse.getFirst().offreAcceptee(billets.getFirst());
                double pr = (double) billets.getFirst().getPrix() * 0.40;

                billets.getFirst().getOrganisme().rente((int) pr);
                budget += billets.getFirst().getPrix() - (int) pr;
                billets.getFirst().placeReservee();
            }
            Personne p = interesse.getFirst();
            interesse.removeFirst();
            interesse.addLast(p);
        }
        if(billets.getFirst().getNbPersonnesMax()==0) {
            billets.removeFirst();
        }
    }

    public boolean aDesBillets() {

        return billets == null || billets.isEmpty();
    }

    public Billet getBilletCourant() {
        if (aDesBillets()) return null;
        return billets.getFirst();
    }

    public int getBudget() {
        return budget;
    }
}
