import java.io.*;
import java.util.*;

public class Main {
    public static class Station {
        int[] prev = new int[1_000_001];
        int[] next = new int[1_000_001];

        public void init(int[] stations) {
            int n = stations.length;
            for (int i = 0; i < n; i++) {
                int curr = stations[i];
                int p = stations[(i - 1 + n) % n];
                int nxt = stations[(i + 1) % n];
                prev[curr] = p;
                next[curr] = nxt;
            }
        }

        public int bn(int i, int j) {
            int target = next[i];
            next[i] = j;
            prev[j] = i;
            next[j] = target;
            prev[target] = j;
            return target;
        }

        public int bp(int i, int j) {
            int target = prev[i];
            next[target] = j;
            prev[j] = target;
            next[j] = i;
            prev[i] = j;
            return target;
        }

        public int cn(int i) {
            int target = next[i];
            int after = next[target];
            next[i] = after;
            prev[after] = i;
            return target;
        }

        public int cp(int i) {
            int target = prev[i];
            int before = prev[target];
            next[before] = i;
            prev[i] = before;
            return target;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] stations = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            stations[i] = Integer.parseInt(st.nextToken());
        }

        Station station = new Station();
        station.init(stations);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            String cmd = st.nextToken();

            switch (cmd) {
                case "BN":
                    sb.append(station.bn(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
                    break;
                case "BP":
                    sb.append(station.bp(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
                    break;
                case "CN":
                    sb.append(station.cn(Integer.parseInt(st.nextToken())));
                    break;
                case "CP":
                    sb.append(station.cp(Integer.parseInt(st.nextToken())));
                    break;
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
}