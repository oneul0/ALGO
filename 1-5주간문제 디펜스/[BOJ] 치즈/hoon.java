import java.util.*;
import java.io.*;

public class Main {
    static int n, m, count = 0;
    static int[][] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new int[n][m];

        for(int i = 0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j<m; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
                if(arr[i][j] == 1){
                    count++;
                }
            }
        }

        solve(count);
    }
    public static void solve(int cheeseCount){
        int lastCheese = cheeseCount, time = 0;
        while(true){
            time++;
            //찾기
            List<int[]> result = bfs(0, 0);
            cheeseCount -= result.size();
            //만약 없으면 다 녹은거
            if(cheeseCount == 0){
                System.out.print(time +"\n"+lastCheese);
                return;
            }

            //치즈 개수 최신화
            lastCheese = cheeseCount;
            //지우기
            for(int[] pos : result){
                int x = pos[0];
                int y = pos[1];
                arr[x][y] = 0;
            }

        }
    }

    static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    public static List<int[]> bfs(int sx, int sy){
        //찾기
        Queue<int[]> q= new ArrayDeque<>();
        boolean[][] visited = new boolean[n][m];

        q.offer(new int[]{sx, sy});
        visited[sx][sy] = true;

        List<int[]> melt = new ArrayList<>();
        while(!q.isEmpty()){
            int[] cur = q.poll();
            if(arr[cur[0]][cur[1]] == 1) {
                melt.add(new int[]{cur[0], cur[1]});
                continue;
            }
            for(int i = 0; i<4; i++){
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if(nx<0 || ny<0 || nx>=n || ny>=m|| visited[nx][ny]) continue;
                q.offer(new int[]{nx, ny});
                visited[nx][ny] = true;
            }
        }
        return melt;
    }
}
