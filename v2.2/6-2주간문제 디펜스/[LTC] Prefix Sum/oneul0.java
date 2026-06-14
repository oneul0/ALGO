//Q1. Find the Highest Altitude
class Solution {
    public int largestAltitude(int[] gain) {
        int val= 0;
        int maxVal = 0;
        for(int i : gain){
            val += i;
            maxVal = Math.max(val, maxVal);
        }
        return maxVal;
    }
}
//Q2. Make Sum Divisible by P
//너무 어려웠던 문제..
//수학적으로 나머지 연산 법칙을 충실하게 따라서 방정식을 구해야함
class Solution {
    public int minSubarray(int[] nums, int p) {
        int n = nums.length;

        // 1. 전체 배열의 합의 나머지 구하기
        long totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        int target = (int) (totalSum % p);

        if (target == 0) return 0;

        // 2. 해시맵 초기화
        // Key: 누적합 % p, Value: 해당 나머지가 나타난 가장 최근 인덱스
        HashMap<Integer, Integer> modMap = new HashMap<>();

        modMap.put(0, -1);

        int currentSumMod = 0;
        int minLength = n; // 가장 짧은 길이를 찾기 위해 일단 최대값으로 초기화

        // 3. 배열 순회하며 누적합의 나머지 계산하고 매칭되는 과거 기록 찾기
        for (int i = 0; i < n; i++) {
            currentSumMod = (currentSumMod + nums[i]) % p;

            // 우리가 과거에서 찾아야 하는 나머지 값 계산
            int neededMod = (currentSumMod - target + p) % p;

            // 만약 과거에 해당 나머지가 나온 적이 있다면 그때의 인덱스와 현재 인덱스의 차이 구하기
            if (modMap.containsKey(neededMod)) {
                minLength = Math.min(minLength, i - modMap.get(neededMod));
            }

            // 현재의 나머지 위치를 갱신(가장 최근 인덱스로 덮어쓰기)
            modMap.put(currentSumMod, i);
        }

        // 4. 예외 처리 및 결과 반환
        // 만약 전체 배열을 다 지워야만 하거나(minLength == n), 조건을 만족하는 구간을 못 찾았다면 -1
        return minLength == n ? -1 : minLength;
    }
}
/*
남은 원소들의 합이 p로 나누어 떨어지게 만드는 가장 짧은 부분 배열의 길이를 구해야 함
그래서 누적합의 나머지를 구해서 필요한 값이 가장 최근에 나온 위치를 체크해서 현재 위치와의 거리를 계산해야 함
*/

//Q3. Ways to Make a Fair Array
//요소 하나를 지울 때
//짝수 인덱스 합과
//홀수 인덱스 합이 같도록
//선택한 인덱스 후로 짝/홀이 뒤집힘
class Solution {
    public int waysToMakeFair(int[] nums) {
        if(nums.length == 1) return 1;
        int n = nums.length;
        int[] evenSum = new int[n];
        int[] oddSum = new int[n];
        evenSum[0] = evenSum[1] = nums[0];
        oddSum[0] = 0;
        oddSum[1] = nums[1];
        for(int i = 2; i<n; i++){
            if(i%2 == 0){
                evenSum[i] = evenSum[i-1]+nums[i];
                oddSum[i] = oddSum[i-1];
            }
            else{
                oddSum[i] = oddSum[i-1]+nums[i];
                evenSum[i] = evenSum[i-1];
            }
        }
        int count = 0;
        for(int i = 0; i<n; i++){
            int leftEven = (i == 0 ? 0 : evenSum[i-1]);
            int leftOdd  = (i == 0 ? 0 : oddSum[i-1]);

            int rightEven = evenSum[n-1] - evenSum[i];
            int rightOdd  = oddSum[n-1] - oddSum[i];

            int newEven = leftEven + rightOdd;
            int newOdd  = leftOdd + rightEven;

            if (newEven == newOdd) count++;
        }
        return count;
    }
}
