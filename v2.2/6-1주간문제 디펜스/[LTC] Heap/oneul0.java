//Q1. Last Stone Weight
class Solution {
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int s : stones) {
            pq.offer(s);
        }
        while (pq.size() > 1) {
            int x = pq.poll();
            int y = pq.poll();
            if (x != y) pq.offer(Math.abs(x - y));
        }
        return pq.isEmpty() ? 0 : pq.poll();
    }
}
//Q2. Find K Pairs with Smallest Sums
class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>();

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            int sumA = nums1[a[0]] + nums2[a[1]];
            int sumB = nums1[b[0]] + nums2[b[1]];
            return Integer.compare(sumA, sumB);
        });

        int n = nums1.length;
        int m = nums2.length;

        for (int i = 0; i < Math.min(n, k); i++) {
            pq.offer(new int[]{i, 0});
        }

        while (k > 0 && !pq.isEmpty()) {
            int[] cur = pq.poll();

            int i = cur[0];
            int j = cur[1];

            result.add(Arrays.asList(nums1[i], nums2[j]));
            k--;

            if (j + 1 < m) {
                pq.offer(new int[]{i, j + 1});
            }
        }

        return result;
    }
}
//Q3. Construct Target Array With Multiple Sums
import java.util.*;

class Solution {
    public boolean isPossible(int[] target) {
        if (target.length == 1) {
            return target[0] == 1;
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        long sum = 0;

        for (int num : target) {
            pq.offer(num);
            sum += num;
        }

        while (true) {
            int max = pq.poll();
            long rest = sum - max;

            if (max == 1) return true;

            if (rest == 1) return true;

            if (rest == 0 || max <= rest) return false;

            long prev = max % rest;

            if (prev == 0) return false;

            sum = rest + prev;
            pq.offer((int) prev);
        }
    }
}