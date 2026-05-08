package agents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Collections;

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

        // mélange pour éviter le biais des ids
        Collections.shuffle(interesse);

        while (!interesse.isEmpty() && billet.getNbPersonnesMax() > 0) {

            int prixCourant = billet.getPrix();

            Personne meilleur = null;
            int meilleureOffre = prixCourant;

            // vraie phase d'enchère
            for (Personne p : interesse) {

                // la personne propose une surenchère
                int offre = p.surencherir(prixCourant);

                // refuse d'acheter
                if (offre == -1) {
                    continue;
                }

                // meilleure offre trouvée
                if (offre > meilleureOffre && offre <= p.getBudget()) {
                    meilleureOffre = offre;
                    meilleur = p;
                }
            }

            // personne ne veut acheter
            if (meilleur == null) {
                System.out.println("Personne ne veut acheter le billet");
                break;
            }

            // vente finale
            int partOrganisme = (int)(meilleureOffre * 0.40);
            int partFournisseur = meilleureOffre - partOrganisme;

            meilleur.offerAccepted(meilleureOffre);

            billet.getOrganisme().rente(partOrganisme);
            budget += partFournisseur;

            billet.placeReservee();

            System.out.println(
                    "Billet vendu à "
                            + meilleur.getId()
                            + " pour "
                            + meilleureOffre
            );

            // on retire le gagnant pour éviter
            // qu'il achète toutes les places
            interesse.remove(meilleur);
        }

        // billet entièrement vendu
        if (billet.getNbPersonnesMax() == 0) {
            billets.removeFirst();
        }
    }


    public boolean aPasDeBillets() {

        return billets == null || billets.isEmpty();
    }

    public Billet getBilletCourant() {
        if (aPasDeBillets()) return null;
        return billets.getFirst();
    }

    public int getBudget() {
        return budget;
    }
}
