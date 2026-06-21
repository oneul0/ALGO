//Q1. Merge Sorted Array
//정렬이 되어있으므로 하나씩 뽑아서 채움
//O(m + n) 풀이를 위해서..
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] merged = new int[m + n];
        int idx1 = 0, idx2 = 0;
        int midx = 0;

        while (idx1 < m && idx2 < n) {
            if (nums1[idx1] > nums2[idx2]) {
                merged[midx++] = nums2[idx2++];
            } else {
                merged[midx++] = nums1[idx1++];
            }
        }

        if (idx1 < m) {
            for (int i = idx1; i < m; i++) {
                merged[midx++] = nums1[i];
            }
        }

        if (idx2 < n) {
            for (int i = idx2; i < n; i++) {
                merged[midx++] = nums2[i];
            }
        }

        for (int i = 0; i < m + n; i++) {
            nums1[i] = merged[i];
        }
    }
}

//Q2. Find Right Interval
//ceilingKey를 이용해서 간단하게 x 이상인 key 중 가장 작은 key를 찾을 수 있음. 자바 문법적인 부분 더 익혀야할듯

class Solution {
    public int[] findRightInterval(int[][] intervals) {
        int n = intervals.length;
        int[] answer = new int[n];

        TreeMap<Integer, Integer> map = new TreeMap<>();

        for (int i = 0; i < n; i++) {
            map.put(intervals[i][0], i);
        }

        for (int i = 0; i < n; i++) {
            Integer key = map.ceilingKey(intervals[i][1]);

            if (key == null) {
                answer[i] = -1;
            } else {
                answer[i] = map.get(key);
            }
        }

        return answer;
    }
}
