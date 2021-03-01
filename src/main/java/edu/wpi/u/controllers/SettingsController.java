package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Paths;


public class SettingsController {

    @FXML
    private TextField nodeLocation;

    @FXML
    private TextField edgeLocation;

    @FXML
    private TextField requestLocation;


    public void HandleOpenNodeLocation(){
        nodeLocation.setText(getFileLocation());
    }

    public void HandleOpenEdgeLocation(){
        edgeLocation.setText(getFileLocation());
    }

    public void HandleRequestLocation(){
        String path = getFileLocation();
        App.requestService.loadCSVFile(path, "REQUESTS");
        requestLocation.setText(path);
    }
    //This is just a helper function so there isn't reused code!
    public String getFileLocation(){
        FileChooser csvWindow = new FileChooser();
        String currentPath = Paths.get(".\\").toAbsolutePath().normalize().toString();
        csvWindow.setInitialDirectory(new File(currentPath));
        csvWindow.getExtensionFilters().add
                (0, new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        String csvFile = csvWindow.showOpenDialog(null).getAbsolutePath();
        return csvFile;
    }


    public void handleRegTheme(ActionEvent actionEvent) {
        App.getPrimaryStage().getScene().getStylesheets().removeAll();
        App.getPrimaryStage().getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/RegularTheme.css").toExternalForm());
    }

    public void handleAltTheme(ActionEvent actionEvent) {
        System.out.println("I clicked the theme!!!!");
        App.getPrimaryStage().getScene().getStylesheets().removeAll();
        App.getPrimaryStage().getScene().getStylesheets().add(getClass().getResource("/edu/wpi/u/views/css/LightTheme.css").toExternalForm());
    }
}
