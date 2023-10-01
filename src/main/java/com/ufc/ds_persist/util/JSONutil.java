package com.ufc.ds_persist.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ufc.ds_persist.controller.BookController;
import com.ufc.ds_persist.model.Leituras;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JSONutil {

    public static void seriallize(Leituras leituras) {

        BookController controller = BookController.getInstance();
        int last = controller.getCSVFilePath().lastIndexOf(".");
        String jsonName = controller.getCSVFilePath().substring(0, last) + ".json";

        try (FileOutputStream outputStream = new FileOutputStream(jsonName)) {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Habilita a formatação do JSON

            String leiturasJsonString = objectMapper.writeValueAsString(leituras);

            byte[] leiturasBytes = leiturasJsonString.getBytes(StandardCharsets.UTF_8);
            outputStream.write(leiturasBytes);

            System.out.println("Lista serializada em JSON com sucesso!");

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

}
