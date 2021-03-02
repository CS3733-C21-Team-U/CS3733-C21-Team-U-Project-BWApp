package edu.wpi.u.controllers.login;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import jdk.nashorn.api.scripting.JSObject;
import org.apache.http.HttpConnection;
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
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import com.google.gson.Gson;

import javafx.concurrent.Worker.State;

public class LoginController {

    @FXML
    public JFXTextField accountName;
    public JFXPasswordField passWord;
    public JFXButton login;
    public JFXButton forgotPasswordButton;


    public boolean authenticated;

    // This function, upon handling login button, will check the accountName and passWord
    // against the database, if this works, the user will be taken to the application, if not
    //the user will recieve an error
    // TODO : Get GSON working


    @FXML
    public void handleLogin() throws IOException {
//        get("http://localhost:3000/getstatus", (request,response) -> {
//            System.out.println("Here in post");
//            String queryParams = request.queryParams("status");
//            System.out.println("Status: " + queryParams);
//            if (queryParams.equals("approved")){
//                this.authenticated = true;
//                // TODO : Allow app entry -> NewMainPage.fxml
//                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
//                Scene scene = new Scene(root);
//                App.getPrimaryStage().setScene(scene);
//            }
//            else if (queryParams.equals("pending")){
//                this.authenticated = false;
//                // TODO : Stop from switching out of page & display error
//            }
//            else {
//                // TODO : Throw new error
//                System.out.println("Error");
//            }
//            System.out.println(this.authenticated);
//            return response;
//        });

        String username = accountName.getText();
        String password = passWord.getText();
        //TODO: Stop from switching windows
        try {
            if (!App.userService.checkUsername(username).equals("")) {
                if (!App.userService.checkPassword(password).equals("")) {
                    App.userService.setUser(username, password, App.userService.checkPassword(password));
                    //switch scene

                        //NEW CODE STARTS HERE
                    WebView webView = new WebView();
                    webView.setCache(true);
                    WebEngine webEngine = webView.getEngine();
                    webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
                        if (newState == State.SUCCEEDED) {
                            Document doc = webEngine.getDocument();
                            EventListener listener = ev -> {
                                System.out.println("Event triggered");
                                String t = doc.getElementById("statusLabel").getTextContent();
                                doc.getElementById("statusLabel").setTextContent("Changed");
                                Parent root = null;
                                try {
                                    root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Scene scene = new Scene(root);
                                App.getPrimaryStage().setScene(scene);
                            };
                            Element el = doc.getElementById("tokenSubmit");
                            ((EventTarget) el).addEventListener("click", listener, false);
                        }
                    });
                    webEngine.load("http://localhost:3000/");
                    webEngine.setJavaScriptEnabled(true);
                    VBox vBox = new VBox(webView);
                    Scene scene = new Scene(vBox, 1500, 750);
                    App.getPrimaryStage().setScene(scene);

                        //TODO : Make this request send until it gets the correct appStatus / run when updated
                        /*
                        Problem : GET request runs once when the appStatus in BW-WebApp is from the last recent appStatus
                        IE : This GET request needs to run after the new status has been updated
                        TODO : Possibly fix code below or fix BW-WebApp
                         */
                        URL url = new URL("http://localhost:3000/getstatus"); // make GET request
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.setRequestProperty("Content-Type", "application/json");
                        int responseCode = con.getResponseCode();
                        long date = con.getDate();
                        System.out.println("Date "+ date);
                        //System.out.println(responseCode);
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer content = new StringBuffer();
                        while ((inputLine = in.readLine()) != null){
                            content.append(inputLine);
                        }
                        in.close();
                        webEngine.reload();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        JsonObject obj = new Gson().fromJson(String.valueOf(content), JsonObject.class);
                        String status = obj.get("status").toString(); // "approved" or "pending"
                        System.out.println("Status from get " + status);
                        // NEW CODE ENDS HERE
                } else {
                    throw new PasswordNotFoundException();
                }
            } else {
                throw new AccountNameNotFoundException();
            }
        } catch (Exception e) {
            AccountNameNotFoundException accountException = new AccountNameNotFoundException();
            accountException.description = username + " not found in system.";
            PasswordNotFoundException passwordException = new PasswordNotFoundException();
            passwordException.description = password + " not associated with account. Check username or click Forgot Password.";
        }
    }
//Throws exceptions if username or password not found

    public void handleLogin(JFXTextField accountName, JFXPasswordField passWord){

    }

    public  void handleForgotPassword(){


    }
}


