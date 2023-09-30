package com.ufc.ds_persist.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.formdev.flatlaf.*;
import com.ufc.ds_persist.view.interfaces.FileObserver;
import com.ufc.ds_persist.view.interfaces.FileStatusObserver;

public class Main extends JFrame implements FileStatusObserver, FileObserver {

    private static final String ERROR_NO_CSV_LOADED = "Nenhum arquivo CSV carregado";
    private static final String ERROR_TITLE = "Erro";

    private final JLabel csvMessage;

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

        JLabel optionInfoLabel = new JLabel(new String("Selecione uma opção: ".getBytes(), StandardCharsets.UTF_8));
        optionInfoLabel.setHorizontalAlignment(JLabel.CENTER);
        add(optionInfoLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        JButton CSVButton = new JButton("CSV");
        add(CSVButton, gbc);

        gbc.gridx++;
        JButton JSONXMLButton = new JButton("JSON/XML");
        add(JSONXMLButton, gbc);

        gbc.gridx++;
        JButton ZIPButton = new JButton("ZIP");
        add(ZIPButton, gbc);

        gbc.gridx++;
        JButton HASHButton = new JButton("Hash");
        add(HASHButton, gbc);

        gbc.gridwidth = 5;
        gbc.gridx = 0;
        gbc.gridy++;
        csvMessage = new JLabel("Nenhum arquivo CSV carregado", SwingConstants.CENTER);
        csvMessage.setHorizontalAlignment(JLabel.CENTER);
        csvMessage.setVerticalAlignment(JLabel.BOTTOM);
        csvMessage.setForeground(Color.RED);
        add(csvMessage, gbc);

        CSVButton.addActionListener(e -> {

            CSVFrame csvFrame = new CSVFrame();
            csvFrame.setSize(350, 337);
            csvFrame.setLocationRelativeTo(Main.this);
            csvFrame.addObserver((FileObserver) Main.this);
            csvFrame.addObserver((FileStatusObserver) Main.this);
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

                @Override
                public void windowClosed(WindowEvent e) {
                    Main.this.setVisible(true);
                }
            });

        });

        JSONXMLButton.addActionListener(e -> {

            if (csvFile == null) {
                JOptionPane.showMessageDialog(null, ERROR_NO_CSV_LOADED, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
            } else {
                JSONXMLFrame jsonxmlFrame = new JSONXMLFrame();
                jsonxmlFrame.setSize(337, 150);
                jsonxmlFrame.setLocationRelativeTo(Main.this);
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
            }

        });

        ZIPButton.addActionListener(e -> {

            if (csvFile == null) {
                JOptionPane.showMessageDialog(null, ERROR_NO_CSV_LOADED, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
            } else {

                int last = csvFile.getName().lastIndexOf(".");
                String zipName = csvFile.getName().substring(0, last) + ".zip";
                String directory = csvFile.getParent();

                File newZipFile = new File(directory, zipName);

                try (
                    FileOutputStream fos = new FileOutputStream(newZipFile);
                    ZipOutputStream zipOut = new ZipOutputStream(fos);
                    FileInputStream fis = new FileInputStream(csvFile)
                ) {
                    ZipEntry zipEntry = new ZipEntry(csvFile.getName());
                    zipOut.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        zipOut.write(buffer, 0, bytesRead);
                    }

                    System.out.println("File zipped successfully.");
                } catch (IOException exception) {
                    System.err.println(exception.getMessage());
                }

                JOptionPane.showMessageDialog(null, "Arquivo CSV compactado com sucesso!",
                        new String("Compactação concluída!".getBytes(), StandardCharsets.UTF_8), JOptionPane.INFORMATION_MESSAGE);

            }
        });

        HASHButton.addActionListener(e -> {

            if (csvFile == null) {
                JOptionPane.showMessageDialog(null, ERROR_NO_CSV_LOADED, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
            } else {

                JTextArea ta = new JTextArea(1, 10);

                byte[] data = new byte[(int) csvFile.length()];
                byte[] hash = new byte[0];
                try ( FileInputStream fis = new FileInputStream(csvFile)){
                    int a = 0;
                    while (a != -1) {
                        a = fis.read(data, 0, data.length);
                    }
                    hash = MessageDigest.getInstance("SHA256").digest(data);

                } catch (NoSuchAlgorithmException | IOException ignore) { }

                StringBuilder hashCSVFile = new StringBuilder();
                for (byte b : hash) {
                    hashCSVFile.append(String.format("%02x", b));
                }

                ta.append(hashCSVFile.toString());
                ta.setWrapStyleWord(true);
                ta.setLineWrap(false);
                ta.setCaretPosition(0);
                ta.setEditable(false);

                JOptionPane.showMessageDialog(null, ta, "Hash calculado com sucesso!", JOptionPane.PLAIN_MESSAGE);

            }

        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(337, 200);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    @Override
    public void updateFileStautsLabel(String fileName, Color color) {
        this.csvMessage.setForeground(color);
        this.csvMessage.setText("Arquivo CSV selecionado: " + fileName);
    }

    @Override
    public void setFile(File file) {
        this.csvFile = file;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            FlatIntelliJLaf.setup();
            new Main();

        });
    }


}
