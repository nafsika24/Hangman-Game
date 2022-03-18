package com.example.hangman_gui;

import javafx.scene.control.Alert;

public class InvalidInputException extends Exception {
    public InvalidInputException(String e) {
        super(e);
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);
        a1.setHeaderText("Please Insert a valid input!");
        a1.showAndWait();
    }
}
