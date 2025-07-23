import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Blinky extends Ghosts {
    private Pacman pacman;
    private final int speed = 4;
    BufferedImage blinkySprite;

    public Blinky(int x, int y, char[][] map, Pacman pacman) {
        super(x, y, map);
        this.pacman = pacman;
        try {blinkySprite = ImageIO.read(getClass().getResource("images/blinky.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateDirection() {
        int blockSize = getBlockSize();

        int ghostX = getX() / blockSize;
        int ghostY = getY() / blockSize;
        int pacX = pacman.getX() / blockSize;
        int pacY = pacman.getY() / blockSize;

        int[][] directions = {
                {0, -1},  // up boi
                {-1, 0},  // left boi
                {0, 1},   // down niga
                {1, 0}    // right niga
        };

        int minDistance = Integer.MAX_VALUE;
        int[] bestMove = null;

        for (int[] dir : directions) {
            int newX = ghostX + dir[0];
            int newY = ghostY + dir[1];

            if (isValid(newX, newY)) {
                int manhattanDistance = Math.abs(pacX - newX) + Math.abs(pacY - newY);
                if (manhattanDistance < minDistance) {
                    minDistance = manhattanDistance;
                    bestMove = new int[]{newX, newY};
                }
            }
        }

        if (bestMove != null) {
            int dx = (bestMove[0] - ghostX) * speed;
            int dy = (bestMove[1] - ghostY) * speed;
            setDirection(dx, dy);
        }
        checkCollisionWithPacman(pacman);

        move();


    }

    private boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 &&
                y < map.length && x < map[0].length &&
                map[y][x] != 'W';
    }

    public void drawBlinky(Graphics g){
        g.drawImage(blinkySprite, x, y, 32, 32, null);
    }
}

