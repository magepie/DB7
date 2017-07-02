package de.dis2011.data;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nxirakia on 02.07.17.
 */
public class Time {
    private Date date;
    private int day;
    private int month;
    private int year;
    private int quarter;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public void importTime(ArrayList<java.util.Date> dates) {
        System.out.println("Importing dates on time table...");
        Calendar cal = Calendar.getInstance();
        PreparedStatement pstmt =null;
        String insertSQL = "INSERT INTO time(date, day, month, year , quarter) VALUES (?,?,?,?,?)";

        Connection con = DB2ConnectionManager.getInstance().getConnection();
        try {
            for (java.util.Date d : dates) {
                setDate(new Date(d.getTime()));
                cal.setTime(d);
                setDay(cal.get(Calendar.DAY_OF_MONTH));
                setMonth(cal.get(Calendar.MONTH)+1);
                setYear(cal.get(Calendar.YEAR));
                setQuarter(cal.get(Calendar.MONTH) / 3 + 1);

                pstmt = con.prepareStatement(insertSQL);
                pstmt.setDate(1, getDate());
                pstmt.setInt(2,getDay());
                pstmt.setInt(3,getMonth());
                pstmt.setInt(4,getYear());
                pstmt.setInt(5,getQuarter());
                pstmt.executeUpdate();

            }
        pstmt.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
