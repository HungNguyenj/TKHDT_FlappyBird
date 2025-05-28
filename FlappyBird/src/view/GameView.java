package view;

import model.Bird;
import model.Pipe;
import model.PipeManager;
import config.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameView extends JPanel {
    private BufferedImage birdImage;
    private BufferedImage topPipeImage;
    private BufferedImage bottomPipeImage;
    private BufferedImage backgroundImage;

    private Bird bird;
    private PipeManager pipeManager;

    public GameView() throws IOException {
        birdImage = ImageIO.read(new File("src/image/flappybird.png"));
        backgroundImage = ImageIO.read(new File("src/image/background.png"));
        topPipeImage = ImageIO.read(new File("src/image/toppipe.png"));
        bottomPipeImage = ImageIO.read(new File("src/image/bottompipe.png"));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT, null);

        g.drawImage(bird.getImage(), (int) bird.getX(), (int) bird.getY(), 30, 30, null);

        //draw pipe
        for (Pipe pipe : pipeManager.getPipes()) {
            if (pipe.isTop()) {
                g.drawImage(topPipeImage, pipe.getX(), pipe.getY(), pipe.getWidth(), pipe.getHeight(), null);
            } else {
                g.drawImage(bottomPipeImage, pipe.getX(), pipe.getY(), pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        // score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + pipeManager.getScore(), 30, 50);

        //game over
        if (bird.isGameOver()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", 280, 200);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", 300, 280);
        }
    }

    public Bird getBird() {
        return bird;
    }

    public void setBird(Bird bird) {
        this.bird = bird;
        bird.setImage(birdImage);
    }

    public PipeManager getPipeManager() {
        return pipeManager;
    }

    public void setPipeManager(PipeManager pipeManager) {
        this.pipeManager = pipeManager;
    }
}
