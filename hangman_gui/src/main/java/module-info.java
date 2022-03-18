module com.example.hangman_gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.json;

    opens com.example.hangman_gui to javafx.fxml;
    exports com.example.hangman_gui;
}