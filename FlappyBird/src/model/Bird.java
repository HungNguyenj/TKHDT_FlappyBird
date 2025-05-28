package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Bird {
    private double x;
    private double y;
    private double velocity;
    private int width;
    private int height;
    private Image image;
    private int score;
    private boolean gameOver;
    private final int PIPE_GAP = 150;
    private final int PIPE_SPACING = 200;
    private final int PIPE_WIDTH = 50;
    private final Random random;

    private static Bird instance;

    //pipe
    private ArrayList<Rectangle> pipes;

    private Bird() {
        this.pipes = new ArrayList<>();
        random = new Random();
        gameOver = false;
        score = 0;
        this.setX(100);
        this.setY(300);
    }

    public static Bird getInstance() {
        if (instance == null) {
            instance = new Bird();
        }
        return instance;
    }

    public Bird(Image image, Random random) {
        this.image = image;
        this.random = random;
    }

    public void update() {
        if (!isGameOver()) {
            this.velocity += 0.5;
            this.y += getVelocity();

            //kiem tra va cham voi mat dat hoac tran
            if (this.y > 600 || this.y < 0) {
                gameOver = true;
            }

            //update pipe
            for (int i = pipes.size() - 1; i >= 0; i--) {
                Rectangle pipe = pipes.get(i);
                pipe.x -= 3; // Di chuyển ống sang trái

                // Kiểm tra va chạm với ống
                Rectangle birdRect = new Rectangle((int) this.x, (int) this.y, 30, 30);
                if (birdRect.intersects(pipe)) {
                    gameOver = true;
                }

                // Xóa ống ra khỏi màn hình và thêm ống mới
                if (pipe.x < -pipe.getWidth()) {
                    pipes.remove(i);
                    score++;
                }
            }

            //add pipe
            if (pipes.isEmpty() || pipes.get(pipes.size() - 1).x < 800 - PIPE_SPACING) {
                addPipe();
            }
        }

    }

    public void jump() {
        if (!isGameOver()) {
            this.velocity -= 10;
        }
    }

    public void reset() {
        gameOver = false;
        score = 0;
        this.setX(100);
        this.setY(300);
        velocity = 0;
        pipes.clear();
        addPipe();
    }

    private void addPipe() {
        int pipeHeight = 100 + random.nextInt(300);
        pipes.add(new Rectangle(800, 0, PIPE_WIDTH, pipeHeight)); // Ống trên
        pipes.add(new Rectangle(800, pipeHeight + PIPE_GAP, PIPE_WIDTH, 600)); // Ống dưới
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Rectangle> getPipes() {
        return pipes;
    }
}
