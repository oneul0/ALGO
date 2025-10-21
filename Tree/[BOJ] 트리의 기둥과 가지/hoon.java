import java.io.*;
import java.util.*;

public class Main {
	static class Pair {
		int to, len;
		Pair(int to, int len) {
			this.to = to;
			this.len = len;
		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, R;
	static int giga = 0;
	static int trunk = 0;
	static int branch = 0;
	static List<List<Pair>> tree = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		init();
		findGiga(R, 0, 0);
		if(giga != 0) findLongestBranch(giga, 0, 0);
		System.out.println(trunk + " " + branch);
	}

	public static void findGiga(int cur, int parent, int total) {
		int degree = tree.get(cur).size();

		if ((cur == R && degree >= 2) || (cur != R && degree >= 3)) {
			giga = cur;
			trunk = total;
			return;
		}

		if (cur != R && degree == 1) {
			trunk = total;
			return;
		}

		for (Pair next : tree.get(cur)) {
			if (next.to != parent) {
				findGiga(next.to, cur, total + next.len);
				if (giga != 0) return;
			}
		}
	}
	public static void findLongestBranch(int cur, int parent, int total) {
		if(cur != R && tree.get(cur).size() == 1){
			branch = Math.max(branch, total);
			return;
		}

		for (Pair next : tree.get(cur)) {
			if (next.to != parent) {
				findLongestBranch(next.to, cur, total + next.len);
			}
		}
	}

	public static void init() throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		for (int i = 0; i <= N; i++) {
			tree.add(new ArrayList<>());
		}
		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			tree.get(a).add(new Pair(b, d));
			tree.get(b).add(new Pair(a, d));
		}
	}
}
