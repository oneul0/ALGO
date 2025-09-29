import java.io.*;
import java.util.*;

public class Main {
    static int N, M, K;
    static int[][] A; // 겨울에 추가되는 양분
    static int[][] nutrients; // 각 칸의 현재 양분
    static ArrayList<Integer>[][] trees; // 나무 나이

    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        A = new int[N+1][N+1];
        nutrients = new int[N+1][N+1];
        trees = new ArrayList[N+1][N+1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                nutrients[i][j] = 5;
                trees[i][j] = new ArrayList<>();
            }
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            trees[x][y].add(z);
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                Collections.sort(trees[i][j]);
            }
        }

        for (int year = 0; year < K; year++) {
            springAndSummer();
            autumn();
            winter();
        }

        int answer = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                answer += trees[i][j].size();
            }
        }

        System.out.println(answer);
    }

    static void springAndSummer() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (trees[i][j].isEmpty()) continue;

                ArrayList<Integer> alive = new ArrayList<>();
                int deadNutrients = 0;

                for (int age : trees[i][j]) {
                    if (nutrients[i][j] >= age) {
                        nutrients[i][j] -= age;
                        alive.add(age + 1);
                    } else {
                        deadNutrients += age / 2;
                    }
                }

                trees[i][j] = alive;
                nutrients[i][j] += deadNutrients;
            }
        }
    }

    static void autumn() {
        ArrayList<int[]> newTrees = new ArrayList<>();

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                for (int age : trees[i][j]) {
                    if (age % 5 == 0) {
                        for (int d = 0; d < 8; d++) {
                            int nx = i + dx[d];
                            int ny = j + dy[d];

                            if (nx >= 1 && nx <= N && ny >= 1 && ny <= N) {
                                newTrees.add(new int[]{nx, ny});
                            }
                        }
                    }
                }
            }
        }

        for (int[] pos : newTrees) {
            trees[pos[0]][pos[1]].add(1);
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (!trees[i][j].isEmpty()) {
                    Collections.sort(trees[i][j]);
                }
            }
        }
    }

    static void winter() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                nutrients[i][j] += A[i][j];
            }
        }
    }
}