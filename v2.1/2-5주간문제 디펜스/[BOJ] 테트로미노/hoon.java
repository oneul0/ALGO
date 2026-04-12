import java.io.*;
import java.util.*;

public class Main {
    static int N, M, ans = 0;
    static int[][] arr;
    static boolean[][] visited;
    static int maxVal = 0;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N][M];
        visited = new boolean[N][M];
        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j<M; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
                maxVal = Math.max(maxVal, arr[i][j]);
            }
        }

        for(int i = 0; i<N; i++){
            for(int j = 0; j<M; j++){
                visited[i][j] = true;
                dfs(i, j, 1, arr[i][j]);
                visited[i][j] = false;
            }
        }

        System.out.println(ans);
    }

    static int[] dr = {-1,1,0,0}, dc = {0,0,-1,1};

    public static void dfs(int r, int c, int count, int total){
        if(count == 4){
            ans = Math.max(ans, total);
            return;
        }
        for(int i = 0; i<4; i++){
            int nr = r + dr[i];
            int nc = c+ dc[i];
            if(nr <0 || nc<0 || nr>=N || nc>=M || visited[nr][nc]) continue;
            if(count == 2){
                visited[nr][nc] = true;
                dfs(r, c, count+1, total + arr[nr][nc]);
                visited[nr][nc] = false;
            }
            visited[nr][nc] = true;
            dfs(nr, nc, count+1, total+arr[nr][nc]);
            visited[nr][nc] = false;
        }
    }
}
