import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static class Pair{
        int r, c;
        Pair(int r, int c){
            this.r = r;
            this.c = c;
        }
    }
    static int W, H, answer = 0;
    static int[][] arr;
    static int[][] odd_dyx = {{-1, -1}, {-1, 0}, {0, -1}, {0, 1}, {1, -1}, {1, 0}};
    static int[][] even_dyx = {{-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, 0}, {1, 1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        arr = new int[H+2][W+2];
        for(int j = 1; j<=H; j++){
            st = new StringTokenizer(br.readLine());
            for(int i = 1; i<=W; i++){
                arr[j][i] = Integer.parseInt(st.nextToken());
            }
        }


        bfs(0,0);

        System.out.println(answer);
    }
    public static void bfs(int sr, int sc){
        Queue<Pair> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[H+2][W+2];
        q.offer(new Pair(sr, sc));
        visited[sr][sc] = true;
        while(!q.isEmpty()){
            Pair cur = q.poll();

            for(int i = 0; i<6; i++){
                int nr = cur.r;
                int nc = cur.c;

                if(cur.r %2 == 1){
                    nr += even_dyx[i][0];
                    nc += even_dyx[i][1];
                }
                else {
                    nr += odd_dyx[i][0];
                    nc += odd_dyx[i][1];
                }
                if(nr <0 || nc<0 || nr>=H+2 || nc>=W+2 || visited[nr][nc]) continue;
                if(arr[nr][nc] == 1){
                    answer++;
                }
                else{
                    q.offer(new Pair(nr, nc));
                    visited[nr][nc] = true;
                }
            }
        }
    }
}
//탐색 규칙
// H가 짝수이면 다음(홀수)으로 갈 때 w, w+1까지 가능
// H가 홀수이면 다음(짝수)으로 갈 때 w-1, w까지 가능
// w(바로 아래)로는 모두 가능