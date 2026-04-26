class Solution {
    private var start: Int = 0
    private var end: Int = 0

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
