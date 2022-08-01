package com.company.baza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:Chat.db");

        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
