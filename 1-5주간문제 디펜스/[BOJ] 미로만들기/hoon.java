import java.io.*;
import java.util.*;

public class Main {
    static class Node implements Comparable<Node>{
        int x, y;
        int changed;
        Node(int x, int y, int changed){
            this.x = x;
            this.y = y;
            this.changed = changed;
        }
        @Override
        public int compareTo(Node o){
            return this.changed - o.changed;
        }
    }
    static int n;
    static char[][] maze;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        maze = new char[n][n];
        for(int i = 0; i<n; i++){
            maze[i] = br.readLine().toCharArray();
        }

        System.out.println(bfs(0,0));
    }
    static int[] dx = {-1,1,0,0}, dy={0,0,-1,1};
    public static int bfs(int sx , int sy){
        Deque<Node> pq = new ArrayDeque<>();
        int[][] visited = new int[n][n];
        for(int i = 0; i<n; i++){
            Arrays.fill(visited[i], 1_000_000_000);
        }
        pq.offer(new Node(sx, sy, 0));
        visited[sx][sy] = 0;

        while(!pq.isEmpty()){
            Node cur = pq.poll();
            if(visited[cur.x][cur.y] < cur.changed) continue;
            if(cur.x == n-1 && cur.y == n-1){
                visited[cur.x][cur.y] = cur.changed;
                break;
            }

            for(int i = 0; i<4; i++){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(nx<0 || ny<0 || nx>=n || ny>=n) continue;
                int newChanged = cur.changed;
                if(maze[nx][ny] == '0') newChanged++;
                if(visited[nx][ny] <= newChanged) continue;
                if(maze[nx][ny] == '1') pq.offerFirst(new Node(nx, ny, newChanged));
                else pq.offerLast(new Node(nx, ny, newChanged));
                visited[nx][ny] = newChanged;
            }
        }

        return visited[n-1][n-1];
    }
}
