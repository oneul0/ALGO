import java.util.*;
import java.io.*;

public class Main {
    static int R, C;
    static int[][] arr;
    static int[][] path;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        arr = new int[R][C];
        path = new int[R][C];
        for(int i = 0; i<R; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j<C; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
            Arrays.fill(path[i], -1);
        }

        System.out.println(dfs(0,0));
    }

    static int[] dr = {-1,1,0,0}, dc = {0,0,-1,1};
    static int dfs(int r, int c){
        if(r == R-1 && c == C-1){
            return 1;
        }
        if(path[r][c] != -1){
            return path[r][c];
        }
        path[r][c] = 0;
        for(int i = 0; i<4; i++){
            int nr = r + dr[i];
            int nc = c + dc[i];
            if(nr<0 || nc<0 || nr>=R || nc>=C) continue;
            if(arr[r][c] <= arr[nr][nc]) continue;
            path[r][c] += dfs(nr, nc);
        }
        return path[r][c];
    }
}
