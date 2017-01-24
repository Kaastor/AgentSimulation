
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

    public static final int MAP_WIDTH = 1200;
    public static final int MAP_HEIGHT = 800;
    public static final int CELL_SIZE = 25;
    public static final int MAX_AGENTS_NUMBER = 30;

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

        Map map = new MapInitialization( MAP_WIDTH, MAP_HEIGHT, CELL_SIZE, MAX_AGENTS_NUMBER).initialize();
        EnvVisualisation envVisualisation = new EnvVisualisation(map, MAP_WIDTH, MAP_HEIGHT );
        new VisualisationTimer(envVisualisation);

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
