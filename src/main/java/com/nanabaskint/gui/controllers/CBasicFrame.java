package com.nanabaskint.gui.controllers;

import com.nanabaskint.core.Game;
import com.nanabaskint.core.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CBasicFrame {

    private final String FSCORE = "FScore : ";
    private Game game;
    @FXML
    private VBox mainBox;

    @FXML
    private TextArea frameTitle;

    @FXML
    private TextField input0;
    @FXML
    private TextField input1;
    @FXML
    private TextField input2;

    @FXML
    private TextArea frameScore;


    @FXML
    private void input0Entered() {
        processOnActionGUIinput(input0);
    }

    @FXML
    private void input1Entered() {
        processOnActionGUIinput(input1);
    }

    @FXML
    private void input2Entered() {
        processOnActionGUIinput(input2);
    }

    /**
     * Set the new Game for this controller.
     *
     * @param _game the new Game
     */
    void setGame(Game _game) {
        game = _game;
    }

    /**
     * Activate a given TextField
     *
     * @param indexInput an integer representing the index of an input (0 for input0, 1 for input1 .... )
     */
    public void activateInput(int indexInput) {
        switch (indexInput) {
            case 0:
                input0.setEditable(true);
                input0.setStyle(null);
                break;
            case 1:
                input1.setEditable(true);
                input1.setStyle(null);
                break;
            case 2:
                input2.setEditable(true);
                input2.setStyle(null);
                break;
            default:
                break;
        }
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
     * performe the switch editable property and forward the input to the game.
     *
     * @param input input textFiela
     */
    private void processOnActionGUIinput(TextField input) {
        String inputAsString = input.getText();
        if (!inputAsString.equals("") && input.isEditable()) {
            testInputBeforeProcess(input, inputAsString);
        }
    }

    /**
     * Test the input (have to be integer between 0 ans 15) and convert it into a string if test passed.
     *
     * @param input         the input
     * @param inputAsString a string representing the input.
     */
    private void testInputBeforeProcess(TextField input, String inputAsString) {
        try {
            int inputAsInteger = Utils.checkAndConvert(inputAsString);
            switchOffCurrentInput(input);
            game.lancer(inputAsInteger);
        } catch (Exception e) {
            game.alert(e.getMessage());
        }
    }

    /**
     * Switch off the editable property of the current TextField and switch on for the next
     *
     * @param current to switch off
     */
    private void switchOffCurrentInput(TextField current) {
        if (current.isEditable()) {
            current.setEditable(false);
            current.setStyle(CBoard.DISABLED_COLOR);
        }
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
        input0.setText("");
        input1.setText("");
        input2.setText("");
        setScore("0");
    }

    /**
     * Customise the view according to Spare rule
     *
     * @param indexInput index of the input showing a spare
     */
    public void isASpare(int indexInput) {
        disableAllInput();
        switch (indexInput) {
            case 1:
                input1.setText("/");
                break;
            case 2:
                input2.setText("/");
                break;
            default:
                break;
        }
    }

    /**
     * Customise the view according to Strike rule
     */
    public void isAStrike() {
        disableAllInput();
        input0.setText("X");
    }

    /**
     * Disable all inputs
     */
    public void disableAllInput() {
        input0.setEditable(false);
        input0.setStyle(CBoard.DISABLED_COLOR);
        input1.setEditable(false);
        input1.setStyle(CBoard.DISABLED_COLOR);
        input2.setEditable(false);
        input2.setStyle(CBoard.DISABLED_COLOR);
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
        frameTitle.setText("    com.nanabaskint.core.Frame : " + frameNumber);
    }

}
