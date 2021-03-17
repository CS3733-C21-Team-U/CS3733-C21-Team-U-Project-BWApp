package edu.wpi.u.controllers.generaluserhelp;

import edu.wpi.u.App;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LogInHelpController {
    public void handleBackToMainPageButton(ActionEvent actionEvent) throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root;
        if(App.userService.getActiveUser().getType() ==  Role.ADMIN){
            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/AdminMainHelpPage.fxml"));
        }
        else {
            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainHelpPage.fxml"));
        }
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }
}
