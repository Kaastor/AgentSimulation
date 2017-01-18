package Agent;

import AgentDesires.Desire;
import AgentEvents.WalkEvent;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.List;

@Data
@ToString(exclude = "parentAgent")
public class DecisionModule {

    private Agent parentAgent;
    private Desire intention;

    public DecisionModule(Agent parentAgent){
        this.parentAgent = parentAgent;
        this.intention = null;
    }

    public void deliberate(){
        List<Desire> desires = parentAgent.getDesireModule().getDesires();
        List<Desire> sortedDesires = sortDesiresByPriority(desires);
        if(sortedDesires != null){
            intention = sortedDesires.get(0);
            parentAgent.getDesireModule().getDesires().remove(intention);
        }
        else{
            //TODO lista pusta -> new desire - opusc sklep (w opusc sklep jesli koa = 0 to wychodzi tymi co wszedl,
            //dla koa= 1 szuka najblizszego?
        }
    }

    public void plan(){
        intention.scenario();
    }

    public void realTimePlanning(){
        intention.getPlan().createPath();
        executePlan();
    }

    @SneakyThrows
    public void executePlan(){
        parentAgent.setWalkEvent(new WalkEvent(parentAgent));
    }

    private List<Desire> sortDesiresByPriority(List<Desire> desires){
        List<Desire> sortedDesires = null;
        //TODO sortowanie
        return sortedDesires;
    }
}
