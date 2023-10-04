package com.ufc.ds_persist.view.frames;

import com.ufc.ds_persist.controller.BookController;
import com.ufc.ds_persist.enumeration.BookStatus;
import com.ufc.ds_persist.enumeration.BookType;
import com.ufc.ds_persist.model.Leitura;
import com.ufc.ds_persist.util.CSVutil;
import com.ufc.ds_persist.view.observers.FileObserver;
import com.ufc.ds_persist.view.observers.FileStatusObserver;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CSVFrame extends JFrame {

    private final ArrayList<FileObserver> fileObservers = new ArrayList<>();
    private final ArrayList<FileStatusObserver> statusobservers = new ArrayList<>();
    private final BookController controller = BookController.getInstance();

    private JTabbedPane tabbedPane;
    private JPanel selectCsvPanel;
    private JPanel insertPannel;
    private JPanel showPannel;

    private JTextField titleField;
    private JTextField authorNameField;
    private JTextField pagesQtdField;
    private JComboBox<BookType> typeBox;
    private JComboBox<BookStatus> statusBox;
    private JFileChooser fileChooser;

    public CSVFrame() {

        super(new String("Menu Principal → CSV".getBytes(), StandardCharsets.UTF_8));
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("book-marked.png")));
        setIconImage(icon.getImage());
        initComponents();
        setTabbedPane();
        setResizable(true);
        setVisible(true);

    }

    private void initComponents() {

        tabbedPane = new JTabbedPane();
        selectCsvPanel = new JPanel();
        insertPannel = new JPanel();
        showPannel = new JPanel();

        titleField = new JTextField();
        authorNameField = new JTextField();
        pagesQtdField = new JTextField("0");
        typeBox = new JComboBox<>(BookType.values());
        statusBox = new JComboBox<>(BookStatus.values());

        fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    }

    private void setTabbedPane() {

        tabbedPane.addTab("Selecionar CSV", null, selectCsvPanel);
        tabbedPane.addTab("Inserir elemento", null, insertPannel);
        tabbedPane.addTab("Lista de elementos", null, showPannel);
        tabbedPane.setSelectedIndex(-1);
        add(tabbedPane);

        tabbedPane.addChangeListener(this::handleTabChange);

        setSelectCsvPanel();
        setInsertPannel();
        setShowPannel();

    }

    private void setSelectCsvPanel() {

        selectCsvPanel.setLayout(new GridBagLayout());

        setSize(500, 400);

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Selecione um arquivo .csv");
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Apenas arquivos .csv", "csv"));
        selectCsvPanel.add(fileChooser);

        fileChooser.addActionListener(this::handleFileChooserAction);

    }

    private void setInsertPannel() {

        insertPannel.setLayout(new GridBagLayout());

        setSize(350, 337);

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
        insertPannel.add(typeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        insertPannel.add(new JLabel("Status"), gbc);
        gbc.gridx++;
        insertPannel.add(statusBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        insertPannel.add(addButton, gbc);

        addButton.addActionListener(this::handleAddButtonAction);

    }

    private void setShowPannel() {

        showPannel.setLayout(new GridBagLayout());
        showPannel.setSize(500, 550);

    }

    private void setShowPannelTable() {

        List<String[]> csvData = CSVutil.readCSV(controller.getCSVFilePath());

        String[] columnHeaders = {new String("Título".getBytes(), StandardCharsets.UTF_8), "Nome do Autor",
                new String("Quantidade de Páginas".getBytes(), StandardCharsets.UTF_8), "Tipo", "Status"};

        Object[][] tableData = new Object[csvData.size()][5];
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

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.anchor = GridBagConstraints.CENTER;
        JLabel countLabel = new JLabel("Quantidade de Elementos: " + controller.getLeituras().size());
        showPannel.add(countLabel, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel fileNameLabel = new JLabel("Arquivo: " + controller.getCSVFile().getName());
        showPannel.add(fileNameLabel, gbc);

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        showPannel.add(scrollPane, gbc);

    }

    private void handleTabChange(ChangeEvent e) {

        switch (tabbedPane.getSelectedIndex()) {
            case 0 -> setSize(500, 400);
            case 2 -> {
                if (controller.getCSVFilePath() == null) {
                    JOptionPane.showOptionDialog(
                            this,
                            new String("Arquivo CSV não carregado. Selecione um arquivo CSV primeiro".getBytes(), StandardCharsets.UTF_8),
                            "Erro", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null
                    );
                } else if (controller.getCSVFilePath() != null && controller.getLeituras().isEmpty()) {
                    JOptionPane.showOptionDialog(
                            this, new String("Arquivo não contém nenhuma leitura.".getBytes(), StandardCharsets.UTF_8),
                            "Aviso", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null
                    );
                } else {

                    setSize(500, 550);
                    showPannel.removeAll();
                    setShowPannelTable();

                }
            }
            default -> setSize(350, 337);
        }

    }

    private void handleFileChooserAction(ActionEvent e) {

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
            dispose();
        }

    }

    private void handleAddButtonAction(ActionEvent e) {

        String title = titleField.getText();
        String authorName = authorNameField.getText();

        int pagesQtd;
        try {
            pagesQtd = Integer.parseInt(pagesQtdField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    new String("Número de páginas inválido.".getBytes(), StandardCharsets.UTF_8),
                    "Erro", JOptionPane.ERROR_MESSAGE);

            titleField.setText("");
            authorNameField.setText("");
            pagesQtdField.setText("0");
            typeBox.setSelectedIndex(-1);
            statusBox.setSelectedItem(-1);
            return;
        }

        BookType type = (BookType) typeBox.getSelectedItem();
        BookStatus status = (BookStatus) statusBox.getSelectedItem();

        if (controller.getCSVFile() == null) {

            JOptionPane.showMessageDialog(null,
                    new String("Arquivo CSV ainda não foi carregado.".getBytes(), StandardCharsets.UTF_8),
                    "Erro", JOptionPane.ERROR_MESSAGE);

        } else if (!title.isEmpty() && !authorName.isEmpty() && typeBox.getSelectedItem() != null && statusBox.getSelectedItem() != null) {

            Leitura leitura = new Leitura(title, authorName, pagesQtd, type, status);
            controller.addLeitura(leitura);
            notifyObservers(controller.getCSVFile());
            titleField.setText("");
            authorNameField.setText("");
            pagesQtdField.setText("0");
            typeBox.setSelectedIndex(-1);
            statusBox.setSelectedItem(-1);

            JOptionPane.showMessageDialog(null, "Elemento adicionado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void addObserver(FileObserver observer) {
        fileObservers.add(observer);
    }

    public void addObserver(FileStatusObserver observer) {
        statusobservers.add(observer);
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