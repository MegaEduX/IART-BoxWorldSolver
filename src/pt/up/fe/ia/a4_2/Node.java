package pt.up.fe.ia.a4_2;

import java.util.List;

public class Node<T> {
    private List<Node> childNodes;

    private T value;

    public Node(T val) {
        value = val;
    }

    public void addChild(Node child) {
        childNodes.add(child);
    }

    public void removeChild(Node child) {
        childNodes.remove(child);
    }

    public List<Node> getNodes() {
        return childNodes;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T val) {
        value = val;
    }
}
