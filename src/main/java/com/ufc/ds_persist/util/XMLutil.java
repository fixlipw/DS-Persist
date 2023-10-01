package com.ufc.ds_persist.util;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ufc.ds_persist.controller.BookController;
import com.ufc.ds_persist.model.Leituras;

import java.io.File;
import java.io.IOException;

public class XMLutil {

    public static void serialize(Leituras leituras) {

        BookController controller = BookController.getInstance();
        int last = controller.getCSVFilePath().lastIndexOf(".");
        String xmlName = controller.getCSVFilePath().substring(0, last) + ".xml";

        File xmlFile = new File(xmlName);

        try {

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            xmlMapper.writeValue(xmlFile, leituras);

            System.out.println("Lista serializada em XML com sucesso!");

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

}
