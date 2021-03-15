package edu.wpi.u.controllers.coviddashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
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
    public JFXButton WorcesterMobilityButton;
    @FXML
    public JFXButton BostonCovidButton;
    @FXML
    public JFXButton GovernerUpdateButton;


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

    /**
     * When the Boston Coronavirus Button is clicked, the browser opens to the google coronavirus dashboard for Boston
     */
    public void accessBostonChart() {
        try {
            Desktop.getDesktop().browse(new URL("https://news.google.com/covid19/map?hl=en-US&gl=US&ceid=US%3Aen&mid=%2Fm%2F0k3l5").toURI());
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * When the Governer Update Button is clicked, the browser opens to the Massachusetts governer's updates webpage
     */

    public void accessGovernerUpdate() {
        try {
            Desktop.getDesktop().browse(new URL("https://www.mass.gov/governor-updates").toURI());
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


