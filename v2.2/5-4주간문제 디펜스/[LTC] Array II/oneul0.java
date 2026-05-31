//Array II
//Q1. Set Mismatch
//잘못 적힌 부분 찾기
//오름차순일수도 있고 내림차순일수도 있음
//Answer
class Solution {
    public int[] findErrorNums(int[] nums) {
        int n = nums.length;
        int[] cnt = new int[n+1];

        for (int num : nums) {
            cnt[num]++;
        }

        int duplicate = 0;
        int missing = 0;

        for (int i=1; i<=n; i++) {
            if (cnt[i] == 2) duplicate = i;
            if (cnt[i] == 0) missing = i;
        }

        return new int[]{duplicate, missing};
    }
}
//Wrong
//처음엔 오름차순만 고려해서 내림차순엔 대응이 안됐음
class Solution {
    public int[] findErrorNums(int[] nums) {
        int curVal = 1;
        for(int i = 0; i<nums.length; i++){
            if(curVal != nums[i]) return new int[]{nums[i], curVal};
            curVal++;
        }
        return new int[]{0,0};
    }
}

//Q2. How Many Numbers Are Smaller Than the Current Number
//nums[i]는 100이하이므로 모든 수를 다 검사해볼 수 있음
class Solution {
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] count = new int[101];
        for(int n : nums){
            count[n]++;
        }
        for(int i = 1; i<=100; i++){
            count[i] = count[i]+count[i-1];
        }
        int[] result = new int[nums.length];
        for(int i = 0; i<nums.length; i++){
            result[i] = nums[i] == 0 ? 0 : count[nums[i]-1];
        }
        return result;
    }
}

//Q3. Find All Numbers Disappeared in an Array
//등장하지 않은 숫자 찾기
class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        int n = nums.length+1;
        boolean[] chk = new boolean[n+1];
        for(int i : nums){
            chk[i] = true;
        }
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i<n; i++){
            if(!chk[i]) list.add(i);
        }
        return list;
    }
}

//별도 자료구조 사용하지 않고 O(1)로 가능하게 하려면 nums 배열만을 이용해서 푸는 방법이 있음
//nums[i] 는 양수 범위만 주어지므로 nums 원본 배열을 음수로 변경하여 방문체크 할 수 있음
class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        for (int num : nums) {
            int index = Math.abs(num) - 1;

            if (nums[index] > 0) {
                nums[index] = -nums[index];
            }
        }

        List<Integer> answer = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                answer.add(i + 1);
            }
        }

        return answer;
    }
}