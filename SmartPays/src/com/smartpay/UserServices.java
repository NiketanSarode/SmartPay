package com.smartpay;

import java.sql.*;
import javax.swing.*;
import java.util.Scanner;

public class UserServices {
	private Connection con;

    public UserServices() {
    	con = DatabaseConnection.getConnection();
    }

    public boolean login(int id, int pin) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id=? AND pin=?");
            ps.setInt(1, id);
            ps.setInt(2, pin);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }
    public boolean createAccount(String name, String mobile, double balance, int pin) {
        try {
            String sql = "INSERT INTO users (name, mobile, balance, pin) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, mobile);
            ps.setDouble(3, balance);
            ps.setInt(4, pin);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    JOptionPane.showMessageDialog(null, "✅ Account Created!\nYour Account Number: " + newId);
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Signup error: " + e.getMessage());
        }
        return false;
    }

    public boolean depositMoney(int id, double amt) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET balance = balance + ? WHERE id = ?");
            ps.setDouble(1, amt);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Deposit error: " + e.getMessage());
            return false;
        }
    }

    public boolean withdrawMoney(int id, double amt) {
        try {
            PreparedStatement check = con.prepareStatement("SELECT balance FROM users WHERE id = ?");
            check.setInt(1, id);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getDouble("balance") >= amt) {
                PreparedStatement ps = con.prepareStatement("UPDATE users SET balance = balance - ? WHERE id = ?");
                ps.setDouble(1, amt);
                ps.setInt(2, id);
                return ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.out.println("Withdraw error: " + e.getMessage());
        }
        return false;
    }

    public boolean transferMoney(int senderId, int receiverId, double amt) {
        try {
            con.setAutoCommit(false);
            if (withdrawMoney(senderId, amt)) {
                depositMoney(receiverId, amt);
                con.commit();
                return true;
            } else {
                con.rollback();
            }
        } catch (Exception e) {
            System.out.println("Transfer error: " + e.getMessage());
        }
        return false;
    }

    public void checkBalance(int id) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT balance FROM users WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double bal = rs.getDouble("balance");
                JOptionPane.showMessageDialog(null, "💰 Current Balance: ₹" + bal);
            }
        } catch (Exception e) {
            System.out.println("Balance check error: " + e.getMessage());
        }
    }

    public void showUserInfo(int id, int pin) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id = ? AND pin = ?");
            ps.setInt(1, id);
            ps.setInt(2, pin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String info = "👤 Name: " + rs.getString("name") +
                        "\n📱 Mobile: " + rs.getString("mobile") +
                        "\n💰 Balance: ₹" + rs.getDouble("balance");
                JOptionPane.showMessageDialog(null, info);
            } else {
                JOptionPane.showMessageDialog(null, "❌ Incorrect PIN");
            }
        } catch (Exception e) {
            System.out.println("Show info error: " + e.getMessage());
        }
    }

    public boolean changePassword(int id, int oldPin, int newPin) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET password = ? WHERE id = ? AND password = ?");
            ps.setInt(1, newPin);
            ps.setInt(2, id);
            ps.setInt(3, oldPin);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Change password error: " + e.getMessage());
            return false;
        }
    }

    public boolean updateMobileNumber(int id, int pin, String newMobile) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET mobile = ? WHERE id = ? AND pin = ?");
            ps.setString(1, newMobile);
            ps.setInt(2, id);
            ps.setInt(3, pin);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Update mobile error: " + e.getMessage());
            return false;
        }
    }

    public String getUserInfo(int id) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT name, mobile, balance FROM users WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return "👤 Name: " + rs.getString("name") +
                        "\n📱 Mobile: " + rs.getString("mobile") +
                        "\n💰 Balance: ₹" + rs.getDouble("balance");
            }
        } catch (Exception e) {
            System.out.println("Get user info error: " + e.getMessage());
        }
        return "User not found.";
    }
}



