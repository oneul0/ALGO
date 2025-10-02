import java.io.*;
import java.util.*;

public class Main {
	static final int INF = 987654321;
	static int N;
	// cost[i][j] = i번째 작업자가 j번째 일을 할 때 걸리는 시간, 0~N-1
	static int[][] cost;

	// dp[mask] = mask 상태의 일들이 완료되었을 때의 최소 비용 합
	static int[] dp;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());

		int MAX_MASK = (1 << N);
		dp = new int[MAX_MASK];
		cost = new int[N][N];

		Arrays.fill(dp, INF);
		dp[0] = 0;
		StringTokenizer st;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			Arrays.fill(cost[i], INF);

			for (int j = 0; j < N; j++) {
				int time = Integer.parseInt(st.nextToken());
				cost[i][j] = time;
			}
		}

		br.close();

		for (int i = 0; i < N; i++) {
			int[] next_dp = new int[MAX_MASK];
			Arrays.fill(next_dp, INF);

			for (int mask = 0; mask < MAX_MASK; mask++) {
				if (dp[mask] >= INF) continue;

				for (int j = 0; j < N; j++) {
					if ((mask & (1 << j)) == 0) {
						if (cost[i][j] >= INF) continue;
						int new_mask = (mask | (1 << j));
						int new_cost = dp[mask] + cost[i][j];
						next_dp[new_mask] = Math.min(next_dp[new_mask], new_cost);
					}
				}
			}
			dp = next_dp;
		}

		int target_mask = (1 << N) - 1;
		System.out.println(dp[target_mask]);
	}
}