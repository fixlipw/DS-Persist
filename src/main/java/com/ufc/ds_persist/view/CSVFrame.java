package com.ufc.ds_persist.view;

import com.ufc.ds_persist.controller.BookController;
import com.ufc.ds_persist.enumeration.BookType;
import com.ufc.ds_persist.model.Leitura;
import com.ufc.ds_persist.util.CSVutil;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVFrame extends JFrame {

    private final ArrayList<FileObserver> fileObservers = new ArrayList<>();
    private final ArrayList<FileStatusObserver> statusobservers = new ArrayList<>();
    private final BookController controller = BookController.getInstance();

    public void addObserver(FileObserver observer) {
        fileObservers.add(observer);
    }
    public void addObserver(FileStatusObserver observer) {
        statusobservers.add(observer);
    }

    public CSVFrame() {
        super(new String("Menu Principal → CSV".getBytes(), StandardCharsets.UTF_8));

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel selectCsvPanel = new JPanel();
        JPanel insertPannel = new JPanel();
        JPanel showPannel = new JPanel();

        final JTextField titleField = new JTextField();
        final JTextField authorNameField = new JTextField();
        final JTextField pagesQtdField = new JTextField("0");
        final JComboBox<BookType> box = new JComboBox<>(BookType.values());
        final JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        tabbedPane.addTab("Selecionar CSV", null, selectCsvPanel);
        tabbedPane.addTab("Inserir elemento", null, insertPannel);
        tabbedPane.addTab("Lista de elementos", null, showPannel);
        tabbedPane.setSelectedIndex(-1);
        add(tabbedPane);

        selectCsvPanel.setLayout(new GridBagLayout());
        insertPannel.setLayout(new GridBagLayout());

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
                            notifyObservers(fileChooser.getSelectedFile().getName());
                            notifyObservers(fileChooser.getSelectedFile());
                            JOptionPane.showMessageDialog(null,
                                    "Arquivo CSV selecionado com sucesso!",
                                    new String("Concluído!".getBytes(), StandardCharsets.UTF_8),
                                    JOptionPane.INFORMATION_MESSAGE);

                            controller.setCSVFilePath(fileChooser.getSelectedFile().getPath());
                            controller.readLeiturasFromCSV();

                            dispose();
                        } else if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
                            tabbedPane.setSelectedIndex(-1);
                        }

                    });

                }

                case 1 -> {
                    setSize(337,337);

                    JButton addButton = new JButton("Adicionar");

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.insets = new Insets(5, 5, 5, 5);

                    insertPannel.add(new JLabel(new String("Título:".getBytes(), StandardCharsets.UTF_8)), gbc);
                    gbc.gridx++;
                    insertPannel.add(titleField, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    insertPannel.add(new JLabel("Nome do Autor:"), gbc);
                    gbc.gridx++;
                    insertPannel.add(authorNameField, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    insertPannel.add(new JLabel(new String("Quantidade de Páginas:".getBytes(), StandardCharsets.UTF_8)), gbc);
                    gbc.gridx++;
                    insertPannel.add(pagesQtdField, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    insertPannel.add(new JLabel("Tipo:"), gbc);
                    gbc.gridx++;
                    insertPannel.add(box, gbc);

                    gbc.gridx = 0;
                    gbc.gridy++;
                    gbc.gridwidth = 2;
                    gbc.anchor = GridBagConstraints.CENTER;
                    insertPannel.add(addButton, gbc);

                    addButton.addActionListener(e -> {

                        String title = titleField.getText();
                        String authorName = authorNameField.getText();
                        int pagesQtd = Integer.parseInt(pagesQtdField.getText());
                        BookType type = (BookType) box.getSelectedItem();

                        if (!controller.getCSVFile().exists()) {

                            JOptionPane.showMessageDialog(null,
                                    new String("Arquivo CSV ainda não foi carregado.".getBytes(), StandardCharsets.UTF_8),
                                    "Erro", JOptionPane.ERROR_MESSAGE);

                        } else if (!title.isEmpty() && !authorName.isEmpty() && box.getSelectedItem() != null) {

                            Leitura leitura = new Leitura(title, authorName, pagesQtd, type);
                            controller.addLeitura(leitura);
                            notifyObservers(controller.getCSVFile());
                            titleField.setText("");
                            authorNameField.setText("");
                            pagesQtdField.setText("0");
                            box.setSelectedIndex(-1);

                            JOptionPane.showMessageDialog(null, "Elemento adicionado com sucesso!",
                                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!",
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                }

                case 2 -> {

                    if (controller.getCSVFilePath() == null) {
                        JOptionPane.showOptionDialog(
                                null,
                                new String("Arquivo CSV não carregado. Selecione um arquivo CSV primeiro".getBytes(), StandardCharsets.UTF_8),
                                "Erro", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null
                                );

                    } else if (controller.getCSVFilePath() != null && controller.getLeituras().isEmpty()) {

                        JOptionPane.showOptionDialog(
                            null, new String("Arquivo não contêm nenhuma leitura.".getBytes(), StandardCharsets.UTF_8),
                            "Aviso", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null
                        );

                    } else {

                        setSize(500, 515);

                        List<String[]> csvData = CSVutil.readCSV(controller.getCSVFilePath());

                        String[] columnHeaders = {new String("Título".getBytes(), StandardCharsets.UTF_8), "Nome do Autor",
                                new String("Quantidade de Páginas".getBytes(), StandardCharsets.UTF_8), "Tipo"};

                        Object[][] tableData = new Object[csvData.size()][4];
                        for (int i = 0; i < csvData.size(); i++) {
                            List<String> row = Arrays.asList(csvData.get(i));
                            for (int j = 0; j < row.size(); j++) {
                                tableData[i][j] = row.get(j);
                            }
                        }

                        TableModel tableModel = new DefaultTableModel(tableData, columnHeaders) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };

                        JTable table = new JTable(tableModel);
                        JScrollPane scrollPane = new JScrollPane(table);

                        showPannel.add(scrollPane);

                    }

                }

                default -> setSize(337, 337);
            }
        });

        setVisible(true);
    }

    private void notifyObservers(String message) {
        for (FileStatusObserver observer : statusobservers) {
            observer.updateFileStautsLabel(message, Color.MAGENTA);
        }
    }

    private void notifyObservers(File file) {
        for (FileObserver observer : fileObservers) {
            observer.setFile(file);
        }
    }
}