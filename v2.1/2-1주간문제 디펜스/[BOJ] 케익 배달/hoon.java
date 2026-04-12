import java.io.*;
import java.util.*;

public class Main {
    static final long INF = 987654321;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int sx = Integer.parseInt(st.nextToken());
        int sy = Integer.parseInt(st.nextToken());
        int[] dx = {0, -1, 1, 0, 0}, dy = {0, 0, 0, -1, 1};
        // DP 테이블: dp[2][5] (현재 단계와 이전 단계만 저장)
        long[][] dp = new long[n + 1][5]; //dist
        int[][] pointsX = new int[n + 1][5]; //최소인 지점의 x 좌표
        int[][] pointsY = new int[n + 1][5]; //최소인 지점의 y 좌표

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int cx = Integer.parseInt(st.nextToken());
            int cy = Integer.parseInt(st.nextToken());
            for (int d = 0; d < 5; d++) {
                pointsX[i][d] = cx + dx[d];
                pointsY[i][d] = cy + dy[d];
                dp[i][d] = INF;
            }
        }

        for(int d = 0; d < 5; d++) {
            dp[1][d] = getDist(sx, sy, pointsX[1][d], pointsY[1][d]);
        }

        for(int i = 2; i <= n; i++) {
            for(int cur = 0; cur < 5; cur++) {
                dp[i][cur] = Long.MAX_VALUE;
                for(int prev = 0; prev < 5; prev++) {
                    long d = getDist(pointsX[i-1][prev], pointsY[i-1][prev], pointsX[i][cur], pointsY[i][cur]);
                    dp[i][cur] = Math.min(dp[i][cur], dp[i-1][prev] + d);
                }
            }
        }
        long answer = Arrays.stream(dp[n]).min().orElse(0);
        System.out.println(answer);
    }
    public static long getDist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
