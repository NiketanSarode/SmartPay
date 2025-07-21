package com.smartpay;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField txtAccount;
    private JPasswordField txtPin;
    private UserServices userService;

    public LoginScreen() {
        userService = new UserServices();

        setTitle("SmartPay - Login");
        setSize(350, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("Welcome to SmartPay", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(60, 10, 220, 30);
        panel.add(lblTitle);

        JLabel lblAccount = new JLabel("Account Number:");
        lblAccount.setBounds(30, 60, 120, 25);
        panel.add(lblAccount);

        txtAccount = new JTextField();
        txtAccount.setBounds(160, 60, 140, 25);
        panel.add(txtAccount);

        JLabel lblPin = new JLabel("PIN:");
        lblPin.setBounds(30, 100, 120, 25);
        panel.add(lblPin);

        txtPin = new JPasswordField();
        txtPin.setBounds(160, 100, 140, 25);
        panel.add(txtPin);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(120, 140, 100, 30);
        panel.add(btnLogin);

        JButton btnSignup = new JButton("Signup");
        btnSignup.setBounds(120, 180, 100, 25);
        panel.add(btnSignup);

        btnLogin.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtAccount.getText());
                int pin = Integer.parseInt(new String(txtPin.getPassword()));

                if (userService.login(id, pin)) {
                    JOptionPane.showMessageDialog(this, "✅ Login successful!");
                    this.dispose();
                    new Dashboard(id);
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSignup.addActionListener(e -> {
            this.dispose();
            new SignUpScreen();
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}