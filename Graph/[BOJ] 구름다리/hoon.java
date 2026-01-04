import java.io.*;
import java.util.*;

public class Main {
	static class Edge {
		int u, v;
		Edge(int u, int v) {
			this.u = u;
			this.v = v;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine().trim());

		boolean[][] connected = new boolean[N + 1][N + 1];

		// 기존 트리 간선 입력
		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			connected[a][b] = true;
			connected[b][a] = true;
		}

		List<Edge> added = new ArrayList<>();
		int R;

		if (N <= 4) {
			// case 1: 완전 그래프 (R = 1)
			R = 1;
			for (int i = 1; i <= N; i++) {
				for (int j = i + 1; j <= N; j++) {
					if (!connected[i][j]) {
						added.add(new Edge(i, j));
						connected[i][j] = connected[j][i] = true;
					}
				}
			}
		} else {
			// case 2: 별 그래프 (R = 2)
			R = 2;
			int center = 1;
			for (int v = 2; v <= N; v++) {
				if (!connected[center][v]) {
					added.add(new Edge(center, v));
					connected[center][v] = connected[v][center] = true;
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append(added.size()).append("\n").append(R).append("\n");
		for (Edge e : added) {
			sb.append(e.u).append(" ").append(e.v).append("\n");
		}
		System.out.print(sb);
	}
}


/**
 * 1. 달성할 수 있는 지름의 최저 하한은 1
 * 2. 지름 1이 가능하려면 완전그래프가 필수
 * 3. 예산 제약 때문에 지름 1 가능 여부는 N ≤ 4로 결정됨
 * 4. 지름 1이 불가능하면 최소 지름은 최소 2 이상인데,
 * 5. 별그래프를 만들면 항상 지름 2를 달성 가능 (예산 내)
 * 6. 따라서 **N ≥ 5에서는 최소 지름이 2가 “확정”**이고, 별그래프는 그걸 달성하는 가장 단순한 구성
 *
 * 즉,
 * “다른 복잡한 그래프를 만들어서 지름을 더 줄일 수 있나?”
 * → 줄일 수 있다면 지름 1이어야 하는데, 그건 완전그래프가 필요하고 예산상 불가능.
 * “그럼 지름 2를 달성하는 가장 간단한 확실한 방법은?” → 별그래프
 * */