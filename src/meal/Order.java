package meal;

import java.sql.Date;
import java.util.*;

public class Order {

    //Class attributes
    private String name;
    private java.sql.Date Date;
    private String rst_name;
    private String notes;
    public List<Product> products = new ArrayList<Product>();

    //Constructors
    public Order() {
    }

    public Order(Order order) {
        this.name = order.name;
        this.Date = order.Date;
        this.rst_name = order.rst_name;
        this.notes = order.notes;
        this.products = order.products;
    }

    //Getters
    public Date getDate() {
        return Date;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    //Setters
    public void setDate() {
        this.Date = java.sql.Date.valueOf(java.time.LocalDate.now());
    }

    public void setDate(java.sql.Date date) {
        this.Date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRst_name(String rst_name) {
        this.rst_name = rst_name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
