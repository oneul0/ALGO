import java.io.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static boolean[] cur, goal;
	static int N;
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		cur = new boolean[N];
		goal = new boolean[N];
		String tmp  = br.readLine();
		for(int i = 0; i < N; i++) {
			cur[i] = tmp.charAt(i) != '0';
		}
		tmp = br.readLine();
		for(int i = 0; i < N; i++) {
			goal[i] = tmp.charAt(i) != '0';
		}

		int ans1 = simulation(true);
		int ans2 = simulation(false);
		int ans = Integer.MAX_VALUE;
		if(ans1 != -1) ans = Math.min(ans1, ans);
		if(ans2 != -1) ans = Math.min(ans2, ans);
		System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
	}

	public static int simulation(boolean pushFirst){
		int count = 0;
		boolean[] blubs = cur.clone();
		if(pushFirst) {
			flip(blubs, 0);
			count++;
		}

		for(int i = 1; i < N; i++) {
			if(blubs[i-1] != goal[i-1]) {
				flip(blubs, i);
				count++;
			}
		}

		if(blubs[N-1] != goal[N-1]) return -1;
		return count;
	}

	static void flip(boolean[] bulbs, int idx) {
		for (int i = idx - 1; i <= idx + 1; i++) {
			if (i >= 0 && i < N) {
				bulbs[i] = !bulbs[i];
			}
		}
	}
}
/**
 * 000
 * 010
 * 111
 * 001
 * 010
 * */
//i(1 < i < N)번 스위치를 누르면 i-1, i, i+1의 세 개의 전구의 상태가 바뀐다.