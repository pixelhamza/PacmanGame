package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import util.Direction;
import util.GameState;

public class Pacman {
    int x, y;
    private int dx = 0, dy = 0;
    private final char[][] map;
    final int BlockSize = 32;
    private int score = 0;
    private int intendedDx = 0, intendedDy = 0;
    BufferedImage[] pacman_hallucinations; // for storing the small images
    BufferedImage pacmanSprite;
    int spriteWidth = 32, spriteLength = 32;
    int i = 0;


    public Pacman(int x, int y, char[][] map) {
        this.x = x;
        this.y = y;
        this.map = map;
        pacman_hallucinations = new BufferedImage[8];
        getPacmanFrames();
    }

    public void getPacmanFrames(){
        try {pacmanSprite = ImageIO.read(getClass().getResource("/Resource/images/pacman_ani_try.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 8; i++) {
            pacman_hallucinations[i] = pacmanSprite.getSubimage(i * spriteWidth,0, spriteWidth, spriteLength);
        }
        System.out.println("pacman_animation frames have Initialised");
    }

    public void setDirection(Direction dir) {
        intendedDx = dir.dx;
        intendedDy = dir.dy;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getScore() {
        return score;
    }

    public void move() {
        if (isAligned()) {
            // so we try changing to intended direction if it is valid every single frame bruh!!
            int testX = x + intendedDx;
            int testY = y + intendedDy; // f magic numbers


            if (canMoveTo(testX, testY)) {
                dx = intendedDx; // here we check valid path. if yea then out intended becomes our dx and dy ,if nah then nvm who cares move on with the old direction
                dy = intendedDy;
            }
        }
        //  El teleporta boi
        if(x/BlockSize<0){
         x= map[0].length*BlockSize;
        } else if (x/BlockSize> map[0].length) {
            x=-BlockSize;// minus se not 0 cuz smoothly aate hue dikhta h pacman

        }


        int nextX = x + dx;
        int nextY = y + dy;

        if (canMoveTo(nextX, nextY)) {
            x = nextX;
            y = nextY;
            checkPelletCollision();
        }
    }




    private boolean canMoveTo(int px, int py) {
        // Check all four corners of Pacman lil nga

        int[] xChecks = {px / BlockSize, (px + BlockSize - 1) / BlockSize};
        int[] yChecks = {py / BlockSize, (py + BlockSize - 1) / BlockSize};
        //short way of doing individual 4 corners. unncesscary but looks kinda cool and useful to prevent out of bounds

        for (int yCheck : yChecks) {
            if (yCheck < 0 || yCheck >= map.length) continue;
            for (int xCheck : xChecks) {
                if (xCheck < 0 || xCheck >= map[0].length) continue;
                if (map[yCheck][xCheck] == 'W') {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isAligned() {
        return x % BlockSize == 0 && y % BlockSize == 0; // this is for grid accurate turns obv like you said
    }

    private void checkPelletCollision() {
        int centerX = (x + BlockSize/2) / BlockSize;
        int centerY = (y + BlockSize/2) / BlockSize;

        if (centerY >= 0 && centerY < map.length &&
                centerX >= 0 && centerX < map[0].length &&
                map[centerY][centerX] == 'P') {
            map[centerY][centerX] = 'G'; // Remove pellet
            score += 10;
            System.out.println("Score: " + score); // For debugging
        }
    }

    public void drawPacman(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        if (i > 7) {
            i = 0;
        }
        int angle = 0;
        if (dx > 0) { // right
            angle = 0;
        } else if (dx < 0) { // left mu
            angle = 180;
        } else if (dy < 0) { // up mu
            angle = 270;
        } else if (dy > 0) { // down mu
            angle = 90;
        }

        g.drawImage(rotateImage(pacman_hallucinations[i], angle), x, y, null);
        i++;
        g2d.setTransform(old);

    }

    private BufferedImage rotateImage(BufferedImage img, double angle) {
        BufferedImage copy = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        Graphics2D g2 = copy.createGraphics();
        g2.rotate(Math.toRadians(angle), img.getWidth() / 2.0, img.getHeight() / 2.0);
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        return copy;
    }

}
