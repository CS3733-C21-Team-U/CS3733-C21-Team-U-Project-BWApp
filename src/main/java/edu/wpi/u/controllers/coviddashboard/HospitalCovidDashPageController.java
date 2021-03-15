package edu.wpi.u.controllers.coviddashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public BarChart<java.sql.Date, Double> TotalResultBarChart;
    @FXML
    public BarChart<java.sql.Date, Double> TotalPositiveBarChart;
    @FXML
    public JFXButton returnToMainButton;
    @FXML
    public JFXButton stateResultsButton;
    @FXML
    public JFXButton countryResultsButton;

    public void initialize() {
        resultsWeekTextBox.setText(String.valueOf(App.covidService.getWeeklySurveys()));
        positiveWeekTextBox.setText(String.valueOf(App.covidService.getWeeklyPositiveSurveys()));
        positiveWeekRatesTextBox.setText(App.covidService.getWeeklyPositiveRates() + "%");
        resultsMonthTextBox.setText(String.valueOf(App.covidService.getMonthlySurveys()));
        positiveMonthTextBox.setText(String.valueOf(App.covidService.getMonthlyPositiveSurveys()));
        positiveMonthRatesTextBox.setText(App.covidService.getMonthlyPositiveRates() + "%");

        long interval = ChronoUnit.DAYS.between(LocalDate.now().minusMonths(1), LocalDate.now());
        List<LocalDate> listOfDates = Stream.iterate(LocalDate.now().minusMonths(1), date -> date.plusDays(1))
                .limit(interval)
                .collect(Collectors.toList());


        XYChart.Series totalResultSeries = new XYChart.Series();
        for (LocalDate dataPoint : listOfDates) {
            totalResultSeries.getData().add(new XYChart.Data(dataPoint.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), App.covidService.getDailySurveys(dataPoint)));
        }

        TotalResultBarChart.getData().add(totalResultSeries);

        XYChart.Series totalPositiveSeries = new XYChart.Series();
        for (LocalDate dataPoint : listOfDates) {
            totalPositiveSeries.getData().add(new XYChart.Data(dataPoint.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), App.covidService.getSymptomatic(dataPoint)));
        }

        TotalPositiveBarChart.getData().add(totalPositiveSeries);

    }
}




