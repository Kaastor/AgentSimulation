package Visualisation;

import dissim.simspace.core.SimModel;
import javafx.animation.AnimationTimer;
import lombok.SneakyThrows;

import static java.lang.Thread.sleep;

public class VisualisationTimer extends AnimationTimer {

    private EnvVisualisation envVisualisation;

    public VisualisationTimer(EnvVisualisation envVisualisation){
        this.envVisualisation = envVisualisation;
        this.start();
    }

    @Override
    @SneakyThrows
    public void handle(long now) {

        envVisualisation.drawMapOnScreen();
        sleep(100);
    }
}
