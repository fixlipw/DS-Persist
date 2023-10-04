package com.ufc.ds_persist.view.frames;

import com.ufc.ds_persist.view.observers.FileObserver;
import com.ufc.ds_persist.view.observers.FileStatusObserver;

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
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MainFrame extends JFrame implements FileStatusObserver, FileObserver {

    private static final String ERROR_NO_CSV_LOADED = "Nenhum arquivo CSV carregado";
    private static final String ERROR_TITLE = "Erro";

    private JLabel csvMessage;
    private File csvFile;

    public MainFrame() {
        super("Menu Principal");
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("book-marked.png")));
        setIconImage(icon.getImage());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;

        setOptionInfoLabel(gbc);
        setOptionButtons(gbc);
        setCsvMessageLabel(gbc);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(337, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setOptionInfoLabel(GridBagConstraints gbc) {
        JLabel optionInfoLabel = new JLabel(new String("Selecione uma opção: ".getBytes(), StandardCharsets.UTF_8));
        optionInfoLabel.setHorizontalAlignment(JLabel.CENTER);
        add(optionInfoLabel, gbc);
    }

    private void setOptionButtons(GridBagConstraints gbc) {
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

        addActionListeners(CSVButton, JSONXMLButton, ZIPButton, HASHButton);
    }

    private void setCsvMessageLabel(GridBagConstraints gbc) {
        gbc.gridwidth = 5;
        gbc.gridx = 0;
        gbc.gridy++;
        csvMessage = new JLabel("Nenhum arquivo CSV carregado", SwingConstants.CENTER);
        csvMessage.setHorizontalAlignment(JLabel.CENTER);
        csvMessage.setVerticalAlignment(JLabel.BOTTOM);
        csvMessage.setForeground(Color.RED);
        add(csvMessage, gbc);
    }

    private void addActionListeners(JButton CSVButton, JButton JSONXMLButton, JButton ZIPButton, JButton HASHButton) {
        CSVButton.addActionListener(e -> handleCSVButton());
        JSONXMLButton.addActionListener(e -> handleJSONXMLButton());
        ZIPButton.addActionListener(e -> handleZIPButton());
        HASHButton.addActionListener(e -> handleHASHButton());
    }

    private void handleCSVButton() {
        CSVFrame csvFrame = new CSVFrame();
        csvFrame.setSize(350, 337);
        csvFrame.setLocationRelativeTo(MainFrame.this);
        csvFrame.addObserver((FileStatusObserver) this);
        csvFrame.addObserver((FileObserver) this);
        csvFrame.setVisible(true);

        csvFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                MainFrame.this.setVisible(false);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                MainFrame.this.setVisible(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                MainFrame.this.setVisible(true);
            }
        });
    }

    private void handleJSONXMLButton() {
        if (csvFile == null) {
            JOptionPane.showMessageDialog(null, ERROR_NO_CSV_LOADED, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        } else {
            JSONXMLFrame jsonxmlFrame = new JSONXMLFrame();
            jsonxmlFrame.setSize(337, 150);
            jsonxmlFrame.setLocationRelativeTo(MainFrame.this);
            jsonxmlFrame.setVisible(true);

            jsonxmlFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    MainFrame.this.setVisible(false);
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    MainFrame.this.setVisible(true);
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    MainFrame.this.setVisible(true);
                }
            });
        }
    }

    private void handleZIPButton() {
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

                System.out.println("Arquivo CSV compactado com sucesso!");
            } catch (IOException exception) {
                System.err.println(exception.getMessage());
            }

            JOptionPane.showMessageDialog(null, "Arquivo CSV compactado com sucesso!",
                    new String("Compactação concluída!".getBytes(), StandardCharsets.UTF_8), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleHASHButton() {
        if (csvFile == null) {
            JOptionPane.showMessageDialog(null, ERROR_NO_CSV_LOADED, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        } else {
            JTextArea hashTextArea = new JTextArea(1, 10);

            int fileLength = (csvFile.length() == 0L ? 0 : (int) csvFile.length());

            byte[] data = new byte[fileLength];
            byte[] hash = new byte[0];
            try (FileInputStream fis = new FileInputStream(csvFile)) {
                if (data.length > 0) {
                    int a = 0;
                    while (a != -1) {
                        a = fis.read(data, 0, data.length);
                    }
                }
                hash = MessageDigest.getInstance("SHA256").digest(data);

            } catch (NoSuchAlgorithmException | IOException ignore) {
            }

            StringBuilder hashCSVFile = new StringBuilder();
            for (byte b : hash) {
                hashCSVFile.append(String.format("%02x", b));
            }

            hashTextArea.append(hashCSVFile.toString());
            hashTextArea.setWrapStyleWord(true);
            hashTextArea.setLineWrap(false);
            hashTextArea.setCaretPosition(0);
            hashTextArea.setEditable(false);

            JOptionPane.showMessageDialog(null, hashTextArea, "Hash calculado com sucesso!", JOptionPane.PLAIN_MESSAGE);
        }
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
}
