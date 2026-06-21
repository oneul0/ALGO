//Q1. Kth Largest Element in an Array
//왜 정렬하지 말고 풀라고 하는지........
//하지만 덕분에 퀵셀렉트를 배웠다
//기준이 되는 pivot을 임의로 잡고 그것보다 작으면 왼쪽으로 보내면서 정렬함
//반복하다가 pivot의 인덱스가(위치가) k번째가 되면 반환
class Solution {
    public int findKthLargest(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length-1, k-1);
    }
    public int quickSelect(int[] nums, int left, int right, int k){
        int pivotIdx = partition(nums, left, right);
        if(pivotIdx == k) return nums[pivotIdx];
        else if(k < pivotIdx) return quickSelect(nums,left, pivotIdx-1, k);
        else return quickSelect(nums, pivotIdx+1, right, k);
    }
    public int partition(int[] nums, int left, int right){
        int pivot = nums[right];
        int i = left;
        for(int j = left; j<right; j++){
            if(nums[j] > pivot){
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, right);

        return i;
    }
    public void swap(int[] nums, int i, int j){
        int tmp= nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}

//Q2. Sort an Array
//문제의 의도는 merge sort를 의도한거 같지만
//챗지피티가 힙정렬을 추천해서 힙정렬로 풀어본 문제
//완전이진트리라고 가정하고 인덱스 공식을 이용해서 해당 위치로 이동시키며 정렬
//max heap
class Solution {
    public int[] sortArray(int[] nums) {
        int n = nums.length;

        //max heap 만들기
        for(int i = n/2-1; i>=0 ; i--){
            heapify(nums, n, i);
        }
        for(int end = n-1; end>0; end--){
            swap(nums, 0, end); // 현재 최댓값을 맨 뒤로 이동
            heapify(nums,end, 0); //남은 구간에서 heap 성질 복구
        }

        return nums;
    }

    public void heapify(int[] nums, int size, int root){
        int largest = root;
        //완전 이진트리의 왼쪽 오른쪽 노드
        int left = root*2 + 1; // 그냥 공식임 완전이진트리라 가능
        int right = root*2 + 2;

        //root, left, right 중 큰 것 추출
        if(left < size && nums[left] > nums[largest]){
            largest = left;
        }
        if(right < size && nums[right] > nums[largest]){
            largest = right;
        }

        if(largest != root){
            swap(nums, root, largest);
            heapify(nums, size, largest);
        }

    }

    public void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


}


//Q3. Insertion Sort List
//삽입 정렬
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode insertionSortList(ListNode head) {
        ListNode sorted = new ListNode(-5001);

        while (head != null) {
            ListNode next = head.next;
            ListNode prev = sorted;

            while (prev.next != null && prev.next.val < head.val) {
                prev = prev.next;
            }

            head.next = prev.next;
            prev.next = head;

            head = next;
        }

        return sorted.next;
    }
}
//하나를 잡고
//그것보다 작은 수를 앞으로 보내는건가