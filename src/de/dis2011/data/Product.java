package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nxirakia on 02.07.17.
 */
public class Product {
    private int product_id;
    private String product_name;
    private int product_category_id;
    private String product_category_name;
    private int product_family_id;
    private String product_family_name;
    private int product_group_id;
    private String product_group_name;
    private double price;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_category_id() {
        return product_category_id;
    }

    public void setProduct_category_id(int product_category_id) {
        this.product_category_id = product_category_id;
    }

    public String getProduct_category_name() {
        return product_category_name;
    }

    public void setProduct_category_name(String product_category_name) {
        this.product_category_name = product_category_name;
    }

    public int getProduct_family_id() {
        return product_family_id;
    }

    public void setProduct_family_id(int product_family_id) {
        this.product_family_id = product_family_id;
    }

    public String getProduct_family_name() {
        return product_family_name;
    }

    public void setProduct_family_name(String product_family_name) {
        this.product_family_name = product_family_name;
    }

    public int getProduct_group_id() {
        return product_group_id;
    }

    public void setProduct_group_id(int product_group_id) {
        this.product_group_id = product_group_id;
    }

    public String getProduct_group_name() {
        return product_group_name;
    }

    public void setProduct_group_name(String product_group_name) {
        this.product_group_name = product_group_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void loadImportProducts(){

        PreparedStatement pstmt = null;
        PreparedStatement pstmt2=null;

        String selectSQL="SELECT a.articleid, a.name , a.preis , pg.productgroupid , pg.name, pf.productfamilyid, pf.name, pc.productcategoryid, pc.name\n" +
            "FROM db2inst1.articleid a\n" +
            "JOIN db2inst1.productgroupid pg on (pg.productgroupid = a.productgroupid)\n" +
            "JOIN db2inst1.productfamilyid pf on (pf.productfamilyid = pg.productfamilyid) \n" +
            "JOIN db2inst1.productcategoryid pc on (pc.productcategoryid = pf.productcategoryid) ";
        String insertSQL = "INSERT INTO product (product_id, product_name, product_family_id, product_family_name, product_group_id, product_group_name, product_category_id, product_category_name,price) VALUES (?,?,?,?,?,?,?,?,?)";

        try{
            System.out.println("Searching and importing products ..");
            Connection con = DB2ConnectionManager.getInstance().getConnection();
            pstmt = con.prepareStatement(selectSQL);
            ResultSet rs= pstmt.executeQuery();

            while(rs.next()){
                setProduct_id(rs.getInt(1));
                setProduct_name(rs.getString(2));
                setPrice(rs.getDouble(3));
                setProduct_group_id(rs.getInt(4));
                setProduct_group_name(rs.getString(5));
                setProduct_family_id(rs.getInt(6));
                setProduct_family_name(rs.getString(7));
                setProduct_category_id(rs.getInt(8));
                setProduct_category_name(rs.getString(9));

                pstmt2=con.prepareStatement(insertSQL);
                pstmt2.setInt(1,getProduct_id());
                pstmt2.setString(2,getProduct_name());
                pstmt2.setInt(3,getProduct_family_id());
                pstmt2.setString(4,getProduct_family_name());
                pstmt2.setInt(5,getProduct_group_id());
                pstmt2.setString(6,getProduct_group_name());
                pstmt2.setInt(7,getProduct_category_id());
                pstmt2.setString(8,getProduct_category_name());
                pstmt2.setDouble(9,getPrice());
                pstmt2.executeUpdate();
            }
            pstmt.close();
            pstmt2.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
    public int searchForProdId(String prod_name){

        String selectSQL="SELECT product_id from product where product_name='"+prod_name+"'";
        try {
            Connection con = DB2ConnectionManager.getInstance().getConnection();
            PreparedStatement pstmt =con.prepareStatement(selectSQL);
            ResultSet rs=pstmt.executeQuery();
            if(rs.next())
                setProduct_id(rs.getInt("product_id"));
            pstmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return getProduct_id();
    }
}
