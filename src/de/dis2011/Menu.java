package de.dis2011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Small help class for menus
 * Add menu options by using the addEntry
 * The method show() will display the menu
 * and the given constant will be returned
 * 
 * Example:
 * Menu m = new Menu("Hauptmenü");
 * m.addEntry("Hart arbeiten", 0);
 * m.addEntry("Ausruhen", 1);
 * m.addEntry("Nach Hause gehen", 2);
 * int wahl = m.show();
 * 
 * The result of show():
 * Hauptmenü:
 * [1] Hart arbeiten
 * [2] Ausruhen
 * [3] Nach Hause gehen
 * --
 */
public class Menu {
	private String title;
	private ArrayList<String> labels = new ArrayList<String>();
	private ArrayList<Integer> returnValues = new ArrayList<Integer>();
	
	/**
	 * Constructor.
	 * @param title The title of the menus e.g "Main Menu"
	 */
	public Menu(String title) {
		super();
		this.title = title;
	}
	
	/**
	 * Adds a menu item to the menu
	 * @param label Name of the entry
	 * @param returnValue Constant, which is returned when this entry is selected
	 */
	public void addEntry(String label, int returnValue) {
		this.labels.add(label);
		this.returnValues.add(new Integer(returnValue));
	}
	
	/**
	 * Displays the menu
	 * @return the constant of the selected menu item
	 */
	public int show()  {
		int selection = -1;
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		while(selection == -1) {
			System.out.println(title+":");
			
			for(int i = 0; i < labels.size(); ++i) {
				System.out.println("["+(i+1)+"] "+labels.get(i));
			}
			
			System.out.print("-- ");
			try {
				selection = Integer.parseInt(stdin.readLine());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(selection < 1 || selection > returnValues.size()) {
				System.err.println("Invalid input!");
				selection = -1;
			} 
		}
		
		return returnValues.get(selection-1);
	}
}
