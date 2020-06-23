package com.nanabaskint.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CFinalFrame extends CFrame {
    @FXML
    private TextField input3;
    @FXML
    private TextField input4;

    @FXML
    private TextArea frameScore;

    public CFinalFrame() {
        super(5);
    }


    @FXML
    private void input3Entered() {
        processOnActionGUIinput(input3);
    }

    @FXML
    private void input4Entered() {
        processOnActionGUIinput(input4);
    }

    /**
     * Activate a given TextField
     *
     * @param indexInput an integer representing the index of an input (0 for input0, 1 for input1 .... )
     */
    public void activateInput(int indexInput) {
        if (indexInput > 3) {
            super.activateInput(indexInput);
        } else {
            switch (indexInput) {
                case 3:
                    input3.setEditable(true);
                    input3.setStyle(null);
                    break;
                case 4:
                    input4.setEditable(true);
                    input4.setStyle(null);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Initialise the frame
     */
    void init() {
        super.init();
        disableAllInput();
        input3.setText("");
        input4.setText("");
        setScore("0");
    }

    /**
     * Customise the view according to Spare rule
     *
     * @param indexInput index of the requested input
     */
    public void isASpare(int indexInput) {
        disableAllInput();
        switch (indexInput) {
            case 3:
                input3.setText("/");
                break;
            case 4:
                input4.setText("/");
                break;
            default:
                break;
        }
    }

    /**
     * Customise the view according to Strike rule
     */
    public void isAStrike(int indexInput) {
        if (indexInput < 3) {
            super.isAStrike(indexInput);
        } else {
            disableAllInput();
            switch (indexInput) {
                case 3:
                    input3.setText("X");
                    break;
                case 4:
                    input4.setText("X");
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * Disable all inputs
     */
    public void disableAllInput() {
        super.disableAllInput();
        input3.setEditable(false);
        input3.setStyle(CBoard.DISABLED_COLOR);
        input4.setEditable(false);
        input4.setStyle(CBoard.DISABLED_COLOR);
    }
}
