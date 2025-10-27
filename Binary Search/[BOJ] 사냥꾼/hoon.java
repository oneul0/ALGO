import java.io.*;
import java.util.*;

public class Main {
	static class Pos {
		int x, y;
		Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) throws IOException {

		StringTokenizer st = new StringTokenizer(br.readLine());

		int M = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		int L = Integer.parseInt(st.nextToken());

		int[] shotPos = new int[M];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < M; i++) {
			shotPos[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(shotPos);

		int count = 0;

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());

			if (y > L) continue;

			int left = x - (L - y);
			int right = x + (L - y);

			int idx = lowerBound(shotPos, left);

			if (idx < M && shotPos[idx] <= right) {
				count++;
			}
		}

		System.out.println(count);
	}

	static int lowerBound(int[] arr, int target) {
		int l = 0, r = arr.length;
		while (l < r) {
			int mid = (l + r) / 2;
			if (arr[mid] < target) l = mid + 1;
			else r = mid;
		}
		return l;
	}
}
