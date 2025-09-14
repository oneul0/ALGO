import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N;
	static char[][] net;
	static int[] parents;

	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		net = new char[N][N];
		parents = new int[N];

		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			for (int j = 0; j < N; j++) {
				net[i][j] = line.charAt(j);
			}
		}

		List<int[]> edges = new ArrayList<>();
		int totalLength = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (net[i][j] != '0') {
					int length = charToLength(net[i][j]);
					edges.add(new int[]{length, i, j});
					totalLength += length;
				}
			}
		}

		edges.sort((a, b) -> a[0] - b[0]);

		for (int i = 0; i < N; i++) {
			parents[i] = i;
		}

		// 크루스칼
		int mstLength = 0;
		int edgeCount = 0;

		for (int[] edge : edges) {
			int weight = edge[0];
			int u = edge[1];
			int v = edge[2];

			if (union(u, v)) {
				mstLength += weight;
				edgeCount++;
				if (edgeCount == N - 1) break;
			}
		}

		if (edgeCount == N - 1) {
			System.out.println(totalLength - mstLength);
		} else {
			System.out.println(-1);
		}
	}

	public static int find(int x) {
		if (parents[x] != x) {
			parents[x] = find(parents[x]);
		}
		return parents[x];
	}

	public static boolean union(int a, int b) {
		a = find(a);
		b = find(b);
		if (a == b) return false;
		parents[b] = a;
		return true;
	}

	public static int charToLength(char c) {
		if (c >= 'a' && c <= 'z') {
			return c - 'a' + 1;
		} else if (c >= 'A' && c <= 'Z') {
			return c - 'A' + 27;
		} else {
			return 0;
		}
	}
}
