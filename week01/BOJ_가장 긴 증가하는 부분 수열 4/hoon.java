import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i<N; i++){
			arr[i] = Integer.parseInt(st.nextToken());
		}

		int[] dp = new int[N];
		int[] prev = new int[N];
		int maxLen = 0;
		int maxIdx = 0;

		for(int i = 0; i<N; i++){
			dp[i] = 1;
			prev[i] = -1;
			for(int j = 0; j<i; j++){
				if(arr[i] > arr[j] && dp[i] < dp[j] + 1){
					dp[i] = dp[j]+1;
					prev[i] = j;
				}
			}

			if(dp[i] > maxLen){
				maxLen = dp[i];
				maxIdx = i;
			}
		}

		System.out.println(maxLen);
		Deque<Integer> stack = new ArrayDeque<>();
		int curIdx = maxIdx;
		while(curIdx != -1){
			stack.push(arr[curIdx]);
			curIdx = prev[curIdx];
		}

		while(!stack.isEmpty()){
			System.out.print(stack.pop()+" ");
		}
	}
}