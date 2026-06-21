class Solution {
    func findKthLargest(_ nums: [Int], _ k: Int) -> Int {
        var nums = nums
        let target = nums.count - k

        func partition(_ left: Int, _ right: Int) -> Int {
            let pivot = nums[right]
            var i = left

            for j in left..<right {
                if nums[j] <= pivot {
                    nums.swapAt(i, j)
                    i += 1
                }
            }

            nums.swapAt(i, right)
            return i
        }

        var left = 0
        var right = nums.count - 1

        while true {
            let pivotIndex = partition(left, right)

            if pivotIndex == target {
                return nums[pivotIndex]
            } else if pivotIndex < target {
                left = pivotIndex + 1
            } else {
                right = pivotIndex - 1
            }
        }
    }

    func sortArray(_ nums: [Int]) -> [Int] {
        var nums = nums
        let n = nums.count

        func heapify(_ i: Int, _ heapSize: Int) {
            var largest = i
            let left = 2 * i + 1
            let right = 2 * i + 2

            if left < heapSize && nums[left] > nums[largest] {
                largest = left
            }

            if right < heapSize && nums[right] > nums[largest] {
                largest = right
            }

            if largest != i {
                nums.swapAt(i, largest)
                heapify(largest, heapSize)
            }
        }

        for i in stride(from: n / 2 - 1, through: 0, by: -1) {
            heapify(i, n)
        }

        for i in stride(from: n - 1, to: 0, by: -1) {
            nums.swapAt(0, i)
            heapify(0, i)
        }

        return nums
    }

    func insertionSortList(_ head: ListNode?) -> ListNode? {
        let dummy: ListNode? = ListNode(0)

        var curr = head

        while let node = curr {
            let next = node.next

            var prev = dummy
            while let p = prev?.next, p.val < node.val {
                prev = p
            }

            node.next = prev?.next
            prev?.next = node

            curr = next
        }

        return dummy?.next
    }
}

class GorgeousSolution {
    func findKthLargest(_ nums: [Int], _ k: Int) -> Int {
        // Find min and max values
        var minValue = Int.max
        var maxValue = Int.min
        
        for num in nums {
            minValue = min(minValue, num)
            maxValue = max(maxValue, num)
        }
        
        // Frequency array
        var count = Array(
            repeating: 0,
            count: maxValue - minValue + 1
        )
        
        // Store frequencies
        for num in nums {
            
            let index = num - minValue
            
            count[index] += 1
        }
        
        var k = k
        
        // Traverse from largest to smallest
        for i in stride(
            from: count.count - 1,
            through: 0,
            by: -1
        ) {
            
            k -= count[i]
            
            if k <= 0 {
                return i + minValue
            }
        }
        
        return -1
    }

// lastNode까지는 이미 정렬되어 있다고 가정하고, 현재 노드가 순서를 깨는 경우에만 앞쪽에 재삽입한다는 방식
    func sortNodes(_ head: ListNode?, _ node: ListNode) -> ListNode? {
        var curr = head
        var prev: ListNode?

        while let c = curr {
            if c.val > node.val {
                if c === head {
                    node.next = head
                    return node
                }

                prev?.next = node
                node.next = c
                return head
            }

            prev = curr
            curr = curr?.next
        }

        prev?.next = node
        node.next = nil

        return head
    }

    func insertionSortList(_ head: ListNode?) -> ListNode? {
        var newHead = head
        var lastNode = head
        var currNode = head?.next

        while currNode != nil {
            let node = currNode
            currNode = node?.next

            if let lNode = lastNode {
                if node!.val < lNode.val {
                    // Remove curr node
                    lastNode?.next = node!.next
                    newHead = sortNodes(newHead, node!)
                    continue
                }
            }

            lastNode = node
        }

        return newHead
    }
}