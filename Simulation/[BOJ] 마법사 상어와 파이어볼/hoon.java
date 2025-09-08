import java.io.*;
import java.util.*;

public class Main {
    static class FireBall implements Comparable<FireBall> {
        int r, c, m, s,d;
        FireBall(int r, int c, int m, int s, int d){
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
        }
        @Override
        public int compareTo(FireBall o){
            if(this.r == o.r) return this.c-o.c;
            return this.r - o.r;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, M, K;
    static Deque<FireBall> balls = new ArrayDeque<>();
    static Deque<FireBall>[][] map;
    static int[][] directions = {
        {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}
    };
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        map = new ArrayDeque[N][N];
        for(int i = 0; i<N; i++){
            for(int j = 0; j<N; j++){
                map[i][j] = new ArrayDeque<>();
            }
        }
        for(int i = 0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            balls.offer(new FireBall(
                Integer.parseInt(st.nextToken())-1,
                Integer.parseInt(st.nextToken())-1,
                Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken())
            ));
        }
        for(int i = 0; i<K; i++){
            simulation();
        }
        int ans = 0;
        for(FireBall ball : balls){
            ans += ball.m;
        }
        System.out.println(ans);
        br.close();
    }
    public static void simulation(){
        //1. 모든 파이어볼이 자신의 방향 di로 속력 si칸 만큼 이동한다.
        // 이동하는 중에는 같은 칸에 여러 개의 파이어볼이 있을 수도 있다.
        while(!balls.isEmpty()){
            FireBall cur = balls.remove();

            int nr = (cur.r + directions[cur.d][0] * (cur.s % N) + N) % N;
            int nc = (cur.c + directions[cur.d][1] * (cur.s % N) + N) % N;

            map[nr][nc].offer(new FireBall(nr, nc, cur.m, cur.s, cur.d));
        }
        //2. 이동이 모두 끝난 뒤, 2개 이상의 파이어볼이 있는 칸에서는 다음과 같은 일이 일어난다.
        // -같은 칸에 있는 파이어볼은 모두 하나로 합쳐진다.
        for(int i = 0; i<N; i++){
            for(int j = 0; j<N; j++){
                if(map[i][j].isEmpty()) continue;
                if(map[i][j].size() <= 1){
                    balls.offer(map[i][j].remove());
                    continue;
                }

                int mSum = 0, sSum = 0, odd = 0, even = 0;
                int size = map[i][j].size();
                while(!map[i][j].isEmpty()){
                    FireBall cur = map[i][j].remove();
                    mSum += cur.m;
                    sSum += cur.s;
                    if(cur.d % 2 == 0) even++;
                    else odd++;
                }

                // --질량은 ⌊(합쳐진 파이어볼 질량의 합)/5⌋이다.
                mSum /= 5;
                // --질량이 0인 파이어볼은 소멸되어 없어진다.
                if(mSum == 0) continue;
                // --속력은 ⌊(합쳐진 파이어볼 속력의 합)/(합쳐진 파이어볼의 개수)⌋이다.
                sSum /= size;
                // --합쳐지는 파이어볼의 방향이 모두 홀수이거나 모두 짝수이면, 방향은 0, 2, 4, 6이 되고, 그렇지 않으면 1, 3, 5, 7이 된다.
                if(odd == size || even == size){
                    for(int d = 0; d<directions.length; d+=2){
                        balls.offer(new FireBall(i,j,mSum,sSum,d));
                    }
                }
                else{
                    for(int d = 1; d<directions.length; d+=2){
                        balls.offer(new FireBall(i,j,mSum,sSum,d));
                    }
                }

            }
        }
    }
}

