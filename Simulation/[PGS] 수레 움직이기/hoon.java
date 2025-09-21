import java.util.*;

class Solution {

    class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    class State {
        Point red, blue;
        int moves;
        State(Point red, Point blue, int moves) {
            this.red = red;
            this.blue = blue;
            this.moves = moves;
        }
    }

    Point redGoal, blueGoal;
    int n, m;
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};
    int[][] maze;

    boolean[][][][] visited;

    public int solution(int[][] maze) {
        this.maze = maze;
        n = maze.length;
        m = maze[0].length;
        visited = new boolean[n][m][n][m];
        Point red = null, blue=null;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (maze[i][j] == 1) red = new Point(i, j);
                else if (maze[i][j] == 2) blue = new Point(i, j);
                else if (maze[i][j] == 3) redGoal = new Point(i, j);
                else if (maze[i][j] == 4) blueGoal = new Point(i, j);
            }
        }

        return bfs(red, blue);
    }

    public int bfs(Point startRed, Point startBlue) {
        Deque<State> q = new ArrayDeque<>();
        q.offer(new State(startRed, startBlue, 0));
        visited[startRed.x][startRed.y][startBlue.x][startBlue.y] = true;

        while (!q.isEmpty()) {
            State current = q.remove();
            Point red = current.red;
            Point blue = current.blue;
            int moves = current.moves;

            boolean redReached = (red.x == redGoal.x && red.y == redGoal.y);
            boolean blueReached = (blue.x == blueGoal.x && blue.y == blueGoal.y);

            if (redReached && blueReached) {
                return moves;
            }

            for (int i = 0; i < 4; i++) {
                Point nextRed = redReached ? red : new Point(red.x + dx[i], red.y + dy[i]);

                for (int j = 0; j < 4; j++) {
                    Point nextBlue = blueReached ? blue : new Point(blue.x + dx[j], blue.y + dy[j]);

                    if (isValid(nextRed, nextBlue, red, blue)) {
                        if (!visited[nextRed.x][nextRed.y][nextBlue.x][nextBlue.y]) {
                            visited[nextRed.x][nextRed.y][nextBlue.x][nextBlue.y] = true;
                            q.offer(new State(nextRed, nextBlue, moves + 1));
                        }
                    }
                }
            }
        }

        return 0;
    }

    private boolean isValid(Point nextRed, Point nextBlue, Point red, Point blue) {
        if (nextRed.x < 0 || nextRed.x >= n || nextRed.y < 0 || nextRed.y >= m ||
            nextBlue.x < 0 || nextBlue.x >= n || nextBlue.y < 0 || nextBlue.y >= m) {
            return false;
        }

        if (maze[nextRed.x][nextRed.y] == 5 || maze[nextBlue.x][nextBlue.y] == 5) {
            return false;
        }

        if (nextRed.x == nextBlue.x && nextRed.y == nextBlue.y) {
            return false;
        }

        if ((nextRed.x == blue.x && nextRed.y == blue.y) && (nextBlue.x == red.x && nextBlue.y == red.y)) {
            return false;
        }

        return true;
    }
}