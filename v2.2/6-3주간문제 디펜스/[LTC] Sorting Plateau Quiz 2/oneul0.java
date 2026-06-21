//Q1. Minimum Number of Days to Make m Bouquets
//부케를 만들 수 있는 날짜 중 가장 작은 날짜를 구해야하므로
//가장 작은 날~가장 오래 기다려야하는 날 을 범위로 이분탐색
class Solution {
    public int minDays(int[] bloomDay, int m, int k) {
        if ((long) m * k > bloomDay.length) return -1;

        int minVal = Arrays.stream(bloomDay).min().getAsInt();
        int maxVal = Arrays.stream(bloomDay).max().getAsInt();

        return findMinDay(bloomDay, minVal, maxVal, m, k);
    }

    public int findMinDay(int[] bloomDay, int l, int r, int m, int k) {
        while (l < r) {
            int mid = l + (r - l) / 2;

            if (canMake(bloomDay, mid, m, k)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        return l;
    }

    public boolean canMake(int[] bloomDay, int day, int m, int k) {
        int bouquets = 0;
        int flowers = 0;

        for (int bloom : bloomDay) {
            if (bloom <= day) {
                flowers++;

                if (flowers == k) {
                    bouquets++;
                    flowers = 0;
                }
            } else {
                flowers = 0;
            }
        }

        return bouquets >= m;
    }
}

//Q2. Merge k Sorted Lists
//Comparable 구현체이므로 pq로 간단하게 해결 가능
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
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>(
            (a, b) -> a.val - b.val
        );

        for (ListNode node : lists) {
            if (node != null) {
                pq.offer(node);
            }
        }

        ListNode tmp = new ListNode(0);
        ListNode cur = tmp;

        while (!pq.isEmpty()) {
            ListNode node = pq.poll();

            cur.next = node;
            cur = cur.next;

            if (node.next != null) {
                pq.offer(node.next);
            }
        }

        return tmp.next;
    }
}