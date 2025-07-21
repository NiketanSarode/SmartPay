package com.smartpay;

import javax.swing.*;
import java.awt.*;

public class SignUpScreen extends JFrame {
    private JTextField txtName, txtMobile, txtDeposit;
    private JPasswordField txtPin;
    private UserServices userService;

    public SignUpScreen() {
        userService = new UserServices();

        setTitle("SmartPay - Signup");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);

        JLabel lblTitle = new JLabel("Create Your Account", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(90, 10, 220, 30);
        panel.add(lblTitle);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(50, 60, 120, 25);
        panel.add(lblName);
        txtName = new JTextField();
        txtName.setBounds(180, 60, 150, 25);
        panel.add(txtName);

        JLabel lblMobile = new JLabel("Mobile No:");
        lblMobile.setBounds(50, 100, 120, 25);
        panel.add(lblMobile);
        txtMobile = new JTextField();
        txtMobile.setBounds(180, 100, 150, 25);
        panel.add(txtMobile);

        JLabel lblDeposit = new JLabel("Initial Deposit:");
        lblDeposit.setBounds(50, 140, 120, 25);
        panel.add(lblDeposit);
        txtDeposit = new JTextField();
        txtDeposit.setBounds(180, 140, 150, 25);
        panel.add(txtDeposit);

        JLabel lblPin = new JLabel("Set PIN:");
        lblPin.setBounds(50, 180, 120, 25);
        panel.add(lblPin);
        txtPin = new JPasswordField();
        txtPin.setBounds(180, 180, 150, 25);
        panel.add(txtPin);

        JButton btnSignup = new JButton("Create Account");
        btnSignup.setBounds(120, 230, 150, 30);
        panel.add(btnSignup);

        btnSignup.addActionListener(e -> {
            try {
                String name = txtName.getText();
                String mobile = txtMobile.getText();
                double deposit = Double.parseDouble(txtDeposit.getText());
                int pin = Integer.parseInt(new String(txtPin.getPassword()));

                if (userService.createAccount(name, mobile, deposit, pin)) {
                    dispose();
                    new LoginScreen();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Signup failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SignUpScreen();
    }
}
