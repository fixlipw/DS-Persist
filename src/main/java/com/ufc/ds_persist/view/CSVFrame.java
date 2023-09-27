package com.ufc.ds_persist.view;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

public class CSVFrame extends JFrame {

    public CSVFrame() {
        super(new String("Menu Principal → CSV".getBytes(), StandardCharsets.UTF_8));
        setLayout(new FlowLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel selectCsvPanel = new JPanel();
        JPanel insertPannel = new JPanel();
        JPanel showPannel = new JPanel();

        tabbedPane.addTab("Selecionar CSV", null, selectCsvPanel);
        tabbedPane.addTab("Inserir elemento", null, insertPannel);
        tabbedPane.addTab("Lista de elementos", null, showPannel);
        add(tabbedPane);

        setVisible(true);
    }

}