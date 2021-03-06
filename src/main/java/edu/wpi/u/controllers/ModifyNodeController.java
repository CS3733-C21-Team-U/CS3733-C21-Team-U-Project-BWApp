package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXDrawer;
import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ModifyNodeController {

    @FXML public TextField modifyNodeID;
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
        modifyNodeID.setText(App.lastSelectedNode);
        XCoordinate.setText(App.nodeField1);
        YCoordinate.setText(App.nodeField2);
        FXMLLoader errorMessageLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/Oldfxml/ErrorMessage.fxml"));
        AnchorPane error = errorMessageLoader.load();
        controller = errorMessageLoader.getController();
        controller.errorMessage.setText("Please Input a Valid Node");
        errorDrawer.setSidePane(error);
    }


    @FXML
    public void handleNodeSubmitButton() {
        if(modifyNodeID.getText() == null || modifyNodeID.getText().equals("") || XCoordinate.getText() == null || XCoordinate.getText().equals("") || YCoordinate.getText() == null || YCoordinate.getText().equals("")){
            errorDrawer.open();
        } else {
            try {
                Double.parseDouble(XCoordinate.getText());
                Double.parseDouble(YCoordinate.getText());
                //App.mapService.updateNode(modifyNodeID.getText(), Double.parseDouble(XCoordinate.getText()), Double.parseDouble(YCoordinate.getText()));
                App.rightDrawerRoot.set("../views/AdminTools.fxml");
            } catch (Exception e) {
                errorDrawer.open();

            }
        }
    }

    @FXML
    public void handleNodeCancelButton() {
        App.rightDrawerRoot.set("/edu/wpi/u/views/Oldfxml/AdminTools.fxml");
    }
    public void handleErrorMessageClear(){
        errorDrawer.close();
    }

}
