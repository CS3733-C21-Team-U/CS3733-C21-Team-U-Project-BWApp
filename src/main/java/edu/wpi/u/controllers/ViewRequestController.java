package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Paths;

public class ViewRequestController {

    @FXML
    private Text responseText;

    public void newCSV() {
        FileChooser csvWindow = new FileChooser();
        String currentPath = Paths.get(".\\src\\main\\resources\\edu\\wpi\\u").toAbsolutePath().normalize().toString();
        csvWindow.setInitialDirectory(new File(currentPath));
        csvWindow.getExtensionFilters().add
                (0, new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File csvFile = csvWindow.showOpenDialog(null);

        responseText.setText(csvFile.getName());
    }

    public void initialize() throws IOException {
        //This is how you add title panes here
    }

    @FXML
    public void handleChangeToEditRequest(){
        //Switdh to a new right drawer
        App.rightDrawerRoot.set( "../views/EditRequests.fxml");
    }

    @FXML
    public void handleNewRequestButton() {
        App.rightDrawerRoot.set( "../views/NewRequest.fxml");
    }
}
