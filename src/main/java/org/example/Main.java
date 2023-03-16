package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
//        Tree myTree = new Tree(new Node(1, "A"));
//        myTree.addNode(1, new Node(2, "B"));
//        myTree.addNode(1, new Node(3, "C"));
//        System.out.println(myTree.find(3));
//        System.out.println(myTree.find("A"));

        Node first = new Node(1, "A");
        Node second = new Node(2, "B");
        Node third = new Node(3, "C");
        Node fourth = new Node(4, "D");
        Node fifth = new Node(5, "z");
        first.addChild(second);
        second.addChild(third);
        third.addChild(fourth);
        first.addChild(fifth);
        System.out.println(third.getChildren());
        String test = first.toJSON();
        ArrayList<Node> list = first.fromJSON(test);
        System.out.println(first.toJSON());
        System.out.println(list.get(0));
    }
}
