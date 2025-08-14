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
    Timer timer;
    Random rand;

    public Vanisher(int x,int y,char[][] map){
        super(x,y,map);
        VanisherState=GhostState.Home;
        rand= new Random();
        try {VanisherSprite = ImageIO.read(getClass().getResource("/Resource/images/vanisher_real.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    public void move(){
        

    }
    public void drawVanisher(Graphics g){
        g.drawImage(VanisherSprite,x,y,32,32,null);

    }
}
