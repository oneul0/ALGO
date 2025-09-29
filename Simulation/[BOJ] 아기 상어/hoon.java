import java.io.*;
import java.util.*;

public class Main {
    static class Shark {
        int x, y;
        int size;
        int eaten;
        Shark(int x, int y){
            this.x = x;
            this.y = y;
            this.size = 2;
            this.eaten = 0;
        }
        public void eatFish(){
            this.eaten++;
            if (this.eaten == this.size) {
                this.size++;
                this.eaten = 0;
            }
        }
    }

    static class Fish implements Comparable<Fish>{
        int x, y;
        int dist;
        Fish(int x, int y, int dist){
            this.x =x;
            this.y = y;
            this.dist = dist;
        }
        @Override
        public int compareTo(Fish o){
            if(this.dist != o.dist) return this.dist - o.dist;
            if(this.x != o.x) return this.x - o.x;
            return this.y - o.y;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[][] space;
    static int[] dx = {-1,0,1,0}, dy = {0,-1,0,1};
    static Shark baby;
    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        space = new int[N][N];
        StringTokenizer st;
        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j<N; j++){
                space[i][j] = Integer.parseInt(st.nextToken());
                if(space[i][j] == 9) {
                    space[i][j] = 0;
                    baby = new Shark(i, j);
                }
            }
        }
        int answer = 0;
        while(true){
            Fish target = findFish();
            if(target == null) break;

            answer += target.dist;
            baby.x = target.x;
            baby.y = target.y;
            baby.eatFish();
            space[target.x][target.y] = 0;
        }
        System.out.println(answer);

    }

    public static Fish findFish(){
        Deque<int[]> q = new ArrayDeque<>();
        int[][] dist = new int[N][N];

        for(int i = 0; i<N; i++) Arrays.fill(dist[i], -1);

        q.offer(new int[]{baby.x, baby.y, 0});
        dist[baby.x][baby.y] = 0;
        List<Fish> results = new ArrayList<>();
        int mindist = Integer.MAX_VALUE;

        while (!q.isEmpty()){
            int[] cur = q.remove();
            if(cur[2] >= mindist) continue;

            for(int i = 0; i<4; i++){
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if(nx<0||ny<0||nx>=N||ny>=N||dist[nx][ny] != -1) continue;
                if(space[nx][ny] > baby.size) continue;
                dist[nx][ny] = cur[2]+1;
                q.offer(new int[]{nx,ny,cur[2]+1});

                if(space[nx][ny] != 0 && space[nx][ny] < baby.size){
                    results.add(new Fish(nx,ny,cur[2]+1));
                    mindist = Math.min(mindist, cur[2]+1);
                }
            }
        }
        if(results.isEmpty()) return null;
        Collections.sort(results);

        return results.get(0);
    }
}
