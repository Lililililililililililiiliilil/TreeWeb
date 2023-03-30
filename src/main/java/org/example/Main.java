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


        Node second = new Node(2, "A",
                Arrays.asList(new Node(3, "B"), new Node(4, "C")));
        Node first = new Node(1, "Z",
                Arrays.asList(
                        new Node(5, "D", Arrays.asList(second, new Node(7, "G"))), new Node(6, "E")));


        Node test = new Node(1, "A", Arrays.asList(new Node(2, "B"), new Node(3, "C")));


        System.out.println(first);

        try {
            ObjectMapper mapper = new ObjectMapper();

            writeNodeToJSON(first, "Tree.json", mapper);

            Node tree = getNodeFromJSON("Tree.json", mapper);

            System.out.println("id: " + tree.getId() + ", name: " + tree.getName());

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        first.createHTMLFromTree();


    }
}
