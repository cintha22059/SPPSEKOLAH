package com.crud.statistic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class registform {
    private JTextField tAdminName;
    private JTextField tUsername;
    private JPasswordField tPassword;
    private JPanel registrationPanel;
    private JButton registButton;
    private JButton cancelButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Registration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        registform registform = new registform();
        frame.setContentPane(registform.registrationPanel);
        frame.setVisible(true);
    }

    public registform() {
        registButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int username = Integer.parseInt(tUsername.getText());
                int password = Integer.parseInt(new String(tPassword.getPassword()));
                String adminName = tAdminName.getText();

                if (registerAdmin(username, password, adminName)) {
                    JOptionPane.showMessageDialog(registrationPanel, "Registrasi berhasil.");
                } else {
                    JOptionPane.showMessageDialog(registrationPanel, "Registrasi gagal.", "Coba lagi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private boolean registerAdmin(int username, int password, String adminName)
    {
        String url = "jdbc:mysql://localhost/dataspp";
        String dbUser = "root";
        String dbPass = "";

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, dbUser, dbPass);
            String sql = "INSERT INTO admin (username, password, nama_admin) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, username);
            preparedStatement.setInt(2, password);
            preparedStatement.setString(3, adminName);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
