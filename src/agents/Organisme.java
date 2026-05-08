package agents;

import tools.Offre;
import tools.OffreCoalition;

import java.util.List;

public class Organisme extends Acheteur{

    public static int idCoalition;
    private int coalitionId;

    public Organisme(int id, int budget) {
        super(id,budget);
        this.coalition = false;
    }

    @Override
    public void recevoirOffre(Offre m) {
    }

    @Override
    public Offre proposerOffre(Fournisseur exp, int price) {
        if(price <= budget) {
            return new Offre(this, price);
        }

        return null;
    }


    @Override
    public Offre proposerOffre(Fournisseur exp, int price, List<Organisme> orga) {
        System.out.println("Je propose une offre");
        if(price <= budget) {
            return new Offre(this, price);
        }
        return new OffreCoalition(this,orga);
    }

    protected Offre soumettreOffre(Agent fournisseur, int pr) {
        return null;
    }

    public void rente(int amount) {
        budget += amount;
    }

    public void offreAcceptee(Offre offre) {
        budget -= offre.etat();
    }
    public void setCoalition(boolean coal) {
        this.coalition = coal;
    }
    public boolean getCoalition() {
        return this.coalition;
    }

    public int getIdCoalition() {
        return this.coalitionId;
    }
    public void setIdCoalition(int coal) {
        this.coalitionId = coal;
    }
    public static int valeurCoalition(List<Organisme> coalition) {
        int somme = 0;
        for(Organisme o : coalition) {
            somme += o.getBudget();
        }
        return somme;
    }
}
