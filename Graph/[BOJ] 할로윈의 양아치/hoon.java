import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int[] parents;

	public static void union(int a, int b){
		a = find(a);
		b = find(b);

		if(a < b){
			parents[b] = a;
		}
		else{
			parents[a] = b;
		}
	}

	public static int find(int a) {
		if (a == parents[a]) return a;
		return parents[a] = find(parents[a]);
	}

	public static void main(String[] args) throws IOException {
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		int[] children = new int[n+1];

		parents = new int[n+1];
		for(int i = 1; i<=n; i++){
			parents[i] = i;
		}
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i<=n; i++){
			children[i] = Integer.parseInt(st.nextToken());
		}

		for(int i = 0; i < m; i++){
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			union(a, b);
		}

		int[] candies = new int[n+1];
		int[] count = new int[n+1];

		for(int i = 1; i <= n; i++){
			int parent = find(i);
			candies[parent] += children[i];
			count[parent]++;
		}

		int[] dp = new int[k];

		for(int i = 1; i <= n; i++){
			if(count[i] == 0) continue;

			for(int w = k-1; w >= count[i]; w--){
				dp[w] = Math.max(dp[w], dp[w - count[i]] + candies[i]);
			}
		}

		int answer = 0;
		for(int i = 0; i < k; i++){
			answer = Math.max(answer, dp[i]);
		}
		System.out.println(answer);
	}
}
