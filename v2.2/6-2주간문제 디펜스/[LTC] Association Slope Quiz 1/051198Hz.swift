class Solution {
    func nextLargerNodes(_ head: ListNode?) -> [Int] {
        var vals = [Int]()
        var node = head
        while let currentNode = node {
            vals.append(currentNode.val)
            node = currentNode.next
        }

        var answer = [Int](repeating: 0, count: vals.count)
        var stack = [Int]()
        for i in 0..<vals.count {
            while let last = stack.last, vals[last] < vals[i] {
                answer[last] = vals[i]
                stack.removeLast()
            }
            stack.append(i)
        }
        
        return answer
    }

    func checkSubarraySum(_ nums: [Int], _ k: Int) -> Bool {
        var prefixSum = [0]
        for i in 1...nums.count {
            let sum = prefixSum[i-1] + nums[i-1]
            prefixSum.append(sum)
        }

        // (prefixSum[j] - prefixSum[i]) % k = 0
        // prefixSum[j] % k = prefixSum[i] % k
        
        var sumMap = [0: 0]
        
        for i in 1..<prefixSum.count {
            if let index = sumMap[prefixSum[i] % k] {
                if i - index >= 2 {
                    return true
                }
            } else {
                sumMap[prefixSum[i] % k] = i
            }
        }

        return false
    }
}