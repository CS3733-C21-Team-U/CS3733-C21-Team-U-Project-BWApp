package edu.wpi.u.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import javafx.animation.Interpolator;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.SneakyThrows;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Observable;
import java.util.function.BiPredicate;

import static edu.wpi.u.users.StaffType.ADMIN;

public class NewMainPageController {


    public GesturePane map;

    static final Duration DURATION = Duration.millis(300);
    @FXML
    public JFXTabPane mainTabPane;
    public JFXListView listViewDemo;
    public JFXTextField validationFeild;
    public StackPane newMainPageStackPane;

    public Tab adminTab2;
    public Tab adminTab1;

    public SVGPath themeIcon;
    public ToggleGroup group1;
    public JFXChipView chipView;
    public JFXChipView chipViewOnly;


    AnchorPane rightServiceRequestPane;
    AnchorPane leftMenuPane;

    ObservableList<String> listView = FXCollections.observableArrayList("Doesn't work", "For me. Let me know", "if it shows Material Design!", "And not just the default list");


    public void initialize() throws IOException {

        App.themeSVG = themeIcon;

        chipView.getChips().addAll("Start with one item");
        chipView.getSuggestions().addAll("Suggestion1","Suggestion2","Suggestion3");
//        chipView.setSelectionHandler();

        chipViewOnly.getChips().addAll("You", "Can't","Edit","This!");

        listViewDemo.setItems(listView);
        App.tabPaneRoot = mainTabPane;
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        validationFeild.getValidators().add(validator);
        validationFeild.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                validationFeild.validate();
            }
        });

        JFXDatePicker a = new JFXDatePicker();
        LocalDate b = a.getValue();
        mainTabPane.getStylesheets().add("-fx-text-fill: white;");

        if(App.userService.getActiveUser().getType() ==  ADMIN){
            adminTab1.setStyle("-fx-opacity: 1");
            adminTab1.setDisable(false);
            adminTab2.setStyle("-fx-opacity: 1");
            adminTab2.setDisable(false);
        }
        else if(!(App.userService.getActiveUser().getType() ==  ADMIN)){
            adminTab1.setStyle("-fx-opacity: 0");
            adminTab1.setDisable(true);
            adminTab2.setStyle("-fx-opacity: 0");
            adminTab2.setDisable(true);
        }

//        chipView.getChips().addListener((new ListChangeListener<String>(){
//
//            @Override
//            public void onChanged(Change<? extends String> c) {
//                System.out.println("In onChange for newMainPage");
//                ObservableList<String> currently = chipView.getChips();
//                ObservableList<String> options = chipView.getSuggestions();
//
//                for(int i = 0; i < currently.size(); i++){
//                    boolean isValid = false;
//                    for(String option : options){
//                        if(option.equals(currently.get(i))){
//                            isValid = true;
//                        }
//                    }
//                    if(!isValid){
//                        currently.remove(i);
//                        i--;
//                    }
//                }
//
//                chipView.getChips().clear();
//                chipView.getChips().addAll(currently);
//            }
//        }));
    }

    public void handleThemeSwitch(ActionEvent actionEvent) {
        App.getInstance().switchTheme();
    }


    public void handleExit() throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Exit Application?");
        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CANCEL");
        JFXButton button2 = new JFXButton("EXIT");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(event -> {
            dialog.close();
            App.getInstance().end();
        });
        button1.getStyleClass().add("button-text");
        button2.getStyleClass().add("button-contained");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        actions.add(button2);
        content.setActions(actions);
        dialog.show();

    }

    public void handleLogout(ActionEvent actionEvent) throws IOException {
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Log out?");
        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CANCEL");
        JFXButton button2 = new JFXButton("LOGOUT");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @SneakyThrows
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/UserLogin.fxml"));
                App.getPrimaryStage().getScene().setRoot(root);
            }
        });
        button1.getStyleClass().add("button-text");
        button2.getStyleClass().add("button-contained");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        actions.add(button2);
        content.setActions(actions);
        dialog.show();
    }

    public void handleChangeTab() {
        App.mapInteractionModel.setCurrentAction("NONE");
        App.mapInteractionModel.clearPreviousNodeID();
        App.mapInteractionModel.setNodeID(" ");
    }


    public void onChipEnter(KeyEvent keyEvent) {
////        System.out.println("In function");
//        if(keyEvent.getCode() == KeyCode.ENTER){
//            ObservableList<String> currently = chipView.getChips();
//            ObservableList<String> options = chipView.getSuggestions();
//
//            for(int i = 0; i < currently.size(); i++){
//                boolean isValid = false;
//                for(String option : options){
//                    if(option.equals(currently.get(i))){
//                        isValid = true;
//                    }
//                }
//                if(!isValid){
//                    currently.remove(i);
//                    i--;
//                }
//            }
//
//            chipView.getChips().clear();
//            chipView.getChips().addAll(currently);
//        }
    }

    public void handleHelpPageButton(ActionEvent actionEvent) {

        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Help Page");
        Text text = new Text("This is the Main page.If there is an emergency situation, Please call 911");
        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.setBody(text);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CANCEL");
        //JFXButton button2 = new JFXButton("EXIT");
        button1.setOnAction(event -> dialog.close());
            /*button2.setOnAction(event -> {
                dialog.close();
                App.getInstance().end();
            });*/
        button1.getStyleClass().add("button-text");
        //button2.getStyleClass().add("button-contained");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        //actions.add(button2);
        content.setActions(actions);
        dialog.show();
    }
}
