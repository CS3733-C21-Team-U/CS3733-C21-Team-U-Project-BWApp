package edu.wpi.u.controllers;
import com.jfoenix.controls.JFXDrawer;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NewEdgeController {

    @FXML public TextField EdgeIDField;
    @FXML public TextField StartingNode;
    @FXML public TextField EndingNode;
    @FXML JFXDrawer errorDrawer;
    ErrorMessageController controller;

    public void initialize() throws IOException {
        FXMLLoader errorMessageLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/ErrorMessage.fxml"));
        AnchorPane error = errorMessageLoader.load();
        controller = errorMessageLoader.getController();
        controller.errorMessage.setText("Please Input Valid Edges");
        errorDrawer.setSidePane(error);
    }

    @FXML
    public void handleEdgeSubmitButton() {
        if(EdgeIDField.getText() == null || EdgeIDField.getText().equals("") || StartingNode.getText() == null || StartingNode.getText().equals("") || EndingNode.getText() == null || EndingNode.getText().equals("")){
            errorDrawer.open();
        } else {
            try {
                App.mapService.addEdge(EdgeIDField.getText(), StartingNode.getText(), EndingNode.getText());
                App.rightDrawerRoot.set( "/edu/wpi/u/views/AdminTools.fxml");
                errorDrawer.close();
            } catch (Exception e){
                errorDrawer.open();
            }
        }
    }

    @FXML
    public void handleEdgeCancelButton() {
        App.rightDrawerRoot.set( "/edu/wpi/u/views/AdminTools.fxml");
    }
    public void handleErrorMessageClear(){
        errorDrawer.close();
    }
}
