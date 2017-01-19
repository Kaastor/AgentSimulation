package Agent;

import AgentDesires.Desire;
import AgentEvents.WalkEvent;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.Comparator;
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
        if(parentAgent.getDesireModule().desiresExist()) {
            System.out.println("Decision : desires exist: ");
            List<Desire> desires = parentAgent.getDesireModule().getDesires();
            List<Desire> sortedDesires = sortDesiresByPriority(desires);
            intention = getFirstDesire(sortedDesires);

            plan();
        }
        else{
            //TODO lista pusta -> new desire - opusc sklep (w opusc sklep jesli koa = 0 to wychodzi tymi co wszedl,
            //dla koa= 1 szuka najblizszego?
        }
    }

    public void plan(){
        System.out.println("Planning..");
        intention.scenario();
        System.out.println("Decision: Intention: "+ intention);
        realTimePlanning();
    }

    public void realTimePlanning(){
        System.out.println("Planning realtime..");
        intention.getPlan().createPath();
        executePlan();
    }

    @SneakyThrows
    public void executePlan(){
        System.out.println("Exec..");
        parentAgent.setWalkEvent(new WalkEvent(parentAgent, parentAgent.getAgentSpeed()));
    }


    private List<Desire> sortDesiresByPriority(List<Desire> desires){
        desires.sort(Comparator.comparing((Desire::getPriority)));
        List<Desire>  sortedDesires = desires;
        return sortedDesires;
    }

    private Desire getFirstDesire(List<Desire> sortedDesires){
        return sortedDesires.get(0);
    }
}
