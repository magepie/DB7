package de.dis2011;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.dis2011.data.Branch;
import de.dis2011.data.DB2ConnectionManager;
import de.dis2011.data.Product;
import de.dis2011.data.Shop;
import de.dis2011.intiUtils.CleanAndCreateSchema;
import de.dis2011.intiUtils.ImportData;

/**
 * Main class
 */
public class Main {
	private static final int YEAR = 0;
	private static final int QUARTER = 1;
	private static final int MONTH = 2;
	private static final int DAY = 3;
	private static final int COUNTRY = 0;
	private static final int STATE = 1;
	private static final int CITY = 2;
	
	private static int time_level = YEAR; // Year
	private static int loc_level = COUNTRY; // Country
	/**
	 * Starts the application
	 */
	public static void main(String[] args) {
		//defineAndPopulateWh();
		mainMenu();
	}


	private static void defineAndPopulateWh()
	{
		/*CleanAndCreateSchema cs = new CleanAndCreateSchema();
		cs.cleanUp();
		cs.createSchema();

		System.out.println("******************************************");

		ImportData i = new ImportData();
		i.importData();*/

	}
	
	private static void mainMenu()
	{
		final int T_SLICE = 0;
		final int L_SLICE = 1;
		
		Menu mMenu = new Menu("Main");
		mMenu.addEntry("Temporal slicing", T_SLICE);
		mMenu.addEntry("Location slicing", L_SLICE);
		int sel;
		while (true){
			printCube();
			sel = mMenu.show();
			switch (sel){
			case (T_SLICE):
				timeMenu();
				break;
			case (L_SLICE):
				locMenu();
				break;
			}		
		}
	}
	
	private static void timeMenu()
	{
		final int T_DAY = 1;
		final int T_MONTH = 2;
		final int T_QUARTER = 3;
		final int T_YEAR = 4;
		final int T_BACK = 0;
		
		Menu mMenu = new Menu("Main");
		mMenu.addEntry("Per day", T_DAY);
		mMenu.addEntry("Per month", T_MONTH);
		mMenu.addEntry("Per quarter", T_QUARTER);
		mMenu.addEntry("Per year", T_YEAR);
		mMenu.addEntry("BACK", T_BACK);
		// display the main table
		int sel;
		while (true){
			printCube();
			sel = mMenu.show();
			switch (sel){
			case (T_DAY):	
				time_level = DAY;
				break;
			case (T_MONTH):
				time_level = MONTH;
				break;
			case (T_QUARTER):
				time_level = QUARTER;
				break;
			case (T_YEAR):
				time_level = YEAR;
				break;
			case (T_BACK):
				return;
			}
		}
	}
	
	private static void locMenu()
	{
		final int L_CITY = 1;
		final int L_STATE = 2;
		final int L_COUNTRY = 3;
		final int L_BACK = 0;
		
		Menu mMenu = new Menu("Main");
		mMenu.addEntry("Per city", L_CITY);
		mMenu.addEntry("Per state", L_STATE);
		mMenu.addEntry("Per country", L_COUNTRY);
		mMenu.addEntry("BACK", L_BACK);
		int sel;
		while (true){
			printCube();
			sel = mMenu.show();
			switch (sel){
			case (L_CITY):
				loc_level = CITY;
				break;
			case (L_STATE):
				loc_level = STATE;
				break;
			case (L_COUNTRY):
				loc_level = COUNTRY;
				break;
			case (L_BACK):
				return;
			}
		}
	}
	
	private static void printCube(){
		String specifics = "";
		
		switch (loc_level){
			case CITY:
				specifics += "B.City_name, ";
				break;
			case STATE:
				specifics += "B.State_name, ";
				break;
			case COUNTRY:
				specifics += "B.Country_name, ";
				break;
		}
		
		switch (time_level){
		case DAY:
			specifics += "T.Day ";
			break;
		case MONTH:
			specifics += "T.Month ";
			break;
		case QUARTER:
			specifics += "T.Quarter ";
			break;
		case YEAR:
			specifics += "T.Year ";
			break;
		}
		

		//String selectSQL = "select P.Product_name, T.Quarter, T.Year, B.CITY_NAME, SUM(Revenue) AS REV from SALES as S inner join Time as T on (S.Date=T.Date) inner join Branch as B on (S.City_id=B.City_ID) inner join Product as P on (S.Product_id = P.Product_ID) GROUP BY (T.Year, T.Quarter, B.City_name, P.Product_name)"
				//+ "			ORDER BY B.CITY_NAME, T.Year, T.Quarter, P.Product_name";
		String selectSQL = "select " + specifics + ", SUM(Number_items) as SOLD, SUM(Revenue) AS REV from SALES as S inner join Time as T on (S.Date=T.Date) inner join Branch as B on (S.City_id=B.City_ID) inner join Product as P on (S.Product_id = P.Product_ID) GROUP BY ROLLUP (" + specifics + ")"
		+ "			ORDER BY " + specifics + " ASC";
		Connection con = DB2ConnectionManager.getInstance().getConnection();
        
        //PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
        //preparedStatement.setInt(1, 1001);
        //ResultSet rs = preparedStatement.executeQuery(selectSQL);
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ResultSet rs = stmt.executeQuery(selectSQL);
			while(rs.next()){
				String data = "";
				switch (loc_level){
					case CITY:
						data = rs.getString("City_name");
						break;
					case STATE:
						data = rs.getString("State_name");
						break;
					case COUNTRY:
						data = rs.getString("Country_name");
						break;
				}
				if (data == null)
				{
					data = "---";
				}
				System.out.print(String.format("%-20s", data) + "\t|");
				
				int dat;
				switch (time_level){
					case DAY:
						dat = rs.getInt("Day");
						if (dat > 0){
							System.out.print(String.format("%-20s",dat));
						}
						else
						{
							System.out.print(String.format("%-20s","-----"));
						}
						
						break;
					case MONTH:
						dat = rs.getInt("Month");
						if (dat > 0){
							System.out.print(String.format("%-20s",dat));
						}
						else
						{
							System.out.print(String.format("%-20s","-----"));
						}
						break;
					case QUARTER:
						dat = rs.getInt("Quarter");
						if (dat > 0){
							System.out.print(String.format("%-20s",dat + " Q "));
						}
						else
						{
							System.out.print(String.format("%-20s","-----"));
						}
						break;
					case YEAR:
						dat = rs.getInt("Year");
						if (dat > 0){
							System.out.print(String.format("%-20s",dat));
						}
						else
						{
							System.out.print(String.format("%-20s","-----"));
						}
						break;
					}
				System.out.print("|");
				
		         //String name  = rs.getString("Product_name");
		         int sold  = rs.getInt("SOLD");
		         float rev = rs.getFloat("REV");

		         //System.out.print(name + "\t\t\t|");
		         System.out.print(String.format("%20s", sold + " items\t|"));
		         System.out.println(String.format("%20s",rev));
		      }
		      rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
