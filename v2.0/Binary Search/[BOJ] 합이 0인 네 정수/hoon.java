import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int[] A, B, C, D;
	public static void main(String[] args) throws IOException{
		int N = Integer.parseInt(br.readLine());
		A = new int[N];
		B = new int[N];
		C = new int[N];
		D = new int[N];

		StringTokenizer st;
		for(int i = 0; i<N; i++){
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			A[i] = a;
			B[i] = b;
			C[i] = c;
			D[i] = d;
		}
		int range = N*N;
		long[] sumAB = new long[range], sumCD = new long[range];
		int idx = 0;
		for(int i = 0; i<N; i++){
			for(int j = 0; j<N; j++){
				sumAB[idx] = A[i] + B[j];
				sumCD[idx++] = C[i] + D[j];
			}
		}
		Arrays.sort(sumAB);
		Arrays.sort(sumCD);
		long count = 0;
		int p1 = 0;
		int p2 = range - 1;

		while (p1 < range && p2 >= 0) {
			long sum = sumAB[p1] + sumCD[p2];

			if (sum == 0) {
				long abVal = sumAB[p1];
				long cdVal = sumCD[p2];
				long abCnt = 0, cdCnt = 0;

				while (p1 < range && sumAB[p1] == abVal) {
					abCnt++;
					p1++;
				}

				while (p2 >= 0 && sumCD[p2] == cdVal) {
					cdCnt++;
					p2--;
				}

				count += abCnt * cdCnt;
			}
			else if (sum < 0) {
				p1++;
			} else {
				p2--;
			}
		}
		System.out.println(count);
	}
}
