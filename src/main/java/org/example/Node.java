package org.example;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
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


    // вывод дерева в строку
    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }


    // создание красивого вывода с разделением и вложением
    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(name);
        buffer.append('\n');
        for (Iterator<Node> it = children.iterator(); it.hasNext(); ) {
            Node next = it.next();
            if (it.hasNext()) {
                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }

    public String createHTMLString(StringBuilder html) {
        if (!children.isEmpty()) {
            html.append("<li><span class=\"caret\">").append(this.getName()).append("</span>\n");
            html.append("<ul class=\"nested\">\n");
            for (Node node : children) {
                node.createHTMLString(html);
            }
            html.append("</ul>");
            html.append("</li>");
        } else {
            html.append("<li>").append(this.getName()).append("</li>\n");
        }
        return html.toString();
    }

    public void createHTMLFromTree() throws IOException {

        PrintWriter writer = new PrintWriter("Tree.html", "UTF-8");
        StringBuilder html = new StringBuilder();
        writer.println("<h1>Tree</h1>");
        writer.println("<ul id=\"myUL\">");
        writer.println(this.createHTMLString(html));

        writer.println("</ul>");
        writer.close();
    }
}
