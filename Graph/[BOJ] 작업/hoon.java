import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) throws IOException {
		int N = Integer.parseInt(br.readLine());

		int[] time = new int[N + 1]; // 작업 시간
		int[] endTime = new int[N + 1]; // 작업 완료 시점
		List<Integer>[] graph = new ArrayList[N + 1];
		int[] indegree = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}
		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			time[i] = Integer.parseInt(st.nextToken());
			int count = Integer.parseInt(st.nextToken());

			for (int j = 0; j < count; j++) {
				int prev = Integer.parseInt(st.nextToken());
				graph[prev].add(i); // prev -> i
				indegree[i]++;
			}
		}

		Queue<Integer> queue = new ArrayDeque<>();

		for (int i = 1; i <= N; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
				endTime[i] = time[i];
			}
		}

		while (!queue.isEmpty()) {
			int current = queue.remove();

			for (int next : graph[current]) {
				indegree[next]--;

				endTime[next] = Math.max(endTime[next], endTime[current] + time[next]);

				if (indegree[next] == 0) {
					queue.offer(next);
				}
			}
		}

		int result = 0;
		for (int i = 1; i <= N; i++) {
			result = Math.max(result, endTime[i]);
		}

		System.out.println(result);
	}
}