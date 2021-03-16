package edu.wpi.u.controllers;

//import animatefx.animation.Bounce;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.models.MapService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
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
import static edu.wpi.u.users.Role.GUEST;

public class NewMainPageController {


    public GesturePane map;

    static final Duration DURATION = Duration.millis(300);
    @FXML
    public JFXTabPane mainTabPane;
    public JFXListView listViewDemo;
    public JFXTextField validationFeild;
    public StackPane newMainPageStackPane;

    public Tab pathFindingTab;
    public Tab googleTab;
    public Tab requestTab;
    public Tab settingsTab;
    public Tab HelpMainPageTab;
    public Tab AdminHelpMainPageTab;
    public Tab adminTab1;
    public Tab adminTab2;
    public Tab adminTab4;

    public Tab currentTab;

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



    public JFXButton themeSwitchBtn;
    public JFXButton logoutBtn;
    public JFXButton powerBtn;

    public AnchorPane pathfindingDis;
    public AnchorPane googleDis;
    public AnchorPane requestDis;
    public AnchorPane settingsDis;
    public AnchorPane helpDis;
    public AnchorPane adminHelpDis;
    public AnchorPane mapBuildDis;
    public AnchorPane userDis;
//    public AnchorPane guestDis;
    public AnchorPane covidDis;

