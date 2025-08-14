package util;

public enum Direction {
    UP(0,-4) ,
    DOWN(0,4),
    LEFT(-4,0),
    RIGHT(4,0),
    NONE(0,0);

    public final int dx;
    public final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    }
