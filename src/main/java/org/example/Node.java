package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {


    private String name;
    private int id;
    private List<Node> children = new ArrayList<>();

    public Node(int id, String name) {
        this.id = id;
        this.name = name;

    }


    // конструктор по умолчанию для jackson
    public Node() {
        this.name = "";
        this.id = 0;
    }

    public Node(int id, String name, List<Node> children) {
        this.id = id;
        this.name = name;
        this.children = children;
    }


    //сеттеры и геттеры

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String other) {
        this.name = other;
    }

    public String getName() {
        return this.name;
    }

    public void setChildren(List<Node> other) {
        this.children = other;
    }

    public List<Node> getChildren() {
        return this.children;
    }



    // добавить дочерний корень
    public void addChild(Node other) {
        this.children.add(other);

    }

    // поиск в дереве по имени
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


    // удаление всех дочерних узлов
    public void deleteChildren() {
        for (Node node : children) {
            node.deleteChildren();
        }
        this.children = new ArrayList<>();
    }


    // удаление узла по идентификатору
    public void deleteById(int otherId) {
        for (Node node : children) {
            if (node.getId() == (otherId)) {
                children.remove(node);
                node.deleteChildren();

            } else {
                node.deleteById(otherId);
            }
        }
    }

    // удаление узла по названию
    public void deleteByName(String otherName) {
        for (Node node : children) {
            if (Objects.equals(node.getName(), otherName)) {
                children.remove(node);
                node.deleteChildren();

            } else {
                node.deleteByName(otherName);
            }
        }
    }


    //для "красивого" вывода узла
    public String toString() {
        return this.getId() + " " + this.getName();
    }
}
