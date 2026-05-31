//Array I
//Q1. Concatenation of Array
//배열 붙이기
class Solution {
    public int[] getConcatenation(int[] nums) {
        int n = nums.length;
        int[] newArr = new int[n+n];
        for(int i = 0; i<n+n; i++){
            newArr[i] = nums[i%n];
        }
        return newArr;
    }
}

//Q2. Shuffle the Array
//배열 섞기
class Solution {
    public int[] shuffle(int[] nums, int n) {
        int[] newArr = new int[nums.length];
        int idx = 0;
        for(int i = 0; i<n; i++){
            newArr[idx++] = nums[i];
            newArr[idx++] = nums[i+n];
        }
        return newArr;
    }
}

//Q3. Max Consecutive Ones
//연속된 1이 가장 많은 길이
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int ans = 0;
        int sum = 0;
        for(int n : nums){
            if(n == 1) {
                sum+=1;
                ans = Math.max(ans, sum);
            }
            else sum = 0;
        }
        return ans;
    }
}