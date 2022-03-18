package com.example.hangman_gui;

import javafx.scene.control.Alert;

public class NoDescriptionException extends Exception {
    public NoDescriptionException(String e) {
        super(e);
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setHeaderText("The ID you entered does not contain a description field. Please insert a valid ID.");
        a1.showAndWait();
    }
}
