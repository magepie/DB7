package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nxirakia on 02.07.17.
 */
public class Shop {
    private int shop_id;
    private String shop_name;

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public void loadImportShop(){
        PreparedStatement pstmt =null;
        PreparedStatement pstmt2=null;

        String selectSQL="SELECT shopid, name from db2inst1.shopid";
        String insertSQL = "INSERT INTO shop(shop_id, shop_name) VALUES (?,?)";

        try{
            System.out.println("Searching and importing shops ...");
            Connection con = DB2ConnectionManager.getInstance().getConnection();
            pstmt= con.prepareStatement(selectSQL);
            ResultSet rs= pstmt.executeQuery();

            while(rs.next()){
                setShop_id(rs.getInt(1));
                setShop_name(rs.getString(2));

                pstmt2=con.prepareStatement(insertSQL);
                pstmt2.setInt(1,getShop_id());
                pstmt2.setString(2,getShop_name());
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

    public ArrayList<String> searchForShopId(){

        String selectSQL="SELECT shop_id, shop_name from shop";
        ArrayList<String> shopList=new ArrayList<>();
        String s=new String();
        try {
            Connection con = DB2ConnectionManager.getInstance().getConnection();
            PreparedStatement pstmt =con.prepareStatement(selectSQL);
            ResultSet rs=pstmt.executeQuery();

            while(rs.next()){
                s=rs.getString("shop_name");
                shopList.add(s);
            }
            pstmt.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return shopList;
    }
}
