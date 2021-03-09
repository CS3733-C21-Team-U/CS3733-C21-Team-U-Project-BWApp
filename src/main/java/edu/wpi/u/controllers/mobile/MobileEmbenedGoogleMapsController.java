package edu.wpi.u.controllers.mobile;

import com.jfoenix.controls.JFXButton;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.web.WebView;

import java.io.IOException;

public class MobileEmbenedGoogleMapsController {
    public WebView googleMapsEmbedded;
    public JFXButton nextButton;
    String URL = "https://www.google.com/maps/embed/v1/directions" +
            "?key=AIzaSyCttPdnsrVIlOvwMDTAbnaPbC1HtmGfcMs" +
            "&origin=42.27371483220032,-71.8086443997036"+
            "&destination=1153+Centre+St+Jamaica+Plain+MA+02130" +
            "&avoid=tolls|highways";
    public void initialize(){
        googleMapsEmbedded.getEngine().loadContent("<iframe width='412' height='687' src='" + URL + "' />");
    }

    public void handleNext(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/mobile/MobilePathfindingBase.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }


    //42.3016707215711, -71.1274086336297

}
