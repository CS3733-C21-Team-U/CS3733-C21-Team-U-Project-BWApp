package edu.wpi.u.models;

import edu.wpi.u.database.CovidData;

import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;


public class CovidService {
    static CovidData cd;

    public CovidService(){
        cd = new CovidData();
    }

    public CovidService(String testURL){
        cd = new CovidData(testURL);
    }

    public void addDataPoint(boolean symptomatic){
        cd.insertData(symptomatic);
    }

    public int getSymptomatic(Date targetDate){
        return cd.getNumSymptomaticFromDate(targetDate);
    }

    public int getNonSymptomatic(Date targetDate){ return cd.getNumNonSymptomaticFromDate(targetDate);}

    public int getDailySurveys(Date targetDate){ return cd.getNumSurveysFromDate(targetDate);}

    public int getWeeklySurveys(){ return cd.getWeeklySurveys();}

    public int getWeeklyPositiveSurveys() { return cd.getWeeklySymptomaticSurveys();}

    public double getWeeklyPositiveRates() {
        double weekNum = cd.getWeeklySurveys();
        double posNum = cd.getWeeklySymptomaticSurveys();
        DecimalFormat decform = new DecimalFormat("#.##");
        decform.setRoundingMode(RoundingMode.CEILING);
        return Double.parseDouble(decform.format(posNum/weekNum));
    }

    public int getMonthlySurveys(){ return cd.getMonthlySurveys();}

    public int getMonthlyPositiveSurveys() { return cd.getMonthlySymptomaticSurveys();}

    public double getMonthlyPositiveRates() {
        double monthNum = cd.getMonthlySurveys();
        double posNum = cd.getMonthlySymptomaticSurveys();
        DecimalFormat decform = new DecimalFormat("#.##");
        decform.setRoundingMode(RoundingMode.CEILING);
        return Double.parseDouble(decform.format(posNum/monthNum));
    }

}
