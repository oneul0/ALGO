class Solution {
    func merge(_ nums1: inout [Int], _ m: Int, _ nums2: [Int], _ n: Int) {
        var i = m - 1
        var j = n - 1
        var k = m + n - 1

        while j >= 0 {
            if i >= 0 && nums1[i] > nums2[j] {
                nums1[k] = nums1[i]
                i -= 1
            } else {
                nums1[k] = nums2[j]
                j -= 1
            }
            k -= 1
        }
    }

    func findRightInterval(_ intervals: [[Int]]) -> [Int] {
        let starts = intervals.enumerated()
            .map { ($0.element[0], $0.offset) }
            .sorted { $0.0 < $1.0 }

        var result = Array(repeating: -1, count: intervals.count)

        for (i, interval) in intervals.enumerated() {
            let end = interval[1]

            var left = 0
            var right = starts.count

            while left < right {
                let mid = left + (right - left) / 2

                if starts[mid].0 < end {
                    left = mid + 1
                } else {
                    right = mid
                }
            }

            if left < starts.count {
                result[i] = starts[left].1
            }
        }

        return result
    }
}