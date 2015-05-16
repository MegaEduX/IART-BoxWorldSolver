package pt.up.fe.ia.a4_2;

import java.util.ArrayList;

public class Node<T> {
    private ArrayList<Node> childNodes;

    private T value;

    private boolean used = false;

    public Node(T val) {
        value = val;

        childNodes = new ArrayList<Node>();
    }

    public void addChild(Node child) {
        childNodes.add(child);
    }

    public void removeChild(Node child) {
        childNodes.remove(child);
    }

    public ArrayList<Node> getNodes() {
        return childNodes;
    }

    public boolean hasNodes() {
        return (childNodes.size() != 0);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T val) {
        value = val;
    }

    public boolean getUsed() {
        return used;
    }

    public void setUsed(boolean u) {
        used = u;
    }
}
