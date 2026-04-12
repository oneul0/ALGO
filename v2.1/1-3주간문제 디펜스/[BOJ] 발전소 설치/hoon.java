import java.io.*;
import java.util.*;

public class Main {
    public static class Coords {
        int x, y;
        Coords(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static class Node {
        int idx;
        double dist;
        Node(int idx, double dist){
            this.idx = idx;
            this.dist = dist;
        }
    }
    static int n, w;
    static double m;
    static List<Coords> points = new ArrayList<>();
    static boolean[][] isConnected;

    public static int dijikstra(){
        int start = 1;
        PriorityQueue<Node> pq = new PriorityQueue<>(
            (a, b) -> Double.compare(a.dist, b.dist)
        );
        double[] dists = new double[n+1];
        Arrays.fill(dists, Double.MAX_VALUE);
        dists[start] = 0;
        pq.offer(new Node(start, 0.0));

        while(!pq.isEmpty()){
            Node cur = pq.poll();
            if (dists[cur.idx] < cur.dist) continue;
            if(cur.idx == n) return (int)(dists[n]*1000);

            Coords curCoords = points.get(cur.idx);
            for(int next = 1; next <=n; next++){
                if(cur.idx == next) continue;
                Coords nextCoords = points.get(next);
                double newDist = cur.dist;

                if(!isConnected[cur.idx][next]){
                    double wireLen = Math.hypot((nextCoords.x - curCoords.x), (nextCoords.y - curCoords.y));
                    if(wireLen > m) continue;
                    newDist += wireLen;
                }
                if(dists[next] > newDist){
                    pq.offer(new Node(next, newDist));
                    dists[next] = newDist;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        isConnected = new boolean[n+1][n+1];

        m = Double.parseDouble(br.readLine());

        points.add(new Coords(0,0)); //padding

        for(int i = 1; i<=n; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            points.add(new Coords(x, y));
        }

        for(int i =1; i<=w; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            isConnected[x][y] = true;
            isConnected[y][x] = true;
        }
        br.close();
        System.out.println(dijikstra());
    }
}

// 3 1
// 200000.0
// -100000 -100000
// 100000 0
// 100000 100000
// 1 2