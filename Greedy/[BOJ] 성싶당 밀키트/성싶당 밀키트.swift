import Foundation

let first = readLine()!.split(separator: " ").map { Int($0)! }
let N = first[0]
let G = Int64(first[1])
let K = first[2]

var important: [(Int, Int)] = []
var removable: [(Int, Int)] = []

var maxL = 0

for _ in 0..<N {
    let line = readLine()!.split(separator: " ").map { Int($0)! }
    let S = line[0]
    let L = line[1]
    let O = line[2]

    maxL = max(maxL, L)

    if O == 0 {
        important.append((S, L))
    } else {
        removable.append((S, L))
    }
}

func canEat(_ x: Int) -> Bool {
    var total: Int64 = 0
    var cand: [Int64] = []

    for (s, l) in important {
        let days = max(1, x - l)
        total += Int64(s) * Int64(days)
        if total > G { return false }
    }

    for (s, l) in removable {
        let days = max(1, x - l)
        let b = Int64(s) * Int64(days)
        cand.append(b)
        total += b
    }

    if total <= G { return true }

    cand.sort(by: >)
    let limit = min(K, cand.count)

    var cur = total
    for i in 0..<limit {
        cur -= cand[i]
        if cur <= G {
            return true
        }
    }

    return false
}

var left = 0
var right = maxL + Int(G)
var answer = 0

while left <= right {
    let mid = (left + right) / 2
    if canEat(mid) {
        answer = mid
        left = mid + 1
    } else {
        right = mid - 1
    }
}

print(answer)