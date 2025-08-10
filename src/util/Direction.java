package util;

public enum Direction {
    UP(0,-4) ,
    DOWN(0,4),
    LEFT(-4,0),
    RIGHT(4,0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    }
