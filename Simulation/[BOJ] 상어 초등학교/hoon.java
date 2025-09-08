import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[][] board;
    static Map<Integer, List<Integer>> favoriteMap;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        board = new int[N][N];
        favoriteMap = new HashMap<>();
        List<Integer> studentOrder = new ArrayList<>();
        StringTokenizer st;
        for (int i = 0; i < N * N; i++) {
            st = new StringTokenizer(br.readLine());
            int studentId = Integer.parseInt(st.nextToken());
            List<Integer> favorites = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                favorites.add(Integer.parseInt(st.nextToken()));
            }
            studentOrder.add(studentId);
            favoriteMap.put(studentId, favorites);
        }

        // 학생 자리 배치
        for (int studentId : studentOrder) {
            placeStudent(studentId);
        }

        // 총 만족도 계산
        long totalSatisfaction = calculateTotalSatisfaction();

        System.out.println(totalSatisfaction);

        br.close();
    }

    // 학생 자리 배치 함수
    private static void placeStudent(int studentId) {
        int bestRow = -1, bestCol = -1;
        int maxFavoriteCount = -1;
        int maxEmptyCount = -1;

        List<Integer> favorites = favoriteMap.get(studentId);

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (board[r][c] == 0) { // 빈칸인 경우
                    int currentFavoriteCount = 0;
                    int currentEmptyCount = 0;

                    // 인접 칸 확인
                    for (int i = 0; i < 4; i++) {
                        int nr = r + dr[i];
                        int nc = c + dc[i];

                        if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
                            // 인접 칸에 학생이 있으면
                            if (board[nr][nc] != 0) {
                                // 좋아하는 학생인지 확인
                                if (favorites.contains(board[nr][nc])) {
                                    currentFavoriteCount++;
                                }
                                // 인접 칸이 비어있으면
                            } else {
                                currentEmptyCount++;
                            }
                        }
                    }

                    // 최적의 자리 조건 비교
                    if (currentFavoriteCount > maxFavoriteCount) {
                        maxFavoriteCount = currentFavoriteCount;
                        maxEmptyCount = currentEmptyCount;
                        bestRow = r;
                        bestCol = c;
                    } else if (currentFavoriteCount == maxFavoriteCount) {
                        if (currentEmptyCount > maxEmptyCount) {
                            maxEmptyCount = currentEmptyCount;
                            bestRow = r;
                            bestCol = c;
                        } else if (currentEmptyCount == maxEmptyCount) {
                            if (r < bestRow) {
                                bestRow = r;
                                bestCol = c;
                            } else if (r == bestRow && c < bestCol) {
                                bestCol = c;
                            }
                        }
                    }
                }
            }
        }

        // 최적의 자리에 학생 배치
        board[bestRow][bestCol] = studentId;
    }

    // 총 만족도 계산 함수
    private static long calculateTotalSatisfaction() {
        long totalSatisfaction = 0;

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int studentId = board[r][c];
                if (studentId != 0) {
                    List<Integer> favorites = favoriteMap.get(studentId);
                    int adjacentFavoriteCount = 0;

                    for (int i = 0; i < 4; i++) {
                        int nr = r + dr[i];
                        int nc = c + dc[i];

                        if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
                            int adjacentStudentId = board[nr][nc];
                            if (adjacentStudentId != 0 && favorites.contains(adjacentStudentId)) {
                                adjacentFavoriteCount++;
                            }
                        }
                    }
                    totalSatisfaction += getSatisfactionScore(adjacentFavoriteCount);
                }
            }
        }
        return totalSatisfaction;
    }

    // 만족도 점수 계산
    private static int getSatisfactionScore(int count) {
        if (count == 0) return 0;
        if (count == 1) return 1;
        if (count == 2) return 10;
        if (count == 3) return 100;
        if (count == 4) return 1000;
        return 0;
    }
}