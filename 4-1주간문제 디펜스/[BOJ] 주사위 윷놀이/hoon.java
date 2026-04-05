import java.io.*;
import java.util.*;

public class Main {
    static int[] moves = new int[10];
    static int[] piecePos = new int[4];
    static boolean[] placed = new boolean[33];

    // 보드판 정보
    static int[] map = new int[33];//빨간 화살표 (일반 이동)
    static int[] blue = new int[33];//파란 화살표 (지름길 시작)
    static int[] score = new int[33];//각 인덱스의 점수

    static int maxScore = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 10; i++) {
            moves[i] = Integer.parseInt(st.nextToken());
        }

        initBoard();
        solve(0, 0);
        System.out.println(maxScore);
    }

    public static void solve(int depth, int currentSum) {
        // 10번 모두 던졌을 때
        if (depth == 10) {
            maxScore = Math.max(maxScore, currentSum);
            return;
        }

        for (int i = 0; i < 4; i++) {
            int startNode = piecePos[i];

            //이미 도착지에 가 있는 말은 pass
            if (startNode == 21) continue;

            //이동 시뮬레이션
            int nextNode = startNode;
            int moveCnt = moves[depth];

            //첫 번째 칸 이동 시 파란색 칸인지 확인
            if (blue[nextNode] != 0) {
                nextNode = blue[nextNode];
                moveCnt--;
            }

            //남은 칸만큼 이동
            while (moveCnt-- > 0) {
                nextNode = map[nextNode];
                if (nextNode == 21) break; // 도착지에 도달하면 중단
            }

            //이동할 칸에 이미 다른 말이 있으면 pass
            if (nextNode != 21 && placed[nextNode]) continue;

            //백트
            int prevNode = startNode;
            placed[prevNode] = false;
            placed[nextNode] = true;
            piecePos[i] = nextNode;

            solve(depth + 1, currentSum + score[nextNode]);

            //복구
            piecePos[i] = prevNode;
            placed[nextNode] = false;
            placed[prevNode] = true;
        }
    }

    public static void initBoard() {
        // 0 ~ 20, 21번 도착
        for (int i = 0; i <= 19; i++){
            map[i] = i + 1;
        }
        map[20] = 21; // 40점에서 이동하면 도착(21)
        for (int i = 0; i <= 20; i++){
            score[i] = i * 2;
        }

        // 지름길 시작점(파란색 칸)
        blue[5] = 22;  // 10번 칸 -> 13점
        blue[10] = 25; // 20번 칸 -> 22점
        blue[15] = 27; // 30번 칸 -> 28점

        // 지름길 내부 경로
        // 10에서 시작하는 경로
        score[22] = 13;
        map[22] = 23;
        score[23] = 16;
        map[23] = 24;
        score[24] = 19;
        map[24] = 30; // 25점으로

        // 20에서 시작하는 경로
        score[25] = 22;
        map[25] = 26;
        score[26] = 24;
        map[26] = 30; // 25점으로

        // 30에서 시작하는 경로
        score[27] = 28;
        map[27] = 28;
        score[28] = 27;
        map[28] = 29;
        score[29] = 26;
        map[29] = 30; // 25점으로

        // 중앙 합류 지점부터 40번까지의 경로
        score[30] = 25;
        map[30] = 31;
        score[31] = 30;
        map[31] = 32;
        score[32] = 35;
        map[32] = 20; // 35점에서 40점으로

        score[21] = 0; // 도착지
    }
}

//도착 지점을 제외하고 말을 고르고
//도착 지점에 말이 있으면 continue
//옮길 수 있는 말 중 가장 점수가 높은 말
//여기서 이전 지점의 결과가 다음 지점으로 이동할 때의 결과에 영향을 미치므로
//말1, 2, 3, 4 를 10차례 동안 선택하는 경우를 모두 구하여 최댓값