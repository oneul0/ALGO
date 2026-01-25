import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[][] arr;
    static long[][] up, down;
    static final long INF = Long.MIN_VALUE / 2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        up = new long[n][m];
        for (int j = 0; j < m; j++) {
            for (int i = n-1; i >= 0; i--) {
                //시작
                if (i == n-1 && j == 0) {
                    up[i][j] = arr[i][j];
                    continue;
                }

                long fromLeft = (j > 0) ? up[i][j-1] : INF;
                long fromDown = (i < n-1) ? up[i+1][j] : INF;

                up[i][j] = Math.max(fromLeft, fromDown) + arr[i][j];
            }
        }

        down = new long[n][m];
        for (int j = m-1; j >= 0; j--) {
            for (int i = n-1; i >= 0; i--) {
                if (i == n-1 && j == m-1) {
                    down[i][j] = arr[i][j];
                    continue;
                }

                long fromRight = (j < m - 1) ? down[i][j+1] : INF;
                long fromDown = (i < n - 1) ? down[i+1][j] : INF;

                down[i][j] = Math.max(fromRight, fromDown) + arr[i][j];
            }
        }

        long maxScore = INF;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                maxScore = Math.max(maxScore, up[i][j] + down[i][j]);
            }
        }

        System.out.println(maxScore);
    }
}