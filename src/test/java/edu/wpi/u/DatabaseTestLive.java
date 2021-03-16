package edu.wpi.u;

import edu.wpi.u.database.Database;
import edu.wpi.u.exceptions.InvalidEdgeException;
import edu.wpi.u.models.CovidService;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatabaseTestLive {

    private static String testURL = "jdbc:derby:testDB";
    private static Database dbTest = Database.getDBTest();

    public static void main(String[] args){
        dbTest.dropAllValues();
        CovidService csMain = new CovidService(testURL);
        csMain.addDataPoint(true);
        csMain.addDataPoint(true);
        csMain.addDataPoint(true);
        csMain.addDataPoint(true);
        csMain.addDataPoint(true);
        csMain.addDataPoint(true);
        csMain.addDataPoint(false);
        csMain.addDataPoint(false);
        csMain.addDataPoint(false);
        csMain.addDataPoint(false);
        csMain.addDataPoint(false);

//        csMain.getSymptomatic()

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate nowDate = LocalDate.now(); // Localdate should be inserted to Data, then converted to sql date
        String todayDate = (nowDate.format(dtf));
        java.sql.Date sqlDate = java.sql.Date.valueOf(todayDate);

        LocalDate yesterday = LocalDate.of(2021,3,13);
        String yesterdayDate = (yesterday.format(dtf));
        java.sql.Date sqlDateYesterday = java.sql.Date.valueOf(yesterdayDate);

        System.out.println("Today is " + todayDate);
        System.out.println("Yesterday was " + yesterdayDate);

        csMain.addDataPoint(true, yesterday);
        csMain.addDataPoint(false, yesterday);
        csMain.addDataPoint(false, yesterday);

       System.out.println("Symptomatic today: " + csMain.getSymptomatic(nowDate));
       System.out.println("Non-symptomatic today: " + csMain.getNonSymptomatic(nowDate));
       System.out.println("Daily surveys today: " + csMain.getDailySurveys(nowDate));
       System.out.println("Weekly surveys (total): " + csMain.getWeeklySurveys());
       System.out.println("Weekly surveys (positive): " + csMain.getWeeklyPositiveSurveys());
       System.out.println("Need to add weekly positive rates");
       System.out.println("Monthly surveys (total): " + csMain.getMonthlySurveys());
       System.out.println("Monthly surveys (positive): " + csMain.getMonthlyPositiveSurveys());
       System.out.println("Need to add monthly positive rates");

    }

}
