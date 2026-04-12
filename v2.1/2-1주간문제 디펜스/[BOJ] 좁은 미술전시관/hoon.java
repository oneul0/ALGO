import java.util.*;
import java.io.*;

public class Main {
    static final int INF = 1000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            if (N == 0 && K == 0) break;

            int[][] gallery = new int[N + 1][2];
            for (int i = 1; i <= N; i++) {
                st = new StringTokenizer(br.readLine());
                gallery[i][0] = Integer.parseInt(st.nextToken());
                gallery[i][1] = Integer.parseInt(st.nextToken());
            }

            // dp[행][상태][닫은 방 개수]
            // 상태 0: 둘다 오픈, 1: 왼쪽 닫음, 2: 오른쪽 닫음
            int[][][] dp = new int[N + 1][3][K + 1];
            for (int i = 0; i <= N; i++) {
                for (int j = 0; j < 3; j++) {
                    Arrays.fill(dp[i][j], -INF);
                }
            }

            dp[0][0][0] = 0;

            for (int i = 1; i <= N; i++) {
                for (int j = 0; j <= K; j++) {
                    // 0: 둘 다 열림
                    dp[i][0][j] = Math.max(dp[i-1][0][j], Math.max(dp[i-1][1][j], dp[i-1][2][j]))
                        + gallery[i][0] + gallery[i][1];

                    if (j > 0) {
                        // 왼쪽 닫음 (이전 행은 둘 다 열렸거나 왼쪽이 닫혀야 함)
                        dp[i][1][j] = Math.max(dp[i-1][0][j-1], dp[i-1][1][j-1]) + gallery[i][1];

                        // 오른쪽 닫음
                        dp[i][2][j] = Math.max(dp[i-1][0][j-1], dp[i-1][2][j-1]) + gallery[i][0];
                    }
                }
            }

            int answer = Math.max(dp[N][0][K], Math.max(dp[N][1][K], dp[N][2][K]));
            System.out.println(answer);
        }
    }
}