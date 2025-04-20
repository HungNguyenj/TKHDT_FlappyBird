import controller.GameController;
import view.GameView;

import javax.swing.*;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Flappy Bird");
        GameView view = new GameView();
        GameController controller = new GameController(view);

        frame.add(view);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
