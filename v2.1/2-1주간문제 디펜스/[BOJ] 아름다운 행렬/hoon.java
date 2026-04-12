import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());
        int[][] arr = new int[N+1][N+1];
        StringTokenizer st;
        for(int i = 1; i<=N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j= 1; j<=N; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] p = new int[N + 2][N + 2]; // 주 대각선 ( \ )
        int[][] s = new int[N + 2][N + 2]; // 부 대각선 ( / )

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                p[i][j] = p[i - 1][j - 1] + arr[i][j];
                s[i][j] = s[i - 1][j + 1] + arr[i][j];
            }
        }

        int maxBeauty = Integer.MIN_VALUE;

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                for (int k = 1; i + k <= N && j + k <= N; k++) {
                    int rowEnd = i + k;
                    int colEnd = j + k;

                    int pSum = p[rowEnd][colEnd] - p[i - 1][j - 1];

                    int sSum = s[rowEnd][j] - s[i - 1][colEnd + 1];

                    maxBeauty = Math.max(maxBeauty, pSum - sSum);
                }
            }
        }
        System.out.println(maxBeauty);
    }
}
