import java.io.*;
import java.util.*;

public class Main {
    public static class Node {
        int x, y, dist, h, d;
        Node(int x, int y, int dist, int h, int d) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.h = h;
            this.d = d;
        }
    }

    static int N, H, D;
    static char[][] grid;
    static int[][] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        grid = new char[N][N];
        visited = new int[N][N];
        int sx = 0, sy = 0;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < N; j++) {
                grid[i][j] = line.charAt(j);
                if (grid[i][j] == 'S') {
                    sx = i; sy = j;
                }
            }
        }

        System.out.println(bfs(sx, sy));
    }

    public static int bfs(int sx, int sy) {
        int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
        Queue<Node> q = new ArrayDeque<>();
        q.offer(new Node(sx, sy, 0, H, 0));
        visited[sx][sy] = H;

        while (!q.isEmpty()) {
            Node cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (grid[nx][ny] == 'E') return cur.dist + 1;

                int nextH = cur.h;
                int nextD = cur.d;

                if (grid[nx][ny] == 'U') nextD = D;
                if (nextD > 0) nextD--;
                else nextH--;

                if (nextH <= 0) continue;

                if (visited[nx][ny] < nextH + nextD) {
                    visited[nx][ny] = nextH + nextD;
                    q.offer(new Node(nx, ny, cur.dist + 1, nextH, nextD));
                }
            }
        }
        return -1;
    }
}