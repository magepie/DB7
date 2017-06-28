package de.dis2011;

/**
 * Main class
 */
public class Main {
	/**
	 * Starts the application
	 */
	public static void main(String[] args) {
		defineAndPopulateWh();
	}
	
	/**
	 * Shows main menu
	 */
	public static void showMainMenu() {
		//Main menu options
		final int MENU_MAKLER = 1;
		final int MENU_ESTATE = 2;
		final int MENU_CONTRACT = 3;
		final int MENU_OWNER = 4;
		final int QUIT = 5;
		final int MAKLER_MGMT_PW = 98765;
		
		//Creating Main menu
		Menu mainMenu = new Menu("Main Menu");
		mainMenu.addEntry("Agent account management", MENU_MAKLER);
		mainMenu.addEntry("Estate management", MENU_ESTATE);
		mainMenu.addEntry("Contract management", MENU_CONTRACT);
		mainMenu.addEntry("Create new owner",MENU_OWNER);
		mainMenu.addEntry("Exit", QUIT);
		
		//Processing Input
		while(true) {
			int response = mainMenu.show();
			
			switch(response) {
				case MENU_MAKLER:
					
					break;
				case MENU_OWNER:
					
					break;
				case MENU_ESTATE:
					
					break;
				case MENU_CONTRACT:
					
					break;
				case QUIT:
					return;
			}
		}
	}
	
	private static void defineAndPopulateWh()
	{
				
	}
	
}
