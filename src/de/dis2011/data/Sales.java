package de.dis2011.data;

import com.sun.deploy.util.StringUtils;
import com.sun.javafx.util.Utils;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nxirakia on 02.07.17.
 */
public class Sales {
    private int items;
    private float revenue;

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public void getCsvData(String csv, Product p,Shop s, Branch b ) {
        System.out.println("Loading all sales from sales.csv ...");

        String insertSQL = "INSERT INTO sales(date, shop_id, product_id, city_id, number_items , revenue) VALUES (?,?,?,?,?,?)";
        PreparedStatement pstmt =null;

        ArrayList<Date> date= new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date d;

        int shop_id;
        int product_id;
        int city_id;

        try{
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csv),"UTF8"));
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            int i=0;
            while ((line=br.readLine())!=null){
                if(i>0) {
                    String ar[] = line.split(";");
                    d = dateFormat.parse(ar[0]);
                    shop_id=s.searchForShopId(ar[1]);
                    product_id=p.searchForProdId(ar[2]);
                    String city_name[]= ar[1].split(" ");
                    city_id=b.searchForBranchId(city_name[1]);
                    setItems(Integer.parseInt(ar[3]));
                    setRevenue(Float.parseFloat(ar[4]));

                    pstmt = con.prepareStatement(insertSQL);
                    pstmt.setDate(1, new java.sql.Date(d.getTime()));
                    pstmt.setInt(2,shop_id);
                    pstmt.setInt(3,product_id);
                    pstmt.setInt(4,city_id);
                    pstmt.setInt(5,getItems());
                    pstmt.setDouble(6,getRevenue());
                    pstmt.executeUpdate();
                }
                i++;
            }
            pstmt.close();
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}

