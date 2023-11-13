package com.crud.statistic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost/dataspp"; // Ganti dengan URL database Anda
        String user = "username"; // Ganti dengan username database Anda
        String password = "password"; // Ganti dengan password database Anda

        try {
            // Membuat koneksi ke database
            Connection connection = DriverManager.getConnection(url, user, password);

            // Jika koneksi berhasil, tampilkan pesan sukses
            System.out.println("Koneksi ke database berhasil!");

            // Tutup koneksi
            connection.close();
        } catch (SQLException e) {
            // Jika koneksi gagal, tampilkan pesan kesalahan
            System.err.println("Koneksi ke database gagal: " + e.getMessage());
        }
    }
}
