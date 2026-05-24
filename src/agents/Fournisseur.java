package agents;

import java.util.*;

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

    public void ajouterOffre(Billet offre) {

        billets.add(offre);
    }

    public void enchereOrganismesCoalition(List<Organisme> organismes) {

        List<Organisme> participants =
                new ArrayList<>(organismes);

        // tri décroissant selon budget individuel ou coalition
        participants.sort(
                Comparator.comparing(
                        (Organisme o) ->
                                o.getCoalition()
                                        ? o.budgetCoalition()
                                        : o.getBudget()
                ).reversed()
        );

        // création des coalitions
        Organisme.formerCoalition(participants);

        // une coalition = un seul participant réel
        Set<Coalition> coalitionsVues = new HashSet<>();

        List<Organisme> participantsReels =
                new ArrayList<>();

        for (Organisme org : participants) {

            // organisme seul
            if (!org.getCoalition()) {

                participantsReels.add(org);
            }

            // coalition pas encore représentée
            else if (!coalitionsVues.contains(
                    org.getCoalitionObj())) {

                coalitionsVues.add(
                        org.getCoalitionObj());

                participantsReels.add(org);
            }
        }

        int prixCourant = 100;

        Organisme meilleur = null;

        boolean progression = true;

        while (progression) {

            progression = false;

            // IMPORTANT :
            // utiliser participantsReels
            for (Organisme org : participantsReels) {

                int offre =
                        org.surencherir(prixCourant);

                if (offre == -1) {
                    continue;
                }

                if (offre > prixCourant) {

                    prixCourant = offre;
                    meilleur = org;

                    progression = true;
                }
            }
        }

        if (meilleur == null) {
            return;
        }

        Date debut = new Date();

        Calendar c = Calendar.getInstance();

        c.setTime(debut);

        c.add(Calendar.DATE, 7);

        Date fin = c.getTime();

        if (meilleur.getCoalition()) {

            ajouterOffre(
                    new Billet(
                            debut,
                            fin,
                            prixCourant,
                            meilleur.getCoalitionObj()
                    )
            );
        } else {

            ajouterOffre(
                    new Billet(
                            debut,
                            fin,
                            prixCourant,
                            meilleur
                    )
            );
        }

        meilleur.offreAcceptee(prixCourant);

        budget += prixCourant;

        // affichage final
        if (meilleur.getCoalition()) {


            //il faut changer ça pour que ça tire des
            //veleurs de coalition aléatoire pour rester ou se séparer
            //conseil du prof
            //for(Organisme org : organismes) {
            //    org.setCoalition(null);
            //}
        }
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
                break;
            }

            // vente finale
            int partOrganisme = (int)(meilleureOffre * 0.40);
            int partFournisseur = meilleureOffre - partOrganisme;

            meilleur.offerAccepted(meilleureOffre);

            if(billet.appartientCoalition()) {
                billet.getCoalition()
                        .renteCoalition(partOrganisme);
            }
            else {
                billet.getOrganisme()
                        .rente(partOrganisme);
            }
            budget += partFournisseur;

            billet.placeReservee();

            //Collections.shuffle(interesse);
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

    public void enchereOrganismes(List<Organisme> organismes) {

        List<Organisme> participants =
                new ArrayList<>(organismes);

        int prixCourant = 100;

        Organisme meilleur = null;

        boolean progression = true;

        while (progression) {

            progression = false;

            for (Organisme org : participants) {

                int offre = org.surencherir(prixCourant);

                if (offre == -1) {
                    continue;
                }

                if (offre > prixCourant) {

                    prixCourant = offre;
                    meilleur = org;

                    progression = true;
                }
            }
        }

        if (meilleur == null) {
            return;
        }

        Date debut = new Date();

        Calendar c = Calendar.getInstance();

        c.setTime(debut);

        c.add(Calendar.DATE, 7);

        Date fin = c.getTime();

        ajouterOffre(
                new Billet(
                        debut,
                        fin,
                        prixCourant,
                        meilleur
                )
        );

        meilleur.offreAcceptee(prixCourant);

        budget += prixCourant;
    }
}
