package com.crud.statistic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class golonganspp {
    private JPanel golonganpanel;
    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private void showdata() {
        try {
            Object[] columnTittle = {"ID GOLONGAN", "NAMA GOLONGAN", "JUMLAH"};
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
            resultSet = statement.executeQuery("SELECT * FROM golongan_pembayaran");
            while (resultSet.next()) {
                Object[] data = {
                        resultSet.getString("id_golongan"),
                        resultSet.getString("nama_golongan"),
                        resultSet.getString("nominal")
                };
                tableModel.addRow(data);
            }
        } catch (SQLException err) {
            throw new RuntimeException(err);
        }
    }

    private JTextField idgolongan;
    private JTextField jenisgolongan;
    private JTextField jumlahgolongan;
    private JTable table1;
    private JButton SAVEButton;
    private JButton CANCELButton;
    private JButton tampilkanDataButton;
    private JTextField HAPUSID;
    private JButton HAPUSButton;
    private JTextField UPDATEID;
    private JTextField newid;

    private JTextField newjenis;
    private JButton UPDATEButton;

    private JTextField newjumlah;
    private JButton MENUButton;
    private JComboBox comboBox1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("TAMBAH GOLONGAN SPP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        golonganspp golonganspp = new golonganspp();
        frame.setContentPane(golonganspp.golonganpanel);
        frame.setVisible(true);
    }

    public golonganspp() {
        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_golongan = Integer.parseInt(idgolongan.getText());
                String nama_golongan = jenisgolongan.getText();
                float jumlah = Float.parseFloat(jumlahgolongan.getText());

                if (tambahgolongan(id_golongan, nama_golongan, jumlah)) {
                    JOptionPane.showMessageDialog(golonganpanel, "Data berhasil Ditambahkan.");
                    showdata();
                } else {
                    JOptionPane.showMessageDialog(golonganpanel, "Data Gagal ditambahkan.", "Coba lagi", JOptionPane.ERROR_MESSAGE);
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
                SwingUtilities.invokeLater(golonganspp::createmenuGUI);
            }
        });
    }
    public JPanel getgolonganpanel(){
        return golonganpanel;
    }
    private boolean tambahgolongan(int id_golongan, String nama_golongan, float nominal) {
        String url = "jdbc:mysql://localhost/dataspp";
        String dbUser = "root";
        String dbPass = "";

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, dbUser, dbPass);
            String sql = "INSERT INTO golongan_pembayaran (id_golongan, nama_golongan, nominal) VALUES (?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id_golongan);
            preparedStatement.setString(2, nama_golongan);
            preparedStatement.setFloat(3, nominal);

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

    private void updateData() {
        int idToUpdate = Integer.parseInt(UPDATEID.getText());
        int newIdValue = Integer.parseInt(newid.getText());
        String newNamaGolonganValue = newjenis.getText();
        float newNominalValue = Float.parseFloat(newjumlah.getText());

        String Uurl = "jdbc:mysql://localhost/dataspp";
        String UdbUser = "root";
        String Udbpass = "";

        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(Uurl, UdbUser, Udbpass);
            String sql = "UPDATE golongan_pembayaran SET id_golongan = ?, nama_golongan = ?, nominal = ? WHERE id_golongan = ?";

            // Create a new PreparedStatement for update
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newIdValue);
            preparedStatement.setString(2, newNamaGolonganValue);
            preparedStatement.setFloat(3, newNominalValue);
            preparedStatement.setInt(4, idToUpdate);

            System.out.println("Update SQL: " + preparedStatement.toString()); // Add this line

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(golonganpanel, "Data berhasil diupdate.");
                showdata();
            } else {
                JOptionPane.showMessageDialog(golonganpanel, "Data tidak ditemukan atau gagal diupdate.", "Coba lagi", JOptionPane.ERROR_MESSAGE);
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

    private void hapusData() {
        int idToDelete = Integer.parseInt(HAPUSID.getText());

        String url = "jdbc:mysql://localhost/dataspp";
        String dbUser = "root";
        String dbPass = "";

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, dbUser, dbPass);
            String sql = "DELETE FROM golongan_pembayaran WHERE id_golongan = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idToDelete);

            System.out.println("Delete SQL: " + preparedStatement.toString()); // Add this line

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(golonganpanel, "Data berhasil dihapus.");
                showdata();
            } else {
                JOptionPane.showMessageDialog(golonganpanel, "Data tidak ditemukan atau gagal dihapus.", "Coba lagi", JOptionPane.ERROR_MESSAGE);
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
    private static void createmenuGUI() {
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