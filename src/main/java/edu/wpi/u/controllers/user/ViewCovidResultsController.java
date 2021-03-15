package edu.wpi.u.controllers.user;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.twilio.twiml.voice.Sim;
import edu.wpi.u.App;
import edu.wpi.u.requests.Comment;
import edu.wpi.u.requests.SpecificRequest;
import edu.wpi.u.users.Guest;
import edu.wpi.u.users.Role;
import edu.wpi.u.users.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class ViewCovidResultsController {
    @FXML public JFXTreeTableView<SpecificRequest> treeTableView = new JFXTreeTableView<>();

    int numInSystem = 0;
    int takenSurvey = 0;
    int completedSurvey = 0;

    public void initialize() throws IOException {

        ObservableList<SpecificRequest> requests = FXCollections.observableArrayList();
        App.requestService.getRequests().forEach(e ->{
            if (e.getType().equals("CovidSurvey")){
                requests.add(e);
            }
        });
        // todo : # of people on campus, taken the survey but haven't entered & taken survey but have entered

        numInSystem += App.userService.getUsers().size();
        for (SpecificRequest request: requests){
            if (request.getGenericRequest().isResolved()){
                takenSurvey++;
                completedSurvey++;
            }
            else {
                takenSurvey++;
            }
        }

        JFXTreeTableColumn<SpecificRequest, String> treeTableColumnName = new JFXTreeTableColumn<>("Name");
        treeTableColumnName.setCellValueFactory((TreeTableColumn.CellDataFeatures<SpecificRequest,String> param) ->{
            if (treeTableColumnName.validateValue(param)){
                return new SimpleStringProperty(param.getValue().getValue().getGenericRequest().getAuthor());
            }
            else {
                return treeTableColumnName.getComputedValue(param);
            }
        });
        treeTableColumnName.setPrefWidth(100);
        treeTableColumnName.setEditable(false);

        JFXTreeTableColumn<SpecificRequest, String> treeTableColumnVisitDate = new JFXTreeTableColumn<>("Visit date");
        treeTableColumnVisitDate.setCellValueFactory((TreeTableColumn.CellDataFeatures<SpecificRequest,String> param) ->{
            if (treeTableColumnVisitDate.validateValue(param)){
                Timestamp d = param.getValue().getValue().getGenericRequest().getDateCreated();
                String temp = d.toString() + " (" + App.prettyTime.format(d) + ")";
                return new SimpleStringProperty(temp);
            }
            else {
                return treeTableColumnVisitDate.getComputedValue(param);
            }
        });
        treeTableColumnVisitDate.setPrefWidth(280);
        treeTableColumnVisitDate.setEditable(false);

        JFXTreeTableColumn<SpecificRequest, String> treeTableColumnCovidRiskLevel = new JFXTreeTableColumn<>("COVID-19 Risk Level");
        treeTableColumnCovidRiskLevel.setCellValueFactory((TreeTableColumn.CellDataFeatures<SpecificRequest,String> param) ->{
            if (treeTableColumnCovidRiskLevel.validateValue(param)){
                return new SimpleStringProperty(param.getValue().getValue().getSpecificData().get(0));
            }
            else {
                return treeTableColumnCovidRiskLevel.getComputedValue(param);
            }
        });
        treeTableColumnCovidRiskLevel.setPrefWidth(200);
        treeTableColumnCovidRiskLevel.setEditable(false);

        JFXTreeTableColumn<SpecificRequest, String> treeTableColumnCovidTemp = new JFXTreeTableColumn<>("Body Temperature");
        treeTableColumnCovidTemp.setCellValueFactory((TreeTableColumn.CellDataFeatures<SpecificRequest,String> param) ->{
            if (treeTableColumnCovidTemp.validateValue(param)){
                return new SimpleStringProperty("Temp goes here");
            }
            else {
                return treeTableColumnCovidTemp.getComputedValue(param);
            }
        });
        treeTableColumnCovidTemp.setPrefWidth(200);
        treeTableColumnCovidTemp.setEditable(false);

        JFXTreeTableColumn<SpecificRequest, String> treeTableColumnEnteredTheBuilding = new JFXTreeTableColumn<>("Entered the Building");
        treeTableColumnEnteredTheBuilding.setCellValueFactory((TreeTableColumn.CellDataFeatures<SpecificRequest,String> param) ->{
            if (treeTableColumnEnteredTheBuilding.validateValue(param)){
                SimpleStringProperty s = new SimpleStringProperty("Not Entered");
                if (param.getValue().getValue().getGenericRequest().isResolved()){
                    s.set("Entered");
                }
                return s;
            }
            else {
                return treeTableColumnEnteredTheBuilding.getComputedValue(param);
            }
        });
        treeTableColumnEnteredTheBuilding.setPrefWidth(200);
        treeTableColumnEnteredTheBuilding.setEditable(false);

        final TreeItem<SpecificRequest> root = new RecursiveTreeItem<>(requests, RecursiveTreeObject::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        treeTableView.getColumns().setAll(treeTableColumnName, treeTableColumnVisitDate, treeTableColumnCovidRiskLevel, treeTableColumnEnteredTheBuilding);
    }

}
