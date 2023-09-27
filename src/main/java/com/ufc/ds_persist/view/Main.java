package com.ufc.ds_persist.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.formdev.flatlaf.*;

public class Main extends JFrame implements FileStatusObserver {

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

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;

        optionInfoLabel = new JLabel(new String("Selecione uma opção: ".getBytes(), StandardCharsets.UTF_8));
        optionInfoLabel.setHorizontalAlignment(JLabel.CENTER);
        add(optionInfoLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        CSVButton = new JButton("CSV");
        add(CSVButton, gbc);

        gbc.gridx++;
        JSONXMLButton = new JButton("JSON/XML");
        add(JSONXMLButton, gbc);

        gbc.gridx++;
        ZIPButton = new JButton("ZIP");
        add(ZIPButton, gbc);

        gbc.gridx++;
        HASHButton = new JButton("Hash");
        add(HASHButton, gbc);

        gbc.gridwidth = 5;
        gbc.gridx = 0;
        gbc.gridy++;
        csvMessage = new JLabel("Nenhum arquivo CSV carregado", SwingConstants.CENTER);
        csvMessage.setHorizontalAlignment(JLabel.CENTER);
        csvMessage.setVerticalAlignment(JLabel.BOTTOM);
        csvMessage.setForeground(Color.RED);
        add(csvMessage, gbc);

        CSVButton.addActionListener( e -> {

            CSVFrame csvFrame = new CSVFrame();
            csvFrame.setSize(350, 337);
            csvFrame.addObserver(Main.this);
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

    @Override
    public void updateFileStautsLabel(String fileName, Color color) {
        this.csvMessage.setForeground(color);
        this.csvMessage.setText("Arquivo CSV selecionado: " + fileName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            FlatIntelliJLaf.setup();
            Main gui = new Main();

        });
    }


}
