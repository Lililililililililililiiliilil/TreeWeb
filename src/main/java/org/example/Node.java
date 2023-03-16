package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Node {


    private String name;
    private int id;
    private ArrayList<Node> children;

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
        children = new ArrayList<>();

    }

    public String toJSON() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(this);
        return json;
    }
    public ArrayList<Node> fromJSON(String test) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Node> nodes =  objectMapper.readValue(test.getBytes(StandardCharsets.UTF_8), new TypeReference<Node>(){});
        return nodes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String other) {
        this.name = other;
    }

    public void setChildren(ArrayList<Node> other) {
        this.children = other;
    }

    public void addChild(Node other) {
        this.children.add(other);

    }

    public ArrayList<Node> getChildren() {
        return this.children;
    }

    public boolean searchByName(String other) {
        for (Node node : children) {
            if (node.getName().equals(other)) {
                return true;
            } else {
                node.searchByName(other);
            }
        }
        return false;
    }

    public void killChildren() {
        for (Node node : children) {
            node.killChildren();
        }
        this.children = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public void deleteById(int otherId) {
        for (Node node : children) {
            if (node.getId() == (otherId)) {
                children.remove(children.indexOf(node));
                node.killChildren();

            } else {
                node.deleteById(otherId);
            }
        }
    }

    public String toString() {
        return this.getId() + " " + this.getName();
    }
}
