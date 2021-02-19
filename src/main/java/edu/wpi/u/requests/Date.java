package edu.wpi.u.requests;
import java.util.Calendar;

public class Date {
    private int day, month, year;

    public Date() { //Gives today's date by default
        this.month = Calendar.MONTH;
        this.day = Calendar.DAY_OF_WEEK_IN_MONTH;
        this.year = Calendar.YEAR;
    }
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String toString() {
        String[] monthToWord =
                {"January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "Novemeber", "December"};
        return monthToWord[month-1] + " " + day + ", 202" + year;
    }

    public static void main(String[] args) {
        Date d = new Date();
        System.out.println(d);
    }
}
