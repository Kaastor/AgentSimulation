package Visualisation;

import Agent.Agent;
import Environment.GraphNode;
import javafx.animation.AnimationTimer;

public class VisualisationTimer extends AnimationTimer {

    private EnvVisualisation envVisualisation;

    public VisualisationTimer(EnvVisualisation envVisualisation){
        this.envVisualisation = envVisualisation;
        this.start();
    }

    @Override
    public void handle(long now) {
        Agent agent = envVisualisation.getMap().getAgents().get(0);
        int x = agent.getPositionOnMap().getX();
        int y = agent.getPositionOnMap().getY();
        GraphNode graphNode =  new GraphNode(++x, ++y);
        agent.setPositionOnMap(graphNode);

        envVisualisation.drawMapOnScreen();

    }
}
