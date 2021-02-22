package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class SettingsController {

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

}
