package com.ufc.ds_persist.controller;

import com.ufc.ds_persist.enumeration.BookStatus;
import com.ufc.ds_persist.enumeration.BookType;
import com.ufc.ds_persist.model.Leitura;
import com.ufc.ds_persist.util.CSVutil;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BookController {

    private final List<Leitura> leituras = new ArrayList<>();
    private String csvFilePath;

    private BookController() {

    }

    public static BookController getInstance() {
        return InstanceHolder.instance;
    }

    public String getCSVFilePath() {
        return csvFilePath;
    }

    public void setCSVFilePath(String filePath) {
        this.csvFilePath = filePath;
    }

    public File getCSVFile() {
        if (csvFilePath != null) {
            return Paths.get(csvFilePath).toFile();
        }
        return null;
    }

    public void readLeiturasFromCSV() {
        File file = Paths.get(csvFilePath).toFile();

        if (file.exists()) {
            List<String[]> csvData = CSVutil.readCSV(file);
            leituras.clear();
            for (String[] row : csvData) {
                if (row.length == 5) {
                    String title = row[0];
                    String authorName = row[1];
                    int pagesQtd = Integer.parseInt(row[2]);
                    String type = row[3];
                    String status = row[4];
                    Leitura leitura = new Leitura(title, authorName, pagesQtd, BookType.valueOf(type), BookStatus.valueOf(status));
                    leituras.add(leitura);
                }
            }
        }
    }

    public void addLeitura(Leitura leitura) {
        String row = CSVutil.leituraToCSVRow(leitura);
        CSVutil.addCSVRow(csvFilePath, row.split(","));
        leituras.add(leitura);
    }

    public List<Leitura> getLeituras() {
        return leituras;
    }

    public void saveLeiturasToCSV() {
        if (csvFilePath != null) {
            List<String[]> csvData = new ArrayList<>();
            for (Leitura leitura : leituras) {
                String[] row = new String[]{
                        leitura.getTitle(),
                        leitura.getAuthorName(),
                        String.valueOf(leitura.getPagesQtd()),
                        leitura.getType().name(),
                        leitura.getStatus().name()
                };
                csvData.add(row);
            }
            CSVutil.writeCSV(csvFilePath, csvData);
        }
    }

    public void clearLeituras() {
        leituras.clear();
    }

    private static final class InstanceHolder {
        private static final BookController instance = new BookController();
    }
}
