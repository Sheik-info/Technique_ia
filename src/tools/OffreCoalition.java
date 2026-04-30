package tools;
import agents.Organisme;
import java.util.List;

public class OffreCoalition extends Offre{

    List<Organisme> organismes;
    public OffreCoalition() {
        super();
    }
    public OffreCoalition(Organisme org,List<Organisme> o) {
        this.expediteur = org;
        this.organismes = o;
    }
    public void considererOffres() {
        List<Organisme> temp = new java.util.ArrayList<>();
        Organisme.idCoalition++;
        for(Organisme o : organismes) {
            if(o.getBudget() < 2000 && !o.getCoalition()) {
                temp.add(o);
                o.setIdCoalition(Organisme.idCoalition);
                o.setCoalition(true);
                if(temp.size()==2)
                    break;
            }
            else if(o.getBudget() > 2000 && !o.getCoalition())
            {
                o.setIdCoalition(Organisme.idCoalition);
                o.setCoalition(true);
            }
        }
    }
}
