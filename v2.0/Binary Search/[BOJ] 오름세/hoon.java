import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		String tmp;
		while ((tmp = br.readLine()) != null && !tmp.isBlank()) {
			int N = Integer.parseInt(tmp.trim());
			int[] A = new int[N];
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				A[i] = Integer.parseInt(st.nextToken());
			}

			int[] tails = new int[N];
			int size = 0;
			for (int i = 0; i < N; i++) {
				int pos = bs(tails, size, A[i]);
				if (pos == size) tails[size++] = A[i];
				else tails[pos] = A[i];
			}

			System.out.println(size);
		}
	}

	static int bs(int[] arr, int right, int target){
		int l = 0, r = right-1;
		while(l<=r){
			int mid = (l+r) >>> 1;
			if(arr[mid] < target) l = mid+1;
			else r = mid-1;
		}
		return l;
	}
}
