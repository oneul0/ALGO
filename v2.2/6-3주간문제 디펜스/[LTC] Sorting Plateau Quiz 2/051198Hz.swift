class Solution {
    func minDays(_ bloomDay: [Int], _ m: Int, _ k: Int) -> Int {
        let n = bloomDay.count

        if m * k > n {
            return -1
        }

        var left = bloomDay.min()!
        var right = bloomDay.max()!

        func canMake(_ day: Int) -> Bool {
            var bouquets = 0
            var consecutive = 0

            for bloom in bloomDay {
                if bloom <= day {
                    consecutive += 1

                    if consecutive == k {
                        bouquets += 1
                        consecutive = 0

                        if bouquets >= m {
                            return true
                        }
                    }
                } else {
                    consecutive = 0
                }
            }

            return false
        }

        while left < right {
            let mid = left + (right - left) / 2

            if canMake(mid) {
                right = mid
            } else {
                left = mid + 1
            }
        }

        return left
    }
    
    func mergeKLists(_ lists: [ListNode?]) -> ListNode? {
        guard !lists.isEmpty else { return nil }
        return divide(lists, 0, lists.count - 1)
    }

    private func divide(_ lists: [ListNode?], _ left: Int, _ right: Int) -> ListNode? {
        if left == right {
            return lists[left]
        }
    
        let mid = left + (right - left) / 2
    
        let l1 = divide(lists, left, mid)
        let l2 = divide(lists, mid + 1, right)
    
        return mergeTwoLists(l1, l2)
    }

    private func mergeTwoLists(_ l1: ListNode?, _ l2: ListNode?) -> ListNode? {
        let dummy = ListNode()
        var tail: ListNode? = dummy

        var p1 = l1
        var p2 = l2

        while let node1 = p1, let node2 = p2 {
            if node1.val <= node2.val {
                tail?.next = node1
                p1 = node1.next
            } else {
                tail?.next = node2
                p2 = node2.next
            }

            tail = tail?.next
        }

        tail?.next = p1 ?? p2

        return dummy.next
    }
}