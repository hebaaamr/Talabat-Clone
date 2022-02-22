package meal;

import talabat.clone.*;
import users.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import orderManagment.MealInf;

public class Product {

    //Class attributes
    private String ProdutName;
    private String ProdutDescription;
    private String ProdutPhotoPath;
    private String RestaurentName;
    private float ProdutPrice;
    private int ProductQuantity;

    //Constructors
    public Product() {
    }

    public Product(Product p) {
        this.ProdutName = p.getName();
        this.ProdutDescription = p.getDescription();
        this.ProdutPrice = p.getProdutPrice();
        this.ProductQuantity = p.getQuantity();
        this.ProdutPhotoPath = p.getPhotoPath();
    }

    public Product(String ProdutName, String ProdutDescription, int ProdutPrice, String ProdutPhotoPath, String username) {
        Owner owner = new Owner();
        this.ProdutName = ProdutName;
        this.ProdutDescription = ProdutDescription;
        this.ProdutPrice = ProdutPrice;
        this.ProdutPhotoPath = ProdutPhotoPath;
        this.RestaurentName = owner.findRestName(username);
    }

    //Product Methods
    public void insert_product() {
        //Inserting new product in database 
        try {
            String query = "INSERT INTO PRODUCT (PRODUCT_NAME, PRODUCT_DESCRIPTION, PRODUCT_PRICE, PRODUCT_PHOTO_PATH, REST_NAME)" + "values (?, ?, ?, ?, ?)";
            TalabatClone.conn.createStatement();
            PreparedStatement insert_product = TalabatClone.conn.prepareStatement(query);

            insert_product.setString(1, ProdutName);
            insert_product.setString(2, ProdutDescription);
            insert_product.setFloat(3, ProdutPrice);
            insert_product.setString(4, ProdutPhotoPath);
            insert_product.setString(5, RestaurentName);
            insert_product.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public float productPrice(String mealname) {

        float mealPrice = 0;
        try {
            String query = "SELECT PRODUCT_PRICE FROM PRODUCT WHERE PRODUCT_NAME = ?";
            PreparedStatement select_product = TalabatClone.conn.prepareStatement(query);
            select_product.setString(1, mealname);
            ResultSet price = select_product.executeQuery();
            while (price.next()) {
                mealPrice = price.getFloat("PRODUCT_PRICE");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mealPrice;
    }

    public List<Product> productInfo(String rst, String meal) {

        List<Product> productList = new ArrayList<Product>();
        Product product = new Product();

        try {
            String query = "SELECT * FROM PRODUCT WHERE REST_NAME = ? AND PRODUCT_NAME = ?";
            TalabatClone.conn.createStatement();
            PreparedStatement select_products = TalabatClone.conn.prepareStatement(query);
            select_products.setString(1, rst);
            select_products.setString(2, meal);
            ResultSet mealsSet = select_products.executeQuery();

            productList.clear();

            while (mealsSet.next()) {
                product.ProdutName = meal;
                product.ProdutPrice = mealsSet.getFloat("PRODUCT_PRICE");
                product.ProdutDescription = mealsSet.getString("PRODUCT_DESCRIPTION");
                product.ProdutPhotoPath = mealsSet.getString("PRODUCT_PHOTO_PATH");

                productList.add(new Product(product));
            }

        } catch (SQLException ex) {
            Logger.getLogger(MealInf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productList;
    }

    //Getters
    public String getName() {
        return ProdutName;
    }

    public String getDescription() {
        return ProdutDescription;
    }

    public float getProdutPrice() {
        return ProdutPrice;
    }

    public String getPhotoPath() {
        return ProdutPhotoPath;
    }

    public int getQuantity() {
        return ProductQuantity;
    }

    //Setters
    public void setName(String ProdutName) {
        this.ProdutName = ProdutName;
    }

    public void setProdutPrice(float ProdutPrice) {
        this.ProdutPrice = ProdutPrice;
    }

    public void setQuantity(int ProductQuantity) {
        this.ProductQuantity = ProductQuantity;
    }
}
