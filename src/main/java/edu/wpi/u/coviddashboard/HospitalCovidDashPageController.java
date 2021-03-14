package edu.wpi.u.coviddashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.sql.Date;
import java.time.LocalDate;
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
    public BarChart<Date, Double> TotalResultBarChart;
    @FXML
    public BarChart<Date, Double> TotalPositiveBarChart;
    @FXML
    public JFXButton returnToMainButton;
    @FXML
    public JFXButton stateResultsButton;
    @FXML
    public JFXButton countryResultsButton;

    public List<LocalDate> datesBetweenNowAndLastMonth (){
        long interval = ChronoUnit.DAYS.between(LocalDate.now().minusMonths(1), LocalDate.now());
        List<LocalDate> listOfDates = Stream.iterate(LocalDate.now().minusMonths(1), date -> date.plusDays(1))
                .limit(interval)
                .collect(Collectors.toList());
        return listOfDates;
    }


    public void initialize() {
        //getting covid values and setting them to their text boxes
        resultsWeekTextBox.setText(String.valueOf(App.covidService.getWeeklySurveys()));
        positiveWeekTextBox.setText(String.valueOf(App.covidService.getWeeklyPositiveSurveys()));
        positiveWeekRatesTextBox.setText(String.valueOf(App.covidService.getWeeklyPositiveRates()));
        resultsMonthTextBox.setText(String.valueOf(App.covidService.getMonthlySurveys()));
        positiveMonthTextBox.setText(String.valueOf(App.covidService.getMonthlyPositiveSurveys()));
        positiveMonthRatesTextBox.setText(String.valueOf(App.covidService.getMonthlyPositiveRates()));

        XYChart.Series totalResultSeries = new XYChart.Series();
        for(LocalDate dataPoint : datesBetweenNowAndLastMonth()){
            totalResultSeries.getData().add(new XYChart.Data(dataPoint, App.covidService.getDailySurveys(Date.valueOf(dataPoint))));
        }

        TotalResultBarChart.getData().add(totalResultSeries);

        XYChart.Series totalPositiveSeries = new XYChart.Series();
        for(LocalDate dataPoint : datesBetweenNowAndLastMonth()){
            totalResultSeries.getData().add(new XYChart.Data(dataPoint, App.covidService.getSymptomatic(Date.valueOf(dataPoint))));
        }

        TotalPositiveBarChart.getData().add(totalPositiveSeries);

    }

}




