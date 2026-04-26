class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> gr = new ArrayList<>();
        for(int i = 0; i<numCourses; i++){
            gr.add(new ArrayList<>());
        }
        int[] inBound = new int[numCourses];
        for(int[] p : prerequisites){
            inBound[p[0]]++;
            gr.get(p[1]).add(p[0]);
        }

        return solve(gr, inBound);
    }
    public boolean solve(List<List<Integer>> gr, int[] inBound){
        Queue<Integer> q = new ArrayDeque<>();
        for(int i = 0; i<gr.size(); i++){
            if(inBound[i] == 0){
                q.offer(i);
            }
        }
        if(q.isEmpty()) return false;

        while(!q.isEmpty()){
            int cur = q.poll();

            for(int next : gr.get(cur)){
                inBound[next]--;
                if(inBound[next] == 0){
                    q.offer(next);
                }
            }
        }

        for(int i = 0; i<gr.size(); i++){
            if(inBound[i] > 0) return false;
        }
        return true;
    }
}