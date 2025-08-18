import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N;
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) {
			pq.offer(Integer.parseInt(st.nextToken()));
		}
		int ans = 1;
		while(!pq.isEmpty()) {
			int smallest = pq.peek();
			if(smallest > ans) break;
			pq.remove();
			ans += smallest;
		}
		System.out.println(ans);
	}
}
