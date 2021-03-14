package edu.wpi.u.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CovidData extends Data{
    Boolean result;
    // Wherever survey is: App.covidService.addSurveyResults(true, date);

    public CovidData (){
        connect();
    }

    public CovidData(String testURL){
        testConnect(testURL);
    }

    public void insertData(boolean result){
        String str;
        Date todayDate = Date.valueOf(String.valueOf(java.time.LocalDateTime.now()));
        todayDate.toLocalDate();
        if(result) {
            str = "insert into covidSurveyResult(symptomatic, nonsymptomatic," +  todayDate + ") values ( true, false ," + todayDate + ")";
        }
        else{
            System.out.println("Reached Here");
            str = "insert into covidSurveyResult(symptomatic, nonsymptomatic," +  todayDate + ") values (false , true ," + todayDate + ")";
        }
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.execute();
        } catch (SQLException throwables) {
            System.out.println("Reached Exception");
            throwables.printStackTrace();
            throwables.getCause();
        }
    }

    /**
     * Gets the number of covid results from a given day that conclude the user is symptomatic
     * @return - Number of symptomatic surveys
     */
    public int getNumSymptomaticFromDate(Date targetDate){
        return 0;
    }

    /**
     * Gets the number of covid results from a given day that conclude the user is non-symptomatic
     * @return - Number of non-symptomatic surveys
     */
    public int getNumNonSymptomaticFromDate(Date targetDate){
        return 0;
    }

    /**
     * Returns total number of surveys from a given date
     * @return - Number of surveys from given day
     */
    public int getNumSurveysFromDate(Date targetDate){
        return 0;
    }



}
