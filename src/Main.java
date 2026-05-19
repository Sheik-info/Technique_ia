import agents.Fournisseur;
import agents.Organisme;
import agents.Personne;
import tools.Offre;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<Personne> personnes = new ArrayList<>();
        List<Organisme> organismes = new ArrayList<>();
        List<Fournisseur> fournisseurs = new ArrayList<>();

        // Initialisation des agents
        for (int i = 0; i < 50; ++i) {
            personnes.add(new Personne(i, 500));

            if (i % 10 == 0) {
                Organisme org = new Organisme(i / 10, 5000);
                Fournisseur four = new Fournisseur((int)i / 10);

                organismes.add(org);
                fournisseurs.add(four);
            }
        }

        Random rand = new Random();

        // Simulation (10 tours par exemple)
        for (int tour = 0; tour < 10; tour++) {

            System.out.println("TOUR " + tour + ":");

            // PHASE 1 : enchères organismes
            for (Fournisseur f : fournisseurs) {

                if (tour < 5) {
                    System.out.println("tour" + tour);
                    f.enchereOrganismes(organismes);
                }
                else {
                    f.enchereOrganismesCoalition(organismes);
                }
            }

            // PHASE 2 : Vente aux personnes
            for (Fournisseur f : fournisseurs) {

                if (f.aPasDeBillets()) continue;

                List<Personne> interesse = new ArrayList<>();

                // sélection simple : personnes avec budget suffisant
                for (Personne p : personnes) {
                    if (p.getBudget() >= f.getBilletCourant().getPrix()) {
                        interesse.add(p);
                    }
                }

                f.proposerBillet(interesse);
            }

            // Mise à jour (revenus)
            for (Personne p : personnes) {
                p.salaire();
            }

        }
        personnes.sort(Comparator.comparing(Personne::getNbBillets).reversed());
        organismes.sort(Comparator.comparing(Organisme::getBudget).reversed());
        fournisseurs.sort(Comparator.comparing(Fournisseur::getBudget).reversed());
        for(int i=0; i<=2; ++i) {
            System.out.println("gagnant personne n°"
                    +personnes.get(i).getId() +": " + personnes.get(i).getNbBillets() + " billets");

            if(organismes.get(i).getCoalition()) {
                System.out.print("gagnant organisme coalition ");
                organismes.get(i).getMembres();
                System.out.println(" : " + organismes.get(i).budgetCoalition() + " €");
            }
            else {
                System.out.println("gagnant organisme n°"
                        +organismes.get(i).getId() +": " + organismes.get(i).getBudget());
            }
            System.out.println("gagnant fournisseur n°"
                    +fournisseurs.get(i).getId() +": " + fournisseurs.get(i).getBudget());
        }
    }
}