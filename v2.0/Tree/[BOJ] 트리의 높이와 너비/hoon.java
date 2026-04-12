import java.util.*;
import java.io.*;

public class Main {
	static class Node {
		int val;
		Node left, right;
		int x, depth;
		Node(int val) { this.val = val; }
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N;
	static Map<Integer, Node> tree = new HashMap<>();
	static boolean[] isChild;
	static int xCount = 1;
	static int maxDepth = 0;
	static Map<Integer, Integer> minX = new HashMap<>(), maxX = new HashMap<>();

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		isChild = new boolean[N + 1];

		for (int i = 1; i <= N; i++) {
			tree.put(i, new Node(i));
		}

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int val = Integer.parseInt(st.nextToken());
			int left = Integer.parseInt(st.nextToken());
			int right = Integer.parseInt(st.nextToken());
			Node node = tree.get(val);

			if (left != -1) {
				node.left = tree.get(left);
				isChild[left] = true;
			}
			if (right != -1) {
				node.right = tree.get(right);
				isChild[right] = true;
			}
		}

		Node root = null;
		for (int i = 1; i <= N; i++) {
			if (!isChild[i]) {
				root = tree.get(i);
				break;
			}
		}

		inorder(root, 1);

		int maxWidth = 0;
		int level = 1;
		for (int d = 1; d <= maxDepth; d++) {
			if (!minX.containsKey(d)) continue;
			int width = maxX.get(d) - minX.get(d) + 1;
			if (width > maxWidth) {
				maxWidth = width;
				level = d;
			}
		}

		System.out.println(level + " " + maxWidth);
	}

	static void inorder(Node node, int depth) {
		if (node == null) return;
		maxDepth = Math.max(maxDepth, depth);

		inorder(node.left, depth + 1);

		node.x = xCount++;
		node.depth = depth;

		minX.put(depth, Math.min(minX.getOrDefault(depth, Integer.MAX_VALUE), node.x));
		maxX.put(depth, Math.max(maxX.getOrDefault(depth, 0), node.x));

		inorder(node.right, depth + 1);
	}
}
