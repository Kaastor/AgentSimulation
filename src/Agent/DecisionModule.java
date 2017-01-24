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
        List<Desire> desires = parentAgent.getDesireModule().getDesires();
        List<Desire> sortedDesires = sortDesiresByPriority(desires);

        for (Desire desire : desires) {
            System.out.println(parentAgent.getId() + " :Desire deliberation "+desire);
        }

        intention = getDesireWithHighestPriority(sortedDesires);
        System.out.println(parentAgent.getId() + " Chose intention: " + intention);
        plan();
    }

    private void plan(){
        System.out.println(parentAgent.getId() + " Planning intention: " + intention);
        intention.scenario();
        realTimePlanning();
    }

    void realTimePlanning(){
        intention.realTimePlanning();
    }

    @SneakyThrows
    public void executePlan(){
        parentAgent.setWalkEvent(new WalkEvent(parentAgent, parentAgent.getAgentSpeed()));
    }

    private List<Desire> sortDesiresByPriority(List<Desire> desires){
        desires.sort(Comparator.comparing((Desire::getPriority)));
        return desires;
    }

    private Desire getDesireWithHighestPriority(List<Desire> sortedDesires){
        return sortedDesires.get(0);
    }

    public void terminateIntention(){
        intention.terminate();
    }
    public boolean intentionNotTerminated(){
        return intention != null;
    }
}
