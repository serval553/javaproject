package com.booking.booking4.service;

import java.io.IOException;
import java.sql.*;

public class dbConnection {
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "Awtobus470#";
    public static final String URL = "jdbc:mysql://localhost:3306/mysql";
    public static Statement statement;
    public static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException throwables) {
            throw new RuntimeException();
        }
    }

    static {
        try{
            statement = connection.createStatement();
        } catch (SQLException throwables){
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }
}

