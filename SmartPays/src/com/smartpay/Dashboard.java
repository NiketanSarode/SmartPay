package com.smartpay;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private int userId;
    private UserServices userService;

    public Dashboard(int userId) {
        this.userId = userId;
        this.userService = new UserServices();

        setTitle("SmartPay - Dashboard");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 1, 10, 10));

        JButton btnDeposit = new JButton("Deposit Money");
        JButton btnWithdraw = new JButton("Withdraw Money");
        JButton btnCheckBalance = new JButton("Check Balance");
        JButton btnTransfer = new JButton("Transfer Money");
        JButton btnShowInfo = new JButton("Show User Info");
        JButton btnChangePin = new JButton("Change Password");
        JButton btnUpdateMobile = new JButton("Update Mobile Number");
        JButton btnLogout = new JButton("Logout");

        btnDeposit.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
            if (input != null && !input.isEmpty()) {
                double amt = Double.parseDouble(input);
                if (userService.depositMoney(userId, amt)) {
                    JOptionPane.showMessageDialog(this, "✅ Amount deposited!\n\n" + userService.getUserInfo(userId));
                }
            }
        });

        btnWithdraw.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
            if (input != null && !input.isEmpty()) {
                double amt = Double.parseDouble(input);
                if (userService.withdrawMoney(userId, amt)) {
                    JOptionPane.showMessageDialog(this, "✅ Withdrawal successful!\n\n" + userService.getUserInfo(userId));
                }
            }
        });

        btnCheckBalance.addActionListener(e -> userService.checkBalance(userId));

        btnTransfer.addActionListener(e -> {
            String receiverStr = JOptionPane.showInputDialog(this, "Enter receiver account number:");
            String amtStr = JOptionPane.showInputDialog(this, "Enter amount to transfer:");
            if (receiverStr != null && amtStr != null) {
                int receiverId = Integer.parseInt(receiverStr);
                double amt = Double.parseDouble(amtStr);
                if (userService.transferMoney(userId, receiverId, amt)) {
                    JOptionPane.showMessageDialog(this, "✅ Transfer successful!\n\n" + userService.getUserInfo(userId));
                }
            }
        });

        btnShowInfo.addActionListener(e -> {
            String pinStr = JOptionPane.showInputDialog(this, "Enter your PIN:");
            if (pinStr != null) {
                int pin = Integer.parseInt(pinStr);
                userService.showUserInfo(userId, pin);
            }
        });

        btnChangePin.addActionListener(e -> {
            String oldPinStr = JOptionPane.showInputDialog(this, "Enter your old PIN:");
            String newPinStr = JOptionPane.showInputDialog(this, "Enter your new PIN:");
            if (oldPinStr != null && newPinStr != null) {
                int oldPin = Integer.parseInt(oldPinStr);
                int newPin = Integer.parseInt(newPinStr);
                if (userService.changePassword(userId, oldPin, newPin)) {
                    JOptionPane.showMessageDialog(this, "✅ Password changed successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Incorrect old PIN");
                }
            }
        });

        btnUpdateMobile.addActionListener(e -> {
            String pinStr = JOptionPane.showInputDialog(this, "Enter your PIN:");
            String newMobile = JOptionPane.showInputDialog(this, "Enter new mobile number:");
            if (pinStr != null && newMobile != null && !newMobile.trim().isEmpty()) {
                int pin = Integer.parseInt(pinStr);
                if (userService.updateMobileNumber(userId, pin, newMobile)) {
                    JOptionPane.showMessageDialog(this, "✅ Mobile number updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Incorrect PIN or update failed.");
                }
            }
        });

        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginScreen();
        });

        add(btnDeposit);
        add(btnWithdraw);
        add(btnCheckBalance);
        add(btnTransfer);
        add(btnShowInfo);
        add(btnChangePin);
        add(btnUpdateMobile);
        add(btnLogout);

        setVisible(true);
    }
}
