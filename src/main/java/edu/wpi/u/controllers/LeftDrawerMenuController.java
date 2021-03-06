package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;

    public class LeftDrawerMenuController {

        @FXML public Rectangle flair1;
        @FXML public Rectangle flair2;
        @FXML public Rectangle flair3;
        @FXML public Rectangle flair4;

        @FXML public Label text1;
        @FXML public Label text2;
        @FXML public Label text3;
        @FXML public Label text4;

        @FXML public JFXToggleNode toggle1;
        @FXML public JFXToggleNode toggle2;
        @FXML public JFXToggleNode toggle3;
        @FXML public JFXToggleNode toggle4;

        @FXML
        public StackPane stackpane;

        @FXML
        public void initialize() throws IOException{
//        toPathPlanningBtn.onActionProperty()
//        toggle4.setDisable(true);
            setTextColor(App.leftMenuScreenNum);
            toggleButton(App.leftMenuScreenNum);
            setRectVisibility(App.leftMenuScreenNum);
//        setRectVisibility(-1);
//        toggle4.setDisableAnimation(true);
        }

        private void toggleButton(int screen){
            switch(screen) {
                case 1:
                    toggle1.setSelected(true); break;
                case 2:
                    toggle2.setSelected(true); break;
                case 3:
                    toggle3.setSelected(true); break;
                case 4:
                    toggle4.setSelected(true); break;
            }
        }

        private void setRectVisibility(int activeRect){
            flair1.setVisible(false);
            flair2.setVisible(false);
            flair3.setVisible(false);
            flair4.setVisible(false);
            switch (activeRect){
                case 1: flair1.setVisible(true); break;
                case 2: flair2.setVisible(true); break;
                case 3: flair3.setVisible(true); break;
                case 4: flair4.setVisible(true); break;

            }
        }

        private void setTextColor(int activeText){
            text1.setTextFill(Color.web("#8a93a0"));
            text2.setTextFill(Color.web("#8a93a0"));
            text3.setTextFill(Color.web("#8a93a0"));
            text4.setTextFill(Color.web("#8a93a0"));
            switch (activeText){
                case 1: text1.setTextFill(Color.web("#2e5bff")); break;
                case 2: text2.setTextFill(Color.web("#2e5bff")); break;
                case 3: text3.setTextFill(Color.web("#2e5bff")); break;
                case 4: text4.setTextFill(Color.web("#2e5bff")); break;

            }
        }


        public void handleChangeToPathPlanning(ActionEvent actionEvent) {
            App.rightDrawerRoot.set("/edu/wpi/u/views/Oldfxml/PathfindingRightPage.fxml");
            App.leftMenuScreenNum = 1;
            setRectVisibility(1);
            setTextColor(1);
        }

        public void handleChangeToRequests(ActionEvent actionEvent) {
            App.rightDrawerRoot.set("/edu/wpi/u/views/Oldfxml/ViewRequest.fxml");
            App.leftMenuScreenNum = 2;
            setRectVisibility(2);
            setTextColor(2);
        }

        public void handleChangeToAdmin(ActionEvent actionEvent) {
            App.rightDrawerRoot.set("/edu/wpi/u/views/Oldfxml/AdminTools.fxml");
            App.leftMenuScreenNum = 3;
            setRectVisibility(3);
            setTextColor(3);
        }

        public void handleChangeToSettings(ActionEvent actionEvent) {
            App.rightDrawerRoot.set("/edu/wpi/u/views/Oldfxml/Settings.fxml");
            App.leftMenuScreenNum = 4;
            setRectVisibility(4);
            setTextColor(4);
        }

        public void handleExit(ActionEvent actionEvent) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Exit Application"));
            content.setBody(new Text("You are about to exit the application!"));
            JFXDialog dialog = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton button = new JFXButton("OK");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();
                    App.getInstance().end();
                }
            });
            button.setStyle("-fx-background-color: #4dadf7");
            content.setActions(button);
            dialog.show();

        }
    }