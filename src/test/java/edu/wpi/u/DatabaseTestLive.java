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
        System.out.println("Got to main");

//        csMain.getSymptomatic()

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate newDate = LocalDate.now();
        String todayDate = (newDate.format(dtf));

        System.out.println(todayDate);


    }

}
