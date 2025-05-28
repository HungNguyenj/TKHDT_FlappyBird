package controller;

import model.Bird;
import view.GameView;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController {
    private GameView view;
    private Bird bird;
    private Timer timer;

    public GameController(GameView view) {
        this.bird = Bird.getInstance();
        this.view = view;
        view.setBird(bird);
        timer = new Timer(16, e -> {
            bird.update();
            view.repaint();
        });
        timer.start();

        // handle input
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    bird.jump();
                } else if (e.getKeyCode() == KeyEvent.VK_R && bird.isGameOver()) {
                    bird.reset();
                }
            }
        });
        view.setFocusable(true);
    }
}
