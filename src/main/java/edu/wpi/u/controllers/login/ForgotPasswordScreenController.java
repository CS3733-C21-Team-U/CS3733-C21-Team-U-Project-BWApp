package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.exceptions.AccountNameNotFoundException;
import edu.wpi.u.exceptions.PhoneNumberNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.asynchttpclient.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordScreenController {

    public JFXTextField resetPasswordTextField;
    public JFXButton resetButton;
    public JFXCheckBox employeeCheckBox;
    public JFXTextField usernameTextField, validationCodeFeild;

    boolean sentCode = false;
    boolean correctCode = false;

    public void initialize() throws IOException {
        validationCodeFeild.setDisable(true);
        resetPasswordTextField.setDisable(true);
        resetButton.setText("Send Code");
        usernameTextField.setText(App.userService.getTypedUsername());

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        usernameTextField.getValidators().add(validator);
        usernameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                usernameTextField.validate();
            }
        });

        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator.setMessage("Password Required");
        resetPasswordTextField.getValidators().add(validator2);
        resetPasswordTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                resetPasswordTextField.validate();
            }
        });
    }

    public void handleResetButton(ActionEvent actionEvent) throws IOException, AccountNameNotFoundException {
        if(!sentCode){
            if (!App.userService.checkUsername(usernameTextField.getText()).equals("")) {
                String phonenumber = App.userService.getPhoneNumberFromUserName(usernameTextField.getText());
                try {
                    Pattern pattern = Pattern.compile("^\\d{10}$");
                    Matcher matcher = pattern.matcher(phonenumber);
                    if (!matcher.matches()){
                        throw new PhoneNumberNotFoundException("Phone number is not valid");
                    }
                    URI uri = new URI("https://bw-webapp.herokuapp.com/" +"login?phonenumber=" + "+1"+ phonenumber + "&channel=sms");
                    URL url = uri.toURL(); // make GET request
                    AsyncHttpClient client = Dsl.asyncHttpClient();
                    Future<Response> whenResponse = client.prepareGet(url.toString()).execute();
                    Response response = whenResponse.get();
                    String resString = response.getResponseBody();
                    if(resString.contains("pending")){
                        resetButton.setText("Verify");
                        validationCodeFeild.setDisable(false);
                        sentCode = true;
                    }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            } else {
                throw new AccountNameNotFoundException();
            }
        } else if( sentCode && !correctCode) {
            String token = validationCodeFeild.getText();
            String phoneNumber = App.userService.getPhoneNumberFromUserName(usernameTextField.getText());
            try {
                System.out.println("Token " + token);
                System.out.println("Phonenumber " + phoneNumber);
                URI uri2 = new URI("https://bw-webapp.herokuapp.com/" +"verify?phonenumber=" + "+1"+ phoneNumber + "&code=" + token);
                URL url2 = uri2.toURL(); // make GET request
                System.out.println("URL: " + url2.toString());
                AsyncHttpClient client = Dsl.asyncHttpClient();
                Future<Response> whenResponse = client.prepareGet(url2.toString()).execute();
                Response response = whenResponse.get();
                String resString = response.getResponseBody();
                if(resString.contains("approved")){
                    correctCode = true;
                    resetPasswordTextField.setDisable(false);
                    resetButton.setText("Set Password");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else if(sentCode && correctCode){
            if(App.userService.checkUsername(usernameTextField.getText()).equals("Employees")){
                App.userService.changePassword(usernameTextField.getText(), resetPasswordTextField.getText(), "Employees");
            }else{
                App.userService.changePassword(usernameTextField.getText(), resetPasswordTextField.getText(), "Patients");
            }
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        }
    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleGoBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
}
