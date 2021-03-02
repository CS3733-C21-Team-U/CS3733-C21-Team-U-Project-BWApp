package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExitDialogController {
    @FXML
    public JFXButton exit;
    @FXML
    public StackPane stackpane;

    @FXML
    public void loadDialog(ActionEvent actionEvent) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Exit Application"));
        content.setBody(new Text("You are about to exit the application!"));
        JFXDialog dialog = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("OK");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                dialog.close();
                Stage stage = (Stage) exit.getScene().getWindow();
                stage.close();
            }
        });
        content.setActions(button);
        dialog.show();
    }
}
