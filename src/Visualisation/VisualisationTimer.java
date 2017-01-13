package Visualisation;

import javafx.animation.AnimationTimer;


public class VisualisationTimer extends AnimationTimer {

    private final EnvVisualisation envVisualisation;

    public VisualisationTimer(EnvVisualisation envVisualisation){
        this.envVisualisation = envVisualisation;
        this.start();
    }

    @Override
    public void handle(long now) {
        envVisualisation.clearMapOnScreen();
        envVisualisation.drawMapOnScreen();
    }
}
