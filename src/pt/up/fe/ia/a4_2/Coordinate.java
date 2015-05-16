package pt.up.fe.ia.a4_2;

public class Coordinate {
    public int x, y;

    public Coordinate(int xValue, int yValue) {
        x = xValue;
        y = yValue;
    }

    public static double distance(Coordinate c1, Coordinate c2) {
        return Math.sqrt(
                Math.pow(
                        Math.abs(c1.x - c2.x) + Math.abs(c1.y - c2.y), 2));
    }
}
