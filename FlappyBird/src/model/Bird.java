package model;

import util.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Bird {
    private double x;
    private double y;
    private double velocity;
    private int width = Constants.BIRD_WIDTH;
    private int height = Constants.BIRD_HEIGHT;
    private Image image;
    private int score;
    private double gravity = Constants.GRAVITY;
    private boolean gameOver;
    private final Random random;

    private static Bird instance;

    //pipe
    private ArrayList<Rectangle> pipes;
    private ArrayList<Boolean> pipePassed;
    private int pipeSpeed = Constants.PIPE_SPEED;

    private Bird() {
        this.pipes = new ArrayList<>();
        this.pipePassed = new ArrayList<>();
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

    public void update() {
        if (!isGameOver()) {
            this.velocity += gravity;
            this.y += getVelocity();

            //kiem tra va cham voi mat dat hoac tran
            if (this.y > 600 || this.y < 0) {
                gameOver = true;
            }

            //update pipe
            for (int i = pipes.size() - 1; i >= 0; i--) {
                Rectangle pipe = pipes.get(i);
                pipe.x -= pipeSpeed; // Di chuyển ống sang trái

                // check
                Rectangle birdRect = new Rectangle((int) this.x, (int) this.y, width, height);
                if (birdRect.intersects(pipe)) {
                    gameOver = true;
                }

                // score
                if (i % 2 == 0 && !pipePassed.get(i) && pipe.getX() + pipe.getWidth() < this.x) {
                    score++;
                    pipePassed.set(i, true);
                }

                // Xóa ống ra khỏi màn hình và thêm ống mới
                if (pipe.getX() < -pipe.getWidth()) {
                    pipes.remove(i);
                    pipePassed.remove(i);
                }
                
            }

            //add pipe
            if (pipes.isEmpty() || pipes.get(pipes.size() - 1).x < Constants.BOARD_WIDTH - Constants.PIPE_SPACING) {
                addPipe();
            }
        }

    }

    public void jump() {
        if (!isGameOver()) {
            this.velocity = - Constants.JUMP_VELOCITY;
        }
    }

    public void reset() {
        gameOver = false;
        score = 0;
        this.setX(100);
        this.setY(300);
        velocity = 0;
        pipes.clear();
        pipePassed.clear();
        addPipe();
    }

    private void addPipe() {
        int pipeHeight = 100 + random.nextInt(300);
        pipes.add(new Rectangle(800, 0, Constants.PIPE_WIDTH, pipeHeight)); // top pipe
        pipes.add(new Rectangle(800, pipeHeight + Constants.PIPE_GAP, Constants.PIPE_WIDTH, 600)); // bottom pipe

        pipePassed.add(false); // top
        pipePassed.add(false); // bottom
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
