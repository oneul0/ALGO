//Q1. Linked List Cycle
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
}

//Q2. 3Sum Closest

//느린 풀이
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int answer = nums[0] + nums[1] + nums[2];

        for(int i = 0; i<nums.length-2; i++){
            for(int j = i+1; j<nums.length-1; j++){
                int need = target - nums[i] -nums[j];

                int l = j+1;
                int r = nums.length-1;
                while(l<=r){
                    int mid = (l+r)/2;

                    int sum = nums[i] + nums[j] + nums[mid];

                    if(Math.abs(sum - target) < Math.abs(answer - target)){
                        answer = sum;
                    }

                    if(nums[mid] < need){
                        l = mid+1;
                    }
                    else{
                        r = mid-1;
                    }
                }
            }
        }

        return answer;
    }
}

//개선 풀이
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int answer = nums[0] + nums[1] + nums[2];

        for(int i = 0; i<nums.length-2; i++){
            int l = i+1;
            int r = nums.length-1;
            while(l<r){
                int sum = nums[l] + nums[r] + nums[i];

                if(Math.abs(sum - target) < Math.abs(answer - target)){
                    answer = sum;
                }

                if(sum < target){
                    l++;
                }
                else{
                    r--;
                }
            }
        }
        return answer;
    }
}

//Q3. Magical String
class Solution {
    public int magicalString(int n) {
        if(n <= 3) return 1;
        int[] arr = new int[n+2];
        arr[0] = 1;
        arr[1] = arr[2] = 2;

        int head = 2;
        int tail = head;
        int answer = 1;

        while(tail< n){
            int count = arr[head];
            int val = 3-arr[tail];

            for(int i = 0; i<count; i++){
                arr[++tail] = val;
                if(tail<n && val == 1) answer++;
            }
            head++;
        }
        return answer;
    }
}