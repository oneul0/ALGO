//Q1. Contains Duplicate II
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if(nums.length == 1) return false;
        Set<Integer> window =  new HashSet<>();
        for(int i = 0; i<nums.length; i++){
            if(window.contains(nums[i])) return true;
            window.add(nums[i]);
            if(i>=k) window.remove(nums[i-k]);
        }
        return false;
    }
}

//Q2. Number of Substrings Containing All Three Characters
class Solution {
    public int numberOfSubstrings(String s) {
        int[] lastIdx = {-1, -1, -1};
        int answer = 0;

        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            lastIdx[idx] = i;

            int minLastIdx = Math.min(lastIdx[0], Math.min(lastIdx[1], lastIdx[2]));

            if (minLastIdx != -1) {
                answer += minLastIdx + 1;
            }
        }

        return answer;
    }
}
//a b c 비트마스킹으로 하면 left가 확장될 때 처리 어려움


//Q3. Longest Repeating Character Replacement
class Solution {
    public int characterReplacement(String s, int k) {
        int[] count = new int[26];
        int left = 0;
        int maxCount = 0;
        int answer = 0;

        for(int right = 0; right<s.length(); right++){
            int curIdx = s.charAt(right)-'A';
            count[curIdx]++;

            maxCount = Math.max(maxCount, count[curIdx]);

            int windowLen = right - left + 1;

            //맨 왼쪽 문자열 제거
            if(windowLen - maxCount > k){
                int leftCharIdx = s.charAt(left)-'A';
                count[leftCharIdx]--;
                left++;
            }
            answer = Math.max(answer, right-left+1);
        }

        return answer;
    }
}
//구간의 알파벳 중 큰 것이 x라고 했을 때 구간의 길이 - x 가 k보다 작거나 같으면 가능