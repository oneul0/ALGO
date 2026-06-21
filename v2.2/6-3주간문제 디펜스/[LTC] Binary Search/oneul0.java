//Q1. Peak Index in a Mountain Array
class Solution {
    public int peakIndexInMountainArray(int[] arr) {
        int val = -1;
        int idx = -1;
        for(int i = 0; i<arr.length; i++){
            if(val < arr[i]){
                val = arr[i];
                idx = i;
            }
        }
        return idx;
    }
}

//Q2. Binary Search
class Solution {
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length-1;
        while(l<=r){
            int t = (l+r) >>> 1;
            if(nums[t] == target) return t;
            else if(nums[t] < target) l = t+1;
            else r = t-1;
        }
        return -1;
    }
}

//Q3. Sum of Square Numbers
class Solution {
    public boolean judgeSquareSum(int c) {
        long l = 0;
        long r = (long) Math.sqrt(c);
        while(l<=r){
            long sum = l*l + r*r;
            if(sum == c) return true;
            else if(sum<c) l++;
            else r--;
        }
        return false;
    }
}

//Q4. Search in Rotated Sorted Array
//rotated니까 어느 기점을 기준으로 오름차순을 충족하는 구간이 존재함
//그러므로 target이 존재할 수 있는 연속된 구간을 찾는다면 그 구간만 탐색하면 target의 인덱스를 구할 수 있음
//따라서 분할정복으로 접근 가능
class Solution {
    public int search(int[] nums, int target) {
        //반복
        //절반 나눠서 target이 있을 수 있는 구간을 찾고
        //그 구간에서 정렬된 구간이고 target이 있을 수 있는 구간을 찾음
        int l = 0;
        int r = nums.length-1;
        while(l<=r){
            int m = (l+r)>>>1;
            if(target == nums[m]) return m;
            if(nums[l]<=nums[m]){
                if(nums[l]<=target && target < nums[m]){
                    r = m-1;
                }
                else{
                    l = m+1;
                }
            }
            else {
                if(nums[m]<target && target <= nums[r]){
                    l = m+1;
                }
                else{
                    r = m-1;
                }
            }
        }
        return -1;
    }
}