package com.crud.statistic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class tambahsiswa {
    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private void showdata() {
        try {
            Object[] columnTittle = {"NIS", "NAMA","jenis_kelamin","Agama","Telp Siswa","Orang Tua","Telp Ortu","Alamat"
            ,"Id_Kelas","Golongan SPP"};
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
            resultSet = statement.executeQuery("SELECT * FROM siswa");
            while (resultSet.next()) {
                Object[] data = {
                resultSet.getString("nis"),
                resultSet.getString("nama_siswa"),
                        resultSet.getString("jenis_kelamin"),
                resultSet.getString("agama"),
                resultSet.getString("telp_siswa"),
                resultSet.getString("nama_orangtua"),
                resultSet.getString("tlp_ortu"),
                resultSet.getString("alamat"),
                resultSet.getString("kelas_id"),
                resultSet.getString("golongan_id")
                };
                tableModel.addRow(data);
            }
        } catch (SQLException err) {
            throw new RuntimeException(err);
        }
    }
    private JTextField Tnamaortu;
    private JTextField Tnoortu;
    private JTextField Talamat;
    private JTextField Tkelas;
    private JTextField Tgolspp;
    private JTextField Tnis;
    private JTextField Tnama;
    private JTextField Tagama;
    private JTextField Tnosiswa;
    private JComboBox Tjeniskelamin;
    private JButton SAVEButton;
    private JButton CANCELButton;
    private JPanel tambahsiswapanel;
    private JTable table1;
    private JButton TAMPILKANDATAButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("TAMBAH SISWA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        tambahsiswa tambahsiswa = new tambahsiswa();
        frame.setContentPane(tambahsiswa.tambahsiswapanel);
        frame.setVisible(true);
    }

    public tambahsiswa() {
        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nis = Integer.parseInt(Tnis.getText());
                String nama_siswa = Tnama.getText();
                String agama = Tagama.getText();
                String telp_siswa = Tnosiswa.getText();
                String jenis_kelamin = (String) Tjeniskelamin.getSelectedItem();
                String nama_orangtua = Tnamaortu.getText();
                String telp_ortu = Tnoortu.getText();
                String alamat = Talamat.getText();
                int kelas_id = Integer.parseInt(Tkelas.getText());
                int golongan_id = Integer.parseInt(Tgolspp.getText());

                if (tambahSiswa(nis, nama_siswa, jenis_kelamin, agama, telp_siswa, nama_orangtua,
                        telp_ortu, alamat, kelas_id, golongan_id)) {
                    JOptionPane.showMessageDialog(tambahsiswapanel, "Data siswa berhasil disimpan ke database.");
                    showdata();
                } else {
                    JOptionPane.showMessageDialog(tambahsiswapanel, "Gagal menyimpan data siswa ke database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        TAMPILKANDATAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showdata();
            }
        });
    }
    public JPanel gettambahsiswapanel(){
        return tambahsiswapanel;
    }

    private boolean tambahSiswa(int nis, String nama_siswa, String jenis_kelamin, String agama, String telp_siswa, String nama_orangtua, String telp_ortu,
                                String alamat, int kelas_id, int golongan_id) {
        String url = "jdbc:mysql://localhost/dataspp";
        String dbUser = "root";
        String dbPass = "";

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, dbUser, dbPass);
            String sql = "INSERT INTO siswa (nis, nama_siswa, jenis_kelamin, agama, telp_siswa, nama_orangtua, telp_ortu, " +
                    "alamat, kelas_id, golongan_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, nis);
            preparedStatement.setString(2, nama_siswa);
            preparedStatement.setString(3, jenis_kelamin);
            preparedStatement.setString(4, agama);
            preparedStatement.setString(5, telp_siswa);
            preparedStatement.setString(6, nama_orangtua);
            preparedStatement.setString(7, telp_ortu);
            preparedStatement.setString(8, alamat);
            preparedStatement.setInt(9, kelas_id);
            preparedStatement.setInt(10, golongan_id);

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
