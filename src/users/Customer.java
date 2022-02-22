package users;

import talabat.clone.*;
import meal.Order;
import meal.Product;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer extends User {

    //Class attributes
    private String mobileNumber;
    private String address;

    //Constructors
    public Customer() {
        super(null, null);
    }

    public Customer(String username) {
        super(username);
    }

    public Customer(String userName, String password, String mobileNumber, String address) {
        super(userName, password);
        this.mobileNumber = mobileNumber;
        this.address = address;
    }

    //Overriden methods
    @Override
    public void registerValidation() {
        try {
            String query = "SELECT * FROM CUSTOMER";
            Statement select = TalabatClone.conn.createStatement();
            ResultSet signupSet = select.executeQuery(query);

            while (signupSet.next()) {
                if (signupSet.getString("CUSTOMER_USERNAME").equals(this.getUserName())) {
                    setIsUnique(2); //User Name is used
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void register() {
        //Inserting new Customer in database
        try {
            String query = "INSERT INTO CUSTOMER (CUSTOMER_USERNAME, CUSTOMER_PASSWORD, MOBILE_NUMBER, ADDRESS) values(?,?,?,?)";
            TalabatClone.conn.createStatement();
            PreparedStatement insert = TalabatClone.conn.prepareStatement(query);
            insert.setString(1, super.getUserName());
            insert.setString(2, super.getPassword());
            insert.setString(3, mobileNumber);
            insert.setString(4, address);
            insert.execute();

            System.out.println("Added to database.");
        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int login(String username_login, String password_login) {

        int check = 0;

        try {
            String query = "SELECT * FROM CUSTOMER";
            Statement select = TalabatClone.conn.createStatement();
            ResultSet loginSet = select.executeQuery(query);

            while (loginSet.next()) {
                if (loginSet.getString("CUSTOMER_USERNAME").equals(username_login)) {
                    if (loginSet.getString("CUSTOMER_PASSWORD").equals(password_login)) {
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
    public List<Order> browseOrder(String username) {

        List<Order> orderList = new ArrayList<Order>();

        Order order = new Order();

        try {
            //Get Orders' information of this Customer
            String query = "SELECT * FROM ORDER_INFO WHERE USERNAME = ?";
            TalabatClone.conn.createStatement();
            PreparedStatement browse_orders = TalabatClone.conn.prepareStatement(query);
            browse_orders.setString(1, username);
            ResultSet orderInfoResult = browse_orders.executeQuery();//Set of Customer's Orders from ORDER_INFO(ORDER_NAME, ORDER_DATE, NOTES, REST_NAME)

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
            //Get Orders' information of this Customer
            String query = "SELECT * FROM ORDER_INFO WHERE USERNAME = ?";
            TalabatClone.conn.createStatement();
            PreparedStatement browse_orders = TalabatClone.conn.prepareStatement(query);
            browse_orders.setString(1, username);
            ResultSet orderInfoResult = browse_orders.executeQuery();//Set of Customer's Orders from ORDER_INFO(ORDER_NAME, ORDER_DATE, NOTES, REST_NAME)

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

    //Customer Methods
    public float makeorder(String rst_name, String meal_name, String notes, int quantity) {

        int min = 0, max = 999;
        int rnd = ThreadLocalRandom.current().nextInt(min, max + 1);
        Order ord = new Order();
        ord.setDate();
        float totalPrice = 0;

        try {
            String query = "INSERT INTO ORDER_INFO (ORDER_NAME, ORDER_DATE, NOTES, REST_NAME, USERNAME)" + "values (?, ?, ?, ?, ?)";
            PreparedStatement insert_product = TalabatClone.conn.prepareStatement(query);

            insert_product.setString(1, this.getUserName() + "-æ-" + rnd);
            insert_product.setDate(2, ord.getDate());
            insert_product.setString(3, notes);
            insert_product.setString(4, rst_name);
            insert_product.setString(5, this.getUserName());
            insert_product.execute();

            query = "INSERT INTO ORDERS (PRODUCT_NAME, ORDER_NAME, QUANTITY)" + "values (?, ?, ?)";
            TalabatClone.conn.createStatement();
            insert_product = TalabatClone.conn.prepareStatement(query);

            insert_product.setString(1, meal_name);
            insert_product.setString(2, this.getUserName() + "-æ-" + rnd);
            insert_product.setFloat(3, quantity);
            insert_product.execute();

            query = "SELECT PRODUCT_PRICE FROM PRODUCT WHERE PRODUCT_NAME = ?";
            PreparedStatement select_product = TalabatClone.conn.prepareStatement(query);

            select_product.setString(1, meal_name);
            ResultSet price = select_product.executeQuery();

            while (price.next()) {
                totalPrice = price.getFloat("PRODUCT_PRICE");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Owner.class.getName()).log(Level.SEVERE, null, ex);
        }

        return totalPrice * quantity;
    }
}
