package model;

import java.awt.*;

public class Pipe {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isTop;
    private boolean passed;

    public Pipe(int x, int y, int width, int height, boolean isTop, boolean passed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isTop = isTop;
        this.passed = passed;
    }

    public void move(int dx) {
        this.x -= dx;
    }

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public boolean isTop() {
        return isTop;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
