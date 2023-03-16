import org.example.Node;
import org.junit.Test;

import static org.junit.Assert.*;

public class TreeTest {

    @Test
    public void createTree() {
        Node node = new Node(1, "1");
        assertEquals("1", node.getName());
    }

    @Test
    public void addNode() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        first.addChild(second);
        assertEquals(first.getChildren().size(), 1);
        assertEquals(first.getChildren().get(0).getName(), "B");
    }

    @Test
    public void findByNameTrue() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        first.addChild(third);
        assertTrue(first.searchByName("C"));
    }

    @Test
    public void findByNameFalse() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        first.addChild(third);
        assertFalse(first.searchByName("D"));
    }

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

    @Test
    public void killChildren1() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        first.addChild(third);
        first.killChildren();
        assertEquals(first.getChildren().size(), 0);
    }

    @Test
    public void killChildren2() {
        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        first.addChild(second);
        second.addChild(third);
        first.killChildren();
        assertEquals(second.getChildren().size(), 0);
    }

    @Test
    public void changeName() {
        Node first = new Node(1, "A");
        first.setName("B");
        assertEquals(first.getName(), "B");
    }
}
