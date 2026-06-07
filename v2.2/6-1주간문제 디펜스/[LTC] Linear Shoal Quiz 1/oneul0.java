//Q1. Plus One
class Solution {
    public int[] plusOne(int[] digits) {
        for(int i = digits.length-1; i>=0; i--){
            if(digits[i] < 9){
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }
        int[] result = new int[digits.length+1];
        result[0] = 1;
        return result;
    }
}
//Q2. Valid Mountain Array
class Solution {
    public boolean validMountainArray(int[] arr) {
        if(arr.length < 3
            || arr[arr.length-2] <= arr[arr.length-1]
            || arr[0] >= arr[1]) return false;
        boolean decreasing = false;
        for(int i = 0; i<arr.length-1; i++){
            if(arr[i] == arr[i+1]) return false;
            if(decreasing){
                if(arr[i] <= arr[i+1]) return false;
            }
            else {
                if(arr[i] > arr[i+1]) decreasing = true;
            }
        }
        if(!decreasing) return false;
        return true;
    }
}
//decreasing이 true고 내림차순이면 continue
//decreasing이 true고 오름차순이면 false;

//decreasing이 false고 오름차순이면 continue

//decreasing이 false고 내림차순이면 decreasing = true
