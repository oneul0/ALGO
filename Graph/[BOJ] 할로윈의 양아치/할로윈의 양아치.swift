import Foundation

final class FastScanner {
    private var data: [UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile()) + [0]
    private var idx: Int = 0

    func readInt() -> Int {
        var num = 0
        while data[idx] == 10 || data[idx] == 13 || data[idx] == 32 {
            idx += 1
        }
        while data[idx] >= 48 {
            num = num * 10 + Int(data[idx] - 48)
            idx += 1
        }
        return num
    }
}

let scanner = FastScanner()

let N = scanner.readInt()
let M = scanner.readInt()
let K = scanner.readInt()

var candy = Array(repeating: 0, count: N + 1)
for i in 1...N {
    candy[i] = scanner.readInt()
}

var parent = Array(0...N)
var size = Array(repeating: 1, count: N + 1)
var candySum = Array(repeating: 0, count: N + 1)

for i in 1...N {
    candySum[i] = candy[i]
}

func find(_ x: Int) -> Int {
    var x = x
    while parent[x] != x {
        parent[x] = parent[parent[x]]
        x = parent[x]
    }
    return x
}

func union(_ a: Int, _ b: Int) {
    let pa = find(a)
    let pb = find(b)
    if pa == pb { return }
    parent[pb] = pa
    size[pa] += size[pb]
    candySum[pa] += candySum[pb]
}

for _ in 0..<M {
    union(scanner.readInt(), scanner.readInt())
}

var visited = Array(repeating: false, count: N + 1)
var singles: [Int] = []
var groups: [(Int, Int)] = []

for i in 1...N {
    let p = find(i)
    if !visited[p] {
        visited[p] = true
        if size[p] == 1 {
            singles.append(candySum[p])
        } else if size[p] < K {
            groups.append((size[p], candySum[p]))
        }
    }
}

let limit = K - 1
var dp = Array(repeating: 0, count: limit + 1)
var currentMax = 0

for (w, v) in groups {
    let upper = min(limit, currentMax + w)
    var i = upper
    while i >= w {
        dp[i] = max(dp[i], dp[i - w] + v)
        i -= 1
    }
    currentMax = upper
}

singles.sort(by: >)

var answer = dp.max()!
var remain = limit

for i in 0...limit {
    let used = i
    let canTake = min(remain - used, singles.count)
    if canTake > 0 {
        let extra = singles.prefix(canTake).reduce(0, +)
        answer = max(answer, dp[i] + extra)
    }
}

print(answer)