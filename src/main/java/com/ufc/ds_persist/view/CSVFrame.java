package com.ufc.ds_persist.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CSVFrame extends JFrame {

    private final ArrayList<FileStatusObserver> observers = new ArrayList<>();

    public void addObserver(FileStatusObserver observer) {
        observers.add(observer);
    }

    public CSVFrame() {
        super(new String("Menu Principal → CSV".getBytes(), StandardCharsets.UTF_8));

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel selectCsvPanel = new JPanel();
        JPanel insertPannel = new JPanel();
        JPanel showPannel = new JPanel();

        final JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        tabbedPane.addTab("Selecionar CSV", null, selectCsvPanel);
        tabbedPane.addTab("Inserir elemento", null, insertPannel);
        tabbedPane.addTab("Lista de elementos", null, showPannel);
        tabbedPane.setSelectedIndex(-1);
        add(tabbedPane);

        selectCsvPanel.setLayout(new GridBagLayout());

        tabbedPane.addChangeListener(l -> {

            switch (tabbedPane.getSelectedIndex()) {
                case 0 -> {
                    setSize(500, 400);

                    fileChooser.setAcceptAllFileFilterUsed(false);
                    fileChooser.setDialogTitle("Selecione um arquivo .csv");
                    fileChooser.addChoosableFileFilter(
                            new FileNameExtensionFilter("Apenas arquivos .csv", "csv"));

                    selectCsvPanel.add(fileChooser);

                    fileChooser.addActionListener( e -> {

                        if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                            String fileName = fileChooser.getSelectedFile().getName();
                            notifyObservers(fileName);
                            JOptionPane.showMessageDialog(null,
                                    "Arquivo CSV selecionado com sucesso!",
                                    new String("Concluído!".getBytes(), StandardCharsets.UTF_8),
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
                            tabbedPane.setSelectedIndex(-1);
                        }

                    });

                }
                default -> setSize(350, 337);
            }

        });

        setVisible(true);
    }

    private void notifyObservers(String message) {
        for (FileStatusObserver observer : observers) {
            observer.updateFileStautsLabel(message, Color.MAGENTA);
        }
    }
}