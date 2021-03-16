package edu.wpi.u.models;

import edu.wpi.u.database.CovidData;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;


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

    public void addDataPoint(boolean symptomatic, LocalDate targetDate){
        cd.insertData(symptomatic,targetDate);
    }

    public int getSymptomatic(LocalDate targetDate){
        return cd.getNumSymptomaticFromDate(targetDate);
    }

    public int getNonSymptomatic(LocalDate targetDate){ return cd.getNumNonSymptomaticFromDate(targetDate);}

    public int getDailySurveys(LocalDate targetDate){ return cd.getNumSurveysFromDate(targetDate);}

    public int getWeeklySurveys(){ return cd.getWeeklySurveys();}

    public int getWeeklyPositiveSurveys() { return cd.getWeeklySymptomaticSurveys();}

    public double getWeeklyPositiveRates() {
        double weekNum = cd.getWeeklySurveys();
        double posNum = cd.getWeeklySymptomaticSurveys();
        DecimalFormat decform = new DecimalFormat("#.##");
        decform.setRoundingMode(RoundingMode.CEILING);
        if(weekNum == 0 & posNum == 0){
            return 0;
        }
        return Double.parseDouble(decform.format((posNum/weekNum) * 100));
    }

    public int getMonthlySurveys(){ return cd.getMonthlySurveys();}

    public int getMonthlyPositiveSurveys() { return cd.getMonthlySymptomaticSurveys();}

    public double getMonthlyPositiveRates() {
        double monthNum = cd.getMonthlySurveys();
        double posNum = cd.getMonthlySymptomaticSurveys();
        DecimalFormat decform = new DecimalFormat("#.##");
        decform.setRoundingMode(RoundingMode.CEILING);
        if(monthNum == 0 & posNum == 0){
            return 0;
        }
        return Double.parseDouble(decform.format((posNum/monthNum) * 100));
    }

}
