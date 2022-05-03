package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionHolder {
    static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (ConnectionHolder.class) {
                if (connection == null) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        ResourceBundle config = ResourceBundle.getBundle("config");
                        connection = DriverManager.getConnection(config.getString("jdbc.url"));

                        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    connection.close();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                            }
                        }));
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return connection;
    }
}
