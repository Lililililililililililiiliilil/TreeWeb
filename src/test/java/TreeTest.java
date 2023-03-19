import org.example.Node;
import org.junit.Test;

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
        assertEquals(first.getChildren().size(), 1);
        assertEquals(first.getChildren().get(0).getName(), "B");
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
        assertEquals(first.getChildren().size(), 1);
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
        assertEquals(first.getChildren().size(), 1);
    }

    //удаление всех потомков - случай с потомками - листьями
    @Test
    public void killChildrenLeaves() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        first.addChild(third);
        first.killChildren();
        assertEquals(first.getChildren().size(), 0);
    }


    //удаление всех потомков - случай с потомками у удаляемого узла
    @Test
    public void killChildrenWithSubNodes() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        second.addChild(third);
        first.killChildren();
        assertEquals(second.getChildren().size(), 0);
    }


    // изменение названия узла
    @Test
    public void changeName() {
        Node first = new Node(1, "A");
        first.setName("B");
        assertEquals(first.getName(), "B");
    }


}
