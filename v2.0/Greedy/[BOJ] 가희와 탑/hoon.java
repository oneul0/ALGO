import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int a = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());

		if (a + b > N + 1) {
			System.out.println(-1);
			return;
		}

		List<Integer> H = new ArrayList<>(N);

		// 왼쪽 증가 부분 (1 ~ a-1)
		for (int i = 1; i < a; i++) {
			H.add(i);
		}

		// peak
		H.add(Math.max(a, b));

		// 오른쪽 감소 부분 (b-1 ~ 1)
		for (int i = b - 1; i >= 1; i--) {
			H.add(i);
		}

		// padding
		int needed = N - H.size();

		if (needed > 0) {
			List<Integer> padding = new ArrayList<>(Collections.nCopies(needed, 1));

			if (a == 1) {
				H.addAll(1, padding);
			} else {
				H.addAll(0, padding);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int x : H) sb.append(x).append(" ");
		System.out.println(sb);
	}
}
