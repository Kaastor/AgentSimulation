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

    DecisionModule(Agent parentAgent){
        this.parentAgent = parentAgent;
        this.intention = null;
    }

    public void deliberate() {
        //System.out.println("DecisionModule - deliberation : desires exist: ");
        List<Desire> desires = parentAgent.getDesireModule().getDesires();
        List<Desire> sortedDesires = sortDesiresByPriority(desires);

        //System.out.println("DecisionModule: sortedDesires: ");
        for (Desire desire : desires) {
            System.out.println(desire);
        }

        intention = getDesireWithHighestPriority(sortedDesires);
        //System.out.println("DecisionModule - deliberation : choosed intention: " + intention);
        plan();
    }

    private void plan(){
        //System.out.println("Planning..");
        intention.scenario();
        realTimePlanning();
    }

    void realTimePlanning(){
        //System.out.println("Planning realtime..");
        intention.realTimePlanning();
        executePlan();
    }

    @SneakyThrows
    void executePlan(){
        parentAgent.setWalkEvent(new WalkEvent(parentAgent, parentAgent.getAgentSpeed()));
    }

    private List<Desire> sortDesiresByPriority(List<Desire> desires){
        desires.sort(Comparator.comparing((Desire::getPriority)));
        return desires;
    }

    private Desire getDesireWithHighestPriority(List<Desire> sortedDesires){
        return sortedDesires.get(0);
    }
}
