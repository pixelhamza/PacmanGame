import java.awt.*;

public class Pacman {
    int x, y;
    private int dx = 0, dy = 0;
    private final char[][] map;
    final int BlockSize = 32;
    private int score = 0;
    private int intendedDx = 0, intendedDy = 0;



    public Pacman(int x, int y, char[][] map) {
        this.x = x;
        this.y = y;
        this.map = map;
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
        g.setColor(Color.YELLOW);
        int startAngle = 45; // default chehra

        if (dx > 0) { // right
            startAngle = 45;
        } else if (dx < 0) { // left mu
            startAngle = 225;
        } else if (dy < 0) { // up mu
            startAngle = 135;
        } else if (dy > 0) { // down mu
            startAngle = 315;
        }

        g.fillArc(x, y, BlockSize, BlockSize, startAngle, 270);
    }
    public int getScore() {
        return score;
    }
}
