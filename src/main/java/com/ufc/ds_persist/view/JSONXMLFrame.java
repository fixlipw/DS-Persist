package com.ufc.ds_persist.view;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

public class JSONXMLFrame extends JFrame {

    public JSONXMLFrame() {

        super(new String(("Menu Principal → JSON/XML").getBytes(), StandardCharsets.UTF_8));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weighty = 1;
        gbc.weightx = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;

        JLabel label = new JLabel(new String("Selecione como deseja serializar:".getBytes(), StandardCharsets.UTF_8));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        JRadioButton xmlRadioButton = new JRadioButton("Gerar XML");
        add(xmlRadioButton, gbc);

        gbc.gridx++;
        JRadioButton jsonRadioButton = new JRadioButton("Gerar JSON");
        add(jsonRadioButton, gbc);

        gbc.gridx++;
        JRadioButton bothRadioButton = new JRadioButton("Gerar ambos");
        add(bothRadioButton, gbc);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(xmlRadioButton);
        radioGroup.add(jsonRadioButton);
        radioGroup.add(bothRadioButton);

        JButton genarateButton = new JButton("Gerar");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(genarateButton, gbc);

        setVisible(true);

    }

}
