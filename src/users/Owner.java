package users;

import talabat.clone.*;
import meal.Order;
import meal.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Owner extends User {

    //Class attributes
    private String restaurentName;

    //Constructors
    public Owner() {
        super(null, null);
    }

    public Owner(String username, String password, String restaurentName) {
        super(username, password);
        this.restaurentName = restaurentName;
    }

    //Overriden methods
    @Override
    public void registerValidation() {
        try {
            String query = "SELECT * FROM OWNER";
            Statement select = TalabatClone.conn.createStatement();
            ResultSet signupSet = select.executeQuery(query);

            while (signupSet.next()) {
                if (signupSet.getString("OWNER_USERNAME").equals(this.getUserName())) {
                    setIsUnique(2); //User Name is used
                    break;
                }
                if (signupSet.getString("RESTAURENT_NAME").equals(this.restaurentName)) {
                    setIsUnique(3); //Restaurent Name is used
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void register() {
        //Inserting new owner in database
        try {
            String query = "INSERT INTO OWNER (OWNER_USERNAME, OWNER_PASSWORD, RESTAURENT_NAME)" + "values (?, ?, ?)";
            TalabatClone.conn.createStatement();
            PreparedStatement insert_user = TalabatClone.conn.prepareStatement(query);
            insert_user.setString(1, this.getUserName());
            insert_user.setString(2, this.getPassword());
            insert_user.setString(3, restaurentName);
            insert_user.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int login(String username_login, String password_login) {

        int check = 0; //User initially not found

        try {
            String query = "SELECT * FROM OWNER";
            Statement select = TalabatClone.conn.createStatement();
            ResultSet loginSet = select.executeQuery(query);

            while (loginSet.next()) {
                if (loginSet.getString("OWNER_USERNAME").equals(username_login)) {
                    if (loginSet.getString("OWNER_PASSWORD").equals(password_login)) {
                        check = 1; //User found
                        break;
                    } else {
                        check = 2; //Wrong password
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    @Override
    //Combo box filler
    public List<Order> browseOrder(String username) {

        List<Order> orderList = new ArrayList<Order>();

        Order order = new Order();

        try {
            //Get Restaurant name of this owner
            String rest_Name = findRestName(username);

            //Get Orders' information of this restaurent
            String query = "SELECT * FROM ORDER_INFO WHERE REST_NAME = ?";
            TalabatClone.conn.createStatement();
            PreparedStatement browse_orders = TalabatClone.conn.prepareStatement(query);
            browse_orders.setString(1, rest_Name);
            ResultSet orderInfoResult = browse_orders.executeQuery();//Set of Reastaurent's Orders from ORDER_INFO(ORDER_NAME, ORDER_DATE, NOTES, REST_NAME)

            //Get Orders
            while (orderInfoResult.next()) {
                order.setName(orderInfoResult.getString("ORDER_NAME"));
                order.setDate(orderInfoResult.getDate("ORDER_DATE"));
                order.setNotes(orderInfoResult.getString("NOTES"));
                order.setRst_name(orderInfoResult.getString("REST_NAME"));

                orderList.add(new Order(order));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderList;
    }

    @Override
    public List<Order> browseOrder(String username, String ordername) {

        List<Order> orderList = new ArrayList<Order>();
        List<Product> productList = new ArrayList<Product>();

        Order order = new Order();
        Product product = new Product();

        try {
            //Get Restaurent name of this owner
            String rest_Name = findRestName(username);

            //Get Orders' information of this restaurent
            String query = "SELECT * FROM ORDER_INFO WHERE REST_NAME = ?";
            TalabatClone.conn.createStatement();
            PreparedStatement browse_orders = TalabatClone.conn.prepareStatement(query);
            browse_orders.setString(1, rest_Name);
            ResultSet orderInfoResult = browse_orders.executeQuery();//Set of Reastaurent's Orders from ORDER_INFO(ORDER_NAME, ORDER_DATE, NOTES, REST_NAME)

            //Get Orders
            while (orderInfoResult.next()) {
                if (ordername.equals(orderInfoResult.getString("ORDER_NAME"))) {
                    order.setName(orderInfoResult.getString("ORDER_NAME"));
                    order.setDate(orderInfoResult.getDate("ORDER_DATE"));
                    order.setNotes(orderInfoResult.getString("NOTES"));
                    order.setRst_name(orderInfoResult.getString("REST_NAME"));

                    query = "SELECT * FROM ORDERS WHERE ORDER_NAME = ?";
                    TalabatClone.conn.createStatement();
                    PreparedStatement browse_meal = TalabatClone.conn.prepareStatement(query);
                    browse_meal.setString(1, ordername);
                    ResultSet orderResult = browse_meal.executeQuery(); //Set of Orders's Meals from ORDERS(ORDER_NAME, PRODUCT_NAME, QUANTITY)

                    productList.clear();

                    while (orderResult.next()) {
                        product.setName(orderResult.getString("PRODUCT_NAME"));
                        product.setQuantity(orderResult.getInt("QUANTITY"));

                        query = "SELECT * FROM PRODUCT WHERE PRODUCT_NAME = ?";
                        TalabatClone.conn.createStatement();
                        PreparedStatement browse_price = TalabatClone.conn.prepareStatement(query);
                        browse_price.setString(1, product.getName());
                        ResultSet result = browse_price.executeQuery();
                        while (result.next()) {
                            product.setProdutPrice(result.getInt("PRODUCT_PRICE"));
                        }
                        productList.add(new Product(product));
                    }
                    order.setProducts(new ArrayList<Product>(productList));

                    orderList.add(new Order(order));
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderList;
    }

    //Owner Methods
    public void deleteMeal(String meal) {
        try {

            String query = "DELETE FROM PRODUCT WHERE PRODUCT_NAME = ?";
            TalabatClone.conn.createStatement();
            PreparedStatement delete_meal = TalabatClone.conn.prepareStatement(query);
            delete_meal.setString(1, meal);
            delete_meal.execute();

        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editMeal(Product product, String OldProductName) {
        try {
            String query = "UPDATE PRODUCT SET PRODUCT_NAME = ?, PRODUCT_DESCRIPTION = ?, PRODUCT_PRICE = ?, PRODUCT_PHOTO_PATH = ? WHERE PRODUCT_NAME = ?";
            TalabatClone.conn.createStatement();
            PreparedStatement update_product = TalabatClone.conn.prepareStatement(query);
            update_product.setString(1, product.getName());
            update_product.setString(2, product.getDescription());
            update_product.setFloat(3, product.getProdutPrice());
            update_product.setString(4, product.getPhotoPath());
            update_product.setString(5, OldProductName);
            update_product.execute();

            query = "UPDATE ORDERS SET PRODUCT_NAME = ? WHERE PRODUCT_NAME = ?";
            TalabatClone.conn.createStatement();
            update_product = TalabatClone.conn.prepareStatement(query);
            update_product.setString(1, product.getName());
            update_product.setString(2, OldProductName);
            update_product.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String findRestName(String username) {
        String rest_Name = null;
        try {
            String query = "SELECT * FROM OWNER WHERE OWNER_USERNAME = ?";
            TalabatClone.conn.createStatement();
            PreparedStatement get_rest = TalabatClone.conn.prepareStatement(query);
            get_rest.setString(1, username);
            ResultSet result = get_rest.executeQuery();

            while (result.next()) {
                rest_Name = result.getString("RESTAURENT_NAME");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rest_Name;
    }

    public List<String> rest_meal(String username) {
        List<String> Items = new ArrayList<String>();

        try {
            String rest_Name = findRestName(username);

            String query = "SELECT * FROM PRODUCT WHERE REST_NAME = ?";
            TalabatClone.conn.createStatement();
            PreparedStatement select_products = TalabatClone.conn.prepareStatement(query);
            select_products.setString(1, rest_Name);
            ResultSet mealsSet = select_products.executeQuery();

            while (mealsSet.next()) {
                Items.add(mealsSet.getString("PRODUCT_NAME"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Items;
    }

    public List<String> rest_meal2(String restname) {
        List<String> Items = new ArrayList<String>();

        try {

            String query = "SELECT * FROM PRODUCT WHERE REST_NAME = ?";
            TalabatClone.conn.createStatement();
            PreparedStatement select_products = TalabatClone.conn.prepareStatement(query);
            select_products.setString(1, restname);
            ResultSet mealsSet = select_products.executeQuery();

            while (mealsSet.next()) {
                Items.add(mealsSet.getString("PRODUCT_NAME"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Items;
    }

    public List<String> rest_name() {
        List<String> Items = new ArrayList<String>();

        try {
            String query = "SELECT RESTAURENT_NAME from OWNER ";
            TalabatClone.conn.createStatement();
            PreparedStatement select_products = TalabatClone.conn.prepareStatement(query);
            ResultSet mealsSet = select_products.executeQuery();

            while (mealsSet.next()) {
                Items.add(mealsSet.getString("RESTAURENT_NAME"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Items;
    }
}
