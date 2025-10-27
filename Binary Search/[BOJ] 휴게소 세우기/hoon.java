import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M, L;
	static int[] x;
	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		x = new int[N+2];
		x[0] = 0;
		x[N+1] = L;
		if(N>0){
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i<N; i++){
				x[i] = Integer.parseInt(st.nextToken());
			}
		}

		Arrays.sort(x);

		int left = 1;
		int right = L;
		int result = 0;

		while(left <= right){
			int mid = (left+right)/2;
			int count = 0;

			for(int i = 1; i<x.length; i++){
				int distance = x[i] - x[i-1];
				if(distance > mid){
					count += (distance-1)/mid;
				}
			}
			if(count > M){
				left = mid+1;
			}
			else{
				result = mid;
				right = mid-1;
			}

		}
		System.out.println(result);
	}
}
