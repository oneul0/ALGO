import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException{
		int N = Integer.parseInt(br.readLine());
		int[] A = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i<N; i++){
			A[i] = Integer.parseInt(st.nextToken());
		}

		int[] tails = new int[N];
		int size = 0;

		for(int i = 0; i<N; i++){
			int pos = lowerBound(tails, 0, size, A[i]);
			if(pos == size){
				tails[size++] = A[i];
			}
			else{
				tails[pos] = A[i];
			}
		}

		System.out.println(N-size);

	}

	static int lowerBound(int[] arr, int low, int high, int target){
		int left = low, right = high-1;
		while (left <= right){
			int mid = (left + right) >>> 1;
			if(arr[mid] < target){
				left = mid+1;
			}
			else{
				right = mid-1;
			}
		}

		return left;
	}
}
//lis 구하고 N-lis