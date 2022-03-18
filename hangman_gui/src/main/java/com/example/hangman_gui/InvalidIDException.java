
package com.example.hangman_gui;
import javafx.scene.control.Alert;

public class InvalidIDException extends Exception {
    public InvalidIDException(String e) {
        super(e);
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);
        a1.setHeaderText("Ivalid ID");
        a1.showAndWait();
    }
}