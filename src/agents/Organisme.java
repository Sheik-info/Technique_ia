package agents;

import tools.Coalition;
import tools.Offre;
import tools.Strategie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Organisme extends Acheteur{

    private Coalition alliance;
    public static int nextCoalitionId = 0;

    public Organisme(int id, int budget) {
        super(id,budget);

        this.alliance = null;

        Random random = new Random();
        this.budget = 8000 + random.nextInt(4000);

        this.strat = new Strategie();
    }

    @Override
    public void recevoirOffre(Offre m) {
    }


    @Override
    public Offre proposerOffre(Fournisseur exp, int price, List<Organisme> orga) {
        if(price <= budget) {
            return new Offre(this, price);
        }
        System.out.println("je suis passé par là");
        if(!getCoalition()) {
            //alliance.
        }
        return null;
    }

    public void rente(int amount) {
        if(!getCoalition()) {
            budget += amount;
        }
        else {
            alliance.renteCoalition(amount);
        }
    }

    public void offreAcceptee(int offre) {
        if(!getCoalition()) {
            budget -= offre;
        }
        else {
            payerEnsemble(offre);
        }
    }

    public Coalition getCoalitionObj() {
        return alliance;
    }

    public void setCoalition(Coalition coalition) {
        this.alliance = coalition;
    }

    public boolean getCoalition() {
        return alliance != null;
    }

    public void proposerCoalition(Organisme dest) {

        if(dest.getBudget() >= 12000 || this.budget >= 12000) {
            return;
        }

        // déjà ensemble
        if(this.alliance != null && this.alliance == dest.alliance) {
            return;
        }

        Coalition coalition;

        if(this.alliance == null && !dest.getCoalition()) {

            coalition = new Coalition(++nextCoalitionId);

            coalition.ajouterMembre(this);
            coalition.ajouterMembre(dest);
        }
        else if(this.alliance != null && !dest.getCoalition()) {

            if(this.alliance.getMembres().size() < 3) {
                this.alliance.ajouterMembre(dest);
            }
        }
        else if(this.alliance == null && dest.getCoalition()) {

            if(dest.alliance.getMembres().size() < 3) {
                dest.alliance.ajouterMembre(this);
            }
        }
    }

    public static int valeurCoalition(List<Organisme> coalition) {
        int somme = 0;
        for(Organisme o : coalition) {
            somme += o.getBudget();
        }
        return somme;
    }

    public int surencherir(int prixActuel) {

        int decision;
        if(!getCoalition()) {
            decision = strat.decision(prixActuel, budget);
        }
        else {
            decision = strat.decision(prixActuel, budgetCoalition());
        }

        // abandon
        if (decision == 0) {
            return -1;
        }

        int offre;

        int budgetReference =
                getCoalition() ? budgetCoalition() : budget;

        int vd = strat.valeurDepart(budgetReference, prixActuel);

        if (vd == -1) {
            return -1;
        }

        // petite stratégie prudente
        if (decision == 1) {

            offre = strat.valeurOffre(prixActuel, vd);

            if (offre == -1) {
                return -1;
            }
        }

        // stratégie agressive
        else {

            offre = vd;

            int nouvelle;

            while ((nouvelle =
                    strat.valeurOffre(offre, vd)) != -1) {

                offre = nouvelle;
            }
        }

        if(!getCoalition()) {
            if (offre > budget) {
                return -1;
            }
        }
        else {
            if (offre > budgetCoalition()) {
                return -1;
            }
        }


        return offre;
    }

    public int budgetCoalition() {
        return alliance.budgetTotal();
    }

    public void payerEnsemble(int price) {
        alliance.payer(price);
    }

    public void getMembres() {
        for(Organisme o : alliance.getMembres()) {
            System.out.print(" "+ o.id);
        }
    }

    public void retirerBudget(int montant) {
        budget -= montant;
    }

    public void ajouterBudget(int montant) {
        budget += montant;
    }

}
