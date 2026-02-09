import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[][] gr = new int[N + 1][2];
        int[] inbound = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            gr[i][0] = a;
            gr[i][1] = b;
            inbound[a]++;
            inbound[b]++;
        }

        boolean[] cantJoin = new boolean[N + 1];
        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 1; i <= N; i++) {
            if (inbound[i] < 2) {
                cantJoin[i] = true;
                q.offer(i);
            }
        }

        int removedCount = 0;
        while (!q.isEmpty()) {
            int cur = q.poll();
            removedCount++;

            for (int next : gr[cur]) {
                if (cantJoin[next]) continue;

                inbound[next]--;
                if (inbound[next] < 2) {
                    cantJoin[next] = true;
                    q.offer(next);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        int answerSize = N - removedCount;
        sb.append(answerSize).append("\n");

        if (answerSize > 0) {
            for (int i = 1; i <= N; i++) {
                if (!cantJoin[i]) {
                    sb.append(i).append(" ");
                }
            }
        }
        System.out.println(sb.toString().trim());
    }
}