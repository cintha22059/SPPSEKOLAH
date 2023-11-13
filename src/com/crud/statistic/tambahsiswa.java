package com.crud.statistic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class tambahsiswa {
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

                if (tambahSiswa(nis, nama_siswa, jenis_kelamin, agama, telp_siswa, nama_orangtua, telp_ortu, alamat, kelas_id, golongan_id)) {
                    JOptionPane.showMessageDialog(tambahsiswapanel, "Data siswa berhasil disimpan ke database.");
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
            String sql = "INSERT INTO siswa (nis, nama_siswa, jenis_kelamin, agama, telp_siswa, nama_orangtua, telp_ortu, alamat, kelas_id, golongan_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
