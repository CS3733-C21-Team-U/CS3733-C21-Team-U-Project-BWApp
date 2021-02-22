package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.GraphService;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class NewRequestController {

    @FXML
    public TextField titleTextField;
    @FXML
    public TextField descriptionTextField;
    @FXML
    public TextField nodeTextField;
    @FXML
    public TextField assigneeTextField;
    @FXML
    public Button submitRequestButton;


    public String getTitle(TextField titleTextField){
    return "";
    }

    public String getDescription(TextField descriptionTextFieldTextField){
    return "";
    }

    public String getLocation(TextField titleTextField){
    return"";
    }

   // public ArrayList<String> getStaff(TextField assigneeTextField){
  //  return staff;
  //  }
}
