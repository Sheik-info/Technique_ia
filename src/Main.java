import agents.Fournisseur;
import agents.Organisme;
import agents.Personne;

import java.util.*;

public class Main {

    public static void main() {

        // idée il faudrait plutot que les coalitions
        //mettent tout leur budget qu'ils mettent chacun
        // ce qu'ils sont pret à mettre dans la coalition
        //en fonction des enchères actuelles

        //aussi un agent qui mets trop peu dans la coallition
        //fait casser la coalition ce qui peut être idéal
        List<Personne> personnes = new ArrayList<>();
        List<Organisme> organismes = new ArrayList<>();
        List<Fournisseur> fournisseurs = new ArrayList<>();

        // Initialisation des agents
        for (int i = 0; i < 50; ++i) {
            personnes.add(new Personne(i, 500));

            if (i % 10 == 0) {
                Organisme org = new Organisme(i / 10, 5000);
                Fournisseur four = new Fournisseur(i / 10);

                organismes.add(org);
                fournisseurs.add(four);
            }
        }

        // Simulation (10 tours par exemple)
        for (int tour = 0; tour < 10; tour++) {

            // PHASE 1 : enchères organismes
            for (Fournisseur f : fournisseurs) {
                f.enchereOrganismesCoalition(organismes);
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
        for(int i=0; i<=4; ++i) {
            System.out.println("gagnant personne n°"
                    +personnes.get(i).getId() +": " + personnes.get(i).getNbBillets() + " billets");

            System.out.println("gagnant fournisseur n°"
                    +fournisseurs.get(i).getId() +": " + fournisseurs.get(i).getBudget());
        }
        Set<Integer> coalitionsAffichees =
                new HashSet<>();

        int nbAffiches = 0;

        for(Organisme org : organismes) {

            if(nbAffiches >= 4) {
                break;
            }

            // coalition
            if(org.getCoalition()) {

                int idCoalition =
                        org.getCoalitionObj().getId();

                // déjà affichée
                if(coalitionsAffichees.contains(idCoalition)) {
                    continue;
                }

                coalitionsAffichees.add(idCoalition);

                System.out.print(
                        "gagnant organisme coalition "
                );

                org.getMembres();

                System.out.println(
                        " : "
                                + org.budgetCoalition()
                                + " €"
                );
            }

            // organisme seul
            else {

                System.out.println(
                        "gagnant organisme n°"
                                + org.getId()
                                + ": "
                                + org.getBudget()
                );
            }

            nbAffiches++;
        }
    }
}