import java.io.*;
import java.util.*;

public class Main {
	static int[] parent;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		parent = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			parent[i] = i;
		}

		st = new StringTokenizer(br.readLine());
		int truthCount = Integer.parseInt(st.nextToken());
		Set<Integer> truthPeople = new HashSet<>();

		for (int i = 0; i < truthCount; i++) {
			truthPeople.add(Integer.parseInt(st.nextToken()));
		}

		List<List<Integer>> parties = new ArrayList<>();

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int partySize = Integer.parseInt(st.nextToken());

			List<Integer> party = new ArrayList<>();
			for (int j = 0; j < partySize; j++) {
				party.add(Integer.parseInt(st.nextToken()));
			}
			parties.add(party);

			for (int j = 0; j < partySize - 1; j++) {
				union(party.get(j), party.get(j + 1));
			}
		}

		Set<Integer> truthGroup = new HashSet<>();
		for (int truthPerson : truthPeople) {
			int root = find(truthPerson);
			for (int i = 1; i <= N; i++) {
				if (find(i) == root) {
					truthGroup.add(i);
				}
			}
		}

		int answer = 0;
		for (List<Integer> party : parties) {
			boolean canLie = true;
			for (int person : party) {
				if (truthGroup.contains(person)) {
					canLie = false;
					break;
				}
			}
			if (canLie) {
				answer++;
			}
		}

		System.out.println(answer);
	}

	static int find(int x) {
		if (parent[x] != x) {
			parent[x] = find(parent[x]);
		}
		return parent[x];
	}

	static void union(int x, int y) {
		int rootX = find(x);
		int rootY = find(y);

		if (rootX != rootY) {
			parent[rootY] = rootX;
		}
	}
}