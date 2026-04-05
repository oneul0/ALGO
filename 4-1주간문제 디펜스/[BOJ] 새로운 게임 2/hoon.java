import java.io.*;
import java.util.*;

public class Main {
    static class Piece {
        int r, c, d;
        Piece(int r, int c, int d){
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }
    static int N, K;
    static int[] dr = {0,0,-1,1}, dc = {1,-1,0,0}; // →, ←, ↑, ↓
    static int[][] arr;
    static ArrayList<Integer>[][] map;
    static Piece[] pieces;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        arr = new int[N][N];
        map = new ArrayList[N][N];
        K = Integer.parseInt(st.nextToken());
        pieces = new Piece[K];
        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j<N; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
                map[i][j] = new ArrayList<>();
            }
        }

        for(int i = 0; i<K; i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken())-1;
            int d = Integer.parseInt(st.nextToken())-1;
            pieces[i] = new Piece(r, c, d);
            map[r][c].add(i);
        }
        System.out.println(simulation());
    }

    public static int reverseDirection(int d){
        switch (d){
            case 0: return 1;
            case 1: return 0;
            case 2: return 3;
            case 3: return 2;
        }
        return d;
    }

    public static int simulation() {
        for (int turn = 1; turn <= 1000; turn++) {
            for (int i = 0; i < K; i++) {
                Piece p = pieces[i];
                int nr = p.r + dr[p.d];
                int nc = p.c + dc[p.d];

                // 파란색이거나 범위를 벗어나면 뒤집기
                if (nr < 0 || nc < 0 || nr >= N || nc >= N || arr[nr][nc] == 2) {
                    p.d = reverseDirection(p.d);
                    nr = p.r + dr[p.d];
                    nc = p.c + dc[p.d];

                    // 방향 바꿨는데도 파란색이면 이동 안 함
                    if (nr < 0 || nc < 0 || nr >= N || nc >= N || arr[nr][nc] == 2) continue;
                }

                // 이동할 말들 전부
                int r = p.r;
                int c = p.c;
                int floor = map[r][c].indexOf(i); // 현재 칸 말 수

                List<Integer> moving = new ArrayList<>();
                for (int j = floor; j < map[r][c].size(); j++) {
                    moving.add(map[r][c].get(j));
                }

                // 기존 칸에서 제거
                int originalSize = map[r][c].size();
                for (int j = originalSize - 1; j >= floor; j--) {
                    map[r][c].remove(j);
                }

                // 빨간색이면 뒤집기
                if (arr[nr][nc] == 1) {
                    Collections.reverse(moving);
                }

                // 새 칸에 쌓기 및 좌표 갱신
                for (int pieceIdx : moving) {
                    map[nr][nc].add(pieceIdx);
                    pieces[pieceIdx].r = nr;
                    pieces[pieceIdx].c = nc;
                }

                // 4개 이상 쌓였는지 체크
                if (map[nr][nc].size() >= 4) return turn;
            }
        }
        return -1;
    }
}

//파란색은 벽처럼 사용 파랑 만나면 반대 방향으로
//4개 이상 쌓이는 순간 게임이 종료
//빨간색 뒤집고 쌓음