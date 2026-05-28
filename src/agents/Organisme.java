package agents;

import tools.Coalition;
import tools.Offre;
import tools.Strategie;

import java.util.List;
import java.util.Random;


public class Organisme extends Acheteur {

    private Coalition alliance;
    public static int nextCoalitionId = 0;

    public Organisme(int id, int budget) {
        super(id, budget);

        this.alliance = null;

        Random random = new Random();
        this.budget = 8000 + random.nextInt(4000);

        this.strat = new Strategie();
    }

    @Override
    public void recevoirOffre(Offre m) {
    }

    public void rente(int amount) {
        if (!getCoalition()) {
            budget += amount;
        } else {
            alliance.renteCoalition(amount);
        }
    }

    public void offreAcceptee(int offre) {
        if (!getCoalition()) {
            budget -= offre;
        } else {
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

    public static List<List<Organisme>> proposerCoalition(List<Organisme> dest) {
        List<List<List<Organisme>>> configs = Coalition.partition(dest);
        int indiceMaxCoalition = 0;
        int totCoalLocal;
        int maxCoalition = 0;
        int i = 0;
        for(List<List<Organisme>> o : configs) {
                totCoalLocal = Organisme.valeurCoalition(o);
                maxCoalition = Integer.max(maxCoalition,totCoalLocal);
                if(maxCoalition == totCoalLocal) {
                    indiceMaxCoalition = i;
                }
                ++i;
        }

        System.out.println(configs.get(indiceMaxCoalition));

        return configs.get(indiceMaxCoalition);
    }

    public static void formerCoalition(List<Organisme> dest) {

        // reset anciennes coalitions
        for(Organisme o : dest) {
            o.alliance = null;
        }

        List<List<Organisme>> maxCoalition =
                proposerCoalition(dest);

        for(List<Organisme> groupe : maxCoalition) {

            // organisme seul
            if(groupe.size() == 1) {
                continue;
            }

            Coalition coalition =
                    new Coalition(++nextCoalitionId);

            for(Organisme o : groupe) {
                coalition.ajouterMembre(o);
            }
        }
    }

    public static int valeurCoalition(
            List<List<Organisme>> structure) {

        int valeurTotale = 0;

        for(List<Organisme> coalition : structure) {

            int budgetTotal = 0;

            int budgetMax = 0;

            for(Organisme o : coalition) {

                budgetTotal += o.getBudget();

                if(o.getBudget() > budgetMax) {
                    budgetMax = o.getBudget();
                }
            }

            int taille = coalition.size();

            // puissance économique
            int valeur = budgetTotal / 1000;

            // bonus coopération
            valeur += taille * 12;

            // organisme trop riche
            if(budgetMax > 15000) {
                valeur -= 20;
            }

            // coalition trop grande
            if(taille >= 4) {
                valeur -= 15;
            }

            // bornes
            if(valeur < 0) {
                valeur = 0;
            }

            if(valeur > 100) {
                valeur = 100;
            }

            valeurTotale += valeur;
        }

        return valeurTotale;
    }

    public static int valeurCoalitionInt(List<Organisme> coalition) {
        int somme = 0;
        for(Organisme o : coalition) {
            somme += o.getBudget();
        }
        return somme;
    }

    public int surencherir(int prixActuel) {

        int decision;
        if (!getCoalition()) {
            decision = strat.decision(prixActuel, budget);
        } else {
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

        if (!getCoalition()) {
            if (offre > budget) {
                return -1;
            }
        } else {
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
        for (Organisme o : alliance.getMembres()) {
            System.out.print(" " + o.id);
        }
    }

    public void retirerBudget(int montant) {
        budget -= montant;
    }

    public void ajouterBudget(int montant) {
        budget += montant;
    }

    @Override
    public String toString() {
        return "Org" + id + " : " + budget;
    }

}
