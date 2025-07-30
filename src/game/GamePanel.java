package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.InputStream;
import java.util.EventListener;

import util.*;
import entity.*;


public class GamePanel extends JPanel implements Runnable,KeyListener, EventListener {
    private final int BlockSize = 32;
    private final int MapOffset = 32;
    private final char[][] Map = {
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
            {'W','P','P','P','P','P','P','P','P','P','W','P','P','P','P','P','P','P','P','P','W'},
            {'W','P','W','W','W','P','W','W','W','P','W','P','W','W','W','P','W','W','W','P','W'},
            {'W','P','W','W','W','P','W','W','W','P','W','P','W','W','W','P','W','W','W','P','W'},
            {'W','P','P','P','P','P','P','P','P','P','P','P','P','P','P','P','P','P','P','P','W'},
            {'W','P','W','W','W','P','W','P','W','W','W','W','W','P','W','P','W','W','W','P','W'},
            {'W','P','P','P','P','P','W','P','P','P','W','P','P','P','W','P','P','P','P','P','W'},
            {'W','W','W','W','W','P','W','W','W','P','W','P','W','W','W','P','W','W','W','W','W'},
            {'G','G','G','G','W','P','W','P','P','P','P','P','P','P','W','P','W','G','G','G','G'},
            {'W','W','W','W','W','P','W','P','W','W','D','W','W','P','W','P','W','W','W','W','W'},
            {'P','P','P','P','P','P','P','P','W','G','G','G','W','P','P','P','P','P','P','P','P'},
            {'W','W','W','W','W','P','W','P','W','G','G','G','W','P','W','P','W','W','W','W','W'},
            {'G','G','G','G','W','P','W','P','W','W','W','W','W','P','W','P','W','G','G','G','G'},
            {'G','G','G','G','W','P','P','P','P','P','P','P','P','P','P','P','W','G','G','G','G'},
            {'W','W','W','W','W','P','P','P','W','W','W','W','W','P','P','P','W','W','W','W','W'},
            {'W','P','P','P','P','P','P','P','P','P','W','P','P','P','P','P','P','P','P','P','W'},
            {'W','P','W','W','W','P','W','W','W','P','W','P','W','W','W','P','W','W','W','P','W'},
            {'W','P','P','P','W','P','P','P','P','P','G','P','P','P','P','P','W','P','P','P','W'},
            {'W','P','P','P','W','P','W','P','W','W','W','W','W','P','W','P','W','P','P','P','W'},
            {'W','P','P','P','P','P','W','P','P','P','W','P','P','P','W','P','P','P','P','P','W'},
            {'W','P','W','W','W','W','W','W','W','P','W','P','W','W','W','W','W','W','W','P','W'},
            {'W','P','P','P','P','P','P','P','P','P','P','P','P','P','P','P','P','P','P','P','W'},
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}
    };
    int cookie_offset = 13;
    Pacman pacman;
    Blinky blinky;
    Drunky drunky;
    Thread gameThread;
    Font pixelFont;


    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);
        this.pacman = new Pacman(320, 544,Map);
        this.blinky = new Blinky(320, 32*11,Map,pacman);
        this.drunky= new Drunky(32*9,32*11,Map);
        try (InputStream font = getClass().getResourceAsStream("/Resource/fonts/ByteBounce.ttf")){
            this.pixelFont = Font.createFont(Font.TRUETYPE_FONT,font).deriveFont(48f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0 / 30;
        long lastTime = System.nanoTime();
        double delta = 0;

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / timePerFrame;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();  // redraw
                delta--;
            }
        }
    }

    private void update() {
        pacman.move();
        blinky.updateDirection();
        drunky.move();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMap(g);
        pacman.drawPacman(g);
        blinky.drawBlinky(g);
        drunky.drawDrunky(g);
        int currentScore = pacman.getScore();
        g.setColor(Color.yellow);
        g.setFont(pixelFont);
        g.drawString("Score " + currentScore,32,778);
    }

    private void drawMap(Graphics g) {
        for(int row = 0; row < this.Map.length; ++row) {
            for(int col = 0; col < this.Map[row].length; ++col) {
                if (this.Map[row][col] == 'W') {
                    g.setColor(new Color(20, 74, 128));
                    g.fillRect(col * 32, row * 32, 32, 32);

                } else if (this.Map[row][col] == 'P') {
                    g.setColor(Color.black);
                    g.fillRect(col * 32, row * 32, 32, 32);
                    int cookiex = col*32+cookie_offset;
                    int cookiey = row*32+cookie_offset;
                    g.setColor(Color.yellow);
                    g.fillArc(cookiex,cookiey,6,6,0,360);

                }
            }
        }
    }



    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            pacman.setDirection(-4, 0); // left
        } else if (key == KeyEvent.VK_D) {
            pacman.setDirection(4, 0); // right
        } else if (key == KeyEvent.VK_W) {
            pacman.setDirection(0, -4); // up
        } else if (key == KeyEvent.VK_S) {
            pacman.setDirection(0, 4); // down
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {}
}

