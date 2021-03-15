package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.users.Role;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Timestamp;

public class GuestSignInScreenController {


    public JFXTextField nameGuestTextField;
    public JFXTextArea visitReasonTextField;

    private FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));

    public void initialize() throws IOException {
        fxmlLoader.setClassLoader(App.classLoader);
        fxmlLoader.load();
    }
    public void handleSignInButton(ActionEvent actionEvent) throws IOException {
        //TODO: set active user to guest
        Timestamp t = new Timestamp(System.currentTimeMillis());
        App.userService.addGuest(nameGuestTextField.getText(), t, visitReasonTextField.getText(), false);
//        System.out.println("Name to be added " + nameGuestTextField.getText());
        App.userService.setGuest(nameGuestTextField.getText());
        App.isLoggedIn.set(true);
        loadingNewMainPage();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(500);
                Platform.runLater(() -> {
                    App.userService.setEmployee("debug");
                    App.userService.getActiveUser().setType(Role.ADMIN);
                    App.isLoggedIn.set(true);
                    App.tabPaneRoot.getSelectionModel().selectFirst();
                    App.getPrimaryStage().getScene().setRoot(App.base);
                });
            } catch (Exception e) {
                e.printStackTrace();
                throw new Error("Unexpected interruption");
            }
        });
        thread.start();
        //Parent root = App.base;//FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        //App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
    private void loadingNewMainPage() {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Logging you in...");
        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(App.loadingSpinnerHerePane, content, JFXDialog.DialogTransition.RIGHT);
        dialog.show();
    }
}




