package entity;

import util.GhostState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


public class Vanisher extends Ghosts{
    private Pacman pacman;
    private final int speed=4;
    BufferedImage VanisherSprite;
    GhostState VanisherState;
    Timer stateTimer;
    Random rand;
    private final int[][] directions = {
            {-speed, 0},
            {speed, 0},
            {0, -speed},
            {0, speed}
    };

    public Vanisher(int x,int y,char[][] map,Pacman pacman){
        super(x,y,map);
        VanisherState=GhostState.Visible;
        this.pacman=pacman;
        rand= new Random();
        try {VanisherSprite = ImageIO.read(getClass().getResource("/Resource/images/vanisher_real.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stateCycle();
        
    }
    private void stateCycle() {
        stateTimer = new Timer(10000, e -> {
            if (VanisherState == GhostState.Visible) {
                VanisherState = GhostState.Vanish;
                teleportNearPacman();
                VanisherState = GhostState.Visible;
            }
        });
        stateTimer.start();
    }
    @Override
    public void move(){
        if(VanisherState==GhostState.Visible){
            random_mode();

        }
        super.move();
        

    }
    private void teleportNearPacman() {
        int pacX = pacman.getX() / 32;
        int pacY = pacman.getY() / 32;

        //random tile pick karega nearest (=3)
        for (int i = 0; i < 15; i++) {
            int tx = pacX + rand.nextInt(9) - 3;
            int ty = pacY + rand.nextInt(9) - 3;

            if (isValid(tx, ty)) {
                this.x = tx * 32;
                this.y = ty * 32;
                break;
            }
        }
    }
    void random_mode(){
        if (isAligned()) {
            int[] newDir;
            do {
                newDir = directions[rand.nextInt(directions.length)];
            } while (newDir[0] == -dx && newDir[1] == -dy);

            setDirection(newDir[0], newDir[1]);
        }



    }
    private boolean isValid(int x, int y) {
        if (x < 0 || y < 0 || y >= map.length) return false;
        if (x >= map[0].length) return false;
        return map[y][x] != 'W';
    }
    public void drawVanisher(Graphics g){
        if(VanisherState==GhostState.Visible){
        g.drawImage(VanisherSprite,x,y,32,32,null);}
        if(VanisherState==GhostState.Vanish){
            g.drawRect(x,y,32,32);
        }

    }
}
