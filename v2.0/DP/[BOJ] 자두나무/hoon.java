import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int T = Integer.parseInt(st.nextToken());
		int W = Integer.parseInt(st.nextToken());

		int[] arr = new int[T+1];
		for (int i = 1; i <= T; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}

		int[][] dp = new int[T + 1][W + 1];

		if (arr[1] == 1) {
			dp[1][0] = 1;
		} else {
			dp[1][1] = 1;
		}

		for (int i = 2; i <= T; i++) {
			for (int j = 0; j <= W; j++) {
				int cur = (j % 2 == 0) ? 1 : 2;

				dp[i][j] = dp[i-1][j];
				if (arr[i] == cur) {
					dp[i][j]++;
				}

				if (j > 0) {
					int afterMove = dp[i-1][j-1];
					if (arr[i] == cur) {
						afterMove++;
					}
					dp[i][j] = Math.max(dp[i][j], afterMove);
				}
			}
		}

		int maxVal = 0;
		for (int j = 0; j <= W; j++) {
			maxVal = Math.max(maxVal, dp[T][j]);
		}

		System.out.print(maxVal);

	}
}