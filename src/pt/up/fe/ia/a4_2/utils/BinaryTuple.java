package pt.up.fe.ia.a4_2.utils;

public class BinaryTuple<A, B> {
    private A firstValue;
    private B secondValue;

    public BinaryTuple(A val1, B val2) {
        firstValue = val1;
        secondValue = val2;
    }

    public A getFirst() {
        return firstValue;
    }

    public B getSecond() {
        return secondValue;
    }
}
