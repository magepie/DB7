package de.dis2011.intiUtils;

import de.dis2011.data.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nxirakia on 02.07.17.
 */
public class ImportData {

    public void importData(){
        String salesCsv = "src/sales.csv";

        Product p= new Product();
        p.loadImportProducts();
        Shop s= new Shop();
        s.loadImportShop();
        Branch b= new Branch();
        b.loadImportBranch();
        Time time= new Time();
        ArrayList <Date> date=this.getDates(salesCsv);
        time.importTime(date);
        Sales sales= new Sales();
        sales.getCsvData(salesCsv,p,s,b);

        //System.out.println("******************************************\n Warehouse data are successfully imported!");
    }

    public ArrayList<Date> getDates(String csv){
        System.out.println("Loading Dates from sales.csv ...");
        ArrayList <Date> date= new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date d;

        try{

            String line;
            BufferedReader br = new BufferedReader(new FileReader(csv));
            int i=0;
            while ((line=br.readLine())!=null){
                if(i>0) {
                    String ar[] = line.split(";");
                    d = dateFormat.parse(ar[0]);
                    if(!date.contains(d))
                        date.add(d);
                }
                i++;
            }
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
