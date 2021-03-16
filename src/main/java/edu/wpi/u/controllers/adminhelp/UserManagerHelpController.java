package edu.wpi.u.controllers.adminhelp;

import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static edu.wpi.u.users.Role.ADMIN;

public class UserManagerHelpController {
    public void handleBackToMainPageButton(ActionEvent actionEvent) throws IOException {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        if(App.userService.getActiveUser().getType() ==  ADMIN){
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/AdminMainHelpPage.fxml"));
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
        }
        else if(!(App.userService.getActiveUser().getType() ==  ADMIN)){
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainHelpPage.fxml"));
            anchor.getChildren().clear();
            anchor.getChildren().add(root);
        }
    }

}
