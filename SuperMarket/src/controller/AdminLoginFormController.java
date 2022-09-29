package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AdminLoginFormController {
    public JFXTextField txtAdmin;
    public JFXPasswordField txtPassword;
    public AnchorPane LoginFormContext;

    public void openSystemReportForm(ActionEvent actionEvent) throws IOException {

        URL resource = getClass().getResource("../views/SystemReportForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) LoginFormContext.getScene().getWindow();
        window.setTitle("System Report Form");

        if (txtAdmin.getText().equalsIgnoreCase("amayuru") && txtPassword.getText().equalsIgnoreCase("123")){
            window.setScene(new Scene(load));
        }else {
            new Alert(Alert.AlertType.WARNING,"User name or Password incorrect").show();
        }

    }
}
