package edu.wpi.u.controllers;
import com.jfoenix.controls.JFXDrawer;
import edu.wpi.u.models.GraphManager;
import edu.wpi.u.models.GraphService;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NewNodeController {

    @FXML public TextField NodeIDField;
    @FXML public TextField XCoordinate;
    @FXML public TextField YCoordinate;
    @FXML public TextField FloorField;
    @FXML public TextField BuildingField;
    @FXML public TextField NodeTypeField;
    @FXML public TextField ShortNameField;
    @FXML public TextField LongNameField;
    @FXML JFXDrawer errorDrawer;
    ErrorMessageController controller;

    public void initialize() throws IOException {
        FXMLLoader errorMessageLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/ErrorMessage.fxml"));
        AnchorPane error = errorMessageLoader.load();
        controller = errorMessageLoader.getController();
        controller.errorMessage.setText("Please Input a Valid Node");
        errorDrawer.setSidePane(error);
    }

    @FXML
    public void handleNodeSubmitButton() {
        if(NodeIDField.getText() == null || NodeIDField.getText().equals("") || XCoordinate.getText() == null || XCoordinate.getText().equals("") || YCoordinate.getText() == null || YCoordinate.getText().equals("")){
                errorDrawer.open();
            } else {
                try {
                    Integer.parseInt(XCoordinate.getText());
                    Integer.parseInt(YCoordinate.getText());
                App.graphService.addNode(NodeIDField.getText(), Integer.parseInt(XCoordinate.getText()), Integer.parseInt(YCoordinate.getText()));
                App.rightDrawerRoot.set("/edu/wpi/u/views/AdminTools.fxml");
                errorDrawer.close();
            } catch (Exception e){
                errorDrawer.open();
            }
        }
    }
    @FXML
    public void handleNodeCancelButton() {
        App.rightDrawerRoot.set( "/edu/wpi/u/views/AdminTools.fxml");
    }
    public void handleErrorMessageClear(){
        errorDrawer.close();
    }
}
