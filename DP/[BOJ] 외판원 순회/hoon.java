import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static final int MAX_N = 16, MAX_V = 65536, INF = Integer.MAX_VALUE;
	static int N;
	static long[][] W = new long[MAX_N][MAX_N], dp = new long[MAX_N][MAX_V];

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				W[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for(int i = 0; i < N; i++) {
			Arrays.fill(dp[i], -1);
		}
		System.out.println(tsp(0,1));
	}

	public static long tsp(int here, int status){
		if(status == (1<<N)-1) return (W[here][0] != 0) ? W[here][0] : INF;
		if(dp[here][status] != -1) return dp[here][status];

		dp[here][status] = INF;
		for(int next = 0; next < N; next++){
			if(!hasVisited(status, next) && W[here][next] != 0){
				long newCost = tsp(next, visit(status, next)) + W[here][next];
				if(newCost < dp[here][status]){
					dp[here][status] = newCost;
				}
			}
		}
		return dp[here][status];
	}

	public static boolean hasVisited(int bits, int city){
		return (bits & (1 << city)) != 0;
	}

	public static int visit(int bits, int city){
		return bits | (1 << city);
	}
}
//https://velog.io/@vantaa89/%EC%99%B8%ED%8C%90%EC%9B%90-%EC%88%9C%ED%9A%8C-%EB%AC%B8%EC%A0%9CTraveling-Salesman-Problem-TSP
//https://velog.io/@jxlhe46/%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-%EC%99%B8%ED%8C%90%EC%9B%90-%EC%88%9C%ED%9A%8C-%EB%AC%B8%EC%A0%9C