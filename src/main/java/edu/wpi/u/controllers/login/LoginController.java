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
import java.util.Locale;
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

                    WebView webView = new WebView();
                    webView.setCache(true);
                    WebEngine webEngine = webView.getEngine();
                    webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
                        if (newState == State.SUCCEEDED) {
                            App.getPrimaryStage().setTitle(webEngine.getLocation());
                            Document doc = webEngine.getDocument();
                            EventListener listener = ev -> {
                                System.out.println("Event triggered and fetching status " + ev.getTimeStamp());
                                String status = " ";
                                try{
                                    URL url = new URL("https://bw-webapp.herokuapp.com/getstatus"); // make GET request
                                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                    con.setRequestMethod("GET");
                                    con.setRequestProperty("Content-Type", "application/json");
                                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                    String inputLine;
                                    StringBuffer content = new StringBuffer();
                                    while ((inputLine = in.readLine()) != null){
                                        content.append(inputLine);
                                    }
                                    in.close();
                                    webEngine.reload();
                                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                    System.out.println("Content: " + content);
                                    JsonObject obj = new Gson().fromJson(String.valueOf(content), JsonObject.class);
                                    System.out.println("JSON Object: " + obj);
                                    status = obj.get("status").toString(); // "approved" or "pending"
                                    System.out.println("Status from get: " + status);
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                                doc.getElementById("statusLabel").setTextContent("Token verified");
                                // TODO : Token status is received at this point
                                status = status.replaceAll("^\"|\"$", "");
                                if (status.equals("approved")){
                                    System.out.println("Token approved");
                                    doc.getElementById("statusLabel").setTextContent("Token verified");
                                    // TODO : Add a sexier scene switch here
                                    Parent root = null;
                                    try {
                                        root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Scene scene = new Scene(root);
                                    App.getPrimaryStage().setScene(scene);
                                }
                                else if (status.equals("pending")){
                                    System.out.println("Token denied");
                                    doc.getElementById("statusLabel").setTextContent("Token denied");
                                    // DO nothing, wait for correct input
                                }
                            };
                            Element el = doc.getElementById("enterApp");
                            ((EventTarget) el).addEventListener("click", listener, false);

                            EventListener listener2 = ev -> {
                                webEngine.executeScript("enterToken()");
                            };
                            Element el2 = doc.getElementById("tokenSubmit");
                            ((EventTarget) el2).addEventListener("click", listener2, false);
                        }
                    });
                    webEngine.load("https://bw-webapp.herokuapp.com/");
                    webEngine.setJavaScriptEnabled(true);
                    VBox vBox = new VBox(webView);
                    Scene scene = new Scene(vBox, 1920, 1080);
                    App.getPrimaryStage().setScene(scene);

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


