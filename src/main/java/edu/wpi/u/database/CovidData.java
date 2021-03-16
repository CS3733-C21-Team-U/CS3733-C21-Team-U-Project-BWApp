package edu.wpi.u.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CovidData extends Data{
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDate newDate = LocalDate.now();
    String todayDate = (newDate.format(dtf));
    // Wherever survey is: App.covidService.addSurveyResults(true, date);

    public CovidData (){
        connect();
    }

    public CovidData(String testURL){
        testConnect(testURL);
    }

    /**
     * Inserts a singular covid survey result into the database
     * Date is calculated as the date at the moment of adding
     * @param result - Symptomatic or non-symptomatic
     */
    public void insertData(boolean result){
        java.sql.Date sqlDate = java.sql.Date.valueOf(todayDate);

        String str;
        str = "insert into covidSurveyResult(symptomatic, nonsymptomatic, dateOfResults) values ( ?, ?, ?)";

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
     * Inserts a singular covid survey result into the database
     * Date is inserted (mostly for testing purposes)
     * @param result - Symptomatic or non-symptomatic
     */
    public void insertData(boolean result, LocalDate newSurveyDate){
        String specificDate = (newSurveyDate.format(dtf));
        java.sql.Date sqlDate = java.sql.Date.valueOf(specificDate);

        String str;
        str = "insert into covidSurveyResult(symptomatic, nonsymptomatic, dateOfResults) values ( ?, ?, ?)";

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
    public int getNumSymptomaticFromDate(LocalDate targetDate){

        java.sql.Date sqlDate = java.sql.Date.valueOf(targetDate.format(dtf));

        String str = "select count(symptomatic) from covidSurveyResult where dateOfResults=? and (symptomatic = true)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setDate(1,sqlDate);
            ResultSet rset = ps.executeQuery();
            rset.next();
            if(rset.getInt(1) != Integer.parseInt(String.valueOf(rset.getInt(1)))) {
                return 0;
            }
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
    public int getNumNonSymptomaticFromDate(LocalDate targetDate){

        String target = (targetDate.format(dtf));
        java.sql.Date sqlDate = java.sql.Date.valueOf(target);

        String str = "select count(nonsymptomatic) from covidSurveyResult where dateOfResults=? and (nonsymptomatic=true)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setDate(1, sqlDate);
            ResultSet rset = ps.executeQuery();
            rset.next();
            if(rset.getInt(1) != Integer.parseInt(String.valueOf(rset.getInt(1)))) {
                return 0;
            }
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
    public int getNumSurveysFromDate(LocalDate targetDate){

        String target = (targetDate.format(dtf));
        java.sql.Date sqlDate = java.sql.Date.valueOf(target);

        String str = "select count(*) from covidSurveyResult where dateOfResults=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setDate(1,sqlDate);
            ResultSet rset = ps.executeQuery();
            rset.next();
            if(rset.getInt(1) != Integer.parseInt(String.valueOf(rset.getInt(1)))) {
                return 0;
            }
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
        String str = "select count(symptomatic) from covidSurveyResult where (dateOfResults >= ?) and (dateOfResults <= ?) and (symptomatic = true)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setDate(1,sqlLastWeekDate);
            ps.setDate(2,sqlTodayDate);
            ResultSet rset = ps.executeQuery();
            rset.next();
            if(rset.getInt(1) != Integer.parseInt(String.valueOf(rset.getInt(1)))) {
                return 0;
            }
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
        String str = "select count(*) from covidSurveyResult where (dateOfResults >= ?) and (dateOfResults <= ?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setDate(1,sqlLastWeekDate);
            ps.setDate(2,sqlTodayDate);
            ResultSet rset = ps.executeQuery();
            rset.next();
            if(rset.getInt(1) != Integer.parseInt(String.valueOf(rset.getInt(1)))) {
                return 0;
            }
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
        String str = "select count(nonsymptomatic) from covidSurveyResult where (dateOfResults >= ?) and (dateOfResults <= ?) and (nonsymptomatic = true)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setDate(1,sqlLastWeekDate);
            ps.setDate(2,sqlTodayDate);
            ResultSet rset = ps.executeQuery();
            rset.next();
            if(rset.getInt(1) != Integer.parseInt(String.valueOf(rset.getInt(1)))) {
                return 0;
            }
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
        String str = "select count(nonsymptomatic) from covidSurveyResult where (dateOfResults >= ?) and (dateOfResults <= ?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setDate(1,sqlLastMonthDate);
            ps.setDate(2,sqlTodayDate);
            ResultSet rset = ps.executeQuery();
            rset.next();
            if(rset.getInt(1) != Integer.parseInt(String.valueOf(rset.getInt(1)))) {
                return 0;
            }
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
        String str = "select count(nonsymptomatic) from covidSurveyResult where (dateOfResults >= ?) and (dateOfResults <= ?) and (nonsymptomatic = true)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setDate(1,sqlLastMonthDate);
            ps.setDate(2,sqlTodayDate);
            ResultSet rset = ps.executeQuery();
            rset.next();
            if(rset.getInt(1) != Integer.parseInt(String.valueOf(rset.getInt(1)))) {
                return 0;
            }
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
        String str = "select count(symptomatic) from covidSurveyResult where (dateOfResults >= ?) and (dateOfResults <= ?) and (symptomatic = true)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setDate(1,sqlLastMonthDate);
            ps.setDate(2,sqlTodayDate);
            ResultSet rset = ps.executeQuery();
            rset.next();
            if(rset.getInt(1) != Integer.parseInt(String.valueOf(rset.getInt(1)))) {
                return 0;
            }
            return rset.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

}
