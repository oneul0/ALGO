import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static final int MAX_N = 10;
	static final int MAX_M = 2000;

	static long[][] dp = new long[MAX_N + 1][MAX_M + 1];

	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for (int j = 1; j <= MAX_M; j++) {
			dp[1][j] = 1;
		}

		for (int i = 2; i <= MAX_N; i++) {
			long[] prefixSum = new long[MAX_M + 1];

			for (int k = 1; k <= MAX_M; k++) {
				prefixSum[k] = prefixSum[k-1] + dp[i-1][k];
			}

			for (int j = 1; j <= MAX_M; j++) {
				if (j / 2 >= 1) {
					dp[i][j] = prefixSum[j / 2];
				} else {
					dp[i][j] = 0;
				}
			}
		}

		while (T-- > 0) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());

			long ans = 0;
			for (int j = 1; j <= M; j++) {
				ans += dp[N][j];
			}

			System.out.println(ans);
		}
	}
}