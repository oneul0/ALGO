import java.io.*;
import java.util.*;

public class Main {
    static int N, maxVal = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        int[][] board = new int[N][N];
        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j<N; j++){
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        simulation(0, board);
        System.out.println(maxVal);
    }

    public static void simulation(int depth, int[][] prevBoard){
        if(depth == 5){
            updateMax(prevBoard);
            return;
        }

        int[][] rightBoard = copyArr(prevBoard);
        moveRight(rightBoard);
        simulation(depth + 1, rightBoard);

        int[][] leftBoard = copyArr(prevBoard);
        moveLeft(leftBoard);
        simulation(depth + 1, leftBoard);

        int[][] upBoard = copyArr(prevBoard);
        moveUp(upBoard);
        simulation(depth + 1, upBoard);

        int[][] downBoard = copyArr(prevBoard);
        moveDown(downBoard);
        simulation(depth + 1, downBoard);
    }

    static void updateMax(int[][] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                maxVal = Math.max(maxVal, board[i][j]);
            }
        }
    }

    //call by ref -> no return
    static void moveRight(int[][] board) {
        for (int i=0; i<N; i++) {
            int[] line = new int[N];

            // 오른쪽 기준으로 읽기
            for (int j=0; j<N; j++) {
                line[j] = board[i][N-1-j];
            }

            line = compress(line);

            // 다시 방향(여기서는 오른쪽) 기준으로 쓰기
            for (int j=0; j<N; j++) {
                board[i][N-1-j] = line[j];
            }
        }
    }

    static void moveLeft(int[][] board) {
        for (int i = 0; i<N; i++) {
            int[] line = new int[N];

            for (int j=0; j<N; j++) {
                line[j] = board[i][j];
            }

            line = compress(line);

            for (int j=0; j<N; j++) {
                board[i][j] = line[j];
            }
        }
    }

    static void moveUp(int[][] board) {
        for (int j = 0; j<N; j++) {
            int[] line = new int[N];

            for (int i=0; i<N; i++) {
                line[i] = board[i][j];
            }

            line = compress(line);

            for (int i=0; i<N; i++) {
                board[i][j] = line[i];
            }
        }
    }

    static void moveDown(int[][] board) {
        for (int j = 0; j<N; j++) {
            int[] line = new int[N];

            for (int i=0; i<N; i++) {
                line[i] = board[N-1-i][j];
            }

            line = compress(line);

            for (int i = 0; i<N; i++) {
                board[N-1-i][j] = line[i];
            }
        }
    }

    static int[] compress(int[] line) {
        int[] result = new int[N];
        int idx = 0;
        int prev = 0;

        for (int i=0; i<N; i++) {
            if (line[i] == 0) continue;

            //0이면 그냥 옮김
            if (prev == 0) {
                prev = line[i];
            } else {
                //같으면 합치기
                if (prev == line[i]) {
                    result[idx++] = prev*2;
                    prev = 0;
                } else {
                    result[idx++] = prev;
                    prev = line[i];
                }
            }
        }

        //마지막 자리 처리
        if (prev != 0) {
            result[idx] = prev;
        }

        return result;
    }

    public static int[][] copyArr(int[][] origin){
        int[][] newArr = new int[N][N];
        for(int i = 0; i<origin.length; i++){
            for(int j = 0; j<origin[i].length; j++){
                newArr[i][j] = origin[i][j];
            }
        }
        return newArr;
    }
}
//depth 5가 될 때까지(이동을 5번 할 때까지) 최대 수 기록하기
//모든 경우를 다 확인해야함
//N이 작으므로 걍 반복문 돌리자

