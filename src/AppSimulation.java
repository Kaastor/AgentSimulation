import Environment.Map;
import Visualisation.EnvVisualisation;
import Visualisation.VisualisationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppSimulation extends Application{

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage theStage)
    {
        theStage.setTitle( "Agent Simulator" );
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Map map = new Map(1200, 800, 25);
        EnvVisualisation envVisualisation = new EnvVisualisation(map, 1200, 800 );

        Platform.runLater( () -> {
            VisualisationTimer visualisationTimer = new VisualisationTimer(envVisualisation);
        });

        root.getChildren().add( envVisualisation );
        theStage.show();
    }
}
