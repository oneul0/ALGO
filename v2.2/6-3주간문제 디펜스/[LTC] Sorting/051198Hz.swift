class Solution {
    func minimumAbsDifference(_ arr: [Int]) -> [[Int]] {
        var arr = arr.sorted { $0 < $1 }
        var pairs = [[Int]]()

        var minDiff = Int.max
        for i in 1..<arr.count {
            let a = arr[i-1]
            let b = arr[i]
            if b-a < minDiff {
                pairs = []
                minDiff = b-a
            }
            guard minDiff == b-a else { continue }
            pairs.append([a, b])
        }
        return pairs
    }

    func reductionOperations(_ nums: [Int]) -> Int {
        let nums = nums.sorted { $0 < $1 }
        var levels = 0
        var count = 0

        for i in 1..<nums.count {
            if nums[i-1] != nums[i] {
                levels += 1
            }
            count += levels
        }
        return count
    }

    private var start = 0
    private var end = 0

    func merge(_ intervals: [[Int]]) -> [[Int]] {
        var result = [[Int]]()
        var sortedIntervals = intervals.sorted { $0[0] < $1[0] }
        
        start = sortedIntervals[0][0]
        end = sortedIntervals[0][1]
        sortedIntervals.dropFirst()

        for interval in sortedIntervals {
            if interval[0] <= end {
                end = max(interval[1], end)
            } else {
                result.append([start, end])
                start = interval[0]
                end = interval[1]
            }
        }
        result.append([start, end])

        return result
    }
}