import java.io.*;
import java.util.*;

public class Main {
    static List<List<Integer>> gr = new ArrayList<>();
    static int[] compliment;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        for (int i = 0; i <= n; i++) {
            gr.add(new ArrayList<>());
        }

        st = new StringTokenizer(br.readLine());
        int root = 0;
        for (int i = 1; i <= n; i++) {
            int parent = Integer.parseInt(st.nextToken());
            if (parent == -1) {
                root = i;
            } else {
                gr.get(parent).add(i);
            }
        }

        compliment = new int[n + 1];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int staff = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());
            compliment[staff] += value;
        }

        dfs(root);

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(compliment[i]).append(" ");
        }
        System.out.println(sb);
    }

    static void dfs(int cur) {
        for (int next : gr.get(cur)) {
            compliment[next] += compliment[cur];
            dfs(next);
        }
    }
}
