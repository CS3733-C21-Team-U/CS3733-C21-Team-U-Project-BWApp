package edu.wpi.u.controllers.login;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sun.javafx.fxml.builder.URLBuilder;
import edu.wpi.u.App;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.database.UserData;
import edu.wpi.u.exceptions.AccountNameNotFoundException;
import edu.wpi.u.exceptions.PasswordNotFoundException;
import edu.wpi.u.users.User;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import jdk.nashorn.api.scripting.JSObject;
import org.apache.http.HttpConnection;
import org.apache.http.client.methods.RequestBuilder;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.MetaData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.events.Event;
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
import org.riversun.promise.Promise;

import javafx.concurrent.Worker.State;
import java.sql.SQLOutput;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    public JFXTextField tokenField;
    @FXML
    public JFXButton tokenSubmitButton;
    @FXML
    public JFXTextField accountNameTextField;

    public void initialize() throws IOException {

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        userNameTextField.getValidators().add(validator);
        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                userNameTextField.validate();
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
        // TODO : Ability to skip the 2fa
        String username = userNameTextField.getText();
        String password = passWordField.getText();
        String token = tokenField.getText();
        /*

            try {
                URI uri = new URI("https://bw-webapp.herokuapp.com/" +"login?phonenumber=" + "+1"+ username + "&channel=sms");
                URL url = uri.toURL(); // make GET request
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                System.out.println("Content: " + content);
                JsonObject obj = new Gson().fromJson(String.valueOf(content), JsonObject.class);
                String status = obj.get("status").toString();
                System.out.println("Status from get/login: " + status);
            } catch (Exception e) {
                e.printStackTrace();
            }
            */
        try {
            if (!App.userService.checkUsername(username).equals("") || !App.userService.checkPhoneNumber(username).equals("")) {
                System.out.println("HERe");
                if (!App.userService.checkPassword(password).equals("")) {
                    App.userService.setUser(username, password, App.userService.checkPassword(password));
                    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
                    App.getPrimaryStage().getScene().setRoot(root);
                } else {
                    throw new PasswordNotFoundException();
                }
            } else {
                throw new AccountNameNotFoundException();
            }
        } catch (AccountNameNotFoundException | PasswordNotFoundException e) {
            e.printStackTrace();
        }
    }

//Throws exceptions if username or password not found
        public void handleForgotPassword() throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ForgotPassword.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        }

    public void handleSubmit() {
        System.out.println("In handle submit");
        String token = tokenField.getText();
        String username = userNameTextField.getText();
        try {
            URI uri2 = new URI("https://bw-webapp.herokuapp.com/" +"verify?phonenumber=" + "+1"+ username + "&code=" + token);
            URL url2 = uri2.toURL(); // make GET request
            System.out.println(url2.toString());
            HttpURLConnection con = (HttpURLConnection) url2.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println("Content: " + content);
            JsonObject obj = new Gson().fromJson(String.valueOf(content), JsonObject.class);
            String status = obj.get("status").toString(); // "approved" or "pending"
            System.out.println("Status from get/verify: " + status);
            if (status.startsWith("approved", 1)){
                System.out.println("Approved");
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
                Scene scene = new Scene(root);
                App.getPrimaryStage().setScene(scene);
            }
            else if (status.startsWith("pending",1)){
                System.out.println("Pending");
                // TODO : UI display error incorrect token
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




