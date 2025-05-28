package model;

import config.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bird implements Subject{
    private double x;
    private double y;
    private Image image;
    private double velocity;

    private boolean gameOver;

    private static Bird instance;
    private List<Observer> observers;

    private Bird() {
        gameOver = false;
        this.setX(100);
        this.setY(300);
        observers = new ArrayList<>();
    }

    public static Bird getInstance() {
        if (instance == null) {
            instance = new Bird();
        }
        return instance;
    }

    public void update() {
        if (!isGameOver()) {
            this.velocity += Constants.GRAVITY;
            this.y += getVelocity();

            //kiem tra va cham voi mat dat hoac tran
            if (this.y > 600 || this.y < 0) {
                setGameOver(true);
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
        this.setX(100);
        this.setY(300);
        velocity = 0;
    }

    public Rectangle getRectangle() {
        return new Rectangle((int) this.getX(), (int) this.getY(), Constants.BIRD_WIDTH, Constants.BIRD_HEIGHT);
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
        if (gameOver)
            notifyObservers();
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.onGameOver();
        }
    }
}
