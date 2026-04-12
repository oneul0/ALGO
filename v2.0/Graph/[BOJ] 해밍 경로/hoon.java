import java.io.*;
import java.util.*;

public class Main {

	static int N, K, M;
	static int[] codes;
	static int[] prev;

	static void bfs() {
		Queue<Integer> q = new ArrayDeque<>();
		boolean[] visited = new boolean[N + 1];
		prev = new int[N + 1];
		Arrays.fill(prev, -1);

		Map<Integer, Integer> indexMap = new HashMap<>();
		for (int i = 1; i <= N; i++) {
			indexMap.put(codes[i], i);
		}

		q.add(1);
		visited[1] = true;
		prev[1] = 1;

		while (!q.isEmpty()) {
			int cur = q.poll();

			for (int bit = 0; bit < K; bit++) {
				int flipped = codes[cur] ^ (1 << bit);

				if(!indexMap.containsKey(flipped)) continue;
				int next = indexMap.get(flipped);
				if (visited[next]) continue;

				visited[next] = true;
				prev[next] = cur;
				q.add(next);
			}
		}
	}

	static List<Integer> getPath(int target) {
		if (prev[target] == -1) return Collections.emptyList();

		List<Integer> path = new ArrayList<>();
		for (int v = target; v != 1; v = prev[v]) {
			path.add(v);
		}
		path.add(1);
		Collections.reverse(path);
		return path;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());


		codes = new int[N + 1];

		codes[0] = -1;
		for (int i = 1; i <= N; i++) {
			codes[i] = Integer.parseInt(br.readLine(), 2);
		}

		//미리 경로를 계산하고
		//받은 입력을 저장되어 있던 경로 출력
		//bfs가 최단거리를 보장한다는 가정 아래 작성
		//최단거리이므로 겹치는 중간 경로는 최적
		//캐싱해놓는다면 시간을 줄일 수 있을 것
		M = Integer.parseInt(br.readLine());
		bfs();

		for (int i = 0; i < M; i++) {
			int j = Integer.parseInt(br.readLine());
			List<Integer> path = getPath(j);
			if(path.isEmpty()){
				System.out.print(-1);
			}

			StringBuilder sb = new StringBuilder();
			for (int p : path) {
				// System.out.print(p + " ");
				sb.append(p).append(" ");
			}
			System.out.println(sb);
		}
	}
}
