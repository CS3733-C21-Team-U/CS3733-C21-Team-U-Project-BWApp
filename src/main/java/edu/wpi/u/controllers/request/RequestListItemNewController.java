package edu.wpi.u.controllers.request;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.requests.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import lombok.SneakyThrows;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class RequestListItemNewController extends AnchorPane implements Initializable{

    public JFXTextField editTitleField;
    public JFXTextArea editDescriptionField;
    public JFXDatePicker editDateNeededField;
    public JFXTimePicker editTimeNeededField;
    public JFXTextField editAssigneesField;
    public ToggleGroup selectTypeGroup;
    public JFXButton saveButton;
    public SVGPath typeIconSVG;
    public Label titleLabel;

    @FXML
    public JFXListView<String> editAssigneesListView;// = new JFXListView<String>();
    public JFXTextField editLocationsField;

    @FXML
    public JFXListView<String> editLocationsListView;// = new JFXListView<String>();

    public VBox extraFieldsVBox;
    public VBox selectFieldGraphic;

    private JFXTextField[] specificTextFields;
    HashMap<String, String> longNamestoID;
    Set<String> existingAssignee;
    SpecificRequest currSpecificRequest;

    boolean labelSwitch = false;
    public Label selectFieldLabel;
    public Label fieldLabel;
    int msgCounter = 0;
    String[] requestFieldMessages= new String[]{"Select a request type to complete your request!", "Any request type will do.",
            "Feeling indecisive, are we?", "Well then, why don't you choose "};
    String[] reqTypes= new String[]{"Maintenance", "Security",
            "Laundry", "Sanitation", "Religious", "Floral", "Language"};

    public JFXToggleNode soundBtn;
    public JFXToggleNode screenBtn;
    public JFXToggleNode giftBtn;
    public JFXToggleNode languageBtn;
    public JFXToggleNode washerBtn;
    public JFXToggleNode toolBtn;
    public JFXToggleNode medicineBtn;
    public JFXToggleNode churchBtn;
    public JFXToggleNode broomBtn;
    public JFXToggleNode shieldBtn;
    public JFXToggleNode foodBtn;
    public JFXToggleNode flowerBtn;

    public static String AudioVisualIcon = "M3 9v6h4l5 5V4L7 9H3zm13.5 3c0-1.77-1.02-3.29-2.5-4.03v8.05c1.48-.73 2.5-2.25 2.5-4.02zM14 3.23v2.06c2.89.86 5 3.54 5 6.71s-2.11 5.85-5 6.71v2.06c4.01-.91 7-4.49 7-8.77s-2.99-7.86-7-8.77z";
    public static String ComputerIcon = "M3 6h18V4H3c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h4v-2H3V6zm10 6H9v1.78c-.61.55-1 1.33-1 2.22s.39 1.67 1 2.22V20h4v-1.78c.61-.55 1-1.34 1-2.22s-.39-1.67-1-2.22V12zm-2 5.5c-.83 0-1.5-.67-1.5-1.5s.67-1.5 1.5-1.5 1.5.67 1.5 1.5-.67 1.5-1.5 1.5zM22 8h-6c-.5 0-1 .5-1 1v10c0 .5.5 1 1 1h6c.5 0 1-.5 1-1V9c0-.5-.5-1-1-1zm-1 10h-4v-8h4v8z";
    public static String FloralIcon = "M8.66 13.07c.15 0 .29-.01.43-.03C9.56 14.19 10.69 15 12 15s2.44-.81 2.91-1.96c.14.02.29.03.43.03 1.73 0 3.14-1.41 3.14-3.14 0-.71-.25-1.39-.67-1.93.43-.54.67-1.22.67-1.93 0-1.73-1.41-3.14-3.14-3.14-.15 0-.29.01-.43.03C14.44 1.81 13.31 1 12 1s-2.44.81-2.91 1.96c-.14-.02-.29-.03-.43-.03-1.73 0-3.14 1.41-3.14 3.14 0 .71.25 1.39.67 1.93-.43.54-.68 1.22-.68 1.93 0 1.73 1.41 3.14 3.15 3.14zM12 13c-.62 0-1.12-.49-1.14-1.1l.12-1.09c.32.12.66.19 1.02.19s.71-.07 1.03-.19l.11 1.09c-.02.61-.52 1.1-1.14 1.1zm3.34-1.93c-.24 0-.46-.07-.64-.2l-.81-.57c.55-.45.94-1.09 1.06-1.83l.88.42c.4.19.66.59.66 1.03 0 .64-.52 1.15-1.15 1.15zm-.65-5.94c.2-.13.42-.2.65-.2.63 0 1.14.51 1.14 1.14 0 .44-.25.83-.66 1.03l-.88.42c-.12-.74-.51-1.38-1.07-1.83l.82-.56zM12 3c.62 0 1.12.49 1.14 1.1l-.11 1.09C12.71 5.07 12.36 5 12 5s-.7.07-1.02.19l-.12-1.09c.02-.61.52-1.1 1.14-1.1zM8.66 4.93c.24 0 .46.07.64.2l.81.56c-.55.45-.94 1.09-1.06 1.83l-.88-.42c-.4-.2-.66-.59-.66-1.03 0-.63.52-1.14 1.15-1.14zM8.17 8.9l.88-.42c.12.74.51 1.38 1.07 1.83l-.81.55c-.2.13-.42.2-.65.2-.63 0-1.14-.51-1.14-1.14-.01-.43.25-.82.65-1.02zM12 22c4.97 0 9-4.03 9-9-4.97 0-9 4.03-9 9zm2.44-2.44c.71-1.9 2.22-3.42 4.12-4.12-.71 1.9-2.22 3.41-4.12 4.12zM3 13c0 4.97 4.03 9 9 9 0-4.97-4.03-9-9-9zm2.44 2.44c1.9.71 3.42 2.22 4.12 4.12-1.9-.71-3.41-2.22-4.12-4.12z";
    public static String GiftIcon = "M20 6h-2.18c.11-.31.18-.65.18-1 0-1.66-1.34-3-3-3-1.05 0-1.96.54-2.5 1.35l-.5.67-.5-.68C10.96 2.54 10.05 2 9 2 7.34 2 6 3.34 6 5c0 .35.07.69.18 1H4c-1.11 0-1.99.89-1.99 2L2 19c0 1.11.89 2 2 2h16c1.11 0 2-.89 2-2V8c0-1.11-.89-2-2-2zm-5-2c.55 0 1 .45 1 1s-.45 1-1 1-1-.45-1-1 .45-1 1-1zM9 4c.55 0 1 .45 1 1s-.45 1-1 1-1-.45-1-1 .45-1 1-1zm11 15H4v-2h16v2zm0-5H4V8h5.08L7 10.83 8.62 12 12 7.4l3.38 4.6L17 10.83 14.92 8H20v6z";
    public static String LanguageIcon = "M12.87 15.07l-2.54-2.51.03-.03c1.74-1.94 2.98-4.17 3.71-6.53H17V4h-7V2H8v2H1v1.99h11.17C11.5 7.92 10.44 9.75 9 11.35 8.07 10.32 7.3 9.19 6.69 8h-2c.73 1.63 1.73 3.17 2.98 4.56l-5.09 5.02L4 19l5-5 3.11 3.11.76-2.04zM18.5 10h-2L12 22h2l1.12-3h4.75L21 22h2l-4.5-12zm-2.62 7l1.62-4.33L19.12 17h-3.24z";
    public static String LaundryIcon = "M18 2.01L6 2c-1.11 0-2 .89-2 2v16c0 1.11.89 2 2 2h12c1.11 0 2-.89 2-2V4c0-1.11-.89-1.99-2-1.99zM18 20H6L5.99 4H18v16z M 9,6 A 1,1 0 0 1 8,7 1,1 0 0 1 7,6 1,1 0 0 1 8,5 1,1 0 0 1 9,6 Z M 12,6 A 1,1 0 0 1 11,7 1,1 0 0 1 10,6 1,1 0 0 1 11,5 1,1 0 0 1 12,6 Z M12 19c2.76 0 5-2.24 5-5s-2.24-5-5-5-5 2.24-5 5 2.24 5 5 5zm2.36-7.36c1.3 1.3 1.3 3.42 0 4.72-1.3 1.3-3.42 1.3-4.72 0l4.72-4.72z";
    public static String MaintenanceIcon = "M21.67,18.17l-5.3-5.3h-0.99l-2.54,2.54v0.99l5.3,5.3c0.39,0.39,1.02,0.39,1.41,0l2.12-2.12 C22.06,19.2,22.06,18.56,21.67,18.17z M18.84,19.59l-4.24-4.24l0.71-0.71l4.24,4.24L18.84,19.59z M17.34,10.19l1.41-1.41l2.12,2.12c1.17-1.17,1.17-3.07,0-4.24l-3.54-3.54l-1.41,1.41V1.71L15.22,1l-3.54,3.54l0.71,0.71 h2.83l-1.41,1.41l1.06,1.06l-2.89,2.89L7.85,6.48V5.06L4.83,2.04L2,4.87l3.03,3.03h1.41l4.13,4.13l-0.85,0.85H7.6l-5.3,5.3 c-0.39,0.39-0.39,1.02,0,1.41l2.12,2.12c0.39,0.39,1.02,0.39,1.41,0l5.3-5.3v-2.12l5.15-5.15L17.34,10.19z M9.36,15.34 l-4.24,4.24l-0.71-0.71l4.24-4.24l0,0L9.36,15.34L9.36,15.34z";
    public static String MedicalIcon = "M10.5,15H8v-3h2.5V9.5h3V12H16v3h-2.5v2.5h-3V15z M19,8v11c0,1.1-0.9,2-2,2H7c-1.1,0-2-0.9-2-2V8c0-1.1,0.9-2,2-2h10 C18.1,6,19,6.9,19,8z M17,8H7v11h10V8z M18,3H6v2h12V3z";
    public static String ReligiousIcon = "M13,14h-2v-2h2V14z M18.1,16.56L17,14.79V21H7v-6.2l-1.1,1.76L4.2,15.5L12,3l7.8,12.5L18.1,16.56z M15,11.59l-3-4.8l-3,4.8 V19h2v-3h2v3h2V11.59z";
    public static String SanitationIcon = "M16,11h-1V3c0-1.1-0.9-2-2-2h-2C9.9,1,9,1.9,9,3v8H8c-2.76,0-5,2.24-5,5v7h18v-7C21,13.24,18.76,11,16,11z M11,3h2v8h-2V3 z M19,21h-2v-3c0-0.55-0.45-1-1-1s-1,0.45-1,1v3h-2v-3c0-0.55-0.45-1-1-1s-1,0.45-1,1v3H9v-3c0-0.55-0.45-1-1-1s-1,0.45-1,1v3H5 v-5c0-1.65,1.35-3,3-3h8c1.65,0,3,1.35,3,3V21z";
    public static String SecurityIcon = "M12 1L3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4zm0 10.99h7c-.53 4.12-3.28 7.79-7 8.94V12H5V6.3l7-3.11v8.8z";
    public static String CovidSurveyIcon = "M9.5,12c0,0.55-0.45,1-1,1s-1-0.45-1-1c0-0.55,0.45-1,1-1S9.5,11.45,9.5,12z M13.75,10c0.55,0,1-0.45,1-1s-0.45-1-1-1 s-1,0.45-1,1S13.2,10,13.75,10z M10.25,10c0.55,0,1-0.45,1-1s-0.45-1-1-1s-1,0.45-1,1S9.7,10,10.25,10z M10.25,14 c-0.55,0-1,0.45-1,1c0,0.55,0.45,1,1,1s1-0.45,1-1C11.25,14.45,10.8,14,10.25,14z M22,11.25v1.5c0,0.41-0.34,0.75-0.75,0.75 c-0.41,0-0.75-0.34-0.75-0.75h-1.54c-0.15,1.37-0.69,2.63-1.52,3.65l1.09,1.09l0.01-0.01c0.29-0.29,0.77-0.29,1.06,0 c0.29,0.29,0.29,0.77,0,1.06l-1.06,1.06c-0.29,0.29-0.77,0.29-1.06,0c-0.29-0.29-0.29-0.76-0.01-1.05l-1.09-1.09 c-1.02,0.82-2.27,1.36-3.64,1.51v1.54h0.01c0.41,0,0.75,0.34,0.75,0.75c0,0.41-0.34,0.75-0.75,0.75h-1.5 c-0.41,0-0.75-0.34-0.75-0.75c0-0.41,0.33-0.74,0.74-0.75v-1.55c-1.37-0.15-2.62-0.69-3.63-1.51l-1.09,1.09l0.01,0.01 c0.29,0.29,0.29,0.77,0,1.06c-0.29,0.29-0.77,0.29-1.06,0L4.4,18.54c-0.29-0.29-0.29-0.77,0-1.06c0.29-0.29,0.76-0.29,1.05-0.01 l1.09-1.09c-0.82-1.02-1.36-2.26-1.5-3.63H3.5c0,0.41-0.34,0.75-0.75,0.75C2.34,13.5,2,13.16,2,12.75v-1.5 c0-0.41,0.34-0.75,0.75-0.75c0.41,0,0.75,0.34,0.75,0.75h1.54c0.15-1.37,0.69-2.61,1.5-3.63L5.45,6.53C5.16,6.81,4.69,6.81,4.4,6.52 c-0.29-0.29-0.29-0.77,0-1.06L5.46,4.4c0.29-0.29,0.77-0.29,1.06,0c0.29,0.29,0.29,0.77,0,1.06L6.51,5.47L7.6,6.56 c1.02-0.82,2.26-1.36,3.63-1.51V3.5c-0.41-0.01-0.74-0.34-0.74-0.75C10.5,2.34,10.84,2,11.25,2h1.5c0.41,0,0.75,0.34,0.75,0.75 c0,0.41-0.34,0.75-0.75,0.75h-0.01v1.54c1.37,0.14,2.62,0.69,3.64,1.51l1.09-1.09c-0.29-0.29-0.28-0.76,0.01-1.05 c0.29-0.29,0.77-0.29,1.06,0l1.06,1.06c0.29,0.29,0.29,0.77,0,1.06s-0.77,0.29-1.06,0l-0.01-0.01L17.44,7.6 c0.82,1.02,1.37,2.27,1.52,3.65h1.54c0-0.41,0.34-0.75,0.75-0.75C21.66,10.5,22,10.84,22,11.25z M17,12c0-2.76-2.24-5-5-5 s-5,2.24-5,5s2.24,5,5,5S17,14.76,17,12z M12,11c-0.55,0-1,0.45-1,1c0,0.55,0.45,1,1,1s1-0.45,1-1C13,11.45,12.55,11,12,11z M15.5,11c-0.55,0-1,0.45-1,1c0,0.55,0.45,1,1,1s1-0.45,1-1C16.5,11.45,16.05,11,15.5,11z M13.75,14c-0.55,0-1,0.45-1,1 c0,0.55,0.45,1,1,1s1-0.45,1-1C14.75,14.45,14.3,14,13.75,14z";
    public static String foodIcon = "M18.06 22.99h1.66c.84 0 1.53-.64 1.63-1.46L23 5.05h-5V1h-1.97v4.05h-4.97l.3 2.34c1.71.47 3.31 1.32 4.27 2.26 1.44 1.42 2.43 2.89 2.43 5.29v8.05zM1 21.99V21h15.03v.99c0 .55-.45 1-1.01 1H2.01c-.56 0-1.01-.45-1.01-1zm15.03-7c0-8-15.03-8-15.03 0h15.03zM1.02 17h15v2h-15z";

    public static String PRIMARYCommentIcon = "M22 6c0-1.1-.9-2-2-2H4c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6zm-2 0l-8 5-8-5h16zm0 12H4V8l8 5 8-5v10z";
    public static String DEFAULTCommentIcon = "M20 2H4c-1.1 0-2 .9-2 2v18l4-4h14c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2zm0 14H5.17L4 17.17V4h16v12zM7 9h2v2H7zm8 0h2v2h-2zm-4 0h2v2h-2z";
    public static String UPDATECommentIcon = "M14.06 9.02l.92.92L5.92 19H5v-.92l9.06-9.06M17.66 3c-.25 0-.51.1-.7.29l-1.83 1.83 3.75 3.75 1.83-1.83c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.2-.2-.45-.29-.71-.29zm-3.6 3.19L3 17.25V21h3.75L17.81 9.94l-3.75-3.75z";
    public static String RESOLVECommentIcon = "M19.77 4.93l1.4 1.4L8.43 19.07l-5.6-5.6 1.4-1.4 4.2 4.2L19.77 4.93m0-2.83L8.43 13.44l-4.2-4.2L0 13.47l8.43 8.43L24 6.33 19.77 2.1z";

    public static String getIcon(String keyWord){
        switch (keyWord) {
            case "AudioVisual":
                return AudioVisualIcon;
            case "Computer":
                return ComputerIcon;
            case "Floral":
                return FloralIcon;
            case "Gift":
                return GiftIcon;
            case "Language":
                return LanguageIcon;
            case "Laundry":
                return LaundryIcon;
            case "Maintenance":
                return MaintenanceIcon;
            case "Medical":
                return MedicalIcon;
            case "Religious":
                return ReligiousIcon;
            case "Sanitation":
                return SanitationIcon;
            case "Security":
                return SecurityIcon;
            case "Food":
                return foodIcon;
            case "PRIMARY":
                return PRIMARYCommentIcon;
            case "DEFAULT":
                return DEFAULTCommentIcon;
            case "UPDATE":
                return UPDATECommentIcon;
            case "RESOLVE":
                return RESOLVECommentIcon;
            case "CovidSurvey":
                return CovidSurveyIcon;
            default:
                return "";
        }
    }


    public RequestListItemNewController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/request/RequestListItemNew.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currSpecificRequest = new RequestFactory().makeRequest("Maintenance");

        //Tooltips
        soundBtn.setTooltip(new Tooltip("Audio/Visual Request"));
        screenBtn.setTooltip(new Tooltip("Computer Request"));
        giftBtn.setTooltip(new Tooltip("Gift Request"));
        languageBtn.setTooltip(new Tooltip("Language Request"));
        washerBtn.setTooltip(new Tooltip("Laundry Request"));
        toolBtn.setTooltip(new Tooltip("Maintenance Request"));
        medicineBtn.setTooltip(new Tooltip("Medical Request"));
        churchBtn.setTooltip(new Tooltip("Religious Request"));
        broomBtn.setTooltip(new Tooltip("Sanitation Request"));
        shieldBtn.setTooltip(new Tooltip("Security Request"));
        foodBtn.setTooltip(new Tooltip("Food Request"));
        flowerBtn.setTooltip(new Tooltip("Floral Request"));


        //Graphic init
        typeIconSVG.setContent(getIcon("UPDATE"));
        fieldLabel= new Label();
        fieldLabel.setStyle(selectFieldLabel.getStyle());
        fieldLabel.getStyleClass().setAll(selectFieldLabel.getStyleClass());
        fieldLabel.setWrapText(true);

        RequiredFieldValidator assigneeValidator = new RequiredFieldValidator();
        assigneeValidator.setMessage("Valid Assignee Required");
        RequiredFieldValidator locationValidator = new RequiredFieldValidator();
        locationValidator.setMessage("Valid Location Required");
        editAssigneesField.getValidators().add(assigneeValidator);//Assignee and location validator setup here
        editLocationsField.getValidators().add(locationValidator);
        existingAssignee = App.userService.getEmployeeIDByType(currSpecificRequest.getRelevantRole()).keySet();
        AutoCompletionBinding<String> autoFillAssignees = TextFields.bindAutoCompletion(editAssigneesField , existingAssignee);
        longNamestoID  = App.mapService.getLongNames();
        AutoCompletionBinding<String> autoFillStart = TextFields.bindAutoCompletion(editLocationsField , longNamestoID.keySet());

        RequiredFieldValidator validator1 = new RequiredFieldValidator();
        validator1.setMessage("Title Required");
        editTitleField.getValidators().add(validator1);
        editTitleField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                editTitleField.validate();
            }
        });

        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator2.setMessage("Description Required");
        editDescriptionField.getValidators().add(validator2);
        editDescriptionField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                editDescriptionField.validate();
            }
        });

        RequiredFieldValidator validator3 = new RequiredFieldValidator();
        validator3.setMessage("Required Field");
        editDateNeededField.getValidators().add(validator3);
        editDateNeededField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                editDateNeededField.validate();
            }
        });

        RequiredFieldValidator validator4 = new RequiredFieldValidator();
        validator4.setMessage("Required Field");
        editTimeNeededField.getValidators().add(validator4);
        editTimeNeededField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                editTimeNeededField.validate();
            }
        });

        selectTypeGroup.selectedToggleProperty().addListener((o, oldVal, newVal) -> {
            JFXToggleNode target = ((JFXToggleNode)selectTypeGroup.getSelectedToggle());
            if(target==null){
                extraFieldsVBox.getChildren().clear();
                titleLabel.setText("New Request");
                updateFieldLabel();
                extraFieldsVBox.getChildren().add(selectFieldGraphic);
                typeIconSVG.setContent(getIcon("UPDATE"));
                saveButton.setDisable(true);
            }
            else{
                switchFields( ((JFXToggleNode)selectTypeGroup.getSelectedToggle()).getText());
                saveButton.setDisable(false);
            }
        });

        editAssigneesListView.setOnMouseClicked(event -> editAssigneesField.setText(editAssigneesListView.getItems().get(editAssigneesListView.getSelectionModel().getSelectedIndex())));
        editLocationsListView.setOnMouseClicked(event -> editLocationsField.setText(editLocationsListView.getItems().get(editLocationsListView.getSelectionModel().getSelectedIndex())));

    }

    /**
     * Pull from fields, and add request
     */
    public void handleSaveButton(){
        if(!editTitleField.getText().equals("") &&
            !editDescriptionField.getText().equals("") &&
            editDateNeededField.getValue() != null &&
            editTimeNeededField.getValue() != null &&
            checkSpecialFields(requestSpecificItems())){
            ArrayList<String> locationsToAdd = new ArrayList<String>();
            for(String s :editLocationsListView.getItems()){
                locationsToAdd.add(longNamestoID.get(s));
            }
            ArrayList<String> assigneesToAdd = new ArrayList<>(editAssigneesListView.getItems());


            Random rand = new Random();
            int requestID = rand.nextInt();
            String ID = Integer.toString(requestID);//make a random id

            //make components of specifc request,  then set them
        Comment primaryComment = new Comment(editTitleField.getText(), editDescriptionField.getText(),
            App.userService.getActiveUser().getUserName(), CommentType.PRIMARY);

        Request newRequest = new Request(ID, Timestamp.valueOf(LocalDateTime.of(editDateNeededField.getValue(), editTimeNeededField.getValue())),
            locationsToAdd, assigneesToAdd, primaryComment);
            App.requestService.addRequest(currSpecificRequest.setRequest(newRequest).setSpecificData(requestSpecificItems()));

            ArrayList<String> emails = new ArrayList<>();
            ArrayList<String> sms = new ArrayList<>();

            for (String assignee : assigneesToAdd){
                switch (App.userService.getPreferredContactMethod(assignee)) {
                    case "Both":
                        emails.add(App.userService.getEmail(assignee));
                        sms.add(App.userService.getPhoneNumberFromUserName(assignee));
                        break;
                    case "Email":
                        emails.add(App.userService.getEmail(assignee));
                        break;
                    case "SMS":
                        sms.add(App.userService.getPhoneNumberFromUserName(assignee));
                        break;
                }
            }
            Thread t = new Thread(() ->{
                try{
                    Platform.runLater(() -> {
                        for (String to : emails){
                            App.emailService.sendMail(to, currSpecificRequest);
                        }
                        for (String to : sms){
                            App.textingService.sendText(to, currSpecificRequest);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            t.start();

            App.newReqVBox.getChildren().clear();
            App.VBoxChanged.set(!App.VBoxChanged.get());
            App.addNewRequestToList.set(!App.addNewRequestToList.get());
            App.requestService.checkFilters.set(!App.requestService.checkFilters.getValue());

        }else if(editTitleField.getText().equals("")){
            editTitleField.validate();
        }else if(editDescriptionField.getText().equals("")){
            editDescriptionField.validate();
        }else if(editDateNeededField.getValue() != null){
            editDateNeededField.validate();
        }else if(editTimeNeededField.getValue() != null){
            editTimeNeededField.validate();
        }
    }

    private boolean checkSpecialFields(ArrayList<String> input){
        for(String s: input){
            if(s.equals("")){
                return false;
            }
        }
        return true;
    }

    public void handleCancelButton(){
            JFXDialogLayout content = new JFXDialogLayout();
            Label header = new Label("Exit without saving changes?");
            header.getStyleClass().add("headline-2");
            content.setHeading(header);
            content.getStyleClass().add("dialogue");
            JFXDialog dialog = new JFXDialog(App.throwDialogHerePane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton button1 = new JFXButton("CANCEL");
            JFXButton button2 = new JFXButton("EXIT");
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
                    App.newReqVBox.getChildren().clear();
                    App.VBoxChanged.set(!App.VBoxChanged.get());
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

    public JFXTextField[] generateSpecificFields() {

        //specificTitle.setText(currSpecificRequest.getType());
        extraFieldsVBox.getChildren().clear();
        JFXTextField[] ans = new JFXTextField[ currSpecificRequest.getSpecificFields().length];
        for(int i = 0; i <  currSpecificRequest.getSpecificFields().length; i++) {

            JFXTextField j = new JFXTextField();
            j.setPromptText( currSpecificRequest.getSpecificFields()[i]);
            j.setLabelFloat(true);
            j.setStyle("-fx-pref-width: 400px");
            j.setStyle("-fx-pref-height: 50px");
            j.setStyle("-fx-font-size: 12px");

            ans[i] = j;
            extraFieldsVBox.getChildren().add(0,j);
            Region r1 = new Region();
            r1.setPrefHeight(25);
            extraFieldsVBox.getChildren().add(0,r1);
        }
        Region r = new Region();
        r.setPrefHeight(40);
        extraFieldsVBox.getChildren().add(r);
        titleLabel.setText("New " + currSpecificRequest.getType() + " Request");
//        fieldLabel.setText("You chose the "+currSpecificRequest.getType()+" Request.");
//        extraFieldsVBox.getChildren().add(fieldLabel);
        return ans;
    }

    /**
     * Take the get values from unique fields, put it in a ArrayList
     * @return
     */
    public ArrayList<String> requestSpecificItems() {
        ArrayList<String> specifics = new ArrayList<>();
        for(JFXTextField j : specificTextFields) {
            specifics.add(j.getText());
        }
        return specifics;
    }

    public void makeListView(ArrayList<String> list, JFXListView<String> res){
        ObservableList<String> something = FXCollections.observableList(list);
        res.setItems(something);
    }

    public void addAssignee(){
        if(editAssigneesField.getText().equals("") || !existingAssignee.contains(editAssigneesField.getText())){
            editAssigneesField.validate();
        }else {
            editAssigneesListView.getItems().add(editAssigneesField.getText());
            editAssigneesField.setText("");
        }
    }

    public void deleteAssignee(){
        editAssigneesListView.getItems().remove(editAssigneesField.getText());
        editAssigneesField.setText("");
    }

    public void addLocation() {
        if (editLocationsField.getText().equals("") || !longNamestoID.containsKey(editLocationsField.getText())) {
            editLocationsField.validate();
        } else {
            try{
                //App.mapService.get
                editLocationsListView.getItems().add(editLocationsField.getText());
                editLocationsField.setText("");
            }catch(Exception e){

            }
        }
    }

    public void deleteLocation(){
        editLocationsListView.getItems().remove(editLocationsField.getText());
        editLocationsField.setText("");

    }

    /**
     * Section for new Request Buttons
     */
    public void switchFields(String type){
        currSpecificRequest = new RequestFactory().makeRequest(type);
        typeIconSVG.setContent(getIcon(type));
        specificTextFields = generateSpecificFields();
    }

    private void updateFieldLabel(){
        if(labelSwitch){
            msgCounter = Math.min(msgCounter+1, requestFieldMessages.length-1);
            String message = requestFieldMessages[msgCounter];
            if(msgCounter == requestFieldMessages.length-1){
                message = message.concat(reqTypes[new Random().nextInt((reqTypes.length-1) + 1)] + "?");
            }
            selectFieldLabel.setText(message);
        }
    }



}
