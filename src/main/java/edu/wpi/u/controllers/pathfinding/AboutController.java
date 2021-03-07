package edu.wpi.u.controllers.pathfinding;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;

public class AboutController {

    @FXML ImageView aboutImage;
    @FXML Label brighamWomensLabel;
    @FXML Label wilsonWongLabel;
    @FXML Label andrewBonaventuraLabel;
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
     * Hiding the image after mouse leaves the label
     * Author Lily
     */
    public void hideIMG() { aboutImage.setVisible(false);}

    /**
     * Shows Brigham and Women's Hospital Logo
     * Author Lily
     */
    public void showBrighamWomens() { showIMG("/edu/wpi/u/views/Images/logo.png");}

    /**
     * Shows Wilson Wong
     * Author Lily
     */
    public void showWilsonWong() { showIMG("/edu/wpi/u/views/Images/Wilson Wong.jpg");}

    /**
     * Shows Andrew "Elon Musk" Bonaventura
     * Author Lily
     */
    public void showAndrewBonaventura() { showIMG("/edu/wpi/u/views/Images/Andrew Elon Bonaventura.jpg");}

    /**
     * Shows Brigham and Women's Hospital Logo
     * Author Lily
     */
    public void showKohmeiKadoya() { showIMG("/edu/wpi/u/views/Images/logo.png");}

    /**
     * Shows Neville Ingram
     * Author Lily
     */
    public void showNevilleIngram() { showIMG("/edu/wpi/u/views/Images/Neville Ingram.jpg");}

    /**
     * Shows Brigham and Women's Hospital Logo
     * Author Lily
     */
    public void showKaamilLokhandwala() { showIMG("/edu/wpi/u/views/Images/logo.png");}

    /**
     * Shows Brigham and Women's Hospital Logo
     * Author Lily
     */
    public void showCharlieKittler() { showIMG("/edu/wpi/u/views/Images/logo.png");}

    /**
     * Shows Lily Durkin
     * Author Lily
     */
    public void showLilyDurkin() { showIMG("/edu/wpi/u/views/Images/Lily Durkin.jpg");}

    /**
     * Shows Tyler Sanderville
     * Author Lily
     */
    public void showTylerSanderville() { showIMG("/edu/wpi/u/views/Images/Tyler Sanderville.jpeg");}

    /**
     * Shows Brigham and Women's Hospital Logo
     * Author Lily
     */
    public void showJacobBernard() { showIMG("/edu/wpi/u/views/Images/logo.png");}

    /**
     * Shows Brigham and Women's Hospital Logo
     * Author Lily
     */
    public void showNickGao() { showIMG("/edu/wpi/u/views/Images/logo.png");}

    /**
     * Shows Brigham and Women's Hospital Logo
     * Author Lily
     */
    public void showWillBurke() { showIMG("/edu/wpi/u/views/Images/logo.png");}

    /**
     * Shows Brigham and Women's Hospital Logo
     * Author Lily
     */
    public void showMichaelGeary() { showIMG("/edu/wpi/u/views/Images/logo.png");}

    /**
     * Shows Brigham and Women's Hospital Logo
     * Author Lily
     */
    public void showOlaJackson() { showIMG("/edu/wpi/u/views/Images/logo.png");}

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
