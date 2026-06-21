//Q1. Beautiful Array
//nums[k] == (nums[i] + nums[j])/2;
//어떤 beautiful array가 있을때, 그 배열의 짝수, 홀수 인덱스를 뽑아 만든 두 arr도  beautiful array임

class Solution {
    public int[] beautifulArray(int n) {
        List<Integer> result = new ArrayList<>();
        result.add(1);

        while(result.size() < n){
            List<Integer> next = new ArrayList<>();

            for(int x : result){
                int odd = x*2-1;
                if(odd <= n){
                    next.add(odd);
                }
            }
            for(int x : result){
                int even = x*2;
                if(even <= n){
                    next.add(even);
                }
            }
            result = next;
        }

        int[] answer = new int[n];
        for(int i = 0; i<n; i++){
            answer[i] = result.get(i);
        }
        return answer;
    }
}

//Q2. Construct Binary Tree from Inorder and Postorder Traversal
//postorder의 마지막 값은 항상 현재 서브트리의 루트
//따라서 postorder의 마지막 값을 기준으로 재구성해야하므로 postorder는 역순으로,
// inorder는 정방향으로 접근
//또한 재귀적으로 서브트리의 리프노드까지 내려가며 구성
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    int postIdx;
    Map<Integer,Integer> inorderIdxMap = new HashMap<>();
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        postIdx = postorder.length-1;

        for(int i = 0; i<inorder.length; i++){
            inorderIdxMap.put(inorder[i], i);
        }
        return build(inorder, postorder, 0, inorder.length-1);
    }

    public TreeNode build(int[] inorder, int[] postorder, int left, int right){
        if(left > right) return null;

        int rootVal = postorder[postIdx];
        postIdx--;

        TreeNode root = new TreeNode(rootVal);

        int rootIdx = inorderIdxMap.get(rootVal);

        root.right = build(inorder, postorder, rootIdx+1, right);
        root.left = build(inorder, postorder, left, rootIdx-1);

        return root;
    }
}

//Q3. Reverse Pairs
//감도 못 잡았던 문제.. 아직도 어려움
class Solution {
    public int reversePairs(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] tmp = new int[nums.length];
        return mergeSort(nums, tmp, 0, nums.length - 1);
    }

    private int mergeSort(int[] nums, int[] tmp, int left, int right) {
        if (left >= right) {
            return 0;
        }

        int mid = left + (right - left) / 2;

        int count = 0;
        count += mergeSort(nums, tmp, left, mid);
        count += mergeSort(nums, tmp, mid + 1, right);

        // 왼쪽 구간과 오른쪽 구간 사이의 reverse pair 개수 세기
        int j = mid + 1;

        for (int i = left; i <= mid; i++) {
            while (j <= right && (long) nums[i] > 2L * nums[j]) {
                j++;
            }

            count += j - (mid + 1);
        }

        // 정렬된 두 구간 merge
        merge(nums, tmp, left, mid, right);

        return count;
    }

    private void merge(int[] nums, int[] tmp, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                tmp[k] = nums[i];
                i++;
            } else {
                tmp[k] = nums[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            tmp[k] = nums[i];
            i++;
            k++;
        }

        while (j <= right) {
            tmp[k] = nums[j];
            j++;
            k++;
        }

        for (int p = left; p <= right; p++) {
            nums[p] = tmp[p];
        }
    }
}

