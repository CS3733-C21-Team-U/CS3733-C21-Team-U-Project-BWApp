package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AboutController {

    @FXML ImageView aboutImage;
    @FXML Label aboutDescriptionLabel;
    @FXML Label kohmeiKadoyaLabel;
    @FXML Label nevilleIngramLabel;
    @FXML Label kaamilLokhandwalaLabel;
    @FXML Label charlieKittlerLabel;
    @FXML Label lilyDurkinLabel;
    @FXML Label tylerSandervilleLabel;
    @FXML Label jacobBernardLabel;
    @FXML Label nickGaoLabel;
    @FXML Label willBurkeLabel;
    @FXML Label michaelGearyLabel;
    @FXML Label olaJacksonLabel;

    /**
     * Displays an image when a label is hovered over.
     * @param path
     * Author Lily
     */
    private void showIMG(String path){
        //File file = new File(path);
        Image img = new Image(path);
        aboutImage.setImage(img);
        aboutImage.setVisible(true);

    }

    /**
     * Defaults the image after mouse leaves the label to the hospital logo
     * Author Lily
     */
    public void hideIMG() { showIMG("/edu/wpi/u/views/Images/brigham-health-logo-600x200.png");}

    /**
     * Shows Kohmei Kadoya
     * Author Lily
     */
    public void showKohmeiKadoya() { showIMG("/edu/wpi/u/views/Images/KohmeiKadoya.jpeg");}

    /**
     * Shows Neville Ingram
     * Author Lily
     */
    public void showNevilleIngram() { showIMG("/edu/wpi/u/views/Images/NevilleIngram.jpg");}

    /**
     * Shows Kaamil Lokhandwala
     * Author Lily
     */
    public void showKaamilLokhandwala() { showIMG("/edu/wpi/u/views/Images/KaamilLokhandwala.jpg");}

    /**
     * Shows Charlie Kittler
     * Author Lily
     */
    public void showCharlieKittler() { showIMG("/edu/wpi/u/views/Images/CharlieKittler.png");}

    /**
     * Shows Lily Durkin
     * Author Lily
     */
    public void showLilyDurkin() { showIMG("/edu/wpi/u/views/Images/LilyDurkin.jpg");}

    /**
     * Shows Tyler Sanderville
     * Author Lily
     */
    public void showTylerSanderville() { showIMG("/edu/wpi/u/views/Images/TylerSanderville.jpeg");}

    /**
     * Shows Jacob Bernard
     * Author Lily
     */
    public void showJacobBernard() { showIMG("/edu/wpi/u/views/Images/JacobBernard.jpg");}

    /**
     * Nick Gao
     * Author Lily
     */
    public void showNickGao() { showIMG("/edu/wpi/u/views/Images/NickGao.jpg");}

    /**
     * Shows Will Burke
     * Author Lily
     */
    public void showWillBurke() { showIMG("/edu/wpi/u/views/Images/WillBurke.jpg");}

    /**
     * Shows Michael Geary
     * Author Lily
     */
    public void showMichaelGeary() { showIMG("/edu/wpi/u/views/Images/MichaelGeary.jpg");}

    /**
     * Shows Ola Jackson
     * Author Lily
     */
    public void showOlaJackson() { showIMG("/edu/wpi/u/views/Images/OlajumokeJackson.jpg");}

    /**
     * Brings AboutPage back to Pathfinding.
     * @throws Exception
     * Author Lily
     */
    @FXML public void handleAboutCancel() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewMainPage.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
}
