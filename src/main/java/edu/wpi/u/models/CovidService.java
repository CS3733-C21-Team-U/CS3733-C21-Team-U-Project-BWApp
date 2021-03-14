package edu.wpi.u.models;
import java.sql.Date;
import java.time.LocalDate;

import edu.wpi.u.database.CovidData;
import edu.wpi.u.database.UserData;


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

    public void addDataPoint(boolean symptomatic, LocalDate date){cd.insertData(symptomatic,date);}

    public int getSymptomatic(LocalDate targetDate){
        return cd.getNumSymptomaticFromDate(targetDate);
    }

    public int getNonSymptomatic(LocalDate targetDate){ return cd.getNumNonSymptomaticFromDate(targetDate);}

    public int getDailySurveys(LocalDate targetDate){ return cd.getNumSurveysFromDate(targetDate);}

    public int getWeeklySurveys(){ return cd.getWeeklySurveys();}

    public int getWeeklyPositiveSurveys() { return cd.getWeeklySymptomaticSurveys();}

    public double getWeeklyPositiveRates() { return cd.getWeeklySymptomaticSurveys()/cd.getWeeklySurveys();}

    public int getMonthlySurveys(){ return cd.getMonthlySurveys();}

    public int getMonthlyPositiveSurveys() { return cd.getMonthlySymptomaticSurveys();}

    public int getMonthlyPositiveRates() { return cd.getMonthlySymptomaticSurveys()/cd.getMonthlySurveys();}

}
