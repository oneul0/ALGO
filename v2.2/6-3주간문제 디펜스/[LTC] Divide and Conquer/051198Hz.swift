class Solution {
    func beautifulArray(_ n: Int) -> [Int] {
        var arr = [1]

        while arr.count < n {
            var next = [Int]()

            for x in arr {
                let val = 2 * x - 1
                if val <= n {
                    next.append(val)
                }
            }

            for x in arr {
                let val = 2 * x
                if val <= n {
                    next.append(val)
                }
            }

            arr = next
        }

        return arr
    }

    func buildTree(_ inorder: [Int], _ postorder: [Int]) -> TreeNode? {

        var indexMap = [Int: Int]()

        for (i, num) in inorder.enumerated() {
            indexMap[num] = i
        }

        var postIdx = postorder.count - 1

        func build(_ left: Int, _ right: Int) -> TreeNode? {
            if left > right {
                return nil
            }

            let rootVal = postorder[postIdx]
            postIdx -= 1

            let root = TreeNode(rootVal)

            let idx = indexMap[rootVal]!

            root.right = build(idx + 1, right)
            root.left = build(left, idx - 1)

            return root
        }

        return build(0, inorder.count - 1)
    }

    func reversePairs(_ nums: [Int]) -> Int {
        var nums = nums
        var count = 0

        func mergeSort(_ left: Int, _ right: Int) {
            if left >= right { return }

            let mid = left + (right - left) / 2

            mergeSort(left, mid)
            mergeSort(mid + 1, right)

            var j = mid + 1

            for i in left...mid {
                while j <= right &&
                      Int64(nums[i]) > 2 * Int64(nums[j]) {
                    j += 1
                }

                count += j - (mid + 1)
            }

            var temp = [Int]()
            var i = left
            j = mid + 1

            while i <= mid && j <= right {
                if nums[i] <= nums[j] {
                    temp.append(nums[i])
                    i += 1
                } else {
                    temp.append(nums[j])
                    j += 1
                }
            }

            while i <= mid {
                temp.append(nums[i])
                i += 1
            }

            while j <= right {
                temp.append(nums[j])
                j += 1
            }

            for (idx, val) in temp.enumerated() {
                nums[left + idx] = val
            }
        }

        mergeSort(0, nums.count - 1)

        return count
    }
}