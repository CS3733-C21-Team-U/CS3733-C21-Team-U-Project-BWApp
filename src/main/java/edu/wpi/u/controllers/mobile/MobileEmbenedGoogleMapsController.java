package edu.wpi.u.controllers.mobile;

import javafx.scene.web.WebView;

public class MobileEmbenedGoogleMapsController {
    public WebView googleMapsEmbedded;
    String URL = "https://www.google.com/maps/embed/v1/directions" +
            "?key=AIzaSyCttPdnsrVIlOvwMDTAbnaPbC1HtmGfcMs" +
            "&origin=42.27371483220032,-71.8086443997036"+
            "&destination=1153+Centre+St+Jamaica+Plain+MA+02130" +
            "&avoid=tolls|highways";
    public void initialize(){
        googleMapsEmbedded.getEngine().loadContent("<iframe width='412' height='690' src='" + URL + "' />");
    }


    //42.3016707215711, -71.1274086336297

}
