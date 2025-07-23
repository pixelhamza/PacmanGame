import java.util.*;

class Node {
    int x, y;
    Node parent;

    Node(int x, int y, Node parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }
}

public class PathFinding {
    static int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // L, R, U, D

    public static int[] bfs(char[][] map, int startX, int startY, int goalX, int goalY) {
        int rows = map.length;
        int cols = map[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<Node> queue = new LinkedList<>();

        queue.add(new Node(startX, startY, null));
        visited[startX][startY] = true;

        Node end = null;

        while (!queue.isEmpty()) {
            Node curr = queue.poll();

            if (curr.x == goalX && curr.y == goalY) {
                end = curr;
                break;
            }

            for (int[] d : DIRS) {
                int nx = curr.x + d[0];
                int ny = curr.y + d[1];

                if (nx >= 0 && ny >= 0 && nx < rows && ny < cols &&
                        map[nx][ny] != 'W'&& !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new Node(nx, ny, curr));
                }
            }
        }

        if (end == null) return null;

        // Backtrack to get next cell from ghost position
        Node prev = end;
        while (prev.parent != null && !(prev.parent.x == startX && prev.parent.y == startY)) {
            prev = prev.parent;
        }

        return new int[]{prev.x, prev.y}; // Next tile to move toward
    }
}
