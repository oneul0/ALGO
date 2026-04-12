import java.io.*;
import java.util.*;

public class Main {
    static int R, C;
    static int jr, jc;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        char[][] board = new char[R][C];

        jr=0;
        jc=0;

        List<int[]> arduinos = new ArrayList<>();
        for(int i = 0; i<R; i++){
            String line = br.readLine();
            for(int j =0 ;j<C; j++){
                board[i][j] = line.charAt(j);

                if(board[i][j] == 'I'){
                    jr = i;
                    jc = j;
                }
                else if(board[i][j] == 'R'){
                    arduinos.add(new int[]{i, j});
                }
            }
        }

        String cmd = br.readLine();
        br.close();
        //종수의 움직임을 보고 남은 미친놈들 이동
        char[][] result = copyBoard(board);
        for(int i = 0; i<cmd.length(); i++){
            int dir = cmd.charAt(i)-'0';
            result = simulation(arduinos, dir, result);
            if(result == null){
                System.out.println("kraj "+(i+1));
                return;
            }
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for(int i = 0; i<R; i++){
            for(int j = 0; j<C; j++){
                bw.write(result[i][j]+"");
            }
            bw.write("\n");
        }
        bw.flush();
        bw.close();
    }

    static int[] dr = {0, 1, 1, 1, 0, 0, 0, -1, -1, -1};
    static int[] dc = {0, -1, 0, 1, -1, 0, 1, -1, 0, 1};

    public static char[][] simulation(List<int[]> arduinos, int dir, char[][] origin) {
        char[][] board = copyBoard(origin);

        int njr = jr + dr[dir];
        int njc = jc + dc[dir];

        if (!isInRange(njr, njc)) {
            njr = jr;
            njc = jc;
        }

        //game over
        if (board[njr][njc] == 'R') return null;

        board[jr][jc] = '.';
        board[njr][njc] = 'I';
        jr = njr;
        jc = njc;

        // 미친자들 위치
        int[][] countMap = new int[origin.length][origin[0].length];

        for (int[] ar : arduinos) {
            int bestR = -1, bestC = -1;
            int minDist = Integer.MAX_VALUE;

            for (int i = 1; i <= 9; i++) {
                int nAr = ar[0] + dr[i];
                int nAc = ar[1] + dc[i];

                if (!isInRange(nAr, nAc)) continue;

                int curDist = getDist(njr, njc, nAr, nAc);
                if (curDist < minDist) {
                    minDist = curDist;
                    bestR = nAr;
                    bestC = nAc;
                }
            }

            //game over
            if (board[bestR][bestC] == 'I') return null;

            // 이동할 위치에 카운트 표시
            countMap[bestR][bestC]++;
        }

        // 기존 보드의 모든 아두이노 지우고 새로 배치
        clearArduinos(board);
        arduinos.clear();

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                // 하나 있는 경우만 생존
                if (countMap[r][c] == 1) {
                    board[r][c] = 'R';
                    arduinos.add(new int[]{r, c});
                }
            }
        }

        return board;
    }

    // 보드에서 미친자들 삭제
    private static void clearArduinos(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'R') board[i][j] = '.';
            }
        }
    }

    public static boolean isInRange(int r, int c){
        return (r>=0 && c>=0 && r<R && c<C);
    }

    //getDist
    public static int getDist(int r1, int s1, int r2, int s2){
        return Math.abs(r1-r2) + Math.abs(s1-s2);
    }

    public static char[][] copyBoard(char[][] origin){
        char[][] tmp = new char[R][C];
        for(int i = 0; i<R; i++){
            for(int j = 0; j<C; j++){
                tmp[i][j] = origin[i][j];
            }
        }

        return tmp;
    }
}
