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


    /* Добавить дочерний узел
     * @param other - узел, который хотим добавить
     */
    public void addChild(Node other) {
        this.children.add(other);

    }

    /* Поиск в дереве по имени
     * @param otherName - имя узла который хотим найти в дереве
     * @return истина если узел с таким деревом есть в дереве, ложь иначе
     */
    public boolean searchByName(String otherName) {
        for (Node node : children) {
            if (node.getName().equals(otherName)) {
                return true;
            } else {
                node.searchByName(otherName);
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


    /* Удаление узла по идентификатору
     * @param otherId - индентификатор узла, который хотим удалить
     */
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

    /* Удаление узла по имени
     * @param otherName - имя узла, который хотим удалить
     */
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


    /* Вывод дерева в строку
     * @return строка со структурированным выводом дерева
     */
    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }


    /* Создание структурированного вывода дерева
     * @param buffer - StringBuilder, где накапливается вывод
     * @param prefix - префикс перед узлом
     * @param childrenPrefix - префикс перед потомком
     */
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

    /*
     * Создание html-кода для каждого узла
     * @param html - строка в которой накапливается html код, изначально пустая
     * @return итоговая строка с html кодом всех узлов
     */
    public String createHTMLStringForEachNode(StringBuilder html) {
        if (!children.isEmpty()) {
            html.append("<li><span class=\"caret\">").append(this.getName()).append("</span>");
            html.append("<ul class=\"nested\">");
            for (Node node : children) {
                node.createHTMLStringForEachNode(html);
            }
            html.append("</ul>");
            html.append("</li>");
        } else {
            html.append("<li>").append(this.getName()).append("</li>");
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
