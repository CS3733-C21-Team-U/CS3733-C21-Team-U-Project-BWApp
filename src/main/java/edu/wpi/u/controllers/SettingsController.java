package edu.wpi.u.controllers;

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
        requestLocation.setText(getFileLocation());
    }
    //This is just a helper function so there isn't reused code!
    public String getFileLocation(){
        FileChooser csvWindow = new FileChooser();
        String currentPath = Paths.get(".\\src\\main\\resources\\edu\\wpi\\u").toAbsolutePath().normalize().toString();
        csvWindow.setInitialDirectory(new File(currentPath));
        csvWindow.getExtensionFilters().add
                (0, new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        String csvFile = csvWindow.showOpenDialog(null).getAbsolutePath();
        return csvFile;
    }




}
