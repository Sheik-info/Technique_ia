package agents;

import tools.Offre;

import java.util.Random;

public abstract class Agent {

    protected int id;
    //protected StrategieNegociation strategie;

    public Agent() {
        this.id = new Random().nextInt();
    }

    public int getId() {
        return id;
    }

    public abstract void recevoirOffre(Offre m);

}