package edu.wpi.u.controllers.coviddashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;

public class HospitalCovidDashPageController {
    @FXML
    public JFXTextArea resultsWeekTextBox;
    @FXML
    public JFXTextArea positiveWeekTextBox;
    @FXML
    public JFXTextArea positiveWeekRatesTextBox;
    @FXML
    public JFXTextArea resultsMonthTextBox;
    @FXML
    public JFXTextArea positiveMonthTextBox;
    @FXML
    public JFXTextArea positiveMonthRatesTextBox;
    @FXML
    public BarChart PositiveRateBarChart;
    @FXML
    public BarChart NegativeRateBarChart;
    @FXML
    public JFXButton returnToMainButton;
    @FXML
    public JFXButton stateResultsButton;
    @FXML
    public JFXButton countryResultsButton;

    public void initialize() {
        resultsWeekTextBox.setText(String.valueOf(App.covidService.getWeeklySurveys()));
        positiveWeekTextBox.setText(String.valueOf(App.covidService.getWeeklyPositiveSurveys()));
        positiveWeekRatesTextBox.setText(String.valueOf(App.covidService.getWeeklyPositiveRates()));
        resultsMonthTextBox.setText(String.valueOf(App.covidService.getMonthlySurveys()));
        positiveMonthTextBox.setText(String.valueOf(App.covidService.getMonthlyPositiveSurveys()));
        positiveMonthRatesTextBox.setText(String.valueOf(App.covidService.getMonthlyPositiveRates()));

    }
}




