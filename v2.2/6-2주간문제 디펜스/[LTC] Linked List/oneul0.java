//Q1. Remove Duplicates from Sorted List
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode cur = head;

        while (cur != null && cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }

        return head;
    }
}

//Q2. Odd Even Linked List
class Solution {
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;

        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;

            even.next = odd.next;
            even = even.next;
        }

        odd.next = evenHead;

        return head;
    }
}
//조건을 만족하는 가장 그룹 찾고 순차적으로 이어붙이기
//끼워넣으면 되지 않을까


//Q3. Reverse Linked List
class Solution {
    //재귀로 풀기
    // public ListNode reverseList(ListNode head) {
    //     if (head == null || head.next == null) {
    //         return head;
    //     }

    //     ListNode newHead = reverseList(head.next);

    //     head.next.next = head;
    //     head.next = null;

    //     return newHead;
    // }
    //반복문으로 풀기
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        while(cur != null){
            //뒤집기
            ListNode next = cur.next;
            cur.next = prev;
            //한칸이동
            prev = cur;
            cur = next;
        }

        return prev;
    }
}