package agents;

import tools.Offre;
import tools.Strategie;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Personne extends Acheteur {

    private int nbBillets;

    public Personne(int id, int budget) {
        super(id, budget+ThreadLocalRandom.current().nextInt(0, budget+1));
        this.strat = new Strategie();
    }

    @Override
    public void recevoirOffre(Offre m) {
    }

    public void salaire() {
        Random rand = new Random();
        budget += 1000 + rand.nextInt(14000);
    }

    public void offerAccepted(int prixFinal) {
        budget -= prixFinal;
        nbBillets++;
    }

    public int surencherir(int prixActuel) {

        int decision = strat.decision(prixActuel, budget);

        if (decision == 0) return -1;

        int increment;

        if (decision == 1) {
            increment = 10;
        } else {
            increment = budget / 15;
        }

        int nouvelleOffre = prixActuel + increment;

        if (nouvelleOffre > budget) return -1;

        return nouvelleOffre;
    }

    public int getNbBillets() {
        return nbBillets;
    }
}
