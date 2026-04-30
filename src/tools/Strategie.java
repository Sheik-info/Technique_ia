package tools;

public class Strategie {
    int nbOffres;

    public Strategie() {
        nbOffres = 0;
    }

    public int valeurDepart(int budget,int pr) {
        double temp;
        if (budget * 0.10 > pr) {
            temp = budget * 0.10;
            nbOffres++;
            return (int) temp;
        }
        else {
            if (budget * 0.5 > pr) {
                temp = budget * 0.50;
                nbOffres++;
                return (int) temp;
            }
        }
        return -1;
    }

    public int valeurOffre(int offreAct, int vd) {
        if(offreAct<vd+vd*0.10 && nbOffres < 6) {
            double temp = vd * 0.10;
            temp = vd + temp;
            vd = (int) temp;
            nbOffres++;
            return vd;
        }
        return -1;
    }

    public int decision(int offreActuelle, int budget) {
        if(offreActuelle > budget || nbOffres > 6) { //n'y va pas
            return 0;
        }
        else if ( 200 < budget && budget < 500) { // y va mais pas beaucoup
            return 1;
        }
        else {
            return 2;
        }
    }
}
