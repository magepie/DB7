package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class House {
	private int id = -1;
	private String address;
	private float area;
	
	private int floors;
	private int gardenIncl;
	private int price;
	private String agent = new String("");
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public int getFloors() {
		return floors;
	}

	public void setFloors(int floors) {
		this.floors = floors;
	}

	public int getGardenIncl() {
		return gardenIncl;
	}

	public void setGardenIncl(int gardenIncl) {
		this.gardenIncl = gardenIncl;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}	
	
	public void load() {
		try {
			// Get connection
			Connection con = DB2ConnectionManager.getInstance().getConnection();
			// Create inquiry
			String selectSQL = "SELECT * FROM house WHERE id_h = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, getId());
			// Execute inquiry
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {		
				setFloors(Integer.parseInt(rs.getString("floors")));
				setPrice(Integer.parseInt(rs.getString("price")));
				setGardenIncl(Integer.parseInt(rs.getString("garden")));
				rs.close();
				pstmt.close();
			}
			
			selectSQL = "SELECT * FROM estate WHERE id = ?";
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, getId());
			// Execute inquiry
			rs = pstmt.executeQuery();
			if (rs.next()) 
			{	
				setAddress(rs.getString("estateaddress"));
				setArea(Float.parseFloat(rs.getString("square_area")));
				int agentid = rs.getInt("agentid");
				rs.close();
				pstmt.close();
				

				selectSQL = "SELECT * FROM makler WHERE agentid = ?";
				pstmt = con.prepareStatement(selectSQL);
				pstmt.setInt(1, agentid);
				// Execute inquiry
				rs = pstmt.executeQuery();
				if (rs.next()) 
				{	
					setAgent(rs.getString("agentname"));
				}
				rs.close();
				pstmt.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void save()
	{
		//Get connection
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// Fetch new element if the object does not yet have an ID.
			if (getId() == -1) {
				// Here we pass another parameter
				// so that later generated IDs can be delivered!
				String insertSQL = "INSERT INTO estate (ESTATEADDRESS, SQUARE_AREA) VALUES (?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);
				
				// Set request parameters and fetch your request
				pstmt.setString(1, getAddress());
				pstmt.setFloat(2, getArea());
				pstmt.executeUpdate();
				
				// Get the Id of the record
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}
				
				insertSQL = "INSERT INTO house (floors, price, garden, id_h) VALUES (?, ?, ?, ?)";
				pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

				// Set request parameters and fetch your request
				pstmt.setInt(1, getFloors());
				pstmt.setInt(2, getPrice());
				pstmt.setInt(3, getGardenIncl());
				pstmt.setInt(4, getId());
				pstmt.executeUpdate();

				rs.close();
				pstmt.close();
			} else {
				
				if (!getAgent().equals(""))
				{
					int agentid;
					
					String selectSQL = "SELECT * FROM makler WHERE agentname = ?";
					PreparedStatement pstmt = con.prepareStatement(selectSQL);
					pstmt.setString(1, getAgent());
					// Execute inquiry
					ResultSet rs = pstmt.executeQuery();
					if (rs.next()) 
					{	
						agentid = Integer.parseInt(rs.getString("agentid"));
						rs.close();
						pstmt.close();
						
						String updateSQL = "UPDATE estate SET agentid = ? WHERE id = ?";
						pstmt = con.prepareStatement(updateSQL);

						// Set request parameters
						pstmt.setInt(1, agentid);
						pstmt.setInt(2, getId());
						pstmt.executeUpdate();
						pstmt.close();
					}
					
				}
				
				// If an ID already exists, make an update
				String updateSQL = "UPDATE estate SET estateaddress = ?, square_area = ? WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Set request parameters
				pstmt.setString(1, getAddress());
				pstmt.setFloat(2, getArea());
				pstmt.setInt(3, getId());
				pstmt.executeUpdate();
				pstmt.close();
				
				updateSQL = "UPDATE house SET floors = ?, price = ?, garden = ? WHERE id_h = ?";
				pstmt = con.prepareStatement(updateSQL);

				// Set request parameters
				pstmt.setInt(1, getFloors());
				pstmt.setInt(2, getPrice());
				pstmt.setInt(3, getGardenIncl());
				pstmt.setInt(4, getId());
				pstmt.executeUpdate();

				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void remove()
	{
		try {
			// Get connection
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Create inquiry
			String selectSQL = "DELETE FROM house WHERE id_h = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, getId());

			// Execute inquiry
			pstmt.execute();
			
			selectSQL = "DELETE FROM estate WHERE id = ?";
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, getId());

			// Execute inquiry
			pstmt.execute();
			
			
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
