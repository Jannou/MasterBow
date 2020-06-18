import com.nanabaskint.core.Engine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
//        System.out.println(PrettyGame.buildGame(Utils.checkAndPreProcessInput(args)));
    }

    @Override
    public synchronized void start(Stage primaryStage) throws Exception {

        primaryStage.setOnCloseRequest(e -> Platform.exit());

        Engine engin = Engine.run(primaryStage);

        engin.start();


    }
}
