import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        char[][] map = new char[n][m];
        int rx = 0, ry = 0;
        List<int[]> exits = new ArrayList<>();
        for(int i = 0; i<n; i++){
            String line = br.readLine();
            for(int j = 0; j<m; j++){
                map[i][j] = line.charAt(j);
                if(line.charAt(j) == 'R'){
                    rx = i;
                    ry = j;
                }
                if(line.charAt(j) == 'O'){
                    exits.add(new int[]{i, j});
                }
            }
        }


        int[] dx = {1,0,-1};
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j] = -1;
            }
        }
        dp[rx][ry] = 0;
        for (int y = ry; y < m - 1; y++) {
            for (int x = 0; x < n; x++) {
                if (dp[x][y] == -1) continue;
                if (map[x][y] == '#') continue;

                for (int d = 0; d < 3; d++) {
                    int nx = x + dx[d];
                    int ny = y + 1;
                    if (nx < 0 || nx >= n) continue;
                    if (map[nx][ny] == '#') continue;

                    int gain = (map[nx][ny] == 'C') ? 1 : 0;
                    dp[nx][ny] = Math.max(dp[nx][ny], dp[x][y] + gain);
                }
            }
        }

        int ans = -1;
        for (int[] e : exits) {
            int x = e[0], y = e[1];
            ans = Math.max(ans, dp[x][y]);
        }

        System.out.println(ans);

    }
}
