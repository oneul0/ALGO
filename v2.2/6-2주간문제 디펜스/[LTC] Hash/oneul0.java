//Q1. Two Sum
//O(n^2) 솔루션
class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int i=1; i < nums.length; i++) {
            for (int j=i; j < nums.length; j++) {
                if (nums[j]+nums[j - i] == target){
                    return new int[] {j, j - i};
                }
            }
        }
        return null;
    }
}
//O(n) 솔루션
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for(int i = 0; i < nums.length; i++) {
            int need = target - nums[i];

            if(map.containsKey(need)) {
                return new int[]{map.get(need), i};
            }

            map.put(nums[i], i);
        }

        return new int[]{};
    }
    //요소 크기가 너무 크므로 배열 사용 불가
    //인덱스와 value 기록해놓고 가져오기
}

//Q2. Copy List with Random Pointer
//노드가 갖고 있는 값을 모두 찾아놓고
//그 다음에 깊은 복사
class Solution {
    public Node copyRandomList(Node head) {
        Map<Node, Node> map = new HashMap<>();
        Node cur = head;
        while(cur != null){
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }

        cur = head;
        while(cur != null){
            Node tmp = map.get(cur);
            tmp.next = map.get(cur.next);
            tmp.random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(head);
    }
}
//Q3. First Missing Positive
//nums는 0-index
//nums 배열의 인덱스에 위치하는 요소가 맞을 때까지 swap시킴
//이후 인덱스 위치에 숫자가 일치하지 않으면 실패
class Solution {
    public int firstMissingPositive(int[] nums) {
        for(int i = 0; i<nums.length; i++){
            while (
                nums[i] >= 1 &&
                    nums[i] <= nums.length &&
                    nums[i] != nums[nums[i]-1]
            ) {
                int val = nums[i];
                nums[i] = nums[val-1];
                nums[val-1] = val;
            }
        }
        for(int i = 0; i<nums.length; i++){
            if(i != nums[i]-1) return i+1;
        }
        return nums.length+1;
    }
}

//정렬 안된 정수 배열이 주어질 때, nums에 없는 가장 작은 양수 정수 반환
//상수 시간, 추가 공간 x?