    public KeyCombination pathFinding = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
    public KeyCombination googleAPI = new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN);
    public KeyCombination request = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
    public KeyCombination newRequest = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    public KeyCombination settings = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
    public KeyCombination help = new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN);
    public KeyCombination mapBuilder = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);
    public KeyCombination user = new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN);
    public KeyCombination addUser = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);
    public KeyCombination guestList = new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN);
    public KeyCombination covidData = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
    public KeyCombination logOut = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
    public KeyCombination switchTheme = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);



    AnchorPane rightServiceRequestPane;
    AnchorPane leftMenuPane;

    ObservableList<String> listView = FXCollections.observableArrayList("Doesn't work", "For me. Let me know", "if it shows Material Design!", "And not just the default list");


    public void initialize() throws IOException {
        System.out.println("Init for New Main Page");
//        Platform.runLater(()->{
//            System.out.println("Run Later and select first index");
//            App.tabPaneRoot.getSelectionModel().clearAndSelect(0);
//        });

        App.throwDialogHerePane = newMainPageStackPane;

        detectTab();

        //setup tooltips
        themeSwitchBtn.setTooltip(new Tooltip("Switch Themes"));
        logoutBtn.setTooltip(new Tooltip("Log Out"));
        powerBtn.setTooltip(new Tooltip("Power Down"));


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
        App.isLoggedIn.addListener((observable, oldValue, newValue) -> {
            if(App.userService.getActiveUser().getType() ==  ADMIN){
                adminTab1.setStyle("-fx-opacity: 1");
                adminTab1.setDisable(false);
                adminTab2.setStyle("-fx-opacity: 1");
                adminTab2.setDisable(false);
                adminTab4.setStyle("-fx-opacity: 1");
                adminTab4.setDisable(false);
                HelpMainPageTab.setDisable(true);
                HelpMainPageTab.setStyle("-fx-opacity: 0");
                AdminHelpMainPageTab.setDisable(false);
                AdminHelpMainPageTab.setStyle("-fx-opacity: 1");
            }
            else{
                adminTab1.setStyle("-fx-opacity: 0");
                adminTab1.setDisable(true);
                adminTab2.setStyle("-fx-opacity: 0");
                adminTab2.setDisable(true);
                adminTab4.setStyle("-fx-opacity: 0");
                adminTab4.setDisable(true);
                HelpMainPageTab.setDisable(false);
                HelpMainPageTab.setStyle("-fx-opacity: 1");
                AdminHelpMainPageTab.setDisable(true);
                AdminHelpMainPageTab.setStyle("-fx-opacity: 0");
            }

            if(App.userService.getActiveUser().getType() == GUEST){
                settingsTab.setStyle("-fx-opacity: 0");
                settingsTab.setDisable(true);
            }else{
                settingsTab.setStyle("-fx-opacity: 1");
                settingsTab.setDisable(false);
            }
        });


    }

    public void handleThemeSwitch(ActionEvent actionEvent) {
//        App.getInstance().switchTheme();
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
            App.isLoggedIn.set(false);
            App.getInstance().exitApp();
        });
        button1.getStyleClass().add("button-text");
        button2.getStyleClass().add("button-contained");
        ArrayList<Node> actions = new ArrayList<>();
        actions.add(button1);
        actions.add(button2);
        content.setActions(actions);
        dialog.show();

    }

    public void handleLogout() throws IOException {
        App.isLoggedIn.set(false);
        JFXDialogLayout content = new JFXDialogLayout();
        Label header = new Label("Log out?");
        header.getStyleClass().add("headline-2");
        content.setHeading(header);
        content.getStyleClass().add("dialogue");
        JFXDialog dialog = new JFXDialog(newMainPageStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("CANCEL");
        JFXButton button2 = new JFXButton("LOGOUT");
        button1.setOnAction(event -> dialog.close());
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @SneakyThrows
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                App.isLoggedIn.set(false);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
                fxmlLoader.load();
                fxmlLoader.getController();
                App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
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
        App.mapInteractionModel.setCurrentAction("SELECT");
        App.mapInteractionModel.pathFlag.set(String.valueOf(Math.random()));
        App.mapInteractionModel.reloadPathfinding.set(!App.mapInteractionModel.reloadPathfinding.get());
//        App.mapInteractionModel.clearPreviousNodeID();
//        App.mapInteractionModel.setNodeID(" ");
    }

//    public void onChipEnter(KeyEvent keyEvent) {
//////        System.out.println("In function");
////        if(keyEvent.getCode() == KeyCode.ENTER){
////            ObservableList<String> currently = chipView.getChips();
////            ObservableList<String> options = chipView.getSuggestions();
////
////            for(int i = 0; i < currently.size(); i++){
////                boolean isValid = false;
////                for(String option : options){
////                    if(option.equals(currently.get(i))){
////                        isValid = true;
////                    }
////                }
////                if(!isValid){
////                    currently.remove(i);
////                    i--;
////                }
////            }
////
////            chipView.getChips().clear();
////            chipView.getChips().addAll(currently);
////        }
//    }

    public void handleCollapseButton(ActionEvent actionEvent) {
//        this.list2.expandedProperty().set(true);
    }

    public void handleExpandButton(ActionEvent actionEvent) {
//        this.list2.expandedProperty().set(false);
    }

    public void onChipEnter(KeyEvent keyEvent) {
    }

    public void detectTab()
    {
        if (mainTabPane.getSelectionModel().getSelectedItem() == pathFindingTab){handleEnablePathFinding();}
        else if (mainTabPane.getSelectionModel().getSelectedItem() == googleTab){handleEnableGoogleMaps();}
        else if (mainTabPane.getSelectionModel().getSelectedItem() == requestTab){handleEnableRequests();}
        else if (mainTabPane.getSelectionModel().getSelectedItem() == settingsTab){handleEnableSettings();}
        else if (mainTabPane.getSelectionModel().getSelectedItem() == HelpMainPageTab){handleEnableHelp();}
        else if (mainTabPane.getSelectionModel().getSelectedItem() == AdminHelpMainPageTab){handleEnableAdminHelp();}
        else if (mainTabPane.getSelectionModel().getSelectedItem() == adminTab1){handleEnableMapBuild();}
        else if (mainTabPane.getSelectionModel().getSelectedItem() == adminTab2){handleEnableUser();}
//        else if (mainTabPane.getSelectionModel().getSelectedItem() == adminTab3){handleEnableGuest();}
        else if (mainTabPane.getSelectionModel().getSelectedItem() == adminTab4){handleEnableCovid();}
    }


    public void handleEnablePathFinding() {
        pathfindingDis.setDisable(false);
        googleDis.setDisable(true);
        requestDis.setDisable(true);
        settingsDis.setDisable(true);
        helpDis.setDisable(true);
        adminHelpDis.setDisable(true);
        mapBuildDis.setDisable(true);
        userDis.setDisable(true);
//        guestDis.setDisable(true);
        covidDis.setDisable(true);
    }

    public void handleEnableGoogleMaps() {
        pathfindingDis.setDisable(true);
        googleDis.setDisable(false);
        requestDis.setDisable(true);
        settingsDis.setDisable(true);
        helpDis.setDisable(true);
        adminHelpDis.setDisable(true);
        mapBuildDis.setDisable(true);
        userDis.setDisable(true);
//        guestDis.setDisable(true);
        covidDis.setDisable(true);
    }

    public void handleEnableRequests() {
        pathfindingDis.setDisable(true);
        googleDis.setDisable(true);
        requestDis.setDisable(false);
        settingsDis.setDisable(true);
        helpDis.setDisable(true);
        adminHelpDis.setDisable(true);
        mapBuildDis.setDisable(true);
        userDis.setDisable(true);
//        guestDis.setDisable(true);
        covidDis.setDisable(true);
    }

    public void handleEnableSettings() {
        pathfindingDis.setDisable(true);
        googleDis.setDisable(true);
        requestDis.setDisable(true);
        settingsDis.setDisable(false);
        helpDis.setDisable(true);
        adminHelpDis.setDisable(true);
        mapBuildDis.setDisable(true);
        userDis.setDisable(true);
//        guestDis.setDisable(true);
        covidDis.setDisable(true);
    }

    public void handleEnableHelp() {
        pathfindingDis.setDisable(true);
        googleDis.setDisable(true);
        requestDis.setDisable(true);
        settingsDis.setDisable(true);
        helpDis.setDisable(false);
        adminHelpDis.setDisable(true);
        mapBuildDis.setDisable(true);
        userDis.setDisable(true);
//        guestDis.setDisable(true);
        covidDis.setDisable(true);
    }

    public void handleEnableAdminHelp() {
        pathfindingDis.setDisable(true);
        googleDis.setDisable(true);
        requestDis.setDisable(true);
        settingsDis.setDisable(true);
        helpDis.setDisable(true);
        adminHelpDis.setDisable(false);
        mapBuildDis.setDisable(true);
        userDis.setDisable(true);
//        guestDis.setDisable(true);
        covidDis.setDisable(true);
    }

    public void handleEnableMapBuild() {
        pathfindingDis.setDisable(true);
        googleDis.setDisable(true);
        requestDis.setDisable(true);
        settingsDis.setDisable(true);
        helpDis.setDisable(true);
        adminHelpDis.setDisable(true);
        mapBuildDis.setDisable(false);
        userDis.setDisable(true);
//        guestDis.setDisable(true);
        covidDis.setDisable(true);
    }

    public void handleEnableUser() {
        pathfindingDis.setDisable(true);
        googleDis.setDisable(true);
        requestDis.setDisable(true);
        settingsDis.setDisable(true);
        helpDis.setDisable(true);
        adminHelpDis.setDisable(true);
        mapBuildDis.setDisable(true);
        userDis.setDisable(false);
//        guestDis.setDisable(true);
        covidDis.setDisable(true);
    }

    public void handleEnableGuest() {
        pathfindingDis.setDisable(true);
        googleDis.setDisable(true);
        requestDis.setDisable(true);
        settingsDis.setDisable(true);
        helpDis.setDisable(true);
        adminHelpDis.setDisable(true);
        mapBuildDis.setDisable(true);
        userDis.setDisable(true);
//        guestDis.setDisable(false);
        covidDis.setDisable(true);
    }

    public void handleEnableCovid() {
        pathfindingDis.setDisable(true);
        googleDis.setDisable(true);
        requestDis.setDisable(true);
        settingsDis.setDisable(true);
        helpDis.setDisable(true);
        adminHelpDis.setDisable(true);
        mapBuildDis.setDisable(true);
        userDis.setDisable(true);
//        guestDis.setDisable(true);
        covidDis.setDisable(false);
    }

    public void handleTest2(Event event) {

    }


    public void handleShortCuts(KeyEvent keyEvent) throws IOException {
         if (pathFinding.match(keyEvent)){
            System.out.println("pathfinding page");
            mainTabPane.getSelectionModel().select(pathFindingTab);
        }else if (googleAPI.match(keyEvent)){
            System.out.println("googelAPI page");
             mainTabPane.getSelectionModel().select(googleTab);
        }else if (newRequest.match(keyEvent)){
            System.out.println("new request page");
             mainTabPane.getSelectionModel().select(requestTab);
            //handle adding new request
        }else if (request.match(keyEvent)){
            System.out.println("new request page");
             mainTabPane.getSelectionModel().select(requestTab);
        }else if (settings.match(keyEvent)){
            System.out.println("settings page");
             mainTabPane.getSelectionModel().select(settingsTab);
        }else if (help.match(keyEvent)){
            System.out.println("help page");
            //handle user logic
//             mainTabPane.getSelectionModel().select(HelpMainPageTab);
//             mainTabPane.getSelectionModel().select(AdminHelpMainPageTab);
        }else if (mapBuilder.match(keyEvent)){
            System.out.println("map builder page");
             mainTabPane.getSelectionModel().select(adminTab1);
        }else if (user.match(keyEvent)){
            System.out.println("user man page");
             mainTabPane.getSelectionModel().select(adminTab2);
        }else if (addUser.match(keyEvent)){
            System.out.println("add user page");
             mainTabPane.getSelectionModel().select(adminTab2);
             //scene switch to add useu
        }else if (covidData.match(keyEvent)){
            System.out.println("covid data page");
             mainTabPane.getSelectionModel().select(adminTab4);
        }else if (logOut.match(keyEvent)){
            System.out.println("log Out");
            // log out
             handleLogout();
        }else if (switchTheme.match(keyEvent)){
            System.out.println("Switch Theme Gone");
//            handleThemeSwitch();
        }
    }
}

