package users;

import meal.Order;
import java.util.List;

public abstract class User {

    //Class attributes
    private String userName;
    private String password;
    private int isUnique = 1;

    //Constructors
    public User(String username) {
        this.userName = username;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    //User Methods
    public abstract void registerValidation();

    public abstract void register();

    public abstract int login(String username, String password);

    public abstract List<Order> browseOrder(String username);

    public abstract List<Order> browseOrder(String username, String ordername);

    //Getters
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getIsUnique() {
        return isUnique;
    }

    //Setters
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setIsUnique(int isUnique) {
        this.isUnique = isUnique;
    }
}
