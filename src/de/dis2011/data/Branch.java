package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nxirakia on 02.07.17.
 */
public class Branch {
    private int city_id;
    private String city_name;
    private int state_id;
    private String state_name;
    private int country_id;
    private String country_name;


    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public void loadImportBranch(){
        PreparedStatement pstmt =null;
        PreparedStatement pstmt2=null;

        String selectSQL="SELECT st.stadtid, st.name , rg.regionid, rg.name, ld.landid, ld.name\n" +
                "FROM db2inst1.stadtid st\n" +
                "JOIN db2inst1.regionid rg on (rg.regionid = st.regionid)\n" +
                "JOIN db2inst1.landid ld on (ld.landid = rg.landid) \n";
        String insertSQL = "INSERT INTO branch(city_id, city_name, state_id, state_name,country_id, country_name) VALUES (?,?,?,?,?,?)";

        try{
            System.out.println("Searching and importing branches ...");
            Connection con = DB2ConnectionManager.getInstance().getConnection();
            pstmt= con.prepareStatement(selectSQL);
            ResultSet rs= pstmt.executeQuery();

            while(rs.next()){
                setCity_id(rs.getInt(1));
                setCity_name(rs.getString(2));
                setState_id(rs.getInt(3));
                setState_name(rs.getString(4));
                setCountry_id(rs.getInt(5));
                setCountry_name(rs.getString(6));

                pstmt2=con.prepareStatement(insertSQL);
                pstmt2.setInt(1,getCity_id());
                pstmt2.setString(2,getCity_name());
                pstmt2.setInt(3,getState_id());
                pstmt2.setString(4,getState_name());
                pstmt2.setInt(5,getCountry_id());
                pstmt2.setString(6,getCountry_name());
                pstmt2.executeUpdate();
            }
            pstmt.close();
            pstmt2.close();
            rs.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public ArrayList<String> searchForBranchId(){

        String selectSQL="SELECT city_name from branch";
        ArrayList<String> branchList=new ArrayList<>();
        String b=new String();
        try {
            Connection con = DB2ConnectionManager.getInstance().getConnection();
            PreparedStatement pstmt =con.prepareStatement(selectSQL);
            ResultSet rs=pstmt.executeQuery();

            while(rs.next()) {
                b=rs.getString("city_name");
                branchList.add(b);
            }
            pstmt.close();
            rs.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return branchList;
    }
}
