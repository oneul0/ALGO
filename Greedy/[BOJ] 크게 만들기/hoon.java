import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, K;

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		char[] arr = br.readLine().toCharArray();

		Deque<Character> stack = new ArrayDeque<>();
		int toRemove = K;

		for (char c : arr) {
			while (!stack.isEmpty() && toRemove > 0 && stack.peekLast() < c) {
				stack.pollLast();
				toRemove--;
			}
			stack.addLast(c);
		}

		while (toRemove > 0) {
			stack.pollLast();
			toRemove--;
		}

		StringBuilder sb = new StringBuilder();
		for (char c : stack) sb.append(c);
		System.out.println(sb);
	}
}
//스택을 이용해서 내림차순 유지하도록