class Solution {
    func twoSum(_ nums: [Int], _ target: Int) -> [Int] {
        var indicesMap = [Int: Set<Int>]()
        for i in 0..<nums.count {
            indicesMap[nums[i], default: Set<Int>()].insert(i)
        }

        for i in 0..<nums.count {
            if indicesMap[target-nums[i]] != nil {
                indicesMap[target-nums[i], default: Set<Int>()].insert(i)
                guard indicesMap[target-nums[i]]!.count >= 2 else { continue }
                return indicesMap[target-nums[i], default: Set<Int>()].map { $0 }
            }
        }

        return []
    }

    func copyRandomList(_ head: Node?) -> Node? {
        var currentNode = head
        var copyHead: Node?
        var nodeMap = [Node?: Node]()

        while let node = currentNode {
            nodeMap[node] = Node(node.val)
            currentNode = node.next
        }
        
        currentNode = head

        while let node = currentNode {
            let copyNode = nodeMap[node]

            if copyHead == nil {
                copyHead = copyNode
            }
            if copyCurrentNode == nil {
                copyCurrentNode = copyNode
            }
            let nextNode = node.next
            let randomNode = node.random

            copyNode?.next = nodeMap[nextNode]
            copyNode?.random = nodeMap[randomNode]

            currentNode = node.next
        }

        return copyHead
    }

    func firstMissingPositive(_ nums: [Int]) -> Int {
        var nums = nums
        let n = nums.count
        
        for i in 0..<n {
            while nums[i] > 0 &&
                nums[i] <= n &&
                nums[i] != nums[nums[i] - 1] {
                    nums.swapAt(i, nums[i] - 1)
                }
        }

        for i in 0..<n {
            if nums[i] != i+1 {
                return i+1
            }
        }

        return n+1

        // var isOneAppeared = false
        // var isInNums = [Int: Bool]()
        // for num in nums {
        //     guard num > 0 else { continue }
        //     isInNums[num] = true
        //     if num == 1 {
        //         isOneAppeared = true
        //     }
        // }

        // var minPositive = Int.max
        // for (key, value) in isInNums {
        //     if isInNums[key-1] == nil, key-1 > 0 {
        //         minPositive = min(key-1, minPositive)
        //     }
        //     if isInNums[key+1] == nil {
        //         minPositive = min(key+1, minPositive)
        //     }
        // }

        // if minPositive > 1 && isOneAppeared {
        //     return minPositive
        // }

        // return 1
    }
}