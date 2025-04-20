import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

// Model
class GameModel {
    private double birdY;
    private double birdVelocity;
    private ArrayList<Rectangle> pipes;
    private int score;
    private boolean gameOver;
    private final int BIRD_X = 100;
    private final int PIPE_WIDTH = 50;
    private final int PIPE_GAP = 150;
    private final int PIPE_SPACING = 200;
    private final Random random;

    public GameModel() {
        birdY = 300;
        birdVelocity = 0;
        pipes = new ArrayList<>();
        score = 0;
        gameOver = false;
        random = new Random();
        addPipe();
    }

    public void update() {
        if (!gameOver) {
            // Cập nhật vị trí chim
            birdVelocity += 0.5; // Trọng lực
            birdY += birdVelocity;

            // Kiểm tra va chạm với mặt đất hoặc trần
            if (birdY > 600 || birdY < 0) {
                gameOver = true;
            }

            // Cập nhật ống
            for (int i = pipes.size() - 1; i >= 0; i--) {
                Rectangle pipe = pipes.get(i);
                pipe.x -= 3; // Di chuyển ống sang trái

                // Kiểm tra va chạm với ống
                Rectangle birdRect = new Rectangle(BIRD_X, (int) birdY, 30, 30);
                if (birdRect.intersects(pipe)) {
                    gameOver = true;
                }

                // Xóa ống ra khỏi màn hình và thêm ống mới
                if (pipe.x < -PIPE_WIDTH) {
                    pipes.remove(i);
                    score++;
                }
            }

            // Thêm ống mới nếu cần
            if (pipes.isEmpty() || pipes.get(pipes.size() - 1).x < 800 - PIPE_SPACING) {
                addPipe();
            }
        }
    }

    private void addPipe() {
        int pipeHeight = 100 + random.nextInt(300);
        pipes.add(new Rectangle(800, 0, PIPE_WIDTH, pipeHeight)); // Ống trên
        pipes.add(new Rectangle(800, pipeHeight + PIPE_GAP, PIPE_WIDTH, 600)); // Ống dưới
    }

    public void jump() {
        if (!gameOver) {
            birdVelocity = -10; // Nhảy lên
        }
    }

    public void reset() {
        birdY = 300;
        birdVelocity = 0;
        pipes.clear();
        score = 0;
        gameOver = false;
        addPipe();
    }

    // Getters
    public double getBirdY() { return birdY; }
    public int getBirdX() { return BIRD_X; }
    public ArrayList<Rectangle> getPipes() { return pipes; }
    public int getScore() { return score; }
    public boolean isGameOver() { return gameOver; }
}

// View
class GameView extends JPanel {
    private BufferedImage birdImg, pipeImg, backgroundImg;
    private GameModel model;

    public GameView(GameModel model) {
        this.model = model;
        try {
            birdImg = ImageIO.read(new File("src/image/flappybird.png"));
            pipeImg = ImageIO.read(new File("src/image/bottompipe.png"));
            backgroundImg = ImageIO.read(new File("src/image/background.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Vẽ nền
        g.drawImage(backgroundImg, 0, 0, 800, 600, null);

        // Vẽ chim
        g.drawImage(birdImg, model.getBirdX(), (int) model.getBirdY(), 30, 30, null);

        // Vẽ ống
        for (Rectangle pipe : model.getPipes()) {
            g.drawImage(pipeImg, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Vẽ điểm số
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + model.getScore(), 10, 20);

        // Vẽ thông báo game over
        if (model.isGameOver()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", 300, 300);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", 320, 340);
        }
    }
}

// Controller
class GameController {
    private GameModel model;
    private GameView view;
    private Timer timer;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;

        // Cập nhật game mỗi 16ms (~60 FPS)
        timer = new Timer(16, e -> {
            model.update();
            view.repaint();
        });
        timer.start();

        // Xử lý đầu vào
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    model.jump();
                } else if (e.getKeyCode() == KeyEvent.VK_R && model.isGameOver()) {
                    model.reset();
                }
            }
        });
        view.setFocusable(true);
    }
}

// Main class
public class FlappyBirdGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view);

        frame.add(view);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}