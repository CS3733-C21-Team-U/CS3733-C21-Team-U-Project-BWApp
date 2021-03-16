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
        Timestamp t = new Timestamp(System.currentTimeMillis());
        App.isLoggedIn.set(true);
        if (App.useCache.get()){
            loadingNewMainPage();
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(500);
                    Platform.runLater(() -> {
                        // todo : fix guest not being set to guest
                        App.userService.addGuest(nameGuestTextField.getText(), t, visitReasonTextField.getText(), false);
                        App.userService.setGuest(nameGuestTextField.getText());
                        App.userService.getActiveUser().setType(Role.GUEST);
                        App.isLoggedIn.set(true);
                        App.tabPaneRoot.getSelectionModel().selectFirst();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        App.getPrimaryStage().getScene().setRoot(root);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Error("Unexpected interruption");
                }
            });
            thread.start();
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        }


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




