package edu.wpi.u.controllers.user;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.u.App;
import edu.wpi.u.requests.SpecificRequest;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

import java.io.IOException;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewCovidResultsController {
    @FXML
    public JFXTreeTableView<SpecificRequest> treeTableView = new JFXTreeTableView<>();
    public Label funnelValue1;
    public Label funnelSubtitle1;
    public Label funnelText1;
    public Label funnelValue2;
    public Label funnelText2;
    public Label funnelValue3;
    public Label resultsWeekTextBox;
    public Label resultsMonthTextBox;
    public Label positiveMonthTextBox;
    public Label positiveWeekRatesTextBox;
    public Label positiveWeekTextBox;
    public Label positiveMonthRatesTextBox;
    public BarChart NegativeTotalTestBarChart;
    public BarChart PositiveTestBarChart;

    int numInSystem = 0;
    int takenSurvey = 0;
    int completedSurvey = 0;

    public void initialize() throws IOException {


        funnelSubtitle1.setText(App.userService.getGuests().size() + " Guests and " + App.userService.getPatients().size() + " Patients and " + App.userService.getEmployees().size() + " Employees");

        ObservableList<SpecificRequest> requests = FXCollections.observableArrayList();
        App.requestService.getRequests().forEach(e -> {
            if (e.getType().equals("CovidSurvey")) {
                requests.add(e);
            }
        });


        numInSystem = App.userService.getUsers().size();
        for (SpecificRequest request: requests){
            if (request.getGenericRequest().isResolved()){
                takenSurvey++;
                completedSurvey++;
            } else {
                takenSurvey++;
            }
        }

        funnelValue1.setText(String.valueOf(numInSystem));
        funnelValue2.setText(String.valueOf(takenSurvey));
        funnelValue3.setText(String.valueOf(completedSurvey));


        if(numInSystem != 0 && takenSurvey != 0 && completedSurvey != 0){
            funnelText1.setText((int) Math.floor(((double) takenSurvey / (double) numInSystem) * 100.0) +"% have arrvied at a kisosk");
            funnelText2.setText((int) Math.floor(((double) completedSurvey / (double) takenSurvey) * 100.0) +"% have seen a nurse");
        }

        JFXTreeTableColumn<SpecificRequest, String> treeTableColumnName = new JFXTreeTableColumn<>("Name");
        treeTableColumnName.setCellValueFactory((TreeTableColumn.CellDataFeatures<SpecificRequest, String> param) -> {
            if (treeTableColumnName.validateValue(param)) {
                return new SimpleStringProperty(param.getValue().getValue().getGenericRequest().getAuthor());
            } else {
                return treeTableColumnName.getComputedValue(param);
            }
        });
        treeTableColumnName.setPrefWidth(100);
        treeTableColumnName.setEditable(false);

        JFXTreeTableColumn<SpecificRequest, String> treeTableColumnVisitDate = new JFXTreeTableColumn<>("Visit date");
        treeTableColumnVisitDate.setCellValueFactory((TreeTableColumn.CellDataFeatures<SpecificRequest, String> param) -> {
            if (treeTableColumnVisitDate.validateValue(param)) {
                Timestamp d = param.getValue().getValue().getGenericRequest().getDateCreated();
                String temp = d.toString() + " (" + App.prettyTime.format(d) + ")";
                return new SimpleStringProperty(temp);
            } else {
                return treeTableColumnVisitDate.getComputedValue(param);
            }
        });
        treeTableColumnVisitDate.setPrefWidth(280);
        treeTableColumnVisitDate.setEditable(false);

        JFXTreeTableColumn<SpecificRequest, String> treeTableColumnCovidRiskLevel = new JFXTreeTableColumn<>("COVID-19 Risk Level");
        treeTableColumnCovidRiskLevel.setCellValueFactory((TreeTableColumn.CellDataFeatures<SpecificRequest, String> param) -> {
            if (treeTableColumnCovidRiskLevel.validateValue(param)) {
                return new SimpleStringProperty(param.getValue().getValue().getSpecificData().get(0));
            } else {
                return treeTableColumnCovidRiskLevel.getComputedValue(param);
            }
        });
        treeTableColumnCovidRiskLevel.setPrefWidth(150);
        treeTableColumnCovidRiskLevel.setEditable(false);

        JFXTreeTableColumn<SpecificRequest, String> treeTableColumnCovidTemp = new JFXTreeTableColumn<>("Body Temperature");
        treeTableColumnCovidTemp.setCellValueFactory((TreeTableColumn.CellDataFeatures<SpecificRequest, String> param) -> {
            if (treeTableColumnCovidTemp.validateValue(param)) {
                return new SimpleStringProperty(param.getValue().getValue().getSpecificData().get(1));
            } else {
                return treeTableColumnCovidTemp.getComputedValue(param);
            }
        });
        treeTableColumnCovidTemp.setPrefWidth(150);
        treeTableColumnCovidTemp.setEditable(false);

        JFXTreeTableColumn<SpecificRequest, String> treeTableColumnEnteredTheBuilding = new JFXTreeTableColumn<>("Entered the Building");
        treeTableColumnEnteredTheBuilding.setCellValueFactory((TreeTableColumn.CellDataFeatures<SpecificRequest, String> param) -> {
            if (treeTableColumnEnteredTheBuilding.validateValue(param)) {
                SimpleStringProperty s = new SimpleStringProperty("Not Entered");
                if (param.getValue().getValue().getGenericRequest().isResolved()) {
                    s.set("Entered");
                }
                return s;
            } else {
                return treeTableColumnEnteredTheBuilding.getComputedValue(param);
            }
        });
        treeTableColumnEnteredTheBuilding.setPrefWidth(200);
        treeTableColumnEnteredTheBuilding.setEditable(false);

        final TreeItem<SpecificRequest> root = new RecursiveTreeItem<>(requests, RecursiveTreeObject::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
        treeTableView.getColumns().setAll(treeTableColumnName, treeTableColumnVisitDate, treeTableColumnCovidRiskLevel, treeTableColumnCovidTemp, treeTableColumnEnteredTheBuilding);


        //resultsWeekTextBox.setText(String.valueOf(App.covidService.getWeeklySurveys()));
        ArrayList<SpecificRequest> thisWeek = new ArrayList<>();
        ArrayList<SpecificRequest> thisMonth = new ArrayList<>();

        for (SpecificRequest covidRequest : App.requestService.getRequests()) {
            if (covidRequest.getType().equals("CovidSurvey")) {
                if (covidRequest.getGenericRequest().getDateCreated().before(new Timestamp(System.currentTimeMillis())) && covidRequest.getGenericRequest().getDateCreated().after(new Timestamp(System.currentTimeMillis() - (86400000 * 7)))) {
                    thisWeek.add(covidRequest);
                }
            }
            resultsWeekTextBox.setText(String.valueOf(thisWeek.size()));
        }
        for (SpecificRequest covidRequest : App.requestService.getRequests()) {
            if (covidRequest.getType().equals("CovidSurvey")) {
                if (covidRequest.getGenericRequest().getDateCreated().before(new Timestamp(System.currentTimeMillis())) && covidRequest.getGenericRequest().getDateCreated().after(new Timestamp(System.currentTimeMillis() - (86400000L * 30)))) {
                    thisMonth.add(covidRequest);
                }
            }
            resultsMonthTextBox.setText(String.valueOf(thisMonth.size()));
        }
        int count = 0;
        for (SpecificRequest covidRequest : thisWeek) {
            if (!covidRequest.getSpecificData().get(0).equals("None")) {
                count++;
            }
            positiveWeekTextBox.setText(String.valueOf(count));
        }

        int count2 = 0;
        for (SpecificRequest covidRequest : thisMonth) {
            if (!covidRequest.getSpecificData().get(0).equals("None")) {
                count2++;
            }
            positiveMonthTextBox.setText(String.valueOf(count2));
        }

        double weekRate = 1;
        if (thisWeek.size() != 0) {
            weekRate = (double) count / thisWeek.size();
        } else {
            weekRate = 0;
        }
        DecimalFormat decform = new DecimalFormat("#.##");
        decform.setRoundingMode(RoundingMode.CEILING);
        if (weekRate == 0) {
            positiveWeekRatesTextBox.setText(String.valueOf(0));
        }
        positiveWeekRatesTextBox.setText(String.valueOf(Double.parseDouble(decform.format(weekRate * 100))) + "%");
        double monthRate = 1;
        if (thisMonth.size() != 0) {
            monthRate = (double) count2 / thisMonth.size();
        } else {
            monthRate = 0;
        }
        decform.setRoundingMode(RoundingMode.CEILING);
        if (monthRate == 0) {
            positiveMonthRatesTextBox.setText(String.valueOf(0));
        }
        positiveMonthRatesTextBox.setText(String.valueOf(Double.parseDouble(decform.format(weekRate * 100))) + "%");

       SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");

        XYChart.Series totalNoRiskSeries = new XYChart.Series();

        HashMap<LocalDate, Integer> saveValue = new HashMap<>();


        for (SpecificRequest noRisk : thisMonth) {
            if (noRisk.getSpecificData().get(0).equals("None")) {
                if (saveValue.containsKey(noRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate())) {
                    totalNoRiskSeries.getData().add(new XYChart.Data(noRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), saveValue.get(noRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate()) + 1));
                    saveValue.put(noRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate(), saveValue.get(noRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate()) + 1);
                } else {
                    totalNoRiskSeries.getData().add(new XYChart.Data(noRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), 1));
                    saveValue.put(noRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate(), 1);
                }
            }
        }
            NegativeTotalTestBarChart.getData().add(totalNoRiskSeries);

            XYChart.Series totalHighRiskSeries = new XYChart.Series();

            HashMap<LocalDate, Integer> saveValue2 = new HashMap<>();

            for (SpecificRequest highRisk : thisMonth) {
                if (highRisk.getSpecificData().get(0).equals("High")) {
                    if (saveValue2.containsKey(highRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate())) {
                        totalHighRiskSeries.getData().add(new XYChart.Data(highRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), saveValue2.get(highRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate()) + 1));
                        saveValue2.put(highRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate(), saveValue2.get(highRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate()) + 1);
                    } else {
                        totalHighRiskSeries.getData().add(new XYChart.Data(highRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), 1));
                        saveValue2.put(highRisk.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate(), 1);
                    }
                }

            }

        PositiveTestBarChart.getData().add(totalHighRiskSeries);

        }
    }

