import java.io.*;
import java.util.*;

public class Main {
    static class Pair implements Comparable<Pair> {
        int r, c;
        int time;
        boolean canPlace;
        Pair(int r, int c, int time, boolean canPlace){
            this.r = r;
            this.c = c;
            this.time = time;
            this.canPlace = canPlace;
        }
        @Override
        public int compareTo(Pair o){
            return this.time - o.time;
        }
    }
    static int N, M;
    static int[][] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N][N];

        for(int i  = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j<N; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(bfs(0,0));
    }

    static int[] dr = {-1,1,0,0}, dc = {0,0,-1,1};
    public static int bfs(int sr, int sc) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.offer(new Pair(sr, sc, 0, true));
        boolean[][][] visited = new boolean[N][N][2];

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();

            if (cur.r == N - 1 && cur.c == N - 1) return cur.time;

            int used = cur.canPlace ? 0 : 1;
            if (visited[cur.r][cur.c][used]) continue;
            visited[cur.r][cur.c][used] = true;

            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                if (nr < 0 || nc < 0 || nr >= N || nc >= N) continue;

                if (arr[nr][nc] == 1) {
                    pq.offer(new Pair(nr, nc, cur.time + 1, cur.canPlace));
                } else {
                    if (arr[cur.r][cur.c] != 1) continue;

                    int period = arr[nr][nc];
                    boolean nextCanPlace = cur.canPlace;

                    if (arr[nr][nc] == 0) {
                        if (!cur.canPlace || !canBuild(nr, nc)) continue;
                        period = M;
                        nextCanPlace = false;
                    }

                    int nextTime = ((cur.time / period) + 1) * period;
                    pq.offer(new Pair(nr, nc, nextTime, nextCanPlace));
                }
            }
        }
        return -1;
    }
    static boolean canBuild(int r, int c) {
        boolean rowCliffs = false;
        if ((r - 1 >= 0 && arr[r - 1][c] == 0) || (r + 1 < N && arr[r + 1][c] == 0)) rowCliffs = true;
        boolean colCliffs = false;
        if ((c - 1 >= 0 && arr[r][c - 1] == 0) || (c + 1 < N && arr[r][c + 1] == 0)) colCliffs = true;

        return !(rowCliffs && colCliffs);
    }
}
