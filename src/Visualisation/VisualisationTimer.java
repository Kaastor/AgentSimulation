package Visualisation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class VisualisationTimer {

    private final EnvVisualisation envVisualisation;

    public VisualisationTimer(EnvVisualisation envVisualisation){
        this.envVisualisation = envVisualisation;
        Timeline animationTimer = new Timeline(new KeyFrame(Duration.millis(16), (event)->handle()));
        animationTimer.setCycleCount(Animation.INDEFINITE);
        animationTimer.play();
    }

    private void handle() {
        envVisualisation.renderMap();
    }
}
