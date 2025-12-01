import java.io.*;
import java.util.*;

public class Main {
    public static class Point {
        int r, c;
        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int R, C;
    static Point mos, zag;
    static char[][] blueprint;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        blueprint = new char[R][C];
        for (int i = 0; i < R; i++) {
            String tmp = br.readLine();
            for (int j = 0; j < C; j++) {
                blueprint[i][j] = tmp.charAt(j);
                if (blueprint[i][j] == 'M') mos = new Point(i, j);
                else if (blueprint[i][j] == 'Z') zag = new Point(i, j);
            }
        }

        // M에서 출발하여 끊어진 지점을 찾기
        Point target = findDisconnectedSpot();

        // 끊어진 지점에서 필요한 파이프 모양을 찾기
        char resultShape = findShape(target.r, target.c);

        System.out.println((target.r + 1) + " " + (target.c + 1) + " " + resultShape);
    }

    // M에서 시작해서 파이프가 끊긴 지점을 찾아 반환
    public static Point findDisconnectedSpot() {
        Point cur = mos;
        Point prev = mos;

        for (int i = 0; i < 4; i++) {
            int nr = cur.r + dr[i];
            int nc = cur.c + dc[i];
            if (isValid(nr, nc) && blueprint[nr][nc] != '.' && blueprint[nr][nc] != 'Z') {
                cur = new Point(nr, nc);
                break;
            }
        }

        while (true) {
            if (blueprint[cur.r][cur.c] == '.') {
                return cur;
            }

            Point next = getNextPos(prev.r, prev.c, cur.r, cur.c, blueprint[cur.r][cur.c]);

            // 이동
            prev = cur;
            cur = next;
        }
    }

    // 빈 칸(r, c) 좌표에서 상하좌우를 확인해 알맞은 파이프 모양 결정
    public static char findShape(int r, int c) {
        boolean top = false, bottom = false, left = false, right = false;

        // 상: 위쪽 칸(r-1)에 아래로 내려오는 파이프가 있는가?
        if (isValid(r - 1, c) && isConnected(r - 1, c, 1)) top = true; // 1 :하 방향
        // 하: 아래쪽 칸(r+1)에 위로 올라가는 파이프가 있는가?
        if (isValid(r + 1, c) && isConnected(r + 1, c, 0)) bottom = true; // 0 : 상
        // 좌: 왼쪽 칸(c-1)에 오른쪽으로 가는 파이프가 있는가?
        if (isValid(r, c - 1) && isConnected(r, c - 1, 3)) left = true; // 3 : 우
        // 우: 오른쪽 칸(c+1)에 왼쪽으로 가는 파이프가 있는가?
        if (isValid(r, c + 1) && isConnected(r, c + 1, 2)) right = true; // 2 : 좌

        //파이프 종류 구하기
        if (top && bottom && left && right) return '+';
        if (top && bottom) return '|';
        if (left && right) return '-';
        if (bottom && right) return '1';
        if (top && right) return '2';
        if (top && left) return '3';
        if (bottom && left) return '4';

        return '?';
    }

    // (r,c) 위치에 있는 파이프가 targetDir(0:상, 1:하, 2:좌, 3:우) 방향으로 연결되어 있는지 확인
    // 내 입장에서 주변 파이프가 나를 향해 구멍이 뚫려있는지 확인하는 함수
    public static boolean isConnected(int r, int c, int reqDir) {
        char mark = blueprint[r][c];
        if (mark == '|' || mark == '+' || mark == '1' || mark == '4') {
            if (reqDir == 1) return true; // 위쪽에서 내려오는 연결
        }
        if (mark == '|' || mark == '+' || mark == '2' || mark == '3') {
            if (reqDir == 0) return true; // 아래에서 올라오는 연결
        }
        if (mark == '-' || mark == '+' || mark == '1' || mark == '2') {
            if (reqDir == 3) return true; // 왼쪽에서 오는 연결
        }
        if (mark == '-' || mark == '+' || mark == '3' || mark == '4') {
            if (reqDir == 2) return true; // 오른쪽에서 오는 연결
        }
        return false;
    }

    public static boolean isValid(int x, int y) {
        return (x >= 0 && y >= 0 && x < R && y < C);
    }

    public static Point getNextPos(int curR, int curC, int toR, int toC, char mark) {
        int dr = toR - curR;
        int dc = toC - curC;

        switch (mark) {
            case '|': case '-': case '+':
                break;
            case '1':
                if (dr == -1) {
                    dr = 0;
                    dc = 1;
                }
                else if (dc == -1) {
                    dr = 1;
                    dc = 0;
                }
                break;
            case '2':
                if (dr == 1) {
                    dr = 0;
                    dc = 1;
                }
                else if (dc == -1) {
                    dr = -1;
                    dc = 0;
                }
                break;
            case '3':
                if (dr == 1) {
                    dr = 0;
                    dc = -1;
                }
                else if (dc == 1) {
                    dr = -1;
                    dc = 0;
                }
                break;
            case '4':
                if (dr == -1) {
                    dr = 0;
                    dc = -1;
                }
                else if (dc == 1) {
                    dr = 1;
                    dc = 0;
                }
                break;
        }
        return new Point(toR + dr, toC + dc);
    }
}