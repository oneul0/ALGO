import java.io.*;
import java.util.*;

public class Main {
    static class Line implements Comparable<Line> {
        long sum;
        int type; //행 열 대1 대2
        int id;

        public Line(long sum, int type, int id) {
            this.sum = sum;
            this.type = type;
            this.id = id;
        }

        @Override
        public int compareTo(Line o) {
            if (this.sum != o.sum) return Long.compare(this.sum, o.sum);
            if (this.type != o.type) return Integer.compare(this.type, o.type);
            return Integer.compare(this.id, o.id);
        }
    }

    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        long[][] board = new long[N][N];

        long[] rowSum = new long[N];
        long[] colSum = new long[N];
        long diag1Sum = 0; // 왼->오 \
        long diag2Sum = 0; // 오->왼 /

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Long.parseLong(st.nextToken());
                rowSum[i] += board[i][j];
                colSum[j] += board[i][j];
                if (i == j) diag1Sum += board[i][j];
                if (i + j == N - 1) diag2Sum += board[i][j];
            }
        }

        boolean[] finished = new boolean[2*N+2]; // 빙고 완성 여부 (0~N-1: 행, N~2N-1: 열, 2N: 대1, 2N+1: 대2)
        PriorityQueue<Line> pq = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            pq.offer(new Line(rowSum[i], 0, i));
            pq.offer(new Line(colSum[i], 1, i));
        }
        pq.offer(new Line(diag1Sum, 2, 0));
        pq.offer(new Line(diag2Sum, 3, 1));

        long totalTime = 0;
        int bingoCnt = 0;
        long[] result = new long[2*N+3]; // k빙고 완성 시간 저장

        boolean[][] visited = new boolean[N][N];
        while (!pq.isEmpty() && bingoCnt < 2 * N + 2) {
            Line cur = pq.poll();

            // 현재 상태와 다르면 무시
            if (isOutDated(cur, rowSum, colSum, diag1Sum, diag2Sum, finished)) continue;

            //지금 줄의 칸 해결 하나씩 해결
            int type = cur.type;
            int idx = cur.id;

            for(int k = 0; k<N; k++){
                int r = 0, c= 0;
                if(type == 0){
                    r = idx;
                    c = k;
                }
                else if(type == 1){
                    r= k;
                    c=idx;
                }
                else if(type == 2){
                    r = k;
                    c = k;
                }
                else {
                    r = k;
                    c = N-1-k;
                }

                if(!visited[r][c]){
                    visited[r][c] = true;
                    totalTime += board[r][c];


                    //이 칸과 연결된 모든 줄의 합 갱신
                    //행 최신화
                    rowSum[r] -= board[r][c];
                    if(rowSum[r] == 0 && !finished[r]){
                        finished[r] = true;
                        recordBingo(++bingoCnt, totalTime, result);
                    }
                    else if(rowSum[r] > 0){
                        pq.offer(new Line(rowSum[r], 0, r));
                    }

                    //열 최신화
                    colSum[c] -= board[r][c];
                    if(colSum[c] == 0 && !finished[N+c]){
                        finished[N+c] = true;
                        recordBingo(++bingoCnt, totalTime, result);
                    }
                    else if (colSum[c] > 0){
                        pq.offer(new Line(colSum[c], 1, c));
                    }

                    //대각1 최신화
                    if(r == c){
                        diag1Sum -= board[r][c];
                        if(diag1Sum == 0 && !finished[2*N]){
                            finished[2*N] = true;
                            recordBingo(++bingoCnt, totalTime, result);
                        }
                        else if(diag1Sum > 0){
                            pq.offer(new Line(diag1Sum, 2, 0));
                        }
                    }

                    //대각2 최신화
                    if(r+c == N-1){
                        diag2Sum -= board[r][c];
                        if(diag2Sum == 0 && !finished[2*N+1]){
                            finished[2*N+1] = true;
                            recordBingo(++bingoCnt, totalTime, result);
                        }
                        else if(diag2Sum > 0){
                            pq.offer(new Line(diag2Sum, 3, 1));
                        }
                    }
                }

            }


        }
        for(int k = 1; k<=2*N+2; k++){
            bw.write(result[k]+"\n");
        }
        bw.flush();
    }

    // 우선순위 큐에서 꺼낸 줄이 유효한지 확인하는 메서드
    public static boolean isOutDated(Line l, long[] rs, long[] cs, long d1, long d2, boolean[] finished) {
        // 1. 이미 완성된 줄 번호인지 체크 (lineFinished 배열 활용)
        if(l.type == 0 && (finished[l.id] || rs[l.id] != l.sum)) return true;
        if(l.type == 1 && (finished[N+l.id] || cs[l.id] != l.sum)) return true;
        if(l.type == 2 && (finished[2*N] || d1 != l.sum)) return true;
        if(l.type == 3 && (finished[2*N+1] || d2 != l.sum)) return true;
        return false;
    }
    public static void recordBingo(int count, long time, long[] result) {
        if (count < result.length && result[count] == 0) {
            result[count] = time;
        }
    }
}