class Solution {
    public int orangesRotting(int[][] grid) {
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[i].length; j++){
                if(grid[i][j] == 2){
                    q.offer(new int[]{i, j, 0});
                    visited[i][j] = true;
                }
            }
        }
        int result = bfs(grid, q, visited);
        return isAllRotten(grid) ? result : -1;
    }

    public int bfs(int[][] grid, ArrayDeque<int[]> q, boolean[][] visited){
        int[] dr = {-1,1,0,0}, dc = {0,0,-1,1};
        int lastDay = 0;
        while(!q.isEmpty()){
            int[] cur = q.poll();

            for(int i = 0; i<4; i++){
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];
                if(nr<0 || nc<0 || nr>=grid.length || nc>=grid[0].length) continue;
                if(visited[nr][nc] || grid[nr][nc] != 1) continue;
                q.offer(new int[]{nr, nc, cur[2]+1});
                grid[nr][nc] = 2;
                lastDay = Math.max(lastDay, cur[2]+1);
                visited[nr][nc] = true;
            }
        }
        return lastDay;
    }
    public boolean isAllRotten(int[][] grid){
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[i].length; j++){
                if(grid[i][j] == 1) return false;
            }
        }
        return true;
    }
}class Solution {
    public int orangesRotting(int[][] grid) {
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[i].length; j++){
                if(grid[i][j] == 2){
                    q.offer(new int[]{i, j, 0});
                    visited[i][j] = true;
                }
            }
        }
        int result = bfs(grid, q, visited);
        return isAllRotten(grid) ? result : -1;
    }

    public int bfs(int[][] grid, ArrayDeque<int[]> q, boolean[][] visited){
        int[] dr = {-1,1,0,0}, dc = {0,0,-1,1};
        int lastDay = 0;
        while(!q.isEmpty()){
            int[] cur = q.poll();

            for(int i = 0; i<4; i++){
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];
                if(nr<0 || nc<0 || nr>=grid.length || nc>=grid[0].length) continue;
                if(visited[nr][nc] || grid[nr][nc] != 1) continue;
                q.offer(new int[]{nr, nc, cur[2]+1});
                grid[nr][nc] = 2;
                lastDay = Math.max(lastDay, cur[2]+1);
                visited[nr][nc] = true;
            }
        }
        return lastDay;
    }
    public boolean isAllRotten(int[][] grid){
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[i].length; j++){
                if(grid[i][j] == 1) return false;
            }
        }
        return true;
    }
}