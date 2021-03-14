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

    public void initialize() throws IOException {

        ObservableList<SpecificRequest> requests = FXCollections.observableArrayList();
        requests.addAll(App.requestService.getRequests());

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
                String temp = d.toString() + " (" + App.p.format(d) + ")";
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
                if (param.getValue().getValue().getSpecificData().get(1).equals("Temperature Not Taken")){
                    return new SimpleStringProperty("");
                }
                return new SimpleStringProperty(param.getValue().getValue().getSpecificData().get(1));
            }
            else {
                return treeTableColumnCovidTemp.getComputedValue(param);
            }
        });
        treeTableColumnCovidTemp.setPrefWidth(200);
        treeTableColumnCovidTemp.setEditable(false);


        final TreeItem<SpecificRequest> root = new RecursiveTreeItem<>(requests, RecursiveTreeObject::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        treeTableView.getColumns().setAll(treeTableColumnName, treeTableColumnVisitDate, treeTableColumnCovidRiskLevel, treeTableColumnCovidTemp);
    }

}
