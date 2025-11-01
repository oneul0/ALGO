import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		int N = Integer.parseInt(br.readLine());
		int[] A = new int[N];

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
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
		System.out.println(size);
	}

	//이전에 연결된게 내가 연결할 번호보다 작으면 됨
	public static int lowerBound(int[] arr, int start, int end, int target) {
		int l = start, r = end-1;
		while (l <= r) {
			int mid = (l+r) >>> 1;
			if (arr[mid] < target) {
				l = mid + 1;
			}
			else {
				r = mid-1;
			}
		}
		return l;
	}
}
