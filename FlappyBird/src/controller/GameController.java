package controller;

import model.Bird;
import model.PipeManager;
import view.GameView;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController {
    private Bird bird;
    private PipeManager pipeManager;
    private Timer timer;

    public GameController(GameView view) {
        this.bird = Bird.getInstance();
        this.pipeManager = new PipeManager();

        view.setBird(bird);
        view.setPipeManager(pipeManager);
        bird.registerObserver(pipeManager);

        timer = new Timer(16, e -> {
            bird.update();
            pipeManager.update(bird.getX());
            if (pipeManager.checkCollision(bird.getRectangle())) {
                bird.setGameOver(true);
            }
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
                    pipeManager.reset();
                }
            }
        });
        view.setFocusable(true);
    }
}
