package com.urlshortener.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {

    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin123";
    private static final String DATABASE = "urlshortener";


    public static Connection getConnection() throws Exception {
        Connection conn = DriverManager.getConnection(
                URL + DATABASE,
                USER,
                PASSWORD);

        System.out.println(conn.isValid(2));
        return conn;
    }
}