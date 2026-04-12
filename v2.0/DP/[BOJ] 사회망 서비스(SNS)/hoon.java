import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static ArrayList<ArrayList<Integer>> gr = new ArrayList<>();
	static int N;
	static int[][] dp;
	static boolean[] visited;
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		dp = new int[N+1][2];
		visited = new boolean[N+1];
		for(int i = 0; i<=N; i++){
			gr.add(new ArrayList<>());
		}
		StringTokenizer st;
		for(int i = 0; i<N-1; i++){
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			gr.get(a).add(b);
			gr.get(b).add(a);
		}

		dfs(1);
		System.out.println(Math.min(dp[1][0], dp[1][1]));
	}
	public static void dfs(int cur){
		visited[cur] = true;
		dp[cur][0] = 0;
		dp[cur][1] = 1;
		for(int child : gr.get(cur)){
			if(!visited[child]){
				dfs(child);
				dp[cur][0] += dp[child][1];
				dp[cur][1] += Math.min(dp[child][0], dp[child][1]);
			}
		}
	}
}


//리프 노드에서 부모가 얼리어답터인 경우
//루트 노드에서 자식이 모두 얼리어답터인 경우
//중간 노드에서 부모와 자식 모두 얼리어답터인 경우