package com.nanabaskint.gui.controllers;

import com.nanabaskint.core.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public abstract class CFrame implements InputConductor {

    private final String FSCORE = "FScore : ";
    private Game game;

    @FXML
    private VBox mainBox;

    @FXML
    private TextArea frameTitle;

    @FXML
    private HBox inputsBox;

    @FXML
    private ArrayList<TextField> inputs;


    @FXML
    private TextArea frameScore;

    public CFrame(int numberOfInputs) {
        inputs = new ArrayList<>(numberOfInputs);
        for (int inputIndex = 0; inputIndex < numberOfInputs; inputIndex++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/VInput.fxml"));
            Parent textField = null;
            try {
                textField = fxmlLoader.load();
            } catch (Exception e) {
                e.printStackTrace(); //TODO lo4j
            }
            ((CInput)fxmlLoader.getController()).setInputConductor(this);
            textField.setId(String.valueOf(inputIndex));

            inputs.add(inputIndex, (TextField) textField);
        }
    }

    /**
     * Set the new Game for this controller.
     *
     * @param _game the new Game
     */
    void setGame(Game _game) {
        game = _game;
    }

    public void initInputs() {
        for (int inputIndex = 0; inputIndex < inputs.size(); inputIndex++) {
            inputsBox.getChildren().add(inputs.get(inputIndex));
        }
    }

    /**
     * Activate a given TextField
     *
     * @param indexInput an integer representing the index of an input (0 for input0, 1 for input1 .... )
     */
    public void activateInput(int indexInput) {
        inputs.get(indexInput).setEditable(true);
        inputs.get(indexInput).setStyle(null);
    }

    /**
     * Set the score of the current frame.
     *
     * @param score int the new score of the frame.
     */
    public void setScore(String score) {
        frameScore.setText(FSCORE + score);
    }

    /**
     * setter to the layoutX property.
     *
     * @param _layoutX new layoutX
     */
    void setLayoutX(double _layoutX) {
        mainBox.setLayoutX(_layoutX);
    }

    /**
     * Initialise the frame
     */
    void init() {
        disableAllInput();
        for (TextField input : inputs) {
            input.setText("");
        }
        setScore("0");
    }

    /**
     * Customise the view according to Spare rule
     *
     * @param indexInput index of the input showing a spare
     */
    public void isASpare(int indexInput) {
        disableAllInput();
        inputs.get(indexInput).setText("/");
    }


    /**
     * Customise the view according to Strike rule
     */
    public void isAStrike(int indexInput) {

        disableAllInput();
        inputs.get(indexInput).setText("X");


    }

    /**
     * Disable all inputs
     */
    public void disableAllInput() {
        for (TextField input : inputs) {
            input.setEditable(false);
            input.setStyle(CBoard.DISABLED_COLOR);
        }

    }


    public String getTitle() {
        return frameTitle.getText();
    }

    /**
     * Set the title of the current frame.
     *
     * @param frameNumber the number of the current frame in the game.
     */
    void setTitle(int frameNumber) {
        frameTitle.setText("Frame : " + frameNumber);
    }

    public void lancer(int inputAsInteger) {
        game.lancer(inputAsInteger);
    }

    public void alert(String message) {
        game.alert(message);
    }
}
