import java.io.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());

		while (T-- > 0) {
			int N = Integer.parseInt(br.readLine());
			char[] hints = br.readLine().toCharArray();
			String mines = br.readLine();
			int result = 0;
			int[] cnt = new int[N];
			for (int i = 0; i < N; i++) cnt[i] = hints[i] - '0';

			for (int i = 0; i < N; i++) {
				if (i == 0) {
					if (cnt[0] > 0 && cnt[1] > 0) {
						result++;
						cnt[0]--; cnt[1]--;
					}
				} else if (i == N - 1) {
					if (cnt[N-2] > 0 && cnt[N-1] > 0) {
						result++;
						cnt[N-2]--; cnt[N-1]--;
					}
				} else {
					if (cnt[i-1] > 0 && cnt[i] > 0 && cnt[i+1] > 0) {
						result++;
						cnt[i-1]--; cnt[i]--; cnt[i+1]--;
					}
				}
			}

			System.out.println(result);
		}
	}
}



//1,-1 / 1, 1/ 1,0 범위에 지뢰가 있는지
//pq에 넣고 돌릴까
//1인 위치를 찾아야하나
//