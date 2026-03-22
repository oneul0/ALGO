import java.util.*;
class Solution {
    public int[] solution(int m, int n, int h, int w, int[][] drops) {
        int[][] grid = new int[m][n];
        int dLen = drops.length;
        for (int[] row : grid) {
            Arrays.fill(row, dLen);
        }

        for (int i = 0; i<dLen; i++) {
            int r = drops[i][0];
            int c = drops[i][1];
            if (grid[r][c] == dLen) grid[r][c] = i;
        }

        int left=0, right=dLen;
        int[] answer = {0, 0};

        //인덱스 k를 기준으로 탐색
        //몇번째 빗방울부터 맞을 수 있는지 최댓값 탐색
        while (left <= right) {
            int mid = (left+right) >>> 1;
            int[] pos = findSafeZone(m, n, h, w, grid, mid);

            if (pos != null) {
                answer = pos;
                left = mid+1;
            } else {
                right = mid-1;
            }
        }
        return answer;
    }

    //특정 시간(index K)일 때, 빗방울 맞는지 검사
    //만약 맞은 빗방울이 없으면 K 번째 빗방울 전에는 맞는게 없으므로 더 나중 빗방울을 맞을 수 있음
    private int[] findSafeZone(int m, int n, int h, int w, int[][] grid, int K) {
        int[][] pSum = new int[m+1][n+1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int val = (grid[i][j] < K) ? 1 : 0;
                pSum[i+1][j+1] = val + pSum[i][j+1] + pSum[i+1][j] - pSum[i][j];
            }
        }

        for (int i = h; i <= m; i++) {
            for (int j = w; j <= n; j++) {
                int sum = pSum[i][j] - pSum[i-h][j] - pSum[i][j-w] + pSum[i-h][j-w];
                if (sum == 0) return new int[]{i-h, j-w};
            }
        }
        return null;
    }
}