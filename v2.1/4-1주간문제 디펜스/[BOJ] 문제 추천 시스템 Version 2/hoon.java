import java.io.*;
import java.util.*;

public class Main {
    public static class Problem implements Comparable<Problem> {
        int num, level, category;
        Problem(int num, int level, int category) {
            this.num = num;
            this.level = level;
            this.category = category;
        }

        @Override
        public int compareTo(Problem o) {
            if (this.level == o.level) {
                return this.num - o.num;
            }
            return this.level - o.level;
        }
    }

    // 전체 문제 관리- 난이도, 번호 순
    static TreeSet<Problem> allProblems = new TreeSet<>();
    // 카테고리별 문제 관리- 카테고리 -> 정렬된 문제
    static Map<Integer, TreeSet<Problem>> categoryProblems = new HashMap<>();
    // 문제 번호로 난이도와 카테고리 정보 조회(삭제용)
    static Map<Integer, int[]> problemInfo = new HashMap<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            addProblem(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            String cmd = st.nextToken();

            switch (cmd) {
                case "recommend": {
                    int g = Integer.parseInt(st.nextToken());
                    int x = Integer.parseInt(st.nextToken());
                    TreeSet<Problem> ts = categoryProblems.get(g);
                    bw.write(String.valueOf(x == 1 ? ts.last().num : ts.first().num));
                    bw.write("\n");
                    break;
                }
                case "recommend2": {
                    int x = Integer.parseInt(st.nextToken());
                    bw.write(String.valueOf(x == 1 ? allProblems.last().num : allProblems.first().num));
                    bw.write("\n");
                    break;
                }
                case "recommend3": {
                    int x = Integer.parseInt(st.nextToken());
                    int l = Integer.parseInt(st.nextToken());
                    Problem res;
                    if (x == 1) {
                        // 난이도 L 이상 중 가장 쉬운 문제(없으면 -1)
                        res = allProblems.ceiling(new Problem(0, l, 0));
                    } else {
                        // 난이도 L 미만 중 가장 어려운 문제(없으면 -1)
                        res = allProblems.lower(new Problem(0, l, 0));
                    }
                    bw.write(String.valueOf(res == null ? -1 : res.num));
                    bw.write("\n");
                    break;
                }
                case "add": {
                    addProblem(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                    break;
                }
                case "solved": {
                    int p = Integer.parseInt(st.nextToken());
                    removeProblem(p);
                    break;
                }
            }
        }
        bw.flush();
    }

    private static void addProblem(int p, int l, int g) {
        // 이미 존재하는 문제라면 먼저 삭제
        if (problemInfo.containsKey(p)) {
            removeProblem(p);
        }
        Problem newProblem = new Problem(p, l, g);
        allProblems.add(newProblem);
        categoryProblems.computeIfAbsent(g, k -> new TreeSet<>()).add(newProblem);
        problemInfo.put(p, new int[]{l, g});
    }

    private static void removeProblem(int p) {
        int[] info = problemInfo.get(p);
        if (info == null) return;

        int l = info[0];
        int g = info[1];
        Problem target = new Problem(p, l, g);

        allProblems.remove(target);
        if (categoryProblems.containsKey(g)) {
            categoryProblems.get(g).remove(target);
        }
        problemInfo.remove(p);
    }
}