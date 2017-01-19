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
            System.out.println("DecisionModule - deliberation : desires exist: ");
            List<Desire> desires = parentAgent.getDesireModule().getDesires();//spr czy lista pusta
            List<Desire> sortedDesires = sortDesiresByPriority(desires);
            intention = getFirstDesire(sortedDesires);
            System.out.println("DecisionModule - deliberation : choosed intention: " + intention);
            plan();
    }

    public void plan(){
        System.out.println("Planning..");
        intention.scenario();
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
