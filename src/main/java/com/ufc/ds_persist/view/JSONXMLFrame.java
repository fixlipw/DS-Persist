package com.ufc.ds_persist.view;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

public class JSONXMLFrame extends JFrame {

    public JSONXMLFrame() {

        super(new String(("Menu Principal → JSON/XML").getBytes(), StandardCharsets.UTF_8));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        JLabel label = new JLabel(new String("Essa é a tela das opções de Json e Xml".getBytes(), StandardCharsets.UTF_8));
        add(label, gbc);

        setVisible(true);

    }

}
