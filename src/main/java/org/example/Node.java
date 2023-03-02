package org.example;

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

    public String getName() {
        return this.name;
    }

    public void setName(String other) {
        this.name = other;
    }

    public void setChildren(ArrayList<Node> other) {
        this.children = other;
    }

    public void addNode(Node other) {
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
        this.children = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public void deleteById(int otherId) {
        for (Node node : children) {
            if (node.getId() == (otherId)) {
                node.getChildren();
            }
        }
    }
}


