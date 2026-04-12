import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static class Pair {
        int x, y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static int n, m;
    static int[][] lab;
    static Pair[] virusPos = new Pair[10];
    static int idx = 0;
    static int minTime = Integer.MAX_VALUE;
    static int maxInfect = 0;
    static int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        lab = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                lab[i][j] = Integer.parseInt(st.nextToken());
                if (lab[i][j] == 2) {
                    virusPos[idx++] = new Pair(i, j);
                }
                if (lab[i][j] != 1) {
                    maxInfect++;
                }
            }
        }

        dfs(0, 0, new int[m]);

        System.out.println(minTime == Integer.MAX_VALUE ? -1 : minTime);
    }

    public static void dfs(int start, int depth, int[] selected) {
        if (depth == m) {
            int result = bfs(selected);
            if (result != -1) {
                minTime = Math.min(minTime, result);
            }
            return;
        }

        for (int i = start; i < idx; i++) {
            selected[depth] = i;
            dfs(i + 1, depth + 1, selected);
        }
    }

    public static int bfs(int[] selected) {
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][n];
        int infected = 0;

        for (int i = 0; i < m; i++) {
            Pair v = virusPos[selected[i]];
            q.offer(new int[]{v.x, v.y, 0});
            visited[v.x][v.y] = true;
            infected++;
        }

        if (infected == maxInfect) return 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if (nx < 0 || ny < 0 || nx >= n || ny >= n) continue;
                if (visited[nx][ny] || lab[nx][ny] == 1) continue;

                infected++;

                if (infected == maxInfect) return cur[2] + 1;

                q.offer(new int[]{nx, ny, cur[2] + 1});
                visited[nx][ny] = true;
            }
        }

        return -1;
    }
}