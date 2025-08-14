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
    boolean IsHome = true;

    public Blinky(int x, int y, char[][] map, Pacman pacman) {
        super(x, y, map);
        this.pacman = pacman;
        try {blinkySprite = ImageIO.read(getClass().getResource("/Resource/images/blinky.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BlinkyState = GhostState.Home;
    }
    int[] lastDir = {0,0};
    private int[] lastPosition = {-1, -1};
    @Override
    public void move() {
        int blockSize = getBlockSize();
        int ghostX = (getX() + blockSize / 2) / blockSize;
        int ghostY = (getY() + blockSize / 2) / blockSize;
        int pacX = pacman.getX() / blockSize;
        int pacY = pacman.getY() / blockSize;

        if(BlinkyState == GhostState.Scatter){
            pacX = 1;
            pacY = 1;
        }
        if(BlinkyState == GhostState.Home && IsHome){
            pacX = 10; pacY = 8;
        }
        if(ghostX == 10 && ghostY == 8){ BlinkyState = GhostState.Scatter; };

        System.out.println("Ghost Tile: (" + ghostX + ", " + ghostY + ")");
        System.out.println("Pacman Tile: (" + pacX + ", " + pacY + ")");

        int[][] directions = {
                {0, -1},
                {-1, 0},
                {0, 1},
                {1, 0}
        };

        Double minDistance = Double.MAX_VALUE;
        int[] bestMove = null;
        int bestPriority = Integer.MAX_VALUE;

        for (int i = 0; i < directions.length; i++) {
            int[] dir = directions[i];
            int newX = ghostX + dir[0];
            int newY = ghostY + dir[1];

            if (isValid(newX, newY)) {
                if(lastDir[0] == -dir[0] && lastDir[1] == -dir[1]) continue;

                //int manhattanDistance = Math.abs(pacX - newX) + Math.abs(pacY - newY);
                double euclideanDistance = Math.sqrt(Math.pow(pacX - newX, 2) + Math.pow(pacY - newY, 2));
                if (euclideanDistance < minDistance ||
                        (euclideanDistance == minDistance && i < bestPriority)) {
                    minDistance = euclideanDistance;
                    bestPriority = i;
                    bestMove = dir;
                }
            }
        }


        if (bestMove != null) {
            int dx = (bestMove[0]) * speed;
            int dy = (bestMove[1]) * speed;
            int testX = getX() + dx;
            int testY = getY() + dy;
            if (canMoveTo(testX, testY)) {
                lastDir[0] = bestMove[0];
                lastDir[1] = bestMove[1];
                //System.out.println("SetDirection: dx = " + dx + ", dy = " + dy);
                setDirection(dx, dy);
            }
        }
        checkCollisionWithPacman(pacman);
        super.move();
    }

    private boolean isValid(int x, int y) {
        System.out.println("isValid: checking bounds...");
        if (x < 0 || y < 0 || y >= map.length) return false;
        if (x >= map[0].length) return false;
        //System.out.println("About to access map[" + y + "][" + x + "]");
        return map[y][x] != 'W';
    }

    public void drawBlinky(Graphics g){
        g.drawImage(blinkySprite, x, y, 32, 32, null);
    }
}

