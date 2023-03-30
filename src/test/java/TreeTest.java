import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Node;
import org.junit.Test;

import java.io.IOException;
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
        assertTrue(first.searchByName("C"));
    }


    // поиск по имени - случай если такого узла нет
    @Test
    public void findByNameFalse() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        first.addChild(third);
        assertFalse(first.searchByName("D"));
    }


    // удаление по ID
    @Test
    public void deleteById() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        first.addChild(third);
        first.deleteById(second.getId());
        assertEquals(1, first.getChildren().size());
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

}
