package com.ufc.ds_persist.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;

public class Main extends JFrame {
    private final JLabel optionInfoLabel;
    private final JButton CSVButton;
    private final JButton JSONXMLButton;
    private final JButton ZIPButton;
    private final JButton HASHButton;
    private JLabel csvMessage;

    private File csvFile;

    public Main() {

        super("Menu Principal");
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;

        optionInfoLabel = new JLabel(new String("Selecione uma opção: ".getBytes(), StandardCharsets.UTF_8));
        optionInfoLabel.setHorizontalAlignment(JLabel.CENTER);
        add(optionInfoLabel, gbc);

        Dimension rv = new Dimension(0,0);

        gbc.gridwidth = 1;
        gbc.gridy++;
        CSVButton = new JButton("CSV");
        CSVButton.setSize(rv);
        add(CSVButton, gbc);

        gbc.gridx++;
        JSONXMLButton = new JButton("JSON/XML");
        JSONXMLButton.setSize(rv);
        add(JSONXMLButton, gbc);

        gbc.gridx++;
        ZIPButton = new JButton("ZIP");
        ZIPButton.setSize(rv);
        add(ZIPButton, gbc);

        gbc.gridx++;
        HASHButton = new JButton("Hash");
        HASHButton.setSize(rv);
        add(HASHButton, gbc);

        gbc.gridwidth = 5;
        gbc.gridx = 0;
        gbc.gridy++;
        csvMessage = new JLabel("Nenhum arquivo CSV carregado", SwingConstants.CENTER);
        csvMessage.setBorder(BasicBorders.getTextFieldBorder());
        csvMessage.setHorizontalAlignment(JLabel.CENTER);
        add(csvMessage, gbc);

        CSVButton.addActionListener( e -> {

            CSVFrame csvFrame = new CSVFrame();
            csvFrame.setSize(337, 337);
            csvFrame.setVisible(true);

            csvFrame.addWindowListener(new WindowAdapter() {

                @Override
                public void windowOpened(WindowEvent e) {
                    Main.this.setVisible(false);
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    Main.this.setVisible(true);
                }
            });

        });

        JSONXMLButton.addActionListener( e -> {

            JSONXMLFrame jsonxmlFrame = new JSONXMLFrame();
            jsonxmlFrame.setSize(337, 337);
            jsonxmlFrame.setVisible(true);

            jsonxmlFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    Main.this.setVisible(false);
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    Main.this.setVisible(true);
                }
            });

        });

        ZIPButton.addActionListener( e -> {

            ZIPFrame zipFrame = new ZIPFrame();
            zipFrame.setSize(337, 337);
            zipFrame.setVisible(true);

            zipFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    Main.this.setVisible(false);
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    Main.this.setVisible(true);
                }
            });

        });

        HASHButton.addActionListener( e -> {

            if (csvFile == null) {
                JOptionPane.showMessageDialog(null, "Nenhum arquivo CSV carregado", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        });


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(337, 337);
        setVisible(true);

    }

    public void setCsvFile(File csvFile) {
        this.csvFile = csvFile;
    }

    public File getCsvFile() {
        return csvFile;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            Main gui = new Main();

        });
    }
}
