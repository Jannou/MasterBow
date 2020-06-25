import com.nanabaskint.core.Engine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


public class MasterBowling extends Application {
    public static void main(String[] args) {
        launch(args);
//        System.out.println(PrettyGame.buildGame(Utils.checkAndPreProcessInput(args))); //OLD boy <3
    }

    @Override
    public synchronized void start(Stage primaryStage) throws Exception {

        primaryStage.setOnCloseRequest(e -> Platform.exit());

        Engine engine = Engine.run(primaryStage);

        engine.start();


    }
}
