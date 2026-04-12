import java.io.*;
import java.util.*;

public class Main {
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, -1, 0, 1};

    static boolean[][] check = new boolean[101][101];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;

        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());

            drawDragonCurve(x, y, d, g);
        }

        int count = countSquares();
        System.out.println(count);
    }

    public static void drawDragonCurve(int sx, int sy, int startD, int targetG) {
        List<Integer> directions = new ArrayList<>();
        directions.add(startD);

        for (int g = 1; g <= targetG; g++) {
            int size = directions.size();
            for (int i = size - 1; i >= 0; i--) {
                int nextDir = (directions.get(i) + 1) % 4;
                directions.add(nextDir);
            }
        }

        int x = sx;
        int y = sy;

        check[x][y] = true;

        for (int d : directions) {
            x += dx[d];
            y += dy[d];

            if (x >= 0 && x <= 100 && y >= 0 && y <= 100) {
                check[x][y] = true;
            }
        }
    }

    public static int countSquares() {
        int count = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (check[i][j] && check[i + 1][j] && check[i][j + 1] && check[i + 1][j + 1]) {
                    count++;
                }
            }
        }
        return count;
    }
}