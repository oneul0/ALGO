class Solution {
    func deleteDuplicates(_ head: ListNode?) -> ListNode? {
        var isDuple = [Int: Bool]()
        var currentNode = head
        var prevNode = head

        while let node = currentNode {

            if !isDuple[node.val, default: false] {
                isDuple[node.val] = true
                prevNode = node
            } else {
                prevNode?.next = node.next
            }
            currentNode = node.next
        }

        return head
    }

    func oddEvenList(_ head: ListNode?) -> ListNode? {
        var evenNode: ListNode?
        var evenHead: ListNode?

        var currentNode = head
        var prevNode: ListNode?
        var isEven = false

        while let node = currentNode {
            if isEven {
                if evenHead == nil {
                    evenHead = node
                }
                if evenNode != nil {
                    evenNode?.next = node
                }
                evenNode = node
            } else {
                if prevNode != nil {
                    prevNode?.next = node
                }
                prevNode = node
            }
            currentNode = node.next
            isEven = !isEven
        }
        evenNode?.next = nil
        prevNode?.next = evenHead

        return head
    }
    
    func reverseList(_ head: ListNode?) -> ListNode? {
        var last: ListNode?
        func recursiveReverse(_ current: ListNode?, _ prev: ListNode?) {
            if current == nil {
                last = prev
                return
            }
            recursiveReverse(current?.next, current)
            current?.next = prev
        }

        recursiveReverse(head, nil)

        return last
    }
}