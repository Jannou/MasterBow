package com.nanabaskint.core;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Engine {
    public static final int NUMBER_OF_FRAMES_IN_THE_GAME = 5;
    private static final double VERSION = 1.0;
    private static Engine core;
    private final Stage primStage;
    private com.nanabaskint.gui.controllers.CBoard boardController;


    private Engine(Stage _primaryStage) {
        primStage = _primaryStage;
    }

    public static Engine getCore() {
        return core;
    }

    public static Engine run(Stage _primaryStage) throws IOException {
        core = new Engine(_primaryStage);
        return getCore();
    }


    public void start() throws IOException {
        {


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/nanabaskint/gui/views/VBoard.fxml"));
            if (fxmlLoader != null) {
                Parent root = fxmlLoader.load();
                boardController = fxmlLoader.getController();

                boardController.setGame(new Game(NUMBER_OF_FRAMES_IN_THE_GAME, boardController));
                boardController.buildBoard();
                primStage.setScene(new Scene(root));
                primStage.setTitle("African Bowling " + VERSION);
                primStage.show();
            }
        }


    }


}
