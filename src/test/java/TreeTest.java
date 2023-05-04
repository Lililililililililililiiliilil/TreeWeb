import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Node;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import java.util.Arrays;


import static org.example.Main.getNodeFromJSON;
import static org.example.Main.writeNodeToJSON;
import static org.junit.Assert.*;

public class TreeTest {


    // создание дерева
    @Test
    public void createTree() {
        Node node = new Node(1, "1");
        assertEquals("1", node.getName());
    }


    // добавление узла дерева
    @Test
    public void addNode() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        first.addChild(second);
        assertEquals(1, first.getChildren().size());
        assertEquals("B", first.getChildren().get(0).getName());
    }


    // поиск по имени - случай если такой узел есть
    @Test
    public void findByNameTrue() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        first.addChild(third);
        assertEquals(first.searchByName("C").getName(), third.getName());
    }


    // поиск по имени - случай если такого узла нет
    @Test
    public void findByNameFalse() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        first.addChild(third);
        assertNull(first.searchByName("D"));
    }


    // удаление по ID
    @Test
    public void deleteById() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        second.addChild(third);
        first.deleteById(second.getId());
        assertEquals(0, first.getChildren().size());
    }

    // удаление по названию узла

    @Test
    public void deleteByName() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        first.addChild(third);
        first.deleteByName(second.getName());
        assertEquals(1, first.getChildren().size());
    }

    //удаление всех потомков - случай с потомками - листьями
    @Test
    public void killChildrenLeaves() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        first.addChild(third);
        first.deleteChildren();
        assertEquals(0, first.getChildren().size());
    }


    //удаление всех потомков - случай с потомками у удаляемого узла
    @Test
    public void killChildrenWithSubNodes() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        second.addChild(third);
        first.deleteChildren();
        assertEquals(0, second.getChildren().size());
    }


    // изменение названия узла
    @Test
    public void changeName() {
        Node first = new Node(1, "A");
        first.setName("B");
        assertEquals("B", first.getName());
    }

    // работа дерева с json-файлом - чтение и запись

    @Test
    public void readWriteTreeJSON() throws IOException {
        Node first = new Node(1, "A", Arrays.asList(new Node(2, "B"), new Node(3, "C")));
        ObjectMapper mapper = new ObjectMapper();
        writeNodeToJSON(first, "Tree.json", mapper);
        Node tree = getNodeFromJSON("Tree.json", mapper);
        assertEquals(tree.getName(), first.getName());
    }


    // печать дерева в строку

    @Test
    public void TreeToString() {
        Node first = new Node(1, "A", Arrays.asList(new Node(2, "B"), new Node(3, "C")));
        String expected = "A\n" +
                "├── B\n" +
                "└── C\n";
        assertEquals(expected, first.toString());
    }

    // создание html по дереву

    @Test
    public void TreeToHTML() throws IOException {
        Node first = new Node(1, "A", Arrays.asList(new Node(2, "B"), new Node(3, "C")));
        first.createHTML("test");

        StringBuilder test = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader("test.html"));

        String line = reader.readLine();
        while (line != null) {
            test.append(line);
            line = reader.readLine();

        }

        String expected = "<h1>My Tree</h1><ul id=\"myUL\"><li>   <span class=\"caret\"> <div style=\"display:flex; margin: auto; \"> A<form style=\"margin: 0 5\" method=\"post\" action=\"add/1\"> <input type=\"submit\" value=\"Добавить\"> </form> <form  method=\"post\" action=\"delete/1\"> <input type=\"submit\" value=\"Удалить\"> </form> <a style=\"margin: 0 5\" href=\"edit/1\"> Редактировать </a>  </div> </span><ul class=\"nested\"> <li> <div style=\"display:flex; margin: auto;\"> B<form style=\"margin: 0 5\" method=\"post\" action=\"add/2\"> <input type=\"submit\" value=\"Добавить\">  </form><form  method=\"post\" action=\"delete/2\"> <input type=\"submit\" value=\"Удалить\"> </form> <a style=\"margin: 0 5\" href=\"edit/2\"> Редактировать </a>  </div>  </li>  <li> <div style=\"display:flex; margin: auto;\"> C<form style=\"margin: 0 5\" method=\"post\" action=\"add/3\"> <input type=\"submit\" value=\"Добавить\">  </form><form  method=\"post\" action=\"delete/3\"> <input type=\"submit\" value=\"Удалить\"> </form> <a style=\"margin: 0 5\" href=\"edit/3\"> Редактировать </a>  </div>  </li> </ul></li></ul>";


        assertEquals(expected, test.toString());

    }

}
