import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] inBound = new int[n+1];
        List<List<Integer>> gr = new ArrayList<>();
        for(int i = 0; i<=n; i++){
            gr.add(new ArrayList<>());
        }
        for(int i = 0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int count = Integer.parseInt(st.nextToken());
            if (count > 1) {
                int from = Integer.parseInt(st.nextToken());
                for (int j = 0; j < count - 1; j++) {
                    int to = Integer.parseInt(st.nextToken());
                    gr.get(from).add(to);
                    inBound[to]++;
                    from = to;
                }
            }
        }
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            if (inBound[i] == 0) q.offer(i);
        }

        List<Integer> result = new ArrayList<>();
        while (!q.isEmpty()) {
            int cur = q.poll();
            result.add(cur);

            for (int next : gr.get(cur)) {
                inBound[next]--;
                if (inBound[next] == 0) {
                    q.offer(next);
                }
            }
        }

        if (result.size() != n) {
            System.out.println(0);
        } else {
            StringBuilder sb = new StringBuilder();
            for (int singer : result) {
                sb.append(singer).append("\n");
            }
            System.out.print(sb);
        }


    }
}