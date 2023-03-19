package org.example;

import java.util.ArrayList;
import java.util.List;

public class Node {


    private String name;
    private int id;
    private List<Node> children = new ArrayList<>();

    public Node(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public Node() {
        this.name = "";
        this.id = 0;
    }

    public Node(int id, String name, List<Node> children) {
        this.id = id;
        this.name = name;
        this.children = children;
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

    public List<Node> getChildren() {
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
                children.remove(node);
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
