import java.io.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int INF = 1_000_000_000;
	public static void main(String[] args) throws IOException {
		long N = Integer.parseInt(br.readLine());
		int k = Integer.parseInt(br.readLine());
		long left = 1;
		long right = Math.min(INF, N*N);
		while(left<=right){
			long mid = (left+right)>>>1;
			long count = 0;
			for(long i = 1; i<=N; i++){
				count += Math.min(mid/i, N);
			}

			if(count < k){
				left = mid+1;
			}
			else{
				right = mid-1;
			}
		}
		System.out.print(left);
	}

}
