package com.crud.statistic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class loginfrom {
    private JTextField tusername;
    private JPasswordField tpassword;
    private JPanel loginpanel;
    private JButton CANCELButton;
    private JButton OKButton;
    private Admin user;

    public static void main(String[] args) {
        JFrame frame = new JFrame("LOGIN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        loginfrom loginfrom = new loginfrom();
        frame.setContentPane(loginfrom.loginpanel);
        frame.setVisible(true);
    }

    public loginfrom() {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int username = Integer.parseInt(tusername.getText());
                int password = Integer.parseInt(new String(tpassword.getPassword()));

                user = getAuthenticateAdmin(username, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(loginpanel, "Login berhasil.");
                } else {
                    JOptionPane.showMessageDialog(loginpanel, "Username atau Password Salah", "Coba lagi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private Admin getAuthenticateAdmin(int username, int password) {
        Admin admin = null;

        String url = "jdbc:mysql://localhost/dataspp";
        String dbUser = "root";
        String dbPass = "";

        try {
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
            String sql = "SELECT * FROM admin WHERE Username = ? AND Password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, username);
            preparedStatement.setInt(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int dbUsername = resultSet.getInt("Username");
                int dbPassword = resultSet.getInt("Password");
                admin = new Admin(dbUsername, dbPassword);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return admin;
    }

    private class Admin {
        private int username;
        private int password;

        public Admin(int username, int password) {
            this.username = username;
            this.password = password;
        }

        public int getUsername() {
            return username;
        }

        public int getPassword() {
            return password;
        }
    }
}
