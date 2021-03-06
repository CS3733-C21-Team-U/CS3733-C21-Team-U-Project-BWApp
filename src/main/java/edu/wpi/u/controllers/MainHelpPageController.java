package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainHelpPageController {


    public void handleServicesButton() throws IOException{
    }

    public void handleContactUsButton() throws IOException{
    }

    public void handleBackToMainPageButton()  throws IOException{
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }
}
