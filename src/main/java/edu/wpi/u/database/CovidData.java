package edu.wpi.u.database;

import org.apache.commons.lang3.StringEscapeUtils;
import soot.Local;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CovidData extends Data{
    Boolean result;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate newDate = LocalDate.now();
    String todayDate = (newDate.format(dtf));
    // Wherever survey is: App.covidService.addSurveyResults(true, date);

    public CovidData (){
        connect();
    }

    public CovidData(String testURL){
        testConnect(testURL);
    }

    public void insertData(boolean result){
        String str;
        final java.sql.Date sqlDate = java.sql.Date.valueOf(todayDate);
        str = "insert into covidSurveyResult(symptomatic, nonsymptomatic, dateOfResults) values ( ?, ?, ?)";
        //System.out.println(todayDate);;
        if(result) {
            try{
                PreparedStatement ps = conn.prepareStatement(str);
                ps.setBoolean(1, true);
                ps.setBoolean(2, false);
                ps.setObject(3, sqlDate);
                ps.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throwables.getCause();
            }
        }
        else{
            try{
                PreparedStatement ps = conn.prepareStatement(str);
                ps.setBoolean(1, false);
                ps.setBoolean(2, true);
                ps.setObject(3, sqlDate);
                ps.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throwables.getCause();
            }
        }
    }

    /**
     * Gets the number of covid results from a given day that conclude the user is symptomatic
     * @return - Number of symptomatic surveys
     */
    public int getNumSymptomaticFromDate(Date targetDate){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = (targetDate.toString().format(String.valueOf(dtf)));
        final java.sql.Date sqlDate = java.sql.Date.valueOf(todayDate);
        String str = "select count(symptomatic) from covidSurveyResult where dateOfResults = " + sqlDate ;
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rset = ps.executeQuery();
            rset.next();
            return rset.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;

    }

    /**
     * Gets the number of covid results from a given day that conclude the user is non-symptomatic
     * @return - Number of non-symptomatic surveys
     */
    public int getNumNonSymptomaticFromDate(Date targetDate){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = (targetDate.toString().format(String.valueOf(dtf)));
        final java.sql.Date sqlDate = java.sql.Date.valueOf(todayDate);
        String str = "select count(nonsymptomatic) from covidSurveyResult where dateOfResults = " + sqlDate ;
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rset = ps.executeQuery();
            rset.next();
            return rset.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * Returns total number of surveys from a given date
     * @return - Number of surveys from given day
     */
    public int getNumSurveysFromDate(Date targetDate){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = (targetDate.toString().format(String.valueOf(dtf)));
        final java.sql.Date sqlDate = java.sql.Date.valueOf(todayDate);
        String str = "select count(*) from covidSurveyResult where dateOfResults = " + sqlDate ;
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rset = ps.executeQuery();
            rset.next();
            return rset.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets the number of symptomatic survey results from the past week
     * @return int, the number of rows with symtomatic marked true
     * Olajumoke
     */
    public int getWeeklySymptomaticSurveys(){
        LocalDate newLastWeekDate = LocalDate.now().minusDays(7);
        String lastWeekDate = (newLastWeekDate.format(dtf));
        final java.sql.Date sqlTodayDate = java.sql.Date.valueOf(todayDate);
        final java.sql.Date sqlLastWeekDate = java.sql.Date.valueOf(lastWeekDate);
        String str = "select count(symptomatic) from covidSurveyResult where (dateOfResults between '" + sqlTodayDate + "' and '" + sqlLastWeekDate + "') and (symptomatic = true)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rset = ps.executeQuery();
            rset.next();
            return rset.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets the number of symptomatic survey results from the past week
     * @return int, the number of rows with symtomatic marked true
     * Olajumoke
     */
    public int getWeeklySurveys(){
        LocalDate newLastWeekDate = LocalDate.now().minusDays(7);
        String lastWeekDate = newLastWeekDate.format(dtf);
        final java.sql.Date sqlTodayDate = java.sql.Date.valueOf(todayDate);
        final java.sql.Date sqlLastWeekDate = java.sql.Date.valueOf(lastWeekDate);
        String str = "select count(symptomatic) from covidSurveyResult where dateOfResults between '" + sqlTodayDate + "' and '" + sqlLastWeekDate + "'";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rset = ps.executeQuery();
            rset.next();
            return rset.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets the number of nonsymptomatic survey results from the past week
     * @return int, the number of rows with nonsymtomatic marked true
     * Olajumoke
     */
    public int getWeeklyNonSymptomaticSurveys(){
        LocalDate newLastWeekDate = LocalDate.now().minusDays(7);
        String lastWeekDate = (newLastWeekDate.format(dtf));
        final java.sql.Date sqlTodayDate = java.sql.Date.valueOf(todayDate);
        final java.sql.Date sqlLastWeekDate = java.sql.Date.valueOf(lastWeekDate);
        String str = "select count(nonsymptomatic) from covidSurveyResult where (dateOfResults between '" + sqlTodayDate + "' and '" + sqlLastWeekDate + "') and (nonsymptomatic = true)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rset = ps.executeQuery();
            rset.next();
            return rset.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets the number of nonsymptomatic survey results from the past month
     * @return int, the number of rows with nonsymtomatic marked true
     * Olajumoke
     */
    public int getMonthlySurveys(){
        LocalDate newLastMonthDate = LocalDate.now().minusMonths(1);
        String lastMonthDate = (newLastMonthDate.format(dtf));
        final java.sql.Date sqlTodayDate = java.sql.Date.valueOf(todayDate);
        final java.sql.Date sqlLastMonthDate = java.sql.Date.valueOf(lastMonthDate);
        String str = "select count(nonsymptomatic) from covidSurveyResult where (dateOfResults between '" + sqlTodayDate + "' and '" + sqlLastMonthDate + "')";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rset = ps.executeQuery();
            rset.next();
            return rset.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets the number of nonsymptomatic survey results from the past month
     * @return int, the number of rows with nonsymtomatic marked true
     * Olajumoke
     */
    public int getMonthlyNonSymptomaticSurveys(){
        LocalDate newLastMonthDate = LocalDate.now().minusMonths(1);
        String lastMonthDate = (newLastMonthDate.format(dtf));
        final java.sql.Date sqlTodayDate = java.sql.Date.valueOf(todayDate);
        final java.sql.Date sqlLastMonthDate = java.sql.Date.valueOf(lastMonthDate);
        String str = "select count(nonsymptomatic) from covidSurveyResult where (dateOfResults between '" + sqlTodayDate + "' and '" + sqlLastMonthDate + "') and (nonsymptomatic = true)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rset = ps.executeQuery();
            rset.next();
            return rset.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }


    /**
     * Gets the number of symptomatic survey results from the past month
     * @return int, the number of rows with symtomatic marked true
     * Olajumoke
     */
    public int getMonthlySymptomaticSurveys(){
        LocalDate newLastMonthDate = LocalDate.now().minusMonths(1);
        String lastMonthDate = (newLastMonthDate.format(dtf));
        final java.sql.Date sqlTodayDate = java.sql.Date.valueOf(todayDate);
        final java.sql.Date sqlLastMonthDate = java.sql.Date.valueOf(lastMonthDate);
        String str = "select count(symptomatic) from covidSurveyResult where (dateOfResults between '" + sqlTodayDate + "' and '" + sqlLastMonthDate + "') and (symptomatic = true)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rset = ps.executeQuery();
            rset.next();
            return rset.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

}
