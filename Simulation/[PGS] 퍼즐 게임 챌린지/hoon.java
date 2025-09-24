class Solution {
    int[] diffs, times;
    long limit;
    int size;
    public int solution(int[] diffs, int[] times, long limit) {
        this.diffs = diffs;
        this.times = times;
        this.limit = limit;
        size = diffs.length;

        int answer = (int) findLevel();
        return answer;
    }

    public long findLevel(){
        long begin = 1;
        long end = Integer.MAX_VALUE;
        long result = end;
        while(begin <= end){
            long mid = begin + (end - begin)/2;
            if(canSolve(mid)){
                result = mid;
                end = mid-1;
            }
            else{
                begin = mid+1;
            }
        }
        return result;
    }

    public boolean canSolve(long lv){
        long solveTime = 0;
        for(int i = 0; i<size; i++){
            if(diffs[i] <= lv){
                solveTime += times[i];
            }
            else{
                long gap = diffs[i] - (int) lv;
                solveTime += (times[i] + times[i-1])*gap;
                solveTime += times[i];
            }
        }
        return solveTime <= limit;
    }
}

//조건을 만족하는 최소의 수(lower bound) 찾기
