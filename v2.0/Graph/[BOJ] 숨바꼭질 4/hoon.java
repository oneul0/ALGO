import java.io.*;
import java.util.*;

public class Main {
	static int N, K;
	static int[] dist = new int[100001];
	static int[] parent = new int[100001];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		if (N == K) {
			System.out.println(0);
			System.out.println(N);
			return;
		}

		bfs();

		System.out.println(dist[K]);

		List<Integer> path = new ArrayList<>();
		int current = K;

		while (current != N) {
			path.add(current);
			current = parent[current];
		}
		path.add(N);

		Collections.reverse(path);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < path.size(); i++) {
			sb.append(path.get(i));
			if (i < path.size() - 1) {
				sb.append(" ");
			}
		}
		System.out.println(sb.toString());
	}

	static void bfs() {
		Queue<Integer> queue = new LinkedList<>();
		boolean[] visited = new boolean[100001];

		queue.offer(N);
		visited[N] = true;
		dist[N] = 0;

		while (!queue.isEmpty()) {
			int cur = queue.poll();

			if (cur == K) {
				return;
			}

			int[] next = {cur - 1, cur + 1, cur * 2};

			for (int nextPos : next) {
				if (nextPos >= 0 && nextPos <= 100000 && !visited[nextPos]) {
					visited[nextPos] = true;
					dist[nextPos] = dist[cur] + 1;
					parent[nextPos] = cur;
					queue.offer(nextPos);
				}
			}
		}
	}
}