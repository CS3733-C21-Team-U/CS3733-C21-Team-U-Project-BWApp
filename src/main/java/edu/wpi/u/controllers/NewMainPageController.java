package edu.wpi.u.controllers;

import animatefx.animation.Bounce;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.models.MapService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.scene.input.KeyEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.SneakyThrows;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static edu.wpi.u.users.Role.ADMIN;

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
    public JFXListView listViewDemoNormal;
    public GridPane listTestingPane;
    public JFXListView listViewTesting;
    public JFXListView list2;
    public JFXButton expandButton;
    public JFXButton collapseButton;


    AnchorPane rightServiceRequestPane;
    AnchorPane leftMenuPane;

    ObservableList<String> listView = FXCollections.observableArrayList("Doesn't work", "For me. Let me know", "if it shows Material Design!", "And not just the default list");


    public void initialize() throws IOException {


//        validationFeild
        TextField test = new TextField("test");
//        test.bindAutoCompletion(comboBox.getJFXEditor(), "option1", "option2");
        HashMap<String, String> namesAndIDs= MapService.md.getLongnames();
        Set<String> strings = namesAndIDs.keySet();

        AutoCompletionBinding<String> acb = TextFields.bindAutoCompletion(validationFeild , FXCollections.observableArrayList("Locaiton 1","getLongNames is Borken"));
//        acb.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<String>>()
//        {
//
//            @Override
//            public void handle(AutoCompletionBinding.AutoCompletionEvent<String> event)
//            {
//                String valueFromAutoCompletion = event.getCompletion();
//            }
//        });

        App.themeSVG = themeIcon;

        this.expandButton.setOnMouseClicked((e) -> {
            this.list2.expandedProperty().set(true);
        });
        this.collapseButton.setOnMouseClicked((e) -> {
            this.list2.expandedProperty().set(!list2.isExpanded());
        });

        for(int i = 0; i < 4; ++i) {
            list2.getItems().add(new Label("ItemB " + i));
            listViewTesting.getItems().add(new Label("Item " + i));
        }

        listViewTesting.getStyleClass().clear();
//        list2.getStyleClass().clear();
//        list2.getStyleClass().add("mylistview2");

//        list2.expandedProperty().set(true);



        chipView.getChips().addAll("Start with one item");
        chipView.getSuggestions().addAll("Suggestion1","Suggestion2","Suggestion3");
//        chipView.setSelectionHandler();

        chipViewOnly.getChips().addAll("You", "Can't","Edit","This!");

//        listViewDemo.setItems(listView);
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
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
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
        JFXButton button1 = new JFXButton("Cancel");
        JFXButton button2 = new JFXButton("Proceed to Help Page");
        button1.setOnAction(event -> dialog.close());
            button2.setOnAction(event -> {
                AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainHelpPage.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                anchor.getChildren().clear();
                anchor.getChildren().add(root);
                dialog.close();
            });


        button1.getStyleClass().add("button-text");
        button2.getStyleClass().add("button-contained");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        actions.add(button2);
        content.setActions(actions);
        dialog.show();
    }
    public void handleCollapseButton(ActionEvent actionEvent) {
//        this.list2.expandedProperty().set(true);
    }

    public void handleExpandButton(ActionEvent actionEvent) {
//        this.list2.expandedProperty().set(false);
    }

    /**
     * Sends users from the main pathfinding page the About Page
     * @throws Exception
     * Author Lily
     */
    @FXML public void handleAboutPage() throws Exception {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/BaseAboutPage.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }
}
