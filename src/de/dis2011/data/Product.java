package de.dis2011.data;

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
    private String procut_group_name;
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

    public String getProcut_group_name() {
        return procut_group_name;
    }

    public void setProcut_group_name(String procut_group_name) {
        this.procut_group_name = procut_group_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
