package com.example.hangman_gui;

import javafx.scene.control.Alert;

public class InvalidCharacter extends Exception {
    public InvalidCharacter(String e) {
        super(e);
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);
        a1.setHeaderText("Please Insert a valid input!");
        a1.showAndWait();
    }
}
