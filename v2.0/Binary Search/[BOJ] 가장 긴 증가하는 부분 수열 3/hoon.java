import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		int[] A = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}

		int[] tails = new int[N];
		int size = 0;

		for (int x : A) {
			int pos = lowerBound(tails, 0, size, x);
			if (pos == size) {
				tails[size++] = x;
			} else {
				tails[pos] = x;
			}
		}

		System.out.println(size);
	}

	static int lowerBound(int[] arr, int lo, int hi, int target) {
		int l = lo, r = hi - 1;
		while (l <= r) {
			int mid = (l + r) >>> 1;
			if (arr[mid] < target) l = mid + 1;
			else r = mid - 1;
		}
		return l;
	}
}
