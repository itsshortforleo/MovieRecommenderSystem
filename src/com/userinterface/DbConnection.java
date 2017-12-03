package com.userinterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {

    public Connection Connect() {
        try {
            //Your database url string,ensure it is correct
            String url = "jdbc:mysql://localhost:3306/movie_recommender?useSSL=false";
            String user = "root";
            String password = "root";

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
