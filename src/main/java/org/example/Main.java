package org.example;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    //запись дерева в json-файл
    public static void writeNodeToJSON(Node node, String fileName, ObjectMapper mapper) throws IOException {
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        writer.writeValue(Paths.get(fileName).toFile(), node);
    }

    //получение дерева из json-файла
    public static Node getNodeFromJSON(String fileName, ObjectMapper mapper) throws IOException {
        return mapper.readValue(Paths.get(fileName).toFile(), Node.class);
    }


    public static void main(String[] args) throws IOException {

    }

}
