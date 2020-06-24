package com.nanabaskint.core;


import com.nanabaskint.gui.controllers.CBoard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Engine {

    private static final double VERSION = 2.0;
    private static final String NAME = "MasterBowling ";
    private static Engine core;
    private final Stage primStage;


    private Engine(Stage _primaryStage) {
        primStage = _primaryStage;
    }

    public static Engine getCore() {
        return core;
    }

    private static CBoard boardController;

    public static Engine run(Stage _primaryStage) throws IOException {
        core = new Engine(_primaryStage);
        return getCore();
    }


    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/VBoard.fxml"));
        try {
            Parent root = fxmlLoader.load();
            boardController = fxmlLoader.getController();
            buildBoard();
            primStage.setScene(new Scene(root));
            primStage.setTitle(NAME + VERSION);
            primStage.show();
        } catch (Exception exception) {
            System.out.println(exception); //todo log4j
        }
    }

    public static void buildBoard() {
        boardController.buildBoard();

    }


}
