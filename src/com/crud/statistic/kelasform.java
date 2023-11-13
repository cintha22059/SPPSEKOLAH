package com.crud.statistic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class kelasform {
    private JPanel tambahkelaspanel;
    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private void showdata() {
        try {
            Object[] columnTittle = {"ID KELAS", "NAMA KELAS"};
            tableModel = new DefaultTableModel(null, columnTittle);
            tableModel.setColumnIdentifiers(columnTittle);
            table1.setModel(tableModel);

            // Use the correct class and method for database connection
            String url = "jdbc:mysql://localhost/dataspp";
            String dbUser = "root";
            String dbPass = "";
            Connection connection = DriverManager.getConnection(url, dbUser, dbPass);


            Statement statement = connection.createStatement();
            tableModel.getDataVector().removeAllElements();

            //inisiasi result
            resultSet= statement.executeQuery("SELECT*FROM kelas");
            while (resultSet.next()){
                Object[]data={
                        resultSet.getString("id_kelas"),
                        resultSet.getString("nama_kelas")
                };
                tableModel.addRow(data);
            }
        } catch (SQLException err) {
            throw new RuntimeException(err);
        }

    }
    private JTextField Tidkelas;
    private JTextField Tnamakelas;
    private JTable table1;
    private JButton SAVEButton;
    private JButton CANCELButton;
    private JButton tampilkanDataButton;


    public static void main(String[] args) {
        JFrame frame = new JFrame("TAMBAH KELAS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        kelasform kelasform = new kelasform();
        frame.setContentPane(kelasform.tambahkelaspanel);
        frame.setVisible(true);
    }

    public kelasform() {
        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                {
                    int id_kelas = Integer.parseInt(Tidkelas.getText());
                    String nama_kelas = Tnamakelas.getText();

                    if (tambahKelas(id_kelas, nama_kelas)) {
                        JOptionPane.showMessageDialog(tambahkelaspanel, "Registrasi berhasil.");
                    } else {
                        JOptionPane.showMessageDialog(tambahkelaspanel, "Registrasi gagal.", "Coba lagi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        tampilkanDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showdata();
            }
        });
    }
        private boolean tambahKelas (int id_kelas, String nama_kelas)
        {
        String url = "jdbc:mysql://localhost/dataspp";
        String dbUser = "root";
        String dbPass = "";

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, dbUser, dbPass);
            String sql = "INSERT INTO kelas (id_kelas, nama_kelas) VALUES (?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id_kelas);
            preparedStatement.setString(2, nama_kelas );


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
