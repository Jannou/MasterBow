package com.nanabaskint.gui.controllers;

import com.nanabaskint.core.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CInput {

    @FXML
    private TextField textField;

    private InputConductor inputConductor;

    @FXML
    private void onAction(ActionEvent actionEvent) {
        if (!textField.getText().equalsIgnoreCase("") && textField.isEditable()) {
            testInputBeforeProcess();
        }
    }


    /**
     * Test the input (have to be integer between 0 ans 15) and convert it into a string if test passed.
     */
    private void testInputBeforeProcess() {
        try {
            int inputAsInteger = Utils.checkAndConvert(textField.getText());
            switchOffCurrentInput();
            inputConductor.lancer(inputAsInteger);
        } catch (Exception e) {
            inputConductor.alert(e.getMessage());
        }
    }

    /**
     * Switch off the editable property of the current TextField and switch on for the next
     */
    private void switchOffCurrentInput() {
        if (textField.isEditable()) {
            textField.setEditable(false);
            textField.setStyle(CBoard.DISABLED_COLOR);
        }
    }

    public void setInputConductor(InputConductor inputConductor){
        this.inputConductor = inputConductor;
    }

}
