import java.io.*;
import java.util.*;

public class Main {
    static int[][] arr = new int[7][7];
    static int num;
    static int[][] grid = new int[7][7];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int i = 0; i < 7; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 7; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        num = Integer.parseInt(br.readLine());

        int minBalls = Integer.MAX_VALUE;

        for (int col = 0; col < 7; col++) {
            if (arr[0][col] != 0) continue;

            minBalls = Math.min(minBalls, solve(col));
        }

        System.out.println(minBalls);
    }

    public static int solve(int col) {
        for (int i = 0; i < 7; i++) {
            grid[i] = arr[i].clone();
        }

        int targetRow = -1;
        for (int r = 6; r >= 0; r--) {
            if (grid[r][col] == 0) {
                targetRow = r;
                break;
            }
        }
        if (targetRow != -1) grid[targetRow][col] = num;

        simulation();

        return ballCount();
    }

    public static void simulation() {
        boolean isChanged = true;

        while (isChanged) {
            isChanged = false;
            boolean[][] shouldRemove = new boolean[7][7];

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (grid[i][j] == 0) continue;

                    int width = getWidth(i, j);
                    int height = getHeight(i, j);

                    if (grid[i][j] == width || grid[i][j] == height) {
                        shouldRemove[i][j] = true;
                        isChanged = true;
                    }
                }
            }

            if (!isChanged) break;

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (shouldRemove[i][j]) {
                        grid[i][j] = 0;
                    }
                }
            }

            drop();
        }
    }

    public static int getWidth(int r, int c) {
        int count = 1;

        for (int k = c - 1; k >= 0; k--) {
            if (grid[r][k] == 0) break;
            count++;
        }

        for (int k = c + 1; k < 7; k++) {
            if (grid[r][k] == 0) break;
            count++;
        }

        return count;
    }

    public static int getHeight(int r, int c) {
        int count = 1;

        for (int k = r - 1; k >= 0; k--) {
            if (grid[k][c] == 0) break;
            count++;
        }

        for (int k = r + 1; k < 7; k++) {
            if (grid[k][c] == 0) break;
            count++;
        }

        return count;
    }

    public static void drop() {
        for (int col = 0; col < 7; col++) {
            Deque<Integer> balls = new ArrayDeque<>();
            for (int row = 0; row < 7; row++) {
                if (grid[row][col] != 0) {
                    balls.addLast(grid[row][col]);
                    grid[row][col] = 0;
                }
            }
            for (int row = 6; row >= 0; row--) {
                if (balls.isEmpty()) break;
                grid[row][col] = balls.pollLast();
            }
        }
    }

    public static int ballCount() {
        int count = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (grid[i][j] != 0) count++;
            }
        }
        return count;
    }
}