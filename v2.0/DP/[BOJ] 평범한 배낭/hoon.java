import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(System.out));
	public static void main(String[] args) throws Exception{
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		ArrayList<Pair> arr = new ArrayList<>();
		for(int i = 0 ; i<N; i++){
			st = new StringTokenizer(br.readLine());
			arr.add(new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		}

		int[][] dp = new int[N+1][K+1];
		for(int i = 1; i<=N; i++){
			int w = arr.get(i-1).W;
			int v = arr.get(i-1).V;
			for(int j = 1; j<=K; j++){
				//현재 최대 무게에 담을 수 없으면
				if(w>j) dp[i][j] = dp[i-1][j]; //이전까지의 최대 가치를 그대로 가져오고
					//담을 수 있다면 w만큼 담을 수 있어야 하므로 현재 최대 무게에서 w만큼 제외한 나머지 담을 수 있는 무게 중
					//최대 가치를 가진 경우를 가져와서 합침
				else dp[i][j] = Math.max(dp[i-1][j], v+dp[i-1][j-w]);
			}
		}
		bw.write(dp[N][K]+"");
		bw.flush();
		br.close();
		bw.close();
	}
}

class Pair {
	int W, V;
	Pair(int W, int V){
		this.W = W;
		this.V = V;
	}
}