package com.ufc.ds_persist;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.ufc.ds_persist.view.frames.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            FlatIntelliJLaf.setup();

            new MainFrame();
        });
    }
}