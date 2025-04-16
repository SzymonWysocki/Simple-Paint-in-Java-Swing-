import java.awt.*;

public class Shape {
    public int x;
    public int y;
    public int radious;
    public ShapeEnum shape;
    public Color color;



    public Shape(int x, int y, ShapeEnum shape, Color color) {
        this.shape = shape;
        this.color = color;
        this.radious = 50;
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
