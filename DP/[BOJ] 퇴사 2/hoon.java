import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static class Pair {
		int t, p;
		Pair(int t, int p){
			this.t = t;
			this.p = p;
		}
	}
	public static void main(String[] args) throws IOException {
		int N = Integer.parseInt(br.readLine());
		List<Pair> counsel = new ArrayList<>();
		counsel.add(new Pair(0,0));
		for(int i = 0; i<N; i++){
			String[] s = br.readLine().split(" ");
			counsel.add(new Pair(Integer.parseInt(s[0]), Integer.parseInt(s[1])));
		}
		int[] dp = new int[N+2];
		for(int i = N; i>=1; i--){
			int curTime = counsel.get(i).t;
			int curProfit = counsel.get(i).p;
			int next = i+curTime;

			if(next <= N+1){
				dp[i] = Math.max(dp[i+1], curProfit + dp[next]);
			}
			else{
				dp[i] = dp[i+1];
			}
		}
		System.out.println(dp[1]);
	}
}
