import java.io.*;
import java.util.*;

public class Main {
    static final int PLAYERS = 9;
    static int N;
    static int maxScore = 0;
    static int[][] playerScoreInInnings;
    static int[] lineup = new int[PLAYERS];
    static boolean[] visited = new boolean[PLAYERS];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        playerScoreInInnings = new int[N][PLAYERS];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < PLAYERS; j++) {
                playerScoreInInnings[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        lineup[3] = 0;
        visited[0] = true;

        permute(0);
        System.out.println(maxScore);
    }

    public static void permute(int depth) {
        if (depth == PLAYERS) {
            getScore(lineup);
            return;
        }

        if (depth == 3) {
            permute(depth + 1);
            return;
        }

        for (int i = 0; i < PLAYERS; i++) {
            if (!visited[i]) {
                lineup[depth] = i;
                visited[i] = true;
                permute(depth + 1);
                visited[i] = false;
            }
        }
    }

    public static void getScore(int[] currentLineup) {
        int totalScore = 0;
        int playerIdx = 0;

        for (int i = 0; i < N; i++) {
            int outCount = 0;
            boolean[] bases = new boolean[4];

            while (outCount < 3) {
                int currentPlayer = currentLineup[playerIdx];
                int result = playerScoreInInnings[i][currentPlayer];

                if (result == 0) {
                    outCount++;
                } else if (result == 4) {
                    int runners = 0;
                    for (int b = 1; b <= 3; b++) {
                        if (bases[b]) {
                            runners++;
                            bases[b] = false;
                        }
                    }
                    totalScore += (runners + 1);
                } else {
                    for (int b = 3; b >= 1; b--) {
                        if (bases[b]) {
                            if (b + result >= 4) {
                                totalScore++;
                            } else {
                                bases[b + result] = true;
                            }
                            bases[b] = false;
                        }
                    }

                    bases[result] = true;
                }

                playerIdx = (playerIdx + 1) % 9;
            }
        }
        maxScore = Math.max(maxScore, totalScore);
    }
}