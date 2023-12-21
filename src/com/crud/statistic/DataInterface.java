package com.crud.statistic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataInterface {
    private JFrame frame;
    private JPanel mainPanel;
    private JButton KELASButton;
    private JButton SISWAButton;
    private JButton TRANSAKSIButton;
    private JButton GOLONGANSPPButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("LOGIN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        DataInterface DataInterface = new DataInterface();
        frame.setContentPane(DataInterface.mainPanel);
        frame.setVisible(true);
    }
    public DataInterface() {

        // Mengatur tombol KELASButton
        KELASButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {SwingUtilities.invokeLater(DataInterface::createkelasGUI);
            }
        });

        // Mengatur tombol SISWAButton
        SISWAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(DataInterface::createsiswaGUI);
            }
        });

        // Mengatur tombol GOLONGANSPPButton
        GOLONGANSPPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(DataInterface::creategolonganGUI);
            }
        });

        // Mengatur tombol TRANSAKSIButton
        TRANSAKSIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(DataInterface::createtransaksiGUI);
            }
        });
    }
    public JPanel getmainPanel(){
        return mainPanel;
    }

    private static void createregistGUI() {
        registform registUI = new registform();
        JPanel registRoot = registUI.getregistrationPanel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(registRoot);
        frame.setPreferredSize(new Dimension(800, 800));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createkelasGUI() {
        kelasform kelasUI = new kelasform();
        JPanel kelasRoot = kelasUI.gettambahkelaspanel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(kelasRoot);
        frame.setPreferredSize(new Dimension(800, 800));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private static void createsiswaGUI() {
        tambahsiswa siswaUI = new tambahsiswa();
        JPanel siswaRoot = siswaUI.gettambahsiswapanel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(siswaRoot);
        frame.setPreferredSize(new Dimension(800, 800));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private static void creategolonganGUI() {
        golonganspp golonganUI = new golonganspp();
        JPanel golonganRoot = golonganUI.getgolonganpanel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(golonganRoot);
        frame.setPreferredSize(new Dimension(800, 800));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createtransaksiGUI() {
        transaksi transaksiUI = new transaksi();
        JPanel transaksiRoot = transaksiUI.gettransaksiPanel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(transaksiRoot);
        frame.setPreferredSize(new Dimension(800, 800));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
