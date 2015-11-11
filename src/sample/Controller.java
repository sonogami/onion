package sample;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    protected TextField text;

    @FXML
    protected PasswordField pwfield;

    @FXML
    protected void login() {
        String id = text.getText();
        String pw = pwfield.getText();
    }
}
