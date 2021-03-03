package edu.wpi.u.controllers.login;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sun.javafx.fxml.builder.URLBuilder;
import com.sun.javafx.property.adapter.PropertyDescriptor;
import edu.wpi.u.App;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.database.UserData;
import edu.wpi.u.exceptions.AccountNameNotFoundException;
import edu.wpi.u.exceptions.PasswordNotFoundException;
import edu.wpi.u.exceptions.PhoneNumberNotFoundException;
import edu.wpi.u.users.User;
import io.netty.handler.codec.http.HttpHeaders;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.SneakyThrows;
import org.apache.http.HttpConnection;
import org.apache.http.client.methods.RequestBuilder;
import org.asynchttpclient.*;
import org.asynchttpclient.netty.request.NettyRequest;
import org.asynchttpclient.proxy.ProxyServer;
import org.asynchttpclient.util.HttpConstants;
import org.eclipse.jetty.http.MetaData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import spark.Spark;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static spark.Spark.*;
import com.google.gson.Gson;
import javafx.concurrent.Worker.State;
import java.sql.SQLOutput;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static edu.wpi.u.users.StaffType.*;

public class LoginController {


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
    @FXML public JFXButton submitButton;
    //public JFXButton loginButton2;
    @FXML public Label errorLabel;
    @FXML public JFXButton debugLoginAdminButton;
    @FXML public JFXButton debugLoginGuest;

    public void initialize() throws IOException {

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
            if (!newVal){
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
            if (!newVal){
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
        System.out.println("HERE");
        progressBar.setStyle("-fx-opacity: 1");
        // TODO : Ability to skip the 2fa
//        Scene scene = new Scene(root);
//        App.getPrimaryStage().setScene(scene);
//        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/Enter2FAToken.fxml"));
//        App.getPrimaryStage().getScene().setRoot(root);
        String username = userNameTextField.getText();
        String password = passWordField.getText();
        System.out.println("Username : " + username + " Password: " + password);
        App.userService.setUser(username, password, App.userService.checkPassword(password));
        System.out.println("Phonenumber from user service: " + App.userService.getActiveUser().getPhoneNumber());
        System.out.println("Phonenumber from get: " + App.userService.getActiveUser().getPhoneNumber());
        String phonenumber = App.userService.getActiveUser().getPhoneNumber();

        // TODO : Extract out to helper
        try {
            if (!App.userService.checkUsername(username).equals("")) {
                if (!App.userService.checkPassword(password).equals("")) {
                    // TODO : Send code

                    try {
                        Pattern pattern = Pattern.compile("^\\d{10}$");
                        Matcher matcher = pattern.matcher(phonenumber);
                        if (!matcher.matches()){
                            errorLabel.setText("Phonenumber associated with account is invalid");
                            throw new PhoneNumberNotFoundException("Phone number is not valid");
                        }
                        URI uri = new URI("https://bw-webapp.herokuapp.com/" +"login?phonenumber=" + "+1"+ phonenumber + "&channel=sms");
                        URL url = uri.toURL(); // make GET request
                        AsyncHttpClient client = Dsl.asyncHttpClient();
                        Future<Integer> whenStatusCode = client.prepareGet(url.toString())
                                .execute(new AsyncHandler<Integer>() {
                                    private Integer status;
                                    @Override
                                    public State onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
                                        status = responseStatus.getStatusCode();
                                        System.out.println("At status code");
                                        return State.CONTINUE;
                                    }
                                    @Override
                                    public State onHeadersReceived(HttpHeaders headers) throws Exception {
                                        System.out.println("At headers code");
                                        return State.CONTINUE;
                                    }
                                    @Override
                                    public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                                        byte[] b = bodyPart.getBodyPartBytes();
                                        String y = new String(b);
                                        System.out.println(y.contains("pending"));
                                        if (y.contains("pending")){
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



//Throws exceptions if username or password not found
        public void handleForgotPassword() throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ForgotPassword.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        }

    public void handleSubmit() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/Enter2FAToken.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleDebugLogin(ActionEvent actionEvent) throws IOException {
        App.userService.setUser("admin", "admin", "Guests");
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleDebugLoginGuest(ActionEvent actionEvent) throws IOException {
        App.userService.setUser("staff", "staff", "Guests");
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
}




