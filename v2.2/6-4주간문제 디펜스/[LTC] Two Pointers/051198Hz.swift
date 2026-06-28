class Solution {
    func hasCycle(_ head: ListNode?) -> Bool {
        var slow = head
        var fast = head

        while fast != nil && fast?.next != nil {
            slow = slow?.next
            fast = fast?.next?.next

            if slow === fast {
                return true
            }
        }

        return false
    }

    func threeSumClosest(_ nums: [Int], _ target: Int) -> Int {
        let nums = nums.sorted { $0 < $1 }
        var result = nums[0] + nums[1] + nums[2]

        for i in 0..<nums.count {
            var left = i+1
            var right = nums.count-1
            while left < right {
                let currentSum = nums[left] + nums[i] + nums[right]
                if abs(target - result) > abs(target - currentSum) {
                    result = currentSum
                }
                if currentSum < target {
                    left += 1
                    continue
                }
                if currentSum > target {
                    right -= 1
                    continue
                }
                return target
            }
        }
        return result
    }
    
    func magicalString(_ n: Int) -> Int {
        var s = [1, 2, 2]
        var read = 2
        var next = 1
        var oneCount = 1

        while s.count < n {
            let repeatCount = s[read]

            for _ in 0..<repeatCount {
                if s.count == n {
                    break
                }
                s.append(next)
                if next == 1 {
                    oneCount += 1
                }
            }

            next = (next == 1) ? 2 : 1
            read += 1
        }
        return oneCount
    }
}