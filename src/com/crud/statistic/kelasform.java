package com.crud.statistic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.*;
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

            // Inisiasi result
            resultSet = statement.executeQuery("SELECT * FROM kelas");
            while (resultSet.next()) {
                Object[] data = {
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
    private JTextField HAPUSID;
    private JButton HAPUSButton;
    private JTextField UPDATEID;
    private JTextField newid;
    private JTextField newnamakelas;
    private JButton UPDATEButton;
    private JButton MENUButton;


    public static void main(String[] args) {
        JFrame frame = new JFrame("TAMBAH KELAS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        kelasform kelasform = new kelasform();
        frame.setContentPane(kelasform.tambahkelaspanel);
        frame.setVisible(true);
    }

    public kelasform() {
        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_kelas = Integer.parseInt(Tidkelas.getText());
                String nama_kelas = Tnamakelas.getText();

                if (tambahKelas(id_kelas, nama_kelas)) {
                    JOptionPane.showMessageDialog(tambahkelaspanel, "Data berhasil Ditambahkan.");
                    showdata();
                } else {
                    JOptionPane.showMessageDialog(tambahkelaspanel, "Data Gagal ditambahkan.", "Coba lagi", JOptionPane.ERROR_MESSAGE);
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
        HAPUSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusData();
            }
        });
        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        MENUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(kelasform::createmenuGUI);

            }
        });
    }

    private boolean tambahKelas(int id_kelas, String nama_kelas) {
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
            preparedStatement.setString(2, nama_kelas);

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

    private void hapusData() {
        // Get the ID to delete from the HAPUSID JTextField
        int idToDelete = Integer.parseInt(HAPUSID.getText());

        String url = "jdbc:mysql://localhost/dataspp";
        String dbUser = "root";
        String dbPass = "";

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, dbUser, dbPass);
            String sql = "DELETE FROM kelas WHERE id_kelas = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idToDelete);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(tambahkelaspanel, "Data berhasil dihapus.");
                showdata(); // Refresh data after deleting
            } else {
                JOptionPane.showMessageDialog(tambahkelaspanel, "Data tidak ditemukan atau gagal dihapus.", "Coba lagi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateData() {
        // Get the IDs and new data from the corresponding JTextFields
        int idToUpdate = Integer.parseInt(UPDATEID.getText());
        int newId = Integer.parseInt(newid.getText());
        String newNamaKelas = newnamakelas.getText();

        String Uurl = "jdbc:mysql://localhost/dataspp";
        String UdbUser = "root";
        String Udbpass = "";

        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(Uurl, UdbUser, Udbpass);
            String sql = "UPDATE kelas SET id_kelas = ?, nama_kelas = ? WHERE id_kelas = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newId);
            preparedStatement.setString(2, newNamaKelas);
            preparedStatement.setInt(3, idToUpdate);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(tambahkelaspanel, "Data berhasil diupdate.");
                showdata(); // Refresh data after updating
            } else {
                JOptionPane.showMessageDialog(tambahkelaspanel, "Data tidak ditemukan atau gagal diupdate.", "Coba lagi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public JPanel gettambahkelaspanel(){
        return tambahkelaspanel;
    }

    static void createmenuGUI() {
        DataInterface menuUI = new DataInterface();
        JPanel menuroot = menuUI.getmainPanel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(menuroot);
        frame.setPreferredSize(new Dimension(800, 800));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}