package com.smartpay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	  private static Connection con = null;

	    public static Connection getConnection() {
	        try {
	            if (con == null || con.isClosed()) {
	                Class.forName("com.mysql.cj.jdbc.Driver");
	                con = DriverManager.getConnection(
	                    "jdbc:mysql://localhost:3306/SmartPay","", ""  
	                );
	                System.out.println("✅ Connected to MySQL");
	            }
	        } catch (ClassNotFoundException e) {
	            System.out.println("❌ Driver not found: " + e.getMessage());
	        } catch (SQLException e) {
	            System.out.println("❌ SQL Error: " + e.getMessage());
	        }
	        return con;
	    }
}
