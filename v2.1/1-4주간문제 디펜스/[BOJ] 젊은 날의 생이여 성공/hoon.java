import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int[] u = new int[n + 1];
        int[] v = new int[n + 1];

        StringTokenizer st;
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            u[i] = Integer.parseInt(st.nextToken());
            v[i] = Integer.parseInt(st.nextToken());
        }

        int[] minHappyPrefix = new int[n + 2];
        int[] maxTiredPrefix = new int[n + 2];

        int[] maxHappySuffix = new int[n + 2];
        int[] minFatigueSuffix = new int[n + 2];

        minHappyPrefix[0] = Integer.MAX_VALUE;
        maxTiredPrefix[0] = 0;

        maxHappySuffix[n + 1] = 0;
        minFatigueSuffix[n + 1] = Integer.MAX_VALUE;

        for (int i = 1; i <= n; i++) {
            if (u[i] == 0) minHappyPrefix[i] = minHappyPrefix[i - 1];
            else minHappyPrefix[i] = Math.min(minHappyPrefix[i - 1], u[i]);

            if (v[i] == 0) maxTiredPrefix[i] = maxTiredPrefix[i - 1];
            else maxTiredPrefix[i] = Math.max(maxTiredPrefix[i - 1], v[i]);
        }

        for (int i = n; i >= 1; i--) {
            if (u[i] == 0) maxHappySuffix[i] = maxHappySuffix[i + 1];
            else maxHappySuffix[i] = Math.max(maxHappySuffix[i + 1], u[i]);

            if (v[i] == 0) minFatigueSuffix[i] = minFatigueSuffix[i + 1];
            else minFatigueSuffix[i] = Math.min(minFatigueSuffix[i + 1], v[i]);
        }

        int ans = -1;

        for (int k = n - 1; k >= 1; k--) {
            if (minHappyPrefix[k] > maxHappySuffix[k + 1] &&
                maxTiredPrefix[k] < minFatigueSuffix[k + 1]) {
                ans = k;
                break;
            }
        }

        System.out.println(ans);
    }
}