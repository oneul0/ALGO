import java.io.*;
import java.util.*;

public class Main {
    static class Pair {
        int idx;
        double x, y;
        Pair(int idx, double x, double y) {
            this.idx = idx;
            this.x = x;
            this.y = y;
        }
    }

    static class Coord implements Comparable<Coord> {
        int idx;
        double dist;

        Coord(int idx, double dist) {
            this.idx = idx;
            this.dist = dist;
        }

        @Override
        public int compareTo(Coord o) {
            return Double.compare(this.dist, o.dist);
        }
    }

    static List<Pair> stars = new ArrayList<>();
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            stars.add(new Pair(i, x, y));
        }

        System.out.printf("%.2f%n", dijikstra(stars.get(0)));
    }

    public static double dijikstra(Pair start) {
        PriorityQueue<Coord> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[n];

        pq.offer(new Coord(start.idx, 0.0));

        double result = 0.0;
        int count = 0;

        while (!pq.isEmpty()) {
            Coord cur = pq.poll();

            if (visited[cur.idx]) continue;

            visited[cur.idx] = true;
            result += cur.dist;

            if (++count == n) break;

            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    Pair next = stars.get(i);
                    double dist = getDist(stars.get(cur.idx), next);
                    pq.offer(new Coord(next.idx, dist));
                }
            }
        }
        return result;
    }

    public static double getDist(Pair p1, Pair p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }
}