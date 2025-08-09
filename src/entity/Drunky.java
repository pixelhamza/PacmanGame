package entity;

import util.GhostState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Drunky extends Ghosts{
    private Pacman pacman;
    private final int speed=4;
    BufferedImage DrunkySprite;
    GhostState DrunkyState;
    private Random rand;
    int dir;
    GhostState state;
    private final int[][] directions = {
            {-speed, 0},
            {speed, 0},
            {0, -speed},
            {0, speed}
    };

    public Drunky(int x,int y,char[][] map){
        super(x,y,map);
        rand= new Random();
        dir= rand.nextInt(4);
        DrunkyState = GhostState.Home;

        try {DrunkySprite = ImageIO.read(getClass().getResource("/Resource/images/vanisher_first.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void move() {
//        if(DrunkyState == GhostState.Home){
//
//        }
        if(getX() == 320 && getY() == 256){ DrunkyState = GhostState.Scatter; };

        if (isAligned()) {
            int[] newDir;
            do {
                newDir = directions[rand.nextInt(directions.length)];
            } while (newDir[0] == -dx && newDir[1] == -dy);

            setDirection(newDir[0], newDir[1]);
        }

        super.move();
    }


    public void drawDrunky(Graphics g){
        g.drawImage(DrunkySprite,x,y,32,32,null);
    }
}
