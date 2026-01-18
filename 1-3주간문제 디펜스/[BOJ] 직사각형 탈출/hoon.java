import java.io.*;
import java.util.*;

public class Main {
    public static class Pair{
        int leftTopX, leftTopY;
        Pair(int ltx, int lty){
            this.leftTopX = ltx;
            this.leftTopY = lty;
        }
    }
    static int n,m,h,w,sx,sy, fx,fy;
    static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    static int[][] wallsInRange;
    static int[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        wallsInRange = new int[n+1][m+1];

        for(int i = 1; i<=n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j<=m; j++){
                int cur = Integer.parseInt(st.nextToken());
                wallsInRange[i][j] = wallsInRange[i-1][j] + wallsInRange[i][j-1]
                    - wallsInRange[i-1][j-1] + cur;
            }
        }
        st = new StringTokenizer(br.readLine());
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        sx = Integer.parseInt(st.nextToken());
        sy = Integer.parseInt(st.nextToken());

        fx = Integer.parseInt(st.nextToken());
        fy = Integer.parseInt(st.nextToken());

        System.out.println(solve());
    }

    public static boolean canMove(int x, int y){
        if(x < 1 || y < 1 || x + h - 1 > n || y + w - 1 > m) return false;

        int x1 = x;
        int y1 = y;
        int x2 = x + h -1;
        int y2 = y + w - 1;

        int wallCount = wallsInRange[x2][y2] - wallsInRange[x1-1][y2]
            - wallsInRange[x2][y1-1] + wallsInRange[x1-1][y1-1];

        return wallCount == 0;
    }

    public static int solve(){
        Queue<Pair> q = new ArrayDeque<>();
        visited = new int[n+1][m+1];
        q.offer(new Pair(sx, sy));
        visited[sx][sy] = 1;
        while(!q.isEmpty()){
            Pair cur = q.poll();
            if(cur.leftTopX == fx && cur.leftTopY == fy){
                return visited[cur.leftTopX][cur.leftTopY]-1;
            }

            for(int i = 0; i<4; i++){
                int nx = cur.leftTopX + dx[i];
                int ny = cur.leftTopY + dy[i];

                //범위 체크
                if(nx<0||ny<0||nx>n||ny>m) continue;
                if(visited[nx][ny] > 0) continue;
                //벽이 있는지
                if(!canMove(nx, ny)) continue;

                if(nx == fx && ny == fy) return visited[cur.leftTopX][cur.leftTopY];
                visited[nx][ny] = visited[cur.leftTopX][cur.leftTopY] + 1;
                q.offer(new Pair(nx, ny));

            }
        }

        return -1;
    }
}
