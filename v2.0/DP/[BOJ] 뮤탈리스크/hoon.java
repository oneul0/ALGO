import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int[][][] dp = new int[61][61][61];
	static int[][] damagePermutations = {
		{9, 3, 1}, {9, 1, 3}, {3, 9, 1},
		{3, 1, 9}, {1, 9, 3}, {1, 3, 9}
	};

	static class SCVState {
		int h1, h2, h3;

		public SCVState(int h1, int h2, int h3) {
			this.h1 = Math.max(0, h1);
			this.h2 = Math.max(0, h2);
			this.h3 = Math.max(0, h3);
		}
	}

	public static void main(String[] args) throws IOException {
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());

		int[] initialSCV = new int[3];
		for (int i = 0; i < N; i++) {
			initialSCV[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < 61; i++) {
			for (int j = 0; j < 61; j++) {
				Arrays.fill(dp[i][j], -1);
			}
		}

		System.out.println(bfs(initialSCV));
	}

	public static int bfs(int[] initial) {
		Deque<SCVState> q = new ArrayDeque<>();

		SCVState startState = new SCVState(initial[0], initial[1], initial[2]);
		q.offer(startState);
		dp[startState.h1][startState.h2][startState.h3] = 0;

		while (!q.isEmpty()) {
			SCVState current = q.poll();
			int h1 = current.h1;
			int h2 = current.h2;
			int h3 = current.h3;
			int time = dp[h1][h2][h3];

			if (h1 == 0 && h2 == 0 && h3 == 0) {
				return time;
			}

			for (int[] damage : damagePermutations) {
				SCVState next = new SCVState(
					h1 - damage[0],
					h2 - damage[1],
					h3 - damage[2]
				);

				int next_h1 = next.h1;
				int next_h2 = next.h2;
				int next_h3 = next.h3;

				if (dp[next_h1][next_h2][next_h3] == -1) {
					dp[next_h1][next_h2][next_h3] = time + 1;
					q.offer(next);
				}
			}
		}

		return -1;
	}
}