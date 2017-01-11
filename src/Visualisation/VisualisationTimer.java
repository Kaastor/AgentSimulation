package Visualisation;

import javafx.animation.AnimationTimer;
import lombok.SneakyThrows;


public class VisualisationTimer extends AnimationTimer {

    private EnvVisualisation envVisualisation;

    public VisualisationTimer(EnvVisualisation envVisualisation){
        this.envVisualisation = envVisualisation;
        this.start();
    }

    @Override
    @SneakyThrows
    public void handle(long now) {
        System.out.println("TIMER");
        envVisualisation.drawMapOnScreen();

    }
}
