package de.dis2011.data;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void getCsvData(String csv, Product p, Shop s, Branch b ) {
        System.out.println("Loading all sales from sales.csv ...");

        String insertSQL = null;
        Statement pstmt =null;
        String ar[]= new String[5];
        String city_name[]=new String[2];

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date d;
        java.sql.Date sql_date;

        ArrayList<String> products=p.searchForProdId();
        ArrayList<String> shops=s.searchForShopId();
        ArrayList<String> branches= b.searchForBranchId();
        int shop_id;
        int product_id;
        int city_id;

        try{
            String line;
            BufferedReader br = new BufferedReader(new FileReader(csv));
            Connection con = DB2ConnectionManager.getInstance().getConnection();
            pstmt=con.createStatement();

            int i=0;
            int batch_count=0;
            while ((line=br.readLine())!=null){
                if(i>0) {
                    ar = line.split(";");
                    d = dateFormat.parse(ar[0]);
                    sql_date=new java.sql.Date(d.getTime());
                    city_name= ar[1].split(" ");  // Splitting ar[1] in two strings to obtain the city_name from string "Superstore $cityname" and then doing a select to obtain the city_id
                    setItems(Integer.parseInt(ar[3]));
                    setRevenue(Float.parseFloat(ar[4]));
                    product_id=products.indexOf(ar[2])+1;
                    shop_id=shops.indexOf(ar[1])+1;
                    city_id=branches.indexOf(city_name[1])+1;

                    insertSQL = "INSERT INTO sales(date, shop_id, product_id, city_id, number_items , revenue) VALUES ('"+sql_date+"',"+shop_id+","+product_id+","+city_id+","+getItems()+","+getRevenue()+")";
                    pstmt.addBatch(insertSQL);

                }
                if(batch_count==20000){
                    batch_count=0;
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                    System.out.println("executing batch..");
                }
                i++;
                batch_count++;

            }
            pstmt.executeBatch();
            System.out.println("executing final batch..");
            pstmt.close();
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ParseException e) {
            e.printStackTrace();
        }catch (SQLException e){
            SQLException e2= e.getNextException();
            if(e2!=null){
                e2.printStackTrace();}
            else e.printStackTrace();
        }

      }

}

