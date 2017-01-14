import Environment.GraphMap;
import Environment.Map;
import Visualisation.MapInitialization;
import Visualisation.EnvVisualisation;
import Visualisation.VisualisationTimer;
import dissim.simspace.core.SimModel;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

@Data
@EqualsAndHashCode(callSuper = false)
public class AppSimulation extends Application{

    @SneakyThrows
    public static void main(String[] args)
    {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");
        launch(args);
    }

    @SneakyThrows
    public void start(Stage theStage)
    {
        theStage.setTitle( "Agent Simulator" );
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Map map = new MapInitialization( 1200, 800, 25, 10).initialize();
        EnvVisualisation envVisualisation = new EnvVisualisation(map, 1200, 800 );
        VisualisationTimer visualisationTimer = new VisualisationTimer(envVisualisation);

        Task task = new Task<Void>() {
            @SneakyThrows
            @Override public Void call() {

                SimModel.getInstance().ASTRONOMICALSimulation();
                SimModel.getInstance().startSimulation();
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();


        root.getChildren().add( envVisualisation );
        theStage.show();


    }
}
