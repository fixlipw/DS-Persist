package com.ufc.ds_persist.util;


import com.ufc.ds_persist.model.Leitura;

import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVutil {

    private static final String SEPARATOR = ",";
    private static final String ESCAPE = "\"";

    public static List<String[]> readCSV(String filepath) {
        return readCSV(Paths.get(filepath).toFile());
    }

    public static List<String[]> readCSV(File file) {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = reader.readLine()) != null) {
                List<String> values = new ArrayList<>();
                StringBuilder builder = new StringBuilder();
                boolean inQuotes = false;

                for (char c : line.toCharArray()) {
                    if (c == '"') {
                        inQuotes = !inQuotes;
                    } else if (c == ',' && !inQuotes) {
                        values.add(builder.toString());
                        builder.setLength(0);
                    } else {
                        builder.append(c);
                    }
                }

                values.add(builder.toString());
                data.add(values.toArray(new String[0]));
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return data;
    }


    public static void writeCSV(String filepath, List<String[]> data) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {

            for (String[] row : data) {
                String line = String.join(",", row);
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public static String leituraToCSVRow(Leitura leitura) {
        if (leitura != null) {
            String title = escapeCSVValue(leitura.getTitle());
            String authorName = escapeCSVValue(leitura.getAuthorName());
            String type = leitura.getType().toString();
            String status = leitura.getStatus().toString();

            return ESCAPE + title + ESCAPE + SEPARATOR +
                    ESCAPE + authorName + ESCAPE + SEPARATOR +
                    leitura.getPagesQtd() + SEPARATOR +
                    ESCAPE + type + ESCAPE + SEPARATOR +
                    ESCAPE + status + ESCAPE;
        }
        return null;
    }

    public static void addCSVRow(String filepath, String[] rowContent) {
        addCSVRow(Paths.get(filepath).toFile(), rowContent);
    }

    public static void addCSVRow(File file, String[] rowContent) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (file.length() > 0) {
                writer.newLine();
            }
            String line = String.join(",", rowContent);
            writer.write(line);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    private static String escapeCSVValue(String value) {
        if (value != null) {
            return value.replace(ESCAPE, ESCAPE + ESCAPE);
        }
        return "";
    }
}
