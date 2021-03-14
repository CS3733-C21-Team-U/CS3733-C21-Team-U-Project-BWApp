package edu.wpi.u.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CovidData extends Data{
    Boolean result;
    public CovidData(){
        insertData(result);

    }

    public void insertData(boolean result){
        //nuvnunfiushuifes
        String str;
        Date todayDate = Date.valueOf(String.valueOf(java.time.LocalDateTime.now()));
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
}
