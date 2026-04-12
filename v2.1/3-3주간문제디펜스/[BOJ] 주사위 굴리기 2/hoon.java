import java.io.*;
import java.util.*;

public class Main {

    static class Dice {
        int top;
        int bottom;
        int north;
        int south;
        int west;
        int east;
        Dice(){
            this.top = 1;
            this.bottom = 6;
            this.south = 5;
            this.north = 2;
            this.west = 4;
            this.east = 3;
        }

        // dir: 0동, 1남, 2서, 3북
        public void roll(int dir) {
            int temp = top;

            if (dir == 0) {
                top = west;
                west = bottom;
                bottom = east;
                east = temp;
            } else if (dir == 1) {
                top = north;
                north = bottom;
                bottom = south;
                south = temp;
            } else if (dir == 2) {
                top = east;
                east = bottom;
                bottom = west;
                west = temp;
            } else if (dir == 3) {
                top = south;
                south = bottom;
                bottom = north;
                north = temp;
            }
        }
    }

    static int N, M, K;
    static int[][] arr;

    static int[] dx = {0,1,0,-1}, dy = {1,0,-1,0}; //동 남 서 북
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        arr = new int[N][M];

        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j<M; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        Dice dice = new Dice();
        int x = 0, y = 0;
        int dir = 0;
        int score = 0;

        for(int i = 0; i<K; i++){
            int nx = x+ dx[dir];
            int ny = y+dy[dir];

            if(nx < 0 || ny<0 || nx>=N || ny>=M){
                dir = (dir+2)%4;
                nx = x + dx[dir];
                ny = y + dy[dir];
            }

            dice.roll(dir);
            x = nx;
            y = ny;

            int A = dice.bottom;
            int B = arr[x][y];

            score += (getCount(x, y)*B);

            if(A>B){
                dir = (dir+1) % 4;
            }
            else if(A<B){
                dir = (dir+3) % 4;
            }
        }
        System.out.println(score);
    }

    public static int getCount(int sx, int sy){
        Queue<int[]> q=new ArrayDeque<>();
        boolean[][] chk = new boolean[N][M];
        q.offer(new int[]{sx, sy});
        chk[sx][sy] = true;
        int C = 1;
        while(!q.isEmpty()){
            int[] cur = q.poll();
            for(int i = 0; i<4; i++){
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                if(nx < 0 || ny<0 || nx>=N || ny>=M) continue;
                if(chk[nx][ny]) continue;
                if(arr[nx][ny] == arr[sx][sy]){
                    q.offer(new int[]{nx, ny});
                    chk[nx][ny] = true;
                    C++;
                }
            }
        }
        return C;
    }
}
