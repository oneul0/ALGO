import java.io.*;
import java.util.*;

public class Main {

	static class Ingredient {
		long S, L;
		boolean optional; // true = O_i = 1 (제거 가능)
		Ingredient(long S, long L, long O) {
			this.S = S;
			this.L = L;
			this.optional = (O == 1);
		}
	}
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, K;
	static long G;
	static Ingredient[] arr;

	// x일 후 먹을 수 있는지 판단
	public static boolean check(long x) {
		long sum = 0;
		List<Long> optionalList = new ArrayList<>();

		for (Ingredient in : arr) {
			long bacteria = in.S * Math.max(1, x - in.L);

			if (!in.optional) {
				sum += bacteria;
				if (sum > G) return false;
			} else {
				optionalList.add(bacteria);
			}
		}

		// 중요하지 않은 재료 -> 세균 많은 순으로 제거
		optionalList.sort(Collections.reverseOrder());

		for (int i = K; i < optionalList.size(); i++) {
			sum += optionalList.get(i);
			if (sum > G) return false;
		}

		return sum <= G;
	}

	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		G = Long.parseLong(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		arr = new Ingredient[N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			long S = Long.parseLong(st.nextToken());
			long L = Long.parseLong(st.nextToken());
			long O = Long.parseLong(st.nextToken());
			arr[i] = new Ingredient(S, L, O);
		}

		long left = 0;
		long right = 2_000_000_000L;
		long answer = 0;

		while (left <= right) {
			long mid = (left + right) / 2;

			if (check(mid)) {
				answer = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}

		System.out.println(answer);
	}
}
