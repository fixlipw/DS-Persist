package com.ufc.ds_persist.view.frames;

import com.ufc.ds_persist.controller.BookController;
import com.ufc.ds_persist.model.Leituras;
import com.ufc.ds_persist.util.JSONutil;
import com.ufc.ds_persist.util.XMLutil;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JSONXMLFrame extends JFrame {
    private final JRadioButton xmlRadioButton;
    private final JRadioButton jsonRadioButton;
    private final JRadioButton bothRadioButton;
    private final BookController controller = BookController.getInstance();
    private final Leituras leituras;

    public JSONXMLFrame() {

        super(new String(("Menu Principal → JSON/XML").getBytes(), StandardCharsets.UTF_8));
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("book-marked.png")));
        setIconImage(icon.getImage());
        setResizable(false);
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
        xmlRadioButton = new JRadioButton("Gerar XML");
        add(xmlRadioButton, gbc);

        gbc.gridx++;
        jsonRadioButton = new JRadioButton("Gerar JSON");
        add(jsonRadioButton, gbc);

        gbc.gridx++;
        bothRadioButton = new JRadioButton("Gerar ambos");
        add(bothRadioButton, gbc);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(xmlRadioButton);
        radioGroup.add(jsonRadioButton);
        radioGroup.add(bothRadioButton);

        JButton generateButton = new JButton("Gerar");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(generateButton, gbc);

        leituras = new Leituras(controller.getLeituras());

        generateButton.addActionListener(e -> {
            if (xmlRadioButton.isSelected()) {

                // Ação para gerar XML
                XMLutil.serialize(leituras);

                JOptionPane.showMessageDialog(null, new String("Serialização XML concluída com sucesso!".getBytes(), StandardCharsets.UTF_8),
                        new String("Concluído".getBytes(), StandardCharsets.UTF_8), JOptionPane.INFORMATION_MESSAGE);

                dispose();

            } else if (jsonRadioButton.isSelected()) {

                JSONutil.seriallize(leituras);

                JOptionPane.showMessageDialog(null, new String("Serialização JSON concluída com sucesso!".getBytes(), StandardCharsets.UTF_8),
                        new String("Concluído".getBytes(), StandardCharsets.UTF_8), JOptionPane.INFORMATION_MESSAGE);

                dispose();

            } else if (bothRadioButton.isSelected()) {

                // Ação para gerar ambos (XML e JSON)
                XMLutil.serialize(leituras);
                JSONutil.seriallize(leituras);

                JOptionPane.showMessageDialog(null, new String("Serialização de ambos concluída com sucesso!".getBytes(), StandardCharsets.UTF_8),
                        new String("Concluído".getBytes(), StandardCharsets.UTF_8), JOptionPane.INFORMATION_MESSAGE);

                dispose();

            } else {

                JOptionPane.showMessageDialog(null, new String("Selecione um método de serialização!".getBytes(), StandardCharsets.UTF_8),
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        });

        setVisible(true);
    }
}
