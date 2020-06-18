package com.nanabaskint.gui.controllers;


import com.nanabaskint.core.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;


public class CBoard {

    public static final String DISABLED_COLOR = "-fx-background-color: #bfbfbf;";
    private final int OFFSET = 103;
    private final String SCORE = "   Score : ";
    @FXML
    Pane boardPane;
    private Game game;
    private int currentLayoutX = 0;
    private ArrayList<CBasicFrame> cBasicFrames;
    private CFinalFrame finalController;
    @FXML
    private TextArea totalScore;

    public void setGame(Game _game) {
        game = _game;
        cBasicFrames = new ArrayList<>(game.getNumberOfFrames());
    }

    /**
     * Add a frame to the board
     *
     * @param isFinal true if it's the final frame of the game
     *                (be carefull to be sure to not add another frame after a final oen);
     */
    private void addFrame(boolean isFinal) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                isFinal ? "/views/VFinalFrame.fxml" : "/views/VBasicFrame.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isFinal) {
            finalController = fxmlLoader.getController();
            finalController.setTitle(cBasicFrames.size() + 1);
            finalController.setLayoutX(currentLayoutX);
            currentLayoutX += OFFSET;
            finalController.setGame(game);
            boardPane.getChildren().add(root);

        } else {
            CBasicFrame controller = fxmlLoader.getController();
            cBasicFrames.add(controller);
            controller.setTitle(cBasicFrames.indexOf(controller) + 1);
            controller.setLayoutX(currentLayoutX);
            currentLayoutX += OFFSET;
            controller.setGame(game);
            boardPane.getChildren().add(root);
        }

    }

    /**
     * Build the frame. (4basic + 1 final)
     */
    public void buildBoard() {
        addFrame(false);
        addFrame(false);
        addFrame(false);
        addFrame(false);
        addFrame(true);
        init();
    }

    /**
     * init the GUI
     */
    private void init() {
        for (CBasicFrame controller : cBasicFrames) {
            controller.init();
        }
        finalController.init();
        game.init();
    }

    /**
     * Launch a new game.
     */
    @FXML
    public void newGame() {
        init();
    }

    /**
     * quit the app
     */
    @FXML
    public void quitApp() {
        Platform.exit();
    }

    /**
     * Return the requested BasicFrame controller
     *
     * @param idOfFrame the id of the requested controller
     * @return the requester controller if present into the stack, null otherwise.
     */
    public CBasicFrame getCBasicFrame(int idOfFrame) {
        return (-1 < idOfFrame && idOfFrame < cBasicFrames.size()) ? cBasicFrames.get(idOfFrame) : null;

    }

    /**
     * return the FinalFrame controller
     *
     * @return the FinalFrame controller for this game.
     */
    public CFinalFrame getCFinalFrame() {
        return finalController;
    }

    /**
     * Update the global score of the game.
     *
     * @param score the delta to add to the global score
     */
    public void setScore(int score) {
        totalScore.setText(SCORE + score);
    }

    /**
     * Display an alerte for the given message
     *
     * @param message the alert message.
     */
    public void alerte(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Input Problem founded");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
