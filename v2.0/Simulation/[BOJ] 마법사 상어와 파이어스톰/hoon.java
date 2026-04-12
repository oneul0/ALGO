import java.io.*;
import java.util.*;

public class Main {
    static int N, Q;
    static int[][] A;
    static int size;
    static int[] dx = {1, -1, 0, 0}, dy = {0, 0, 1, -1};
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        size = (int) Math.pow(2, N);
        A = new int[size][size];

        for (int i = 0; i < size; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < size; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < Q; i++) {
            int L = Integer.parseInt(st.nextToken());
            firestorm(L);
        }

        long sum = getSumOfIce();
        int largestChunk = getLargestChunk();

        System.out.println(sum);
        System.out.println(largestChunk);
    }

    // 파이어스톰 시전
    public static void firestorm(int L) {
        // 1. 격자 회전
        if (L > 0) {
            rotateGrid(L);
        }

        // 2. 얼음 감소
        meltIce();
    }

    // 격자 회전
    public static void rotateGrid(int L) {
        int subGridSize = (int) Math.pow(2, L);
        int[][] temp = new int[size][size];

        for (int r = 0; r < size; r += subGridSize) {
            for (int c = 0; c < size; c += subGridSize) {
                rotateSubGrid(r, c, subGridSize, temp);
            }
        }
        A = temp;
    }

    // 부분 격자 회전
    public static void rotateSubGrid(int r, int c, int subGridSize, int[][] temp) {
        for (int i = 0; i < subGridSize; i++) {
            for (int j = 0; j < subGridSize; j++) {
                temp[r + j][c + subGridSize - 1 - i] = A[r + i][c + j];
            }
        }
    }

    // 얼음 감소
    public static void meltIce() {
        LinkedList<int[]> toMelt = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (A[i][j] > 0) {
                    int count = 0;
                    for (int k = 0; k < 4; k++) {
                        int ni = i + dx[k];
                        int nj = j + dy[k];

                        if (ni >= 0 && ni < size && nj >= 0 && nj < size && A[ni][nj] > 0) {
                            count++;
                        }
                    }
                    if (count < 3) {
                        toMelt.add(new int[]{i, j});
                    }
                }
            }
        }
        for (int[] pos : toMelt) {
            A[pos[0]][pos[1]]--;
        }
    }

    // 얼음 총합 계산
    public static long getSumOfIce() {
        long sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += A[i][j];
            }
        }
        return sum;
    }

    // 가장 큰 덩어리 크기 계산
    public static int getLargestChunk() {
        boolean[][] visited = new boolean[size][size];
        int maxChunk = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (A[i][j] > 0 && !visited[i][j]) {
                    int currentChunk = bfs(i, j, visited);
                    if (currentChunk > maxChunk) {
                        maxChunk = currentChunk;
                    }
                }
            }
        }
        return maxChunk;
    }

    // 탐색
    public static int bfs(int startR, int startC, boolean[][] visited) {
        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{startR, startC});
        visited[startR][startC] = true;
        int count = 0;

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int r = pos[0];
            int c = pos[1];
            count++;

            for (int i = 0; i < 4; i++) {
                int nr = r + dx[i];
                int nc = c + dy[i];

                if (nr >= 0 && nr < size && nc >= 0 && nc < size && A[nr][nc] > 0 && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc});
                }
            }
        }
        return count;
    }
}
