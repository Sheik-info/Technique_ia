package tools;

import agents.Organisme;

import java.util.ArrayList;
import java.util.List;

public class Coalition {

    private int id;
    private List<Organisme> membres;

    public Coalition(int id) {
        this.id = id;
        this.membres = new ArrayList<>();
    }

    public void ajouterMembre(Organisme o) {
        if(!membres.contains(o)) {
            membres.add(o);
            o.setCoalition(this);
        }
    }

    public int getId() {
        return id;
    }

    public List<Organisme> getMembres() {
        return membres;
    }

    public int budgetTotal() {
        int somme = 0;

        for (Organisme o : membres) {
            somme += o.getBudget();
        }

        return somme;
    }

    public void payer(int montant) {

        int budgetTotal = budgetTotal();

        int sommePrelevee = 0;

        for(int i = 0; i < membres.size(); ++i) {

            Organisme o = membres.get(i);

            int part;

            // dernier membre -> corrige les erreurs d'arrondi
            if(i == membres.size() - 1) {

                part = montant - sommePrelevee;
            }
            else {

                double ratio =
                        (double) o.getBudget() / budgetTotal;

                part = (int) (montant * ratio);

                sommePrelevee += part;
            }

            o.retirerBudget(part);
        }
    }

    public void renteCoalition(int montant) {

        int budgetTotal = budgetTotal();

        int sommeDistribuee = 0;

        for(int i = 0; i < membres.size(); ++i) {

            Organisme o = membres.get(i);

            int part;

            // dernier membre -> correction arrondis
            if(i == membres.size() - 1) {

                part = montant - sommeDistribuee;
            }
            else {

                double ratio =
                        (double) o.getBudget() / budgetTotal;

                part = (int) (montant * ratio);

                sommeDistribuee += part;
            }

            o.ajouterBudget(part);
        }
    }

    public static List<List<List<Organisme>>> partition(List<Organisme> organismes) {

        List<List<List<Organisme>>> result = new ArrayList<>();

        backtrack(organismes, 0, new ArrayList<>(), result);

        return result;
    }

    private static void backtrack(
            List<Organisme> organismes,
            int index,
            List<List<Organisme>> current,
            List<List<List<Organisme>>> result) {

        if(index == organismes.size()) {

            List<List<Organisme>> partition = new ArrayList<>();

            for(List<Organisme> coalition : current) {
                partition.add(new ArrayList<>(coalition));
            }

            result.add(partition);
            return;
        }

        Organisme orga = organismes.get(index);

        // ajouter à une coalition existante
        for(int i = 0; i < current.size(); ++i) {

            List<Organisme> coalition = current.get(i);

            coalition.add(orga);

            backtrack(organismes, index + 1, current, result);

            coalition.remove(coalition.size() - 1);
        }

        // nouvelle coalition
        List<Organisme> nouvelleCoalition = new ArrayList<>();
        nouvelleCoalition.add(orga);

        current.add(nouvelleCoalition);

        backtrack(organismes, index + 1, current, result);

        current.remove(current.size() - 1);
    }

}