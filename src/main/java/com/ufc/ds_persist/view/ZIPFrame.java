package com.ufc.ds_persist.view;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

public class ZIPFrame extends JFrame {

    public ZIPFrame() {

        super(new String("Menu Principal → ZIP".getBytes(), StandardCharsets.UTF_8));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        JLabel label = new JLabel(new String("Essa é a tela das opções de ZIP".getBytes(), StandardCharsets.UTF_8));
        add(label, gbc);

        setVisible(true);

    }
}
