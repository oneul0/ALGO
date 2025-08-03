import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static Map<Integer, HashSet<Integer>> tree = new HashMap<>();
	static int[][] dp;
	static boolean[] visited;
	static int[] populations;
	static int N;

	public static void main(String[] args) throws IOException {
		init();

		dfs(1);

		System.out.println(Math.max(dp[1][0], dp[1][1]));
	}

	static void dfs(int node) {
		visited[node] = true;
		dp[node][0] = 0;
		dp[node][1] = populations[node];

		for (int child : tree.get(node)) {
			if (!visited[child]) {
				dfs(child);
				dp[node][0] += Math.max(dp[child][0], dp[child][1]);
				dp[node][1] += dp[child][0];
			}
		}
	}


	public static void init() throws IOException {
		N = Integer.parseInt(br.readLine());
		populations = new int[N + 1];
		dp = new int[N+1][2];
		visited = new boolean[N+1];

		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			populations[i] = Integer.parseInt(st.nextToken());
			tree.putIfAbsent(i, new HashSet<>());
		}
		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			tree.get(a).add(b);
			tree.get(b).add(a);
		}
	}
}
