package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.dis2011.data.DB2ConnectionManager;

/**
 * Makler-Bean
 * 
 * Example table:
 * CREATE TABLE makler(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
 * name varchar(255),
 * address varchar(255),
 * login varchar(40) UNIQUE,
 * password varchar(40));
 */
public class Makler {
	private int id = -1;
	private String name;
	private String address;
	private String login;
	private String password;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Loads a makler from the database
	 * @param id ID of the makler to be loaded
	 * @return makler instance
	 */
	public static Makler load(int id) {
		try {
			// Get connection
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Create inquiry
			String selectSQL = "SELECT * FROM makler WHERE AGENTID = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Execute inquiry
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Makler ts = new Makler();
				ts.setId(id);
				ts.setName(rs.getString("agentname"));
				ts.setAddress(rs.getString("address"));
				ts.setLogin(rs.getString("login"));
				ts.setPassword(rs.getString("password"));

				rs.close();
				pstmt.close();
				return ts;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Stores the makler in the database. If no ID has yet been assigned
	 * the generated Id is fetched from DB2 and passed to the model.
	 */
	public void save() {
		//Get connection
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// Fetch new element if the object does not yet have an ID.
			if (getId() == -1) {
				// Here we pass another parameter
				// so that later generated IDs can be delivered!
				String insertSQL = "INSERT INTO makler(AGENTNAME, ADDRESS, LOGIN, PASSWORD) VALUES (?, ?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				// Set request parameters and fetch your request
				pstmt.setString(1, getName());
				pstmt.setString(2, getAddress());
				pstmt.setString(3, getLogin());
				pstmt.setString(4, getPassword());
				pstmt.executeUpdate();

				// Get the Id of the record
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}

				rs.close();
				pstmt.close();
			} else {
				// If an ID already exists, make an update
				String updateSQL = "UPDATE makler SET agentname = ?, address = ?, login = ?, password = ? WHERE agentid = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Set request parameters
				pstmt.setString(1, getName());
				pstmt.setString(2, getAddress());
				pstmt.setString(3, getLogin());
				pstmt.setString(4, getPassword());
				pstmt.setInt(5, getId());
				pstmt.executeUpdate();

				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void remove(int id)
	{
		try {
			// Get connection
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Create inquiry
			String selectSQL = "DELETE FROM makler WHERE AGENTID = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Execute inquiry
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void read(int id)
	{
		try {
			// Get connection
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Create inquiry
			String selectSQL = "SELECT * FROM makler WHERE AGENTID = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Execute inquiry
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				this.setId(id);
				this.setName(rs.getString("AGENTNAME"));
				this.setAddress(rs.getString("ADDRESS"));
				this.setLogin(rs.getString("LOGIN"));
				this.setPassword(rs.getString("PASSWORD"));

				rs.close();
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String returnPassword()
	{
		try {
			// Get connection
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Create inquiry
			String selectSQL = "SELECT * FROM makler WHERE login = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, getLogin());

			// Execute inquiry
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				this.setId(rs.getInt("AGENTID"));
				this.setPassword(rs.getString("PASSWORD"));

				rs.close();
				pstmt.close();
				
				return getPassword();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
}
