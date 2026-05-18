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
        membres.add(o);
        o.setCoalition(this);
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

        int part = montant / membres.size();

        for (Organisme o : membres) {
            o.offreAcceptee(o.getBudget() - part);
        }
    }
}