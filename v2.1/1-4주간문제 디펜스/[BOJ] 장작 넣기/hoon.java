import java.io.*;
import java.util.*;

public class Main {
    static int n, w, t, k;
    static int[] dx = {-1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[] f = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            f[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(play(1, w, -1, f));
    }

    public static int play(int time, int skhPos, int wood, int[] fire) {
        int[] next = fire.clone();

        reduceFire(next, wood);

        if (getFireCount(next) < k) return 0;

        if (time == t) {
            return 1;
        }

        int result = 0;
        for (int d = 0; d < 3; d++) {
            int nx = skhPos + dx[d];
            if (nx < 0 || nx >= n) continue;
            result += play(time + 1, nx, nx, next);
        }


        return result;
    }

    public static int getFireCount(int[] fire) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (fire[i] > 0) count++;
        }
        return count;
    }

    public static void reduceFire(int[] fire, int firewoodPos) {
        int[] tmp = fire.clone();

        for (int i = 0; i < n; i++) {
            if (i == firewoodPos) continue;
            if (fire[i] <= 0) continue;

            int nearBy = 0;
            if (n > 1) {
                if (i > 0 && tmp[i - 1] > 0) nearBy++;
                if (i < n - 1 && tmp[i + 1] > 0) nearBy++;
            }

            fire[i] -= (3 - nearBy);
        }
    }
}
