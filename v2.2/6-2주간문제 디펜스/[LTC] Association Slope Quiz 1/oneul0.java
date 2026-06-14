//Q1. Next Greater Node In Linked List
//날씨, 온도, 주식 등 스택 이용한 비슷한 문제처럼 생각
//list로 옮겨서 인덱스 추적 가능하도록하고
//스택 이용해서 찾기
class Solution {
    public int[] nextLargerNodes(ListNode head) {
        List<ListNode> list = new ArrayList<>();

        while(head != null){
            list.add(head);
            head = head.next;
        }

        Deque<Integer> stack = new ArrayDeque<>();
        int[] answer = new int[list.size()];
        for(int i = 0; i<list.size(); i++){
            while(!stack.isEmpty()
                && list.get(stack.peek()).val < list.get(i).val){
                int idx = stack.pop();
                answer[idx] = list.get(i).val;
            }
            stack.push(i);
        }
        return answer;
    }
}

//Q2. Continuous Subarray Sum
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int remain = sum % k;

            // 같은 나머지를 이전에 본 적 있음
            if (map.containsKey(remain)) {
                int prev = map.get(remain);

                // 구간 길이가 2 이상인지 확인
                if (i - prev >= 2) {
                    return true;
                }
            }
            // 처음 보는 나머지만 저장
            else {
                map.put(remain, i);
            }
        }

        return false;
    }
}