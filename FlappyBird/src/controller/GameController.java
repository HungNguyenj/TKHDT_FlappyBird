package controller;

import model.Bird;
import model.Pipe;
import view.GameView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController {
    private GameView view;
    private Bird bird;
    private Pipe pipe;
    private Timer timer;

    public GameController(GameView view) {
        this.view = view;
        this.bird = view.getBird();
        this.pipe = view.getBottomPipe();

        timer = new Timer(16, e -> {
            bird.update();
            view.repaint();
        });
        timer.start();

        //control bird to jump
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
