package view;

import model.Bird;
import model.Pipe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameView extends JPanel {
    private final int boardWidth = 800;
    private final int boardHeight = 600;

    private BufferedImage birdImage;
    private BufferedImage topPipeImage;
    private BufferedImage bottomPipeImage;
    private BufferedImage backgroundImage;

    private Bird bird;
    private Pipe topPipe;
    private Pipe bottomPipe;

    public GameView() throws IOException {
        birdImage = ImageIO.read(new File("src/image/flappybird.png"));
        backgroundImage = ImageIO.read(new File("src/image/background.png"));
        topPipeImage = ImageIO.read(new File("src/image/toppipe.png"));
        bottomPipeImage = ImageIO.read(new File("src/image/bottompipe.png"));

        bird  = Bird.getInstance();
        bird.setImage(birdImage);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);

        g.drawImage(bird.getImage(), (int) bird.getX(), (int) bird.getY(), 30, 30, null);

        //draw pipe
        for (int i = 0; i < bird.getPipes().size(); i++) {
            Rectangle pipe = bird.getPipes().get(i);
            if (i % 2 == 0) {
                // Top pipe
                g.drawImage(topPipeImage, pipe.x, pipe.y, pipe.width, pipe.height, null);
            } else {
                // Bottom pipe
                g.drawImage(bottomPipeImage, pipe.x, pipe.y, pipe.width, pipe.height, null);
            }
        }

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

    public Pipe getTopPipe() {
        return topPipe;
    }

    public Pipe getBottomPipe() {
        return bottomPipe;
    }
}
