package de.dis2011;

import de.dis2011.data.Makler;
import de.dis2011.data.Contract;
import de.dis2011.data.Apartment;
import de.dis2011.data.House;

/**
 * Main class
 */
public class Main {
	/**
	 * Starts the application
	 */
	public static void main(String[] args) {
		showMainMenu();
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
					int pw = Integer.parseInt((FormUtil.readString("Enter agent management password")));
					if (pw == MAKLER_MGMT_PW)
					{
						showMaklerMenu();
					}
					else
					{
						System.out.println("Password incorrect");
						break;
					}
					break;
				case MENU_OWNER:
					newOwner();
					break;
				case MENU_ESTATE:
					String login = FormUtil.readString("Login");
					Makler m = new Makler();
					m.setLogin(login);
					String passw = new String(FormUtil.readString("Password"));
					if (passw.equals(m.returnPassword()))
					{
						showEstateMenu();
					}
					else
					{
						System.out.println("Wrong password!");;
					}
					break;
				case MENU_CONTRACT:
					String l = FormUtil.readString("Login");
					Makler makler = new Makler();
					makler.setLogin(l);
					String pass = new String(FormUtil.readString("Password"));
					if (pass.equals(makler.returnPassword()))
					{
						showContractMenu();
					}
					else
					{
						System.out.println("Wrong password!");;
					}
					break;
				case QUIT:
					return;
			}
		}
	}
	
	/**
	 * Shows the Makler administration
	 */
	public static void showMaklerMenu() {
		//Main menu options
		final int NEW_MAKLER = 0;
		final int EDIT_MAKLER = 1;
		final int DELETE_MAKLER = 2;
		final int BACK = 3;
		
		//Makler administration menu
		Menu maklerMenu = new Menu("Agent account management");
		maklerMenu.addEntry("New agent", NEW_MAKLER);
		maklerMenu.addEntry("Edit agent", EDIT_MAKLER);
		maklerMenu.addEntry("Remove agent", DELETE_MAKLER);
		maklerMenu.addEntry("Return to the main menu", BACK);
		
		//Processing input
		while(true) {
			int response = maklerMenu.show();
			
			switch(response) {
				case NEW_MAKLER:
					newMakler();
					break;
				case DELETE_MAKLER:
					deleteMakler();
					break;
				case EDIT_MAKLER:
					editMakler();
					break;
				case BACK:
					return;
			}
		}
	}
	
	/**
	 * Shows the estate administration
	 */
	public static void showEstateMenu() {
		//Main menu options
		final int APT = 0;
		final int HOUSE = 1;
		final int BACK = 2;
		
		//Estate administration menu
		Menu estateMenu = new Menu("Agent account management");
		estateMenu.addEntry("Manage an apartment", APT);
		estateMenu.addEntry("Manage a house", HOUSE);
		estateMenu.addEntry("Return to the main menu", BACK);
		
		//Processing input
		while(true) {
			int response = estateMenu.show();
			
			switch(response) {
				case APT:
					showAptMenu();
					break;
				case HOUSE:
					showHouseMenu();
					break;
				case BACK:
					return;
			}
		}
	}
	
	/**
	 * Shows the estate administration
	 */
	public static void showAptMenu() {
		//Main menu options
		final int ADD_APT = 0;
		final int EDIT_APT = 1;
		final int REMOVE_APT = 2;
		final int BACK = 3;
		
		final int EDIT_ADDR = 1;
		final int EDIT_AREA = 2;
		final int EDIT_ROOM_NO = 4;
		final int EDIT_RENT = 7;
		final int EDIT_EBK = 6;
		final int EDIT_BALC = 5;
		final int EDIT_FLOOR = 3;
		final int EDIT_AGENT = 8;
		final int EDIT_BACK = 0;
		
		//Estate administration menu
		Menu aptMenu = new Menu("Apartment account management");
		aptMenu.addEntry("Add an apartment", ADD_APT);
		aptMenu.addEntry("Edit an apartment", EDIT_APT);
		aptMenu.addEntry("Remove an apartment", REMOVE_APT);
		aptMenu.addEntry("Return to the main menu", BACK);
		
		//Processing input
		while(true) {
			int response = aptMenu.show();
			Apartment apt = new Apartment();
			switch(response) {
				case ADD_APT:
					apt.setAddress(FormUtil.readString("Address"));
					apt.setArea(Float.parseFloat(FormUtil.readString("Area")));
					apt.setFloor(Integer.parseInt(FormUtil.readString("Floor ")));
					apt.setRoomNumber(Integer.parseInt(FormUtil.readString("Number of rooms ")));
					apt.setBalconyIncl(Integer.parseInt(FormUtil.readString("Balcony? (1 if present) ")));
					apt.setEbkIncl(Integer.parseInt(FormUtil.readString("Built in kitchen? (1 is included)")));
					apt.setRent(Integer.parseInt(FormUtil.readString("Rent ")));
					apt.save();
					System.out.println("Apartment with ID "+ apt.getId() +" was added.");
					break;
				case EDIT_APT:
					apt.setId(Integer.parseInt(FormUtil.readString("Apartment ID")));
					apt.load();
					System.out.println("ID: "+ apt.getId() +"|| Address[1]: " + apt.getAddress() + "|| Area[2]: " + apt.getArea() + "\r\n|| Floor[3]: " + apt.getFloor() + "|| Rooms[4]: " + apt.getRoomNumber()
						+ "|| Balcony[5]: " + apt.getBalconyIncl() + "|| Built-in kitchen[6]: " +apt.getEbkIncl()+"\r\nRent[7]: "+apt.getRent()+"||Agent[8]:"+apt.getAgent() + "|| [0] to return.");
					int param = Integer.parseInt(FormUtil.readString("Enter the number in brackets to change the parameter."));
					switch (param)
					{
					case EDIT_ADDR:
						apt.setAddress(FormUtil.readString("Address"));
						break;
					case EDIT_AREA:
						apt.setArea(Float.parseFloat(FormUtil.readString("Area")));
						break;
					case EDIT_FLOOR:
						apt.setFloor(Integer.parseInt(FormUtil.readString("Floor ")));
						break;
					case EDIT_ROOM_NO:
						apt.setRoomNumber(Integer.parseInt(FormUtil.readString("Number of rooms ")));
						break;
					case EDIT_BALC:
						apt.setBalconyIncl(Integer.parseInt(FormUtil.readString("Balcony? (1 if present) ")));
						break;
					case EDIT_EBK:
						apt.setEbkIncl(Integer.parseInt(FormUtil.readString("Built in kitchen? (1 is included)")));
						break;
					case EDIT_RENT:
						apt.setRent(Integer.parseInt(FormUtil.readString("Rent ")));
						break;
					case EDIT_AGENT:
						apt.setAgent(FormUtil.readString("Agent name"));
						break;
					case EDIT_BACK:
						return;
					default:
							return;
					}
					apt.save();
					break;
				case REMOVE_APT:
					apt.setId(Integer.parseInt(FormUtil.readString("Apartment ID ")));
					apt.remove();
					break;
				case BACK:
					return;
			}
		}
	}
	
	/**
	 * Shows the estate administration
	 */
	public static void showHouseMenu() {
		//Main menu options
		final int ADD_H = 0;
		final int EDIT_H = 1;
		final int REMOVE_H = 2;
		final int BACK = 3;
		
		final int EDIT_ADDR = 1;
		final int EDIT_AREA = 2;
		final int EDIT_FLOORS = 3;
		final int EDIT_PRICE = 4;
		final int EDIT_GARDEN = 5;
		final int EDIT_AGENT = 6;
		final int EDIT_BACK = 0;
		
		//Estate administration menu
		Menu houseMenu = new Menu("House account management");
		houseMenu.addEntry("Add a house", ADD_H);
		houseMenu.addEntry("Edit a house", EDIT_H);
		houseMenu.addEntry("Remove a house", REMOVE_H);
		houseMenu.addEntry("Return to the main menu", BACK);
		
		//Processing input
		while(true) {
			int response = houseMenu.show();
			House h = new House();
			switch(response) {
				case ADD_H:
					h.setAddress(FormUtil.readString("Address"));
					h.setArea(Float.parseFloat(FormUtil.readString("Area")));
					h.setFloors(Integer.parseInt(FormUtil.readString("Floors ")));
					h.setPrice(Integer.parseInt(FormUtil.readString("Price ")));
					h.setGardenIncl(Integer.parseInt(FormUtil.readString("Garden? (1 if present) ")));
					h.save();
					System.out.println("House with ID "+ h.getId() +" was added.");
					break;
				case EDIT_H:
					h.setId(Integer.parseInt(FormUtil.readString("House ID")));
					h.load();
					System.out.println("ID: "+ h.getId() +"|| Address[1]: " + h.getAddress() + "|| Area[2]: " + h.getArea() + "\r\n|| Floors[3]: " + h.getFloors() + "|| Price[4]: " + h.getPrice()
						+ "|| Garden[5]: " + h.getGardenIncl() + "||Agent[6]:" + h.getAgent() + "|| [0] to return.");
					int param = Integer.parseInt(FormUtil.readString("Enter the number in brackets to change the parameter."));
					switch (param)
					{
					case EDIT_ADDR:
						h.setAddress(FormUtil.readString("Address"));
						break;
					case EDIT_AREA:
						h.setArea(Float.parseFloat(FormUtil.readString("Area")));
						break;
					case EDIT_FLOORS:
						h.setFloors(Integer.parseInt(FormUtil.readString("Floors ")));
						break;
					case EDIT_PRICE:
						h.setPrice(Integer.parseInt(FormUtil.readString("Priceh ")));
						break;
					case EDIT_GARDEN:
						h.setGardenIncl(Integer.parseInt(FormUtil.readString("Garden? (1 if present) ")));
						break;
					case EDIT_AGENT:
						h.setAgent(FormUtil.readString("Agent name"));
						break;
					case EDIT_BACK:
						return;
					default:
							return;
					}
					h.save();
					break;
				case REMOVE_H:
					h.setId(Integer.parseInt(FormUtil.readString("House ID ")));
					h.remove();
					break;
				case BACK:
					return;
			}
		}
	}
	
	/**
	 * Sets a new makler after the user inserts the correct data
	 */
	public static void newMakler() {
		Makler m = new Makler();
		
		m.setName(FormUtil.readString("Name"));
		m.setAddress(FormUtil.readString("Address"));
		m.setLogin(FormUtil.readString("Login"));
		m.setPassword(FormUtil.readString("Password"));
		m.save();
		
		System.out.println("Makler with ID "+m.getId()+" was created.");
	}
	
	/**
	 * Removes a makler after the user inserts the correct data
	 */
	public static void deleteMakler() {
		Makler m = new Makler();
		
		int id  = Integer.parseInt((FormUtil.readString("Enter agent ID")));
		m.remove(id);
		
		System.out.println("Makler with ID "+ id +" was removed.");
	}
	
	public static void showContractMenu(){

		final int NEW_RENT=0;
		final int NEW_SALE=1;
		final int CONTRACT_OVERVIEW=3;
		final int BACK=2;


		Menu contractMenu= new Menu("Create new Contract");
		contractMenu.addEntry("New Rent Contract",NEW_RENT);
		contractMenu.addEntry("New Sale Contract",NEW_SALE);
		contractMenu.addEntry("Display Contracts",CONTRACT_OVERVIEW);
		contractMenu.addEntry("Return to the main menu", BACK);


		while(true){
			int response = contractMenu.show();

			switch(response){
				case NEW_RENT:
					newRentContract();
					break;
				case NEW_SALE:
					newSaleContract();
					break;
				case CONTRACT_OVERVIEW:
					System.out.println("Press '1' to view the Rent contracts and '2' for the tenancy contracts!");
					displayContract();
				case BACK:
					return;

			}
		}

	}

	public static void newOwner() {
		Contract c = new Contract();

		c.setFirstname(FormUtil.readString("First name"));
		c.setLastName(FormUtil.readString("Last name"));
		c.setAddress(FormUtil.readString("Address"));
		c.createOwner();

	}

	public static void newRentContract(){

		Contract c= new Contract();

		c.setContractdate(FormUtil.readString("Date"));
		c.setSettlemtnPlace(FormUtil.readString("Settlement Place"));
		c.setApartmentid(FormUtil.readInt("Apartment id"));
		c.setOwnerID(FormUtil.readInt("Owner ID"));
		c.setContractType(1);
		c.setDuration(FormUtil.readInt("Duration"));
		c.setExtracosts(FormUtil.readInt("Extra costs"));
		c.setStartDate(FormUtil.readString("Start Date"));

		c.createContract();
	}

	public static void newSaleContract(){
		Contract c= new Contract();

		c.setContractdate(FormUtil.readString("Date"));
		c.setSettlemtnPlace(FormUtil.readString("Settlement Place"));
		c.setHouseid(FormUtil.readInt("House ID"));
		c.setOwnerID(FormUtil.readInt("Owner ID"));
		c.setContractType(2);
		c.setInstallments(FormUtil.readInt("Installments"));
		c.setInterestrate(FormUtil.readInt("Interest Rate"));

		c.createContract();
	}

	public static void displayContract(){
		Contract c= new Contract();
		c.setContractType(FormUtil.readInt("Insert Contract type"));
		c.showContracts();
	}
	
	/**
	 * Edits the makler data after the user inserts the correct data
	 */
	public static void editMakler() {
		
		final int CHG_NAME = 1;
		final int CHG_ADDR = 2;
		final int CHG_LOGIN = 3;
		final int CHG_PW = 4;
		
		Makler m = new Makler();
		
		int id  = Integer.parseInt((FormUtil.readString("Enter agent ID")));
		m.read(id);
		System.out.println("ID: "+ id +"|| Name: " + m.getName() + "|| Address: " + m.getAddress() + "\r\n|| Login: " + m.getLogin() + "|| Password: " + m.getPassword());
		System.out.println("Enter " + CHG_NAME +" to change name, " + CHG_ADDR +" to change address, " + CHG_LOGIN +" to change login, "  + CHG_PW +" to change password.");
		int choice = Integer.parseInt((FormUtil.readString("")));
		switch(choice)
		{
		case CHG_NAME:
			m.setName(FormUtil.readString("Enter new name: "));
			break;
		case CHG_ADDR:
			m.setAddress(FormUtil.readString("Enter new address: "));
			break;
		case CHG_LOGIN:
			m.setLogin(FormUtil.readString("Enter new login: "));
			break;
		case CHG_PW:
			m.setPassword(FormUtil.readString("Enter new password: "));
			break;
		default:
			System.out.println("Failed to reconfigure.");
			return;
		}
		m.save();
		System.out.println("Makler with ID "+ id +" was reconfigured.");
	}
}
