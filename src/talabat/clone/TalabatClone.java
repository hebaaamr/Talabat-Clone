package talabat.clone;

import userManagment.Selection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TalabatClone {

    public static Connection conn;

    public static void databaseConnection() {
        final String dbURL = "jdbc:oracle:thin:@localhost:1521:orcl";
        final String connVar = "hr";
        System.out.println("Establishing Connection...");
        try {
            conn = DriverManager.getConnection(dbURL, connVar, connVar);
            System.out.println("Connected.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to database!");
            e.printStackTrace();
            System.exit(304);
        }
    }

    public static void main(String[] args) {
        TalabatClone.databaseConnection();
        new Selection().setVisible(true);
    }
}
