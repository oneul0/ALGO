import java.util.*;
import java.io.*;

public class Main {
    static class Character{
        int r, c;
        int dir;
        int hp;
        int damage;
        Character(int r, int c, int dir, int hp, int damage){
            this.r = r;
            this.c = c;
            this.dir = dir;
            this.hp = hp;
            this.damage = damage;
        }
    }
    public static class Ahri extends Character{
        int lastR, lastC;
        Ahri(int r, int c, int dir, int hp, int damage, int lastR, int lastC) {
            super(r, c, dir, hp, damage);
            this.lastR = lastR;
            this.lastC = lastC;
        }
    }
    static final String successMsg = "VICTORY!", failMsg = "CAVELIFE...";
    static int N, M;
    static int[][] cave;
    static int[] dr = {-1,0,1,0}, dc = {0,1,0,-1};
    public static void main(String[] args) throws IOException{
        Character[] characters = init();
        Ahri ahri = (Ahri) characters[0];
        Character boss = characters[1];
        System.out.println(simulation(ahri, boss));
    }

    public static String simulation(Ahri ahri, Character boss){
        while(ahri.hp > 0 && boss.hp>0){
            // 아리 공격
            boss.hp -= ahri.damage;
            if(boss.hp <= 0) break;
            // 아리 이동
            boolean isAhriMove = ahriMove(ahri, boss);
            if(ahri.hp <= 0) break;
            // 보스 공격
            bossAttack(ahri, boss);
            if(ahri.hp <= 0) break;
            // 보스 이동
            if(isAhriMove){
                boss.r = ahri.lastR;
                boss.c = ahri.lastC;
                boss.dir = ahri.dir;
            }
        }

        return ahri.hp > 0 ? successMsg : failMsg;
    }

    public static boolean ahriMove(Ahri ahri, Character boss){
        //이동 가능하면 이동
        int nr = ahri.r + dr[ahri.dir];
        int nc = ahri.c + dc[ahri.dir];
        if(isInRange(nr, nc)&&cave[nr][nc] == 0 && !(nr == boss.r && nc == boss.c)){
            ahri.lastR = ahri.r;
            ahri.lastC = ahri.c;
            ahri.r = nr;
            ahri.c = nc;
            return true;
        }
        //불가능하면 회전하면서 찾기
        // 회전(진행방향 찾는거니까 시계방향으로 최대 4회 회전)
        for(int i = 1; i<=4; i++){
            ahri.hp--;
            int nextDir = (ahri.dir + i) %4;
            nr = ahri.r + dr[nextDir];
            nc = ahri.c + dc[nextDir];
            if(!isInRange(nr, nc) || cave[nr][nc] == 1 || (nr == boss.r && nc == boss.c)) continue;
            ahri.lastR = ahri.r;
            ahri.lastC = ahri.c;
            ahri.r = nr;
            ahri.c = nc;
            ahri.dir = nextDir;
            return true;
        }
        ahri.dir = (ahri.dir + 3) % 4;
        return false;
    }

    public static void bossAttack(Ahri ahri, Character boss){
        //boss child만 번거롭게 이동
        int r = boss.r;
        int c = boss.c;
        int dir = boss.dir;
        int len = 1;
        int count = 1;
        int[] nextPos = null;

        while(count < N * M){
            for(int t = 0; t<2; t++){
                for(int i = 0; i<len; i++){
                    r += dr[dir];
                    c += dc[dir];
                    if(isInRange(r, c)){
                        count++;
                        if(cave[r][c] == 1){
                            nextPos = new int[]{r, c, dir};
                            break;
                        }
                    }
                }
                if(nextPos != null) break;
                dir = (dir + 1) % 4;
            }
            if(nextPos != null) break;
            len++;
        }
        if(nextPos == null) return;
        //생성된 부하몬스터 위치에서 아리로 최단거리 공격
        int damage = attackAhri(nextPos, boss.damage, ahri, boss);
        if(damage > 0){
            ahri.hp -= damage;
        }
    }

    // utils
    // 직선 이동(석순 찾아야하니까 1칸씩 이동)
    public static int[] straightMove(int[] pos, int len){
        // -1 만나면 만난 그 위치로 반환
        int r = pos[0];
        int c = pos[1];
        int d = pos[2];
        for(int i = 1; i<=len; i++){
            int nr = r + dr[d];
            int nc = c + dc[d];
            if(cave[nr][nc] == -1 || cave[nr][nc] == 1) return new int[]{nr, nc, d};
            r = nr;
            c = nc;
        }
        return null;
    }
    // bfs 최단거리 이동(부하 몬스터 공격시)
    public static int attackAhri(int[] pos, int hp, Ahri ahri, Character boss){
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{pos[0], pos[1], hp});
        boolean[][] visited = new boolean[N+2][M+2];
        visited[pos[0]][pos[1]] = true;
        while (!q.isEmpty()){
            int[] cur = q.poll();
            if(cur[0] == ahri.r && cur[1] == ahri.c) return cur[2];
            if(cur[2] == 0) return 0;
            for(int i = 0; i<4; i++){
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];
                if(isInRange(nr, nc) && !visited[nr][nc]){
                    if(cave[nr][nc] == 1) continue;
                    if(nr == boss.r && nc == boss.c) continue;
                    q.offer(new int[]{nr, nc, cur[2]-1});
                    visited[nr][nc] = true;
                }
            }
        }
        return 0;
    }

    public static Character[] init() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        cave = new int[N+2][M+2];
        for(int i = 0; i<N+2; i++){
            Arrays.fill(cave[i], -1);
        }

        Ahri ahri = null;
        Character boss = null;

        for(int i = 1; i<=N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j<=M; j++){
                cave[i][j] = Integer.parseInt(st.nextToken());
                if(cave[i][j] == 2){
                    ahri = new Ahri(i, j, -1, 0, 0, i, j);
                    cave[i][j] = 0;
                }
                else if (cave[i][j] == 3){
                    boss = new Character(i, j, -1, 0, 0);
                    cave[i][j] = 0;
                }
            }
        }
        //아리와 보스 공체 채우기
        st = new StringTokenizer(br.readLine());
        int aa = Integer.parseInt(st.nextToken());
        int ad = Integer.parseInt(st.nextToken());
        ahri.hp = aa;
        ahri.damage = ad;

        int ba = Integer.parseInt(st.nextToken());
        int bd = Integer.parseInt(st.nextToken());
        boss.hp = ba;
        boss.damage = bd;

        //아리와 보스 진행방향 산정
        //보스만 찾으면 아리는 반대
        boss.dir = getNeighborAhri(ahri, boss);
        ahri.dir = boss.dir; //0 1 2 3

        return new Character[]{ahri, boss};
    }

    // 보스와 인접한 아리 위치 찾아서 방향 반환
    public static int getNeighborAhri(Character ahri, Character boss){
        for(int i = 0; i<4; i++){
            int nr = boss.r + dr[i];
            int nc = boss.c+dc[i];
            if(!isInRange(nr, nc)) continue;
            if(nr == ahri.r && nc == ahri.c) return i;
        }
        return -1;
    }

    public static boolean isInRange(int r, int c){
        return r>0 && c>0 && r<=N &&c<=M;
    }
}

//두 번 직선 이동할 때마다 1씩 증가함
//보스 이동 -> 아리 이동에 종속