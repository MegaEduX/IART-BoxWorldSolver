package pt.up.fe.ia.a4_2;

import java.util.List;

public class Node<Type> {
    private List<Node> childNodes;

    private Type value;

    public Node(Type val) {
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

    public Type getValue() {
        return value;
    }

    public void setValue(Type val) {
        value = val;
    }
}
