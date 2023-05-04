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
    public Node searchByName(String other) {
        if (Objects.equals(this.name, other)) {
            return this;
        }
        for (Node child : this.children) {
            if (Objects.equals(child.getName(), other)) {
                return child;
            } else {
                Node recursiveChild = child.searchByName(other);
                if (recursiveChild != null) {
                    return recursiveChild;
                }
            }
        }
        return null;

    }


    // поиск узла по номеру
    public Node searchById(int id) {
        if (this.id == id) {
            return this;
        }
        for (Node child : this.children) {
            if (child.getId() == (id)) {
                return child;
            } else {
                Node recursiveChild = child.searchById(id);
                if (recursiveChild != null) {
                    return recursiveChild;
                }
            }
        }
        return null;

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
                return;

            } else {
                node.deleteById(otherId);
            }
        }
    }

    // удаление узла по названию
    public void deleteByName(String otherName) {
        for (Node node : this.children) {
            if (Objects.equals(node.getName(), otherName)) {
                children.remove(node);
                node.deleteChildren();

            } else {
                node.deleteByName(otherName);
            }
        }
    }

    //печать дерева в строку
    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    //Рекурсивное создание строки для печати дерева
    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(name);
        buffer.append('\n');
        for (Iterator<Node> it = children.iterator(); ((Iterator<?>) it).hasNext(); ) {
            Node next = it.next();
            if (it.hasNext()) {
                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }

    // Создание HTML-кода для каждого узла
    public String createHTMLStringForEachNode(StringBuilder html) {
        if (!children.isEmpty()) {
            html.append(
                    "<li>   <span class=\"caret\"> <div style=\"display:flex; margin: auto; \"> " + this.getName() +

                            "<form style=\"margin: 0 5\" method=\"post\" action=\"add/" + this.getId() + "\">"
                            + " <input type=\"submit\" value=\"Добавить\"> </form> " +
                            "<form  method=\"post\" action=\"delete/" + this.getId() + "\">"
                            + " <input type=\"submit\" value=\"Удалить\"> </form> "
                            + "<a style=\"margin: 0 5\" href=\"edit/" + this.getId() + "\"> Редактировать </a>  </div> </span>"
            );
            html.append("<ul class=\"nested\">");
            for (Node node : children) {
                node.createHTMLStringForEachNode(html);
            }
            html.append("</ul>");
            html.append("</li>");
        } else {
            html.append(" <li> <div style=\"display:flex; margin: auto;\"> " + this.getName() +
                    "<form style=\"margin: 0 5\" method=\"post\" action=\"add/" + this.getId() + "\">"
                    + " <input type=\"submit\" value=\"Добавить\">  </form>" +
                    "<form  method=\"post\" action=\"delete/" + this.getId() + "\">"
                    + " <input type=\"submit\" value=\"Удалить\"> </form> "
                    + "<a style=\"margin: 0 5\" href=\"edit/" + this.getId() + "\"> Редактировать </a>  </div>  </li> ");
        }
        return html.toString();
    }

    /*
     * Создание html файла с деревом
     * @param filename - имя файла
     */
    public void createHTML(String fileName) throws IOException {

        PrintWriter writer = new PrintWriter(fileName + ".html", "UTF-8");
        StringBuilder html = new StringBuilder();
        writer.println("<h1>My Tree</h1>");
        writer.println("<ul id=\"myUL\">");
        writer.println(this.createHTMLStringForEachNode(html));

        writer.println("</ul>");
        writer.close();
    }



}