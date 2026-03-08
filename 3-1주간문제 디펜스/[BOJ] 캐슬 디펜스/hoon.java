import java.io.*;
import java.util.*;

public class Main {
    public static class Pair{
        int r, c;
        Pair(int r, int c){
            this.r = r;
            this.c = c;
        }
    }
    static int N, M, D, answer =0;
    static int[][] origin;
    static int[] dr = {0, -1, 0}, dc = {-1, 0, 1};
    static int[] pos = new int[3];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        origin = new int[N][M];

        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j<M; j++){
                origin[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        permute(0, 0);
        System.out.println(answer);
    }

    public static void permute(int depth, int start){
        if(depth == 3){
            simulation();
            return;
        }
        for(int i = start; i<M; i++){
            pos[depth] = i;
            permute(depth+1, i+1);
        }
    }
    public static void simulation(){
        int[][] grid = new int[N][M];
        for(int i = 0; i<N; i++){
            grid[i] = origin[i].clone();
        }
        //적이 모두 없어질 때까지 반복
        int killed = 0;
        while(isEnemyAlive(grid)){
            //궁수턴
            killed += shoot(grid);
            //적턴
            enemyDown(grid);
        }
        answer = Math.max(killed, answer);
    }
    public static boolean isValid(int r, int c){
        return (r>=0 && c>=0 && r<N && c<M);
    }
    public static int shoot(int[][] grid){
        int result = 0;
        Queue<Pair> killed = new ArrayDeque<>();

        //각 궁수에서 가장 가까운 적 위치 도출
        for(int i = 0; i<3; i++){
            Pair e = getEnemyPos(N, pos[i], grid);
            if(e.r == -1 && e.c == -1) continue;
            killed.add(e);
        }

        for(Pair e : killed){
            if(grid[e.r][e.c] == 1) result++;
            grid[e.r][e.c] = 0;
        }
        return result;
    }

    public static Pair getEnemyPos(int sr, int sc, int[][] grid){
        Queue<Pair> q = new ArrayDeque<>();
        boolean[][] chk = new boolean[N][M];
        q.offer(new Pair(sr, sc));

        while(!q.isEmpty()){
            Pair cur = q.poll();

            for(int d = 0; d<3; d++){
                int tr = cur.r + dr[d];
                int tc = cur.c + dc[d];
                if(!isValid(tr, tc)) continue;
                if(chk[tr][tc] || getDist(sr, sc, tr, tc) > D) continue;
                chk[tr][tc] = true;
                if(grid[tr][tc] == 1){
                    return new Pair(tr, tc);
                }
                q.offer(new Pair(tr,tc));
            }
        }
        return new Pair(-1,-1);
    }
    public static void enemyDown(int[][] grid){
        for(int i = N-1; i>=0; i--){
            for(int j = 0; j<M; j++){
                if(grid[i][j] == 1){
                    grid[i][j] = 0;
                    if(i+1 < N){
                        grid[i+1][j] = 1;
                    }
                }
            }
        }
    }

    public static boolean isEnemyAlive(int[][] grid){
        for(int i = 0; i<N; i++){
            for(int j = 0; j<M; j++){
                if(grid[i][j] == 1) return true;
            }
        }
        return false;
    }

    public static int getDist(int r1, int c1, int r2, int c2){
        return Math.abs(r1-r2) + Math.abs(c1 - c2);
    }

}
