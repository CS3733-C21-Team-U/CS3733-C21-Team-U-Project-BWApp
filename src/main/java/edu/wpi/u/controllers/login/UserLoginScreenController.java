package edu.wpi.u.controllers.login;


import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.exceptions.AccountNameNotFoundException;
import edu.wpi.u.exceptions.PasswordNotFoundException;
import edu.wpi.u.exceptions.PhoneNumberNotFoundException;
import edu.wpi.u.users.Role;
import io.netty.handler.codec.http.HttpHeaders;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import org.asynchttpclient.*;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLoginScreenController {


    // TODO: Properly rename JFX artifacts
    @FXML
    public JFXTextField userNameTextField;
    @FXML
    public JFXPasswordField passWordField;
    @FXML
    public JFXButton loginButton;
    @FXML
    public JFXButton forgotPasswordButton;
    @FXML
    public JFXProgressBar progressBar;
    @FXML
    public JFXButton submitButton;
    @FXML
    public Label errorLabel, wrongPasswordLabel;
    @FXML
    public JFXButton debugLoginAdminButton;
    @FXML
    public JFXButton debugLoginGuestButton;
    @FXML
    public JFXButton submitSkipButton;

    private FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));

    public void initialize() throws IOException {
        fxmlLoader.setClassLoader(App.classLoader);
        fxmlLoader.load();

        wrongPasswordLabel.setVisible(false);

        passWordField.focusedProperty().addListener(e -> {
            wrongPasswordLabel.setVisible(false);
        });

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        userNameTextField.getValidators().add(validator);
        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                userNameTextField.validate();
            }
        });
        RequiredFieldValidator validator4 = new RequiredFieldValidator();
        validator4.setMessage("Username Invalid");
        userNameTextField.getValidators().add(validator4);
        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                if (App.userService.checkUsername(passWordField.getText()).equals("")) {
                    userNameTextField.validate();
                }
            }
        });


        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator2.setMessage("Password Required");
        passWordField.getValidators().add(validator2);
        passWordField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passWordField.validate();
            }
        });

        RequiredFieldValidator validator5 = new RequiredFieldValidator();
        validator5.setMessage("Username Invalid");
        userNameTextField.getValidators().add(validator5);
        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                if (App.userService.checkUsername(passWordField.getText()).equals("")) {
                    userNameTextField.validate();
                }
            }

        });


        RequiredFieldValidator validator3 = new RequiredFieldValidator();
        validator3.setMessage("Token Required");
        passWordField.getValidators().add(validator3);
        passWordField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passWordField.validate();
            }
        });
    }

    public void handleLogin() throws IOException {
        progressBar.setStyle("-fx-opacity: 1");

        String username = userNameTextField.getText();
        String password = passWordField.getText();

        if (!App.userService.checkUsername(userNameTextField.getText()).equals("")) {
            if (!App.userService.checkPassword(passWordField.getText(), userNameTextField.getText()).equals("")) {
                App.userService.setUser(userNameTextField.getText(), passWordField.getText(), App.userService.checkPassword(passWordField.getText(), userNameTextField.getText()));
                App.isLoggedIn.set(true);
                handleSubmit();
            } else {
                wrongPasswordLabel.setVisible(true);
            }
        } else {
            wrongPasswordLabel.setVisible(true);
        }

        String phonenumber = App.userService.getActiveUser().getPhoneNumber();

        try {
            if (!App.userService.checkUsername(username).equals("")) {
                if (!App.userService.checkPassword(password, username).equals("")) {
                    try {
                        Pattern pattern = Pattern.compile("^\\d{10}$");
                        Matcher matcher = pattern.matcher(phonenumber);
                        if (!matcher.matches()) {
                            errorLabel.setText("Phonenumber associated with account is invalid");
                            throw new PhoneNumberNotFoundException("Phone number is not valid");
                        }
                        URI uri = new URI("https://bw-webapp.herokuapp.com/" + "login?phonenumber=" + "+1" + phonenumber + "&channel=sms");
                        URL url = uri.toURL(); // make GET request
                        AsyncHttpClient client = Dsl.asyncHttpClient();
                        Future<Integer> whenStatusCode = client.prepareGet(url.toString())
                                .execute(new AsyncHandler<Integer>() {
                                    private Integer status;

                                    @Override
                                    public State onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
                                        status = responseStatus.getStatusCode();
                                        return State.CONTINUE;
                                    }

                                    @Override
                                    public State onHeadersReceived(HttpHeaders headers) throws Exception {
                                        return State.CONTINUE;
                                    }

                                    @Override
                                    public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                                        byte[] b = bodyPart.getBodyPartBytes();
                                        String y = new String(b);
                                        System.out.println(y.contains("pending"));
                                        if (y.contains("pending")) {
                                            progressBar.setStyle("-fx-opacity: 0");
                                            submitButton.setStyle("-fx-opacity: 1");
                                            // TODO : Set alignment
                                        }
                                        return State.CONTINUE;
                                    }

                                    @Override
                                    public Integer onCompleted() throws Exception {
                                        return status;
                                    }

                                    @Override
                                    public void onThrowable(Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new PasswordNotFoundException();
                }
            } else {
                throw new AccountNameNotFoundException();
            }
        } catch (AccountNameNotFoundException | PasswordNotFoundException e) {
            progressBar.setStyle("-fx-opacity: 0");
            errorLabel.setText("Username or Password is Invalid");
            e.printStackTrace();
        }

    }

    public void handleForgotPassword() throws IOException {
        App.userService.setTypedUsername(userNameTextField.getText());
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/ForgotPasswordScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleSubmit() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/Enter2FATokenScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleDebugLogin(ActionEvent actionEvent) throws IOException {
        App.userService.setUser("admin", "admin", "Employees");
        App.isLoggedIn.set(true);
        //Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot()); // todo : this still makes it load???
    }

    public void handleDebugLoginGuest(ActionEvent actionEvent) throws IOException {
        App.userService.setUser("patient", "patient", "Patients");
        App.isLoggedIn.set(true);
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleLonginWithNo2FA() {
        if (!App.userService.checkUsername(userNameTextField.getText()).equals("")) {
            if (!App.userService.checkPassword(passWordField.getText(), userNameTextField.getText()).equals("")) {
                loadingNewMainPage();
                Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(500);
                        Platform.runLater(() -> {
                            App.userService.setUser(userNameTextField.getText(), passWordField.getText(), App.userService.checkPassword(passWordField.getText(), userNameTextField.getText()));
                            App.isLoggedIn.set(true);
                            App.getPrimaryStage().getScene().setRoot(App.base);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new Error("Unexpected interruption");
                    }
                });
                thread.start();
            } else {
                wrongPasswordLabel.setVisible(true);
            }
        } else {
            wrongPasswordLabel.setVisible(true);
        }
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



