package edu.wpi.u.models;
import java.sql.Date;

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

    public double calculatePercentageSymptomatic(Date targetDate){
        return 0.0;
    }

    public int getSymptomatic(Date targetDate){
        return cd.getNumSymptomaticFromDate(targetDate);
    }

    public int getNonSymptomatic(Date targetDate){return 0;}

    public int getDailySurveys(Date targetDate){return 0;}

    public int getWeeklySurveys(Date targetDate){return 0;}

    public int getMonthlySurveys(Date targetDate){return 0;}

}
