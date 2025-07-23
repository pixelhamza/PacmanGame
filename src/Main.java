import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pacman");
        GamePanel panel = new GamePanel();
        panel.setBackground(Color.BLACK);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(685, 780);
        frame.setLocationRelativeTo(null);
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
        panel.requestFocusInWindow();
        panel.setGameThread();
    }
}
