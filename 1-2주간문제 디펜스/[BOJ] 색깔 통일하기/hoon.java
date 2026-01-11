import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        long C = Long.parseLong(st.nextToken());

        int[] origin = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) origin[i] = Integer.parseInt(st.nextToken());

        // 압축
        List<Integer> B = new ArrayList<>();
        List<Integer> pos = new ArrayList<>();
        B.add(origin[0]);
        pos.add(1);
        for (int i = 1; i < N; i++) {
            if (origin[i] != origin[i - 1]) {
                B.add(origin[i]);
                pos.add(i + 1);
            }
        }

        int M = B.size();
        long[] L = new long[M];
        long[] R = new long[M];

        // 왼쪽 흡수 비용 누적합
        for (int i = 1; i < M; i++) {
            long diff = (B.get(i - 1) - B.get(i) + C) % C;
            L[i] = L[i - 1] + diff;
        }

        // 오른쪽 흡수 비용 누적합
        for (int i = M - 2; i >= 0; i--) {
            long diff = (B.get(i + 1) - B.get(i) + C) % C;
            R[i] = R[i + 1] + diff;
        }

        long minPress = Long.MAX_VALUE;
        int btn = -1;

        for (int i = 0; i < M; i++) {
            long curMax = Math.max(L[i], R[i]);

            if (curMax < minPress) {
                minPress = curMax;
                btn = pos.get(i);
            } else if (curMax == minPress) {
                btn = Math.min(btn, pos.get(i));
            }
        }

        System.out.println(btn);
        System.out.println(minPress);
    }
}