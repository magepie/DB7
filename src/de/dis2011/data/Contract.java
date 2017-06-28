package de.dis2011.data;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by nxirakia on 23.04.17.
 */
public class Contract {
    //owner info
    private int ownerid = -1;
    private String firstname;
    private String lastname;
    private String address;

    //contract info
    private int contractid= -1;
    private String contractdate;
    private String settlemtnplace;
    private int contractType;
    private int installments;
    private double interestrate;
    private String startDate;
    private int duration;
    private double extracosts;
    private int houseid;
    private int apartmentid;


    //get/set owner info
    public int getOwnerID() {
        return ownerid;
    }

    public void setOwnerID(int ownerid) {
        this.ownerid = ownerid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //get/set contract info

    public int getContractID() {
        return contractid;
    }

    public void setContractID(int contractid) {
        this.contractid = contractid;
    }

    public String getContractdate(){return contractdate; }

    public void setContractdate(String date){

        this.contractdate = date;

    }

    public String getSettlemtnPlace(){ return settlemtnplace;}

    public void setSettlemtnPlace(String settlemtnplace) {this.settlemtnplace=settlemtnplace;}

    public int getContractType(){return contractType;}

    public void setContractType(int contractType){this.contractType=contractType;}

    public int getInstallments(){return installments;}

    public void setInstallments(int installments){this.installments= installments;}

    public double getInterestrate(){return interestrate;}

    public void setInterestrate(double interestrate){this.interestrate=interestrate;}

    public String getStartDate(){return startDate;}

    public void setStartDate(String startDate){this.startDate=startDate;}

    public int getDuration(){return duration;}

    public void setDuration(int duration){this.duration= duration;}

    public double getExtracosts(){return extracosts;}

    public void setExtracosts(double extracosts){this.extracosts=extracosts;}

    public int getHouseid(){return houseid;}

    public void setHouseid(int houseid){this.houseid= houseid;}

    public int getApartmentid(){return apartmentid;}

    public void setApartmentid(int apartmentid){this.apartmentid= apartmentid;}


    public void createOwner(){
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {

            // Fetch new element if the object does not yet have an ID.
            if (getOwnerID() == -1) {
                // Here we pass another parameter
                // so that later generated IDs can be delivered!
                String insertSQL = "INSERT INTO owner(ownername, ownersurname, address) VALUES (?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                // Set request parameters and fetch your request
                pstmt.setString(1, getFirstname());
                pstmt.setString(2, getLastName());
                pstmt.setString(3, getAddress());
                pstmt.executeUpdate();

                // Get the Id of the record
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setOwnerID(rs.getInt(1));
                }

                rs.close();
                pstmt.close();
            } else {
                // If an ID already exists, make an update
                String updateSQL = "UPDATE owner SET ownername = ?, ownersurname=?, address = ? WHERE ownerid = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                // Set request parameters
                pstmt.setString(1, getFirstname());
                pstmt.setString(2, getLastName());
                pstmt.setString(3, getAddress());
                pstmt.setInt(4, getOwnerID());
                pstmt.executeUpdate();

                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createContract(){
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {

            System.out.println(getContractID());

            // Fetch new element if the object does not yet have an ID.

                // Here we pass another parameter
                // so that later generated IDs can be delivered!
                String insertSQL = "INSERT INTO contract(contractdate, settlementplace) VALUES (?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                // Set request parameters and fetch your request

                pstmt.setString(1, getContractdate());
                pstmt.setString(2, getSettlemtnPlace());
                pstmt.executeUpdate();

                // Get the Id of the record
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setContractID(rs.getInt(1));
                }

                rs.close();
                pstmt.close();

                if (getContractType()==1)
                        this.createTenancy();
                else if (getContractType()==2)
                        this.createPurchase();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createTenancy(){
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            System.out.println(getContractID());

            // Fetch new element if the object does not yet have an ID.
                // Here we pass another parameter
                // so that later generated IDs can be delivered!
                String insertSQL = "INSERT INTO tenancycontract(id, startdate, duration, extracharges) VALUES (?, ?,?,?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                // Set request parameters and fetch your request
             pstmt.setInt(1, getContractID());
             pstmt.setString(2, getStartDate());
             pstmt.setInt(3,getDuration());
             pstmt.setDouble(4,getExtracosts());
             pstmt.executeUpdate();

                // Get the Id of the record
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setContractID(rs.getInt(1));
                }

                rs.close();
                pstmt.close();

                String insertSQL2 = "INSERT INTO apartmentrent(contractid,ownerid,apartmentid) VALUES (?,?,?)";

                PreparedStatement pstmt2 = con.prepareStatement(insertSQL2,
                        Statement.RETURN_GENERATED_KEYS);

                pstmt2.setInt(1,getContractID());
                pstmt2.setInt(2,getOwnerID());
                pstmt2.setInt(3,getApartmentid());
                pstmt2.executeUpdate();

                ResultSet rs2 = pstmt2.getGeneratedKeys();
                if (rs2.next()) {
                    setContractID(rs2.getInt(1));
                }

                rs2.close();
                pstmt2.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createPurchase(){
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {

            // Fetch new element if the object does not yet have an ID.
                // Here we pass another parameter
                // so that later generated IDs can be delivered!
                String insertSQL = "INSERT INTO purchasecontract(id,numberofinstallments, interestrate) VALUES (?, ?,?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                // Set request parameters and fetch your request
                pstmt.setInt(1,getContractID());
                pstmt.setInt(2, getInstallments());
                pstmt.setDouble(3,getInterestrate());
                pstmt.executeUpdate();

                // Get the Id of the record
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    setContractID(rs.getInt(1));
                }

                rs.close();
                pstmt.close();

            String insertSQL2= "INSERT INTO SALES(contract_id,owner_id, house_id) VALUES (?,?,?)";
            PreparedStatement pstmt2 = con.prepareStatement(insertSQL2,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt2.setInt(1,getContractID());
            pstmt2.setInt(2,getOwnerID());
            pstmt2.setInt(3,getHouseid());

            pstmt2.executeUpdate();

            ResultSet rs2 = pstmt2.getGeneratedKeys();
            if (rs2.next()) {
                setContractID(rs2.getInt(1));
            }

            rs2.close();
            pstmt2.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showContracts(){

        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {

            if (getContractType()==1){
                String insertSQL = "select * from contract c where exists(select * from tenancycontract t where t.id=c.id)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL);

                // Set request parameters and fetch your request
                // Get the Id of the record
                ArrayList <Contract> ar= new ArrayList<>();

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {

                    Contract c= new Contract();

                    c.setContractID(rs.getInt("id"));
                    c.setSettlemtnPlace(rs.getString("settlementplace"));
                    c.setContractdate(rs.getString("contractdate"));
                    ar.add(c);

                }

                print(ar);

                rs.close();
                pstmt.close();



            }
            else if(getContractType()==2){

                String insertSQL = "select * from contract c where exists(select * from purchasecontract p where p.id=c.id)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL);

                // Set request parameters and fetch your request
                // Get the Id of the record
                ArrayList <Contract> ar= new ArrayList<>();

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {

                    Contract c= new Contract();

                    c.setContractID(rs.getInt("id"));
                    c.setSettlemtnPlace(rs.getString("settlementplace"));
                    c.setContractdate(rs.getString("contractdate"));
                    ar.add(c);

                }

                print(ar);

                rs.close();
                pstmt.close();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void print(ArrayList<Contract> contracts){

        System.out.println("ID, Settlement Place, Contract Date");

        for(Contract c:contracts){
            System.out.println(c.getContractID()+"     "+c.getSettlemtnPlace()+"              "+c.getContractdate());
        }
    }
}

