import java.io.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int[] coins;
	static int N, FULL_MASK;

	public static int enableBit(int mask, int pos){
		return mask | (1<<pos);
	}

	public static boolean isOn(int mask, int pos){
		return (mask & (1<<pos)) != 0;
	}

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		coins = new int[N];
		for(int i = 0; i<N; i++){
			String str = br.readLine();

			for(int j = 0; j<N; j++){
				if(str.charAt(j) == 'T'){
					coins[i] = enableBit(coins[i], j);
				}
			}
		}

		FULL_MASK = (1<<N) - 1;
		int ans = Integer.MAX_VALUE;

		for (int rowMask = 0; rowMask < (1 << N); rowMask++) {

			int totalT = 0;

			for (int col = 0; col < N; col++) {
				int countT = 0;

				for (int row = 0; row < N; row++) {
					int currentRow = coins[row];

					if (isOn(rowMask, row)) {
						currentRow ^= FULL_MASK;
					}

					if (isOn(currentRow, col)) {
						countT++;
					}
				}

				totalT += Math.min(countT, N - countT);

				if (totalT > ans) break;
			}

			ans = Math.min(ans, totalT);
		}

		System.out.println(ans);
	}
}
//앞은 H 뒤는 T
//T가 최소가 되게