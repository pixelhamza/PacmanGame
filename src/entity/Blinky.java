package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import util.GhostState;
import game.*;


public class Blinky extends Ghosts {
    private Pacman pacman;
    private final int speed = 4;
    BufferedImage blinkySprite;
    GhostState BlinkyState;

    public Blinky(int x, int y, char[][] map, Pacman pacman) {
        super(x, y, map);
        this.pacman = pacman;
        try {blinkySprite = ImageIO.read(getClass().getResource("/Resource/images/blinky.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BlinkyState = GhostState.Scatter;
    }
    int[] lastDir = {0,0};
    private int[] lastPosition = {-1, -1};
    public void updateDirection() {
        int blockSize = getBlockSize();

        int ghostX = (getX() + blockSize / 2) / blockSize;
        int ghostY = (getY() + blockSize / 2) / blockSize;
        int pacX = pacman.getX() / blockSize;
        int pacY = pacman.getY() / blockSize;
//        if(BlinkyState == GhostState.Scatter){
//            pacX = 0;
//            pacY = 0;
//        }


        System.out.println("Ghost Tile: (" + ghostX + ", " + ghostY + ")");
        System.out.println("Pacman Tile: (" + pacX + ", " + pacY + ")");

        int[][] directions = {
                {0, -1},  // up boi
                {-1, 0},  // left boi
                {0, 1},   // down niga
                {1, 0}    // right niga
        };

        int minDistance = Integer.MAX_VALUE;
        int[] bestMove = null;
        int bestPriority = Integer.MAX_VALUE;

        for (int i = 0; i < directions.length; i++) {
            int[] dir = directions[i];
            int newX = ghostX + dir[0];
            int newY = ghostY + dir[1];

            if (isValid(newX, newY)) {
                if(lastDir[0] == -dir[0] && lastDir[1] == -dir[1]) continue;
                if(map[newX][newY] == 'D') continue;

                int manhattanDistance = Math.abs(pacX - newX) + Math.abs(pacY - newY);

                // Pick if better distance, OR same distance but better priority
                if (manhattanDistance < minDistance ||
                        (manhattanDistance == minDistance && i < bestPriority)) {
                    minDistance = manhattanDistance;
                    bestPriority = i;
                    bestMove = dir;
                }
            }
        }

        if (bestMove != null) {
//            lastPosition[0] = ghostX;
//            lastPosition[1] = ghostY;
            int dx = (bestMove[0]) * speed; // removed subtraction of dir ? idk why
            int dy = (bestMove[1]) * speed;
//            lastDir[0] = bestMove[0]; // Store the direction vector
//            lastDir[1] = bestMove[1];
//            System.out.println("SetDirection: dx = " + dx + ", dy = " + dy);
//            setDirection(dx, dy);
            int testX = getX() + dx;
            int testY = getY() + dy;
            if (canMoveTo(testX, testY)) {
                lastDir[0] = bestMove[0];
                lastDir[1] = bestMove[1];
                setDirection(dx, dy);
            }
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

