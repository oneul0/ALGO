import java.io.*;
import java.util.*;

public class Main {
    static class Pos{
        int x, y;
        Pos(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode(){
            return Objects.hash(x, y);
        }
        @Override
        public boolean equals(Object o){
            //참조값이 같으면
            if(this == o) return true;
            //동일한 클래스가 아니면
            if(o == null || getClass() != o.getClass()) return false;
            Pos pos = (Pos) o;
            //필드 비교
            return Objects.equals(x, pos.x) && Objects.equals(y, pos.y);
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[][] directions = {
        {0,0},
        // ←, ↖, ↑,
        {0,-1}, {-1,-1}, {-1,0},
        //↗, →, ↘, ↓, ↙
        {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}
    };
    static int[] dx = {-1,1,-1,1}, dy = {-1,1,1,-1};
    static int[][] map;
    static int N, M;
    static Deque<Pos> cloud = new ArrayDeque<>();
    static Set<Pos> lastCloud = new HashSet<>();
    public static void main(String[] args) throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][N];
        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // (N, 1), (N, 2), (N-1, 1), (N-1, 2)에 비구름이 생긴다.
        cloud.offer(new Pos(N-1,0));
        cloud.offer(new Pos(N-1, 1));
        cloud.offer(new Pos(N-2, 0));
        cloud.offer(new Pos(N-2, 1));

        for(int i = 0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            //시뮬레이션 시작
            simulation(d, s);
        }

        //전체 세는 함수
        int ans = 0;
        for(int[] row : map){
            for(int col : row){
                ans += col;
            }
        }
        System.out.println(ans);
        br.close();
    }


    public static void simulation(int d, int s){
        lastCloud.clear();

        //1. 모든 구름이 di 방향으로 si칸 이동한다.
        int qSize = cloud.size();
        while(qSize-- > 0){
            Pos tmp = cloud.remove();
            int nx = (tmp.x + directions[d][0] * s) % N;
            int ny = (tmp.y + directions[d][1] * s) % N;
            if(nx < 0) nx += N;
            if(ny < 0) ny += N;
            cloud.offer(new Pos(nx, ny));
        }


        // 2. 각 구름에서 비가 내려 구름이 있는 칸의 바구니에 저장된 물의 양이 1 증가한다.
        for(Pos pos : cloud){
            map[pos.x][pos.y]++;
        }
        // 3. 구름이 모두 사라진다.
        while(!cloud.isEmpty()){
            lastCloud.add(cloud.remove());
        }
        // 4. 2에서 물이 증가한 칸 (r, c)에 물복사버그 마법을 시전한다. 물복사버그 마법을 사용하면,
        // 대각선 방향으로 거리가 1인 칸에 물이 있는 바구니의 수만큼 (r, c)에 있는 바구니의 물이 양이 증가한다.
        //  이때는 이동과 다르게 경계를 넘어가는 칸은 대각선 방향으로 거리가 1인 칸이 아니다.
        //  예를 들어, (N, 2)에서 인접한 대각선 칸은 (N-1, 1), (N-1, 3)이고, (N, N)에서 인접한 대각선 칸은 (N-1, N-1)뿐이다.
        for(Pos pos : lastCloud){
            int cnt = 0;
            for(int i = 0; i < 4; i++){
                int nx = pos.x + dx[i];
                int ny = pos.y + dy[i];
                if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if(map[nx][ny] > 0) cnt++;
            }
            map[pos.x][pos.y] += cnt;
        }

        // 5. 바구니에 저장된 물의 양이 2 이상인 모든 칸에 구름이 생기고, 물의 양이 2 줄어든다. 이때 구름이 생기는 칸은 3에서 구름이 사라진 칸이 아니어야 한다.
        makeCloud();


    }
    public static void makeCloud(){
        for(int i = 0; i<N; i++){
            for(int j = 0; j<N; j++){
                if(lastCloud.contains(new Pos(i,j))) continue;
                if(map[i][j] >=2){
                    cloud.offer(new Pos(i,j));
                    map[i][j] -= 2;
                }
            }
        }
    }
}