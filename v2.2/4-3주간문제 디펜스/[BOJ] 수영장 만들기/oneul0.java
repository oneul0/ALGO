import java.io.*;
import java.util.*;

public class Main {
    static class Tile implements Comparable<Tile>{
        int r, c;
        int h;
        Tile(int r, int c, int h){
            this.r = r;
            this.c = c;
            this.h = h;
        }
        @Override
        public int compareTo(Tile o){
            return this.h - o.h;
        }
    }
    static int N, M;
    static int[][] pool;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        pool = new int[N][M];
        PriorityQueue<Tile> pq = new PriorityQueue<>();
        for(int i = 0; i<N; i++){
            String line = br.readLine();
            for(int j = 0; j<M; j++){
                pool[i][j] = line.charAt(j) - '0';
                if(i == 0 || j == 0 || i==N-1 || j==M-1){
                    pq.offer(new Tile(i,j, pool[i][j]));
                }
            }
        }
        System.out.println(fill(pq));

    }

    static int[] dr = {-1,1,0,0}, dc = {0,0,-1,1};
    public static int fill(PriorityQueue<Tile> pq) {
        boolean[][] visited = new boolean[N][M];
        int answer = 0;

        while (!pq.isEmpty()) {
            Tile cur = pq.poll();

            if (visited[cur.r][cur.c]) continue;
            visited[cur.r][cur.c] = true;

            answer += cur.h - pool[cur.r][cur.c];

            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                if (nr < 0 || nc < 0 || nr >= N || nc >= M) continue;
                if (visited[nr][nc]) continue;

                int nh = Math.max(cur.h, pool[nr][nc]);
                pq.offer(new Tile(nr, nc, nh));
            }
        }

        return answer;
    }
}
