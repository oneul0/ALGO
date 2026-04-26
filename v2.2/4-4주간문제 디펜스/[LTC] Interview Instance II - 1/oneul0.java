class Solution {

    int[] parent;
    public int numIslands(char[][] grid) {
        int[][] dir =  {{1,0},{0,1}};
        int m = grid.length;
        int n = grid[0].length;
        parent = new int[m*n];

        int cnt = 0;
        for(int r = 0; r < m ; r++) {
            for(int c = 0; c < n ;c++) {
                int u = r*n+c;
                if(grid[r][c]=='1') {
                    parent[u]=u;
                    cnt++;
                } else {
                    parent[u]=-1;
                }
            }
        }


        for(int r = 0;  r < m ; r++) {
            for(int c = 0; c < n ;c++) {
                if(grid[r][c]=='1') {

                    for(int d = 0; d <dir.length; d++) {
                        int nr = r + dir[d][0];
                        int nc = c + dir[d][1];
                        int v = nr*n+nc;

                        if(0 <= nr && nr < m && 0 <= nc && nc <n && parent[v] !=-1) {
                            //union
                            int u = r*n+c;
                            int rootU = find(u);
                            int rootV = find(v);
                            if(rootU != rootV) {
                                parent[rootV]=rootU;
                                cnt--;
                            }
                        }

                    }
                }
            }
        }
        return cnt;

    }


    int find(int u) {
        if(parent[u]==u) return u;
        return parent[u]=find(parent[u]);
    }
}