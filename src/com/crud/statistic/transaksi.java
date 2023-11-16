package com.crud.statistic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class transaksi {
    private JPanel transaksiPanel;
    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private void showData() {
        try {
            Object[] columnTitles = {"ID", "NIS", "TGL", "ADMIN", "NAMA ADMIN", "GOLONGAN SPP",
                    "NOMINAL", "BULAN", "TAHUN", "NAMA SISWA", "ID KELAS", "NAMA KELAS"};
            tableModel = new DefaultTableModel(null, columnTitles);
            tableModel.setColumnIdentifiers(columnTitles);
            table1.setModel(tableModel);

            String url = "jdbc:mysql://localhost/dataspp";
            String dbUser = "root";
            String dbPass = "";
            Connection connection = DriverManager.getConnection(url, dbUser, dbPass);

            Statement statement = connection.createStatement();
            tableModel.getDataVector().removeAllElements();

            resultSet = statement.executeQuery("SELECT * FROM transaksi");
            while (resultSet.next()) {
                Object[] data = {
                        resultSet.getString("id_transaksi"),
                        resultSet.getString("nis_siswa"),
                        resultSet.getString("tgl_bayar"),
                        resultSet.getString("admin"),
                        resultSet.getString("n_admin"),
                        resultSet.getString("golongan_spp"),
                        resultSet.getString("nominal_spp"),
                        resultSet.getString("bulan"),
                        resultSet.getString("tahun"),
                        resultSet.getString("n_siswa"),
                        resultSet.getString("kelas_id"),
                        resultSet.getString("n_kelas")
                };
                tableModel.addRow(data);
            }
        } catch (SQLException err) {
            throw new RuntimeException(err);
        }
    }

    private JButton SAVEButton;
    private JButton TAMPILKANDATAButton;
    private JButton CANCELButton;
    private JTextField id_transaksi;
    private JTextField nis;
    private JTextField tgl_bayar;
    private JTextField admin;
    private JTextField golongan_spp;
    private JTextField nominal_spp;
    private JTextField bulan;
    private JTextField tahun;
    private JTextField n_siswa;

    private JTextField kelas_id;
    private JTextField n_kelas;
    private JTextField hapus_id;
    private JButton HAPUSButton;

    private JTable table1;
    private JTextField n_admin;

    public static void main(String[] args) {
        JFrame frame = new JFrame("TRANSAKSI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        transaksi transaksi = new transaksi();
        frame.setContentPane(transaksi.transaksiPanel);
        frame.setVisible(true);
    }

    public transaksi() {
        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_transaksiValue = Integer.parseInt(id_transaksi.getText());
                int nis_siswaValue = Integer.parseInt(nis.getText());
                String  tgl_bayarValue = tgl_bayar.getText();
                int adminValue = Integer.parseInt(admin.getText());
                String n_adminValue = n_admin.getText();
                int golongan_sppValue = Integer.parseInt(golongan_spp.getText());
                float nominal_sppValue = Float.parseFloat(nominal_spp.getText());
                int bulanValue = Integer.parseInt(bulan.getText());
                int tahunValue = Integer.parseInt(tahun.getText());
                String n_siswaValue = n_siswa.getText();
                int kelas_idValue = Integer.parseInt(kelas_id.getText());
                String n_kelasValue = n_kelas.getText();

                if (addTransaksi(id_transaksiValue, nis_siswaValue, tgl_bayarValue, adminValue, n_adminValue,
                        golongan_sppValue, nominal_sppValue, bulanValue, tahunValue,
                        n_siswaValue, kelas_idValue, n_kelasValue)) {
                    JOptionPane.showMessageDialog(transaksiPanel, "Data berhasil Ditambahkan.");
                    showData();
                } else {
                    JOptionPane.showMessageDialog(transaksiPanel, "Data Gagal ditambahkan.", "Coba lagi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        TAMPILKANDATAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showData();
            }
        });

        HAPUSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusData();
            }
        });

        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private boolean addTransaksi(int id_transaksi, Integer nis_siswa, String tgl_bayar, Integer admin, String n_admin,
                                 Integer golongan_spp, Float nominal_spp, Integer bulan, Integer tahun,
                                 String n_siswa, Integer kelas_id, String n_kelas) {
        String url = "jdbc:mysql://localhost/dataspp";
        String dbUser = "root";
        String dbPass = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, dbUser, dbPass);
            String sql = "INSERT INTO transaksi (id_transaksi, nis_siswa, tgl_bayar, admin, " +
                    "n_admin,golongan_spp,  nominal_spp, bulan, tahun, n_siswa, kelas_id, n_kelas) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
                preparedStatement.setInt(1, id_transaksi);
                preparedStatement.setInt(2, nis_siswa);
                preparedStatement.setString(3, tgl_bayar);
                preparedStatement.setInt(4, admin);
                preparedStatement.setString(5, n_admin);
                preparedStatement.setInt(6, golongan_spp);
                preparedStatement.setFloat(7, nominal_spp);
                preparedStatement.setInt(8, bulan);
                preparedStatement.setInt(9, tahun);
                preparedStatement.setString(10, n_siswa);
                preparedStatement.setInt(11, kelas_id);
                preparedStatement.setString(12, n_kelas);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public JPanel gettransaksiPanel(){
        return transaksiPanel;
    }
    private void hapusData() {
        int idToDelete = Integer.parseInt(hapus_id.getText());

        String url = "jdbc:mysql://localhost/dataspp";
        String dbUser = "root";
        String dbPass = "";

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, dbUser, dbPass);
            String sql = "DELETE FROM transaksi WHERE id_transaksi = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idToDelete);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(transaksiPanel, "Data berhasil dihapus.");
                showData();
            } else {
                JOptionPane.showMessageDialog(transaksiPanel, "Data tidak ditemukan atau gagal dihapus.", "Coba lagi", JOptionPane.ERROR_MESSAGE);
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
}
