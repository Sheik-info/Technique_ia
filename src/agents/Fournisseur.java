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

        if (billets.isEmpty()) return;

        Billet billet = billets.getFirst();

        while (!interesse.isEmpty() && billet.getNbPersonnesMax() > 0) {

            int prixCourant = billet.getPrix();
            Personne meilleur = null;
            boolean changement = true;

            //phase de surenchère
            while (changement) {
                changement = false;

                for (Personne p : interesse) {

                    if (p.getPrefer() != this.id) continue;

                    int decision = p.decision(prixCourant);

                    // refuse
                    if (decision == 0) continue;

                    // accepte sans surenchérir
                    if (decision == 1) {
                        if (meilleur == null) {
                            meilleur = p;
                        }
                    }

                    // surenchérit
                    if (decision == 2 && p.getBudget() > prixCourant) {

                        int increment = Math.max(10, prixCourant / 10);
                        int nouveauPrix = prixCourant + increment;

                        if (nouveauPrix <= p.getBudget()) {
                            prixCourant = nouveauPrix;
                            meilleur = p;
                            changement = true;
                        }
                    }
                }
            }

            //vente finale
            if (meilleur != null && meilleur.getBudget() >= prixCourant) {

                int prix = prixCourant;

                int partOrganisme = (int)(prix * 0.40);
                int partFournisseur = prix - partOrganisme;

                meilleur.offreAcceptee(billet);

                billet.getOrganisme().rente(partOrganisme);
                budget += partFournisseur;

                billet.placeReservee();

                System.out.println("Billet vendu à " + meilleur.getId() + " pour " + prix);
            }

            // rotation de la liste
            Personne p = interesse.getFirst();
            interesse.removeFirst();
            interesse.addLast(p);
        }

        if (billet.getNbPersonnesMax() == 0) {
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
