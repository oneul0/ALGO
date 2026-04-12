import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N,C;
	static int[] x;
	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		x = new int[N];

		for(int i = 0; i<N; i++){
			x[i] = Integer.parseInt(br.readLine());
		}
		br.close();
		Arrays.sort(x);

		int left = 1, right = x[N-1] - x[0];
		int result = 0;

		while(left <= right){
			int mid = (left + right) / 2;
			int count = 1;
			int last = x[0];

			for(int i = 1; i<N; i++){
				if(x[i] - last >= mid){
					count++;
					last = x[i];
				}
			}
			if(count >= C){
				result = mid;
				left = mid+1;
			}
			else{
				right = mid -1;
			}
		}

		System.out.println(result);
	}
}
