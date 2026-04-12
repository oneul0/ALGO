import Foundation

let NK = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    N = NK[0],
    K = NK[1]

let scoreList = readLine()!.split { $0 == " " }.map { Int(String($0))! }
let maxScore = scoreList.reduce(0, +)
let answer = binSearch(0, maxScore)

print(answer)

func binSearch(_ start: Int, _ end: Int) -> Int {
    var start = start
    var end = end
    var minScore = 0

    while start <= end {
        let mid = (start + end) / 2
        let isGroupable = grouping(mid)
        if !isGroupable {
            end = mid - 1
        }
        if isGroupable {
            minScore = mid
            start = mid + 1
        }
    }
    return minScore
}

func grouping(_ minSum: Int) -> Bool {
    var groupCount = 0
    var currentSum = 0

    for score in scoreList {
        currentSum += score
        guard currentSum >= minSum else { continue }
        currentSum = 0
        groupCount += 1
    }

    return groupCount >= K
}