package entity;

import java.awt.*;

import entity.*;
import game.*;

public class Ghosts {int x, y;
    protected int dx = 0, dy = 0;
    protected final char[][] map;
    final int BlockSize = 32;
    private int intendedDx = 0, intendedDy = 0;

    Ghosts(int x, int y, char[][] map){
        this.x=x;
        this.y=y;
        this.map=map;
    }
    public void setDirection(int dx, int dy) {
        intendedDx = dx;
        intendedDy = dy;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBlockSize() {
        return BlockSize;
    }


    public void move() {
        if (isAligned()) {
            // so we try changing to intended direction if it is valid every single frame bruh!!
            int testX = x + intendedDx;
            int testY = y + intendedDy;


            if (canMoveTo(testX, testY)) {
                dx = intendedDx; // here we check valid path. if yea then out intended becomes our dx and dy ,if nah then nvm who cares move on with the old direction
                dy = intendedDy;
            }
        }
        //  El teleporta boi
//        if(x/BlockSize<0){
//            x= map[0].length*BlockSize;
//        } else if (x/BlockSize> map[0].length) {
//            x=-BlockSize;// minus se not 0 cuz smoothly aate hue dikhta h pacman
//
//        }
        if(getX()==(map[0].length-1)*BlockSize ||getX()<=0)dx=-dx;


        int nextX = x + dx;
        int nextY = y + dy;

        if (canMoveTo(nextX, nextY)) {
            x = nextX;
            y = nextY;

        }
    }




    public boolean canMoveTo(int px, int py) {
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
    public boolean isAligned() {
        return x % BlockSize == 0 && y % BlockSize == 0; // this is for grid accurate turns obv like you said
    }
    public void checkCollisionWithPacman(Pacman pacman) {
        Rectangle ghostRect = new Rectangle(x, y, BlockSize, BlockSize);
        Rectangle pacmanRect = new Rectangle(pacman.getX(), pacman.getY(), BlockSize, BlockSize);
        if(pacmanRect.intersects(ghostRect)){
            System.out.println("NIGA CAUGHT BOI");
        }
    }

}
