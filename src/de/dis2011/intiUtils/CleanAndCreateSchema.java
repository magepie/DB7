package de.dis2011.intiUtils;

import de.dis2011.data.DB2ConnectionManager;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by nxirakia on 01.07.17.
 */
public class CleanAndCreateSchema {
    /* Removing all existing tables */
    public void cleanUp(){
        // Get connection


        ArrayList<String> tables= new ArrayList<>();
        String dropSQL=null;
        PreparedStatement pstmt = null;
        
        tables.add("SHOP");
        tables.add("PRODUCT");
        tables.add("TIME");
        tables.add("BRANCH");
        tables.add("SALES");

        try{
            System.out.println("Performing a clean up...");
            Connection con = DB2ConnectionManager.getInstance().getConnection();
            for(String tableName:tables){
                dropSQL="DROP TABLE "+tableName;
                pstmt = con.prepareStatement(dropSQL);
                pstmt.setQueryTimeout(30);
                pstmt.executeUpdate();
            }

            pstmt.close();
            System.out.println("Clean up completed!");

        }
        catch (SQLException se) {
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void createSchema(){
        ArrayList<String> queries= new ArrayList<>();
        queries.add("CREATE TABLE SHOP (SHOP_ID INTEGER NOT NULL, SHOP_NAME VARCHAR(40) NOT NULL, PRIMARY KEY(SHOP_ID))");
        queries.add("CREATE TABLE TIME (DATE DATE NOT NULL, DAY INTEGER NOT NULL, MONTH INTEGER NOT NULL, YEAR INTEGER NOT NULL, QUARTER INTEGER NOT NULL, PRIMARY KEY(DATE))");
        queries.add("CREATE TABLE PRODUCT (PRODUCT_ID INTEGER NOT NULL, PRODUCT_NAME VARCHAR(40) NOT NULL,PRODUCT_FAMILY_ID INTEGER NOT NULL,PRODUCT_FAMILY_NAME VARCHAR(30) NOT NULL,PRODUCT_GROUP_ID INTEGER NOT NULL,PRODUCT_GROUP_NAME VARCHAR(30) NOT NULL,PRODUCT_CATEGORY_ID INTEGER NOT NULL,PRODUCT_CATEGORY_NAME VARCHAR(30) NOT NULL,PRICE DOUBLE NOT NULL, PRIMARY KEY(PRODUCT_ID))");
        queries.add("CREATE TABLE BRANCH (CITY_ID INTEGER NOT NULL, CITY_NAME VARCHAR(30) NOT NULL, STATE_ID INTEGER NOT NULL, STATE_NAME VARCHAR(30) NOT NULL,COUNTRY_ID INTEGER NOT NULL, COUNTRY_NAME VARCHAR(30) NOT NULL, PRIMARY KEY(CITY_ID))");
        queries.add("CREATE TABLE SALES (PRODUCT_ID INTEGER NOT NULL, SHOP_ID INTEGER NOT NULL, CITY_ID INTEGER NOT NULL, DATE DATE NOT NULL, NUMBER_ITEMS INTEGER NOT NULL, REVENUE REAL NOT NULL, PRIMARY KEY(PRODUCT_ID, SHOP_ID, CITY_ID, DATE))");
        queries.add("ALTER TABLE SALES ADD CONSTRAINT SHOP_KEY FOREIGN KEY (SHOP_ID) REFERENCES SHOP ADD CONSTRAINT PRODUCT_KEY FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT ADD CONSTRAINT DATE_KEY FOREIGN KEY (DATE) REFERENCES TIME ADD CONSTRAINT CITY_KEY FOREIGN KEY (CITY_ID) REFERENCES BRANCH");

        //String dropSQL=null;
        PreparedStatement pstmt = null;
        try{
            System.out.println("Creating Warehouse schema...");
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            for(String createTable:queries){
                pstmt = con.prepareStatement(createTable);
                pstmt.executeUpdate();
            }
            pstmt.close();
            System.out.println("Warehouse schema is successfully created!");

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }

}
