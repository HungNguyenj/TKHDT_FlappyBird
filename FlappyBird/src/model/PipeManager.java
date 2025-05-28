package model;

import config.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PipeManager implements Observer{
    private List<Pipe> pipes;
    private List<Boolean> pipePassed;

    private final Random random;
    private int score;
    private boolean stopped;

    public PipeManager() {
        pipes = new ArrayList<>();
        pipePassed = new ArrayList<>();
        random = new Random();
        score = 0;
        reset();
    }

    public void update(double birdX) {
        if (!stopped) {
            for (int i = pipes.size() - 1; i >= 0; i--) {
                Pipe pipe = pipes.get(i);
                pipe.move(Constants.PIPE_SPEED);

                // score
                if (pipe.isTop() && !pipe.isPassed() && pipe.getX() + pipe.getWidth() < birdX) {
                    score++;
                    pipe.setPassed(true);
                }

                // delete pipe
                if (pipe.getX() < -pipe.getWidth()) {
                    pipes.remove(i);
                    pipePassed.remove(i);
                }

            }

            // add pipe
            if (pipes.isEmpty() || pipes.get(pipes.size() - 1).getX() < Constants.BOARD_WIDTH - Constants.PIPE_SPACING) {
                addPipe();
            }
        }
    }

    public void reset() {
        pipes.clear();
        pipePassed.clear();
        addPipe();
        stopped = false;
        score = 0;
    }

    private void addPipe() {
        int pipeHeight = 100 + random.nextInt(300);

        //new top pipe
        pipes.add(new Pipe(Constants.BOARD_WIDTH, 0, Constants.PIPE_WIDTH,
                pipeHeight, true, false));
        //new bottom pipe
        pipes.add(new Pipe(Constants.BOARD_WIDTH, pipeHeight + Constants.PIPE_GAP,
                Constants.PIPE_WIDTH, Constants.BOARD_HEIGHT, false, false));

        pipePassed.add(false); // top
        pipePassed.add(false); // bottom
    }

    public boolean checkCollision(Rectangle birdRect) {
        for (Pipe pipe : pipes) {
            if (birdRect.intersects(pipe.getBounds())) {
                return true;
            }
        }
        return false;
    }

    public List<Pipe> getPipes() {
        return pipes;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void onGameOver() {
        stopped = true;
    }
}
