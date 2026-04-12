import Foundation

let N = Int(readLine()!)!
var corners = [(Int, Int)]()

for _ in 0..<N {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    corners.append((input[0], input[1]))
}

let M = Int(readLine()!)!
var holes = Array(repeating: Array(repeating: 0, count: 4), count: M)

for i in 0..<M {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    for j in 0..<4 {
        holes[i][j] = input[j]
    }
}

holes.sort {
    if $0[0] != $1[0] { return $0[0] < $1[0] }
    if $0[1] != $1[1] { return $0[1] < $1[1] }
    if $0[2] != $1[2] { return $0[2] < $1[2] }
    return $0[3] < $1[3]
}

// [[너비, 깊이]]
var horizons = Array(
    repeating: Array(repeating: 0, count: 2),
    count: N / 2 - 1
)

var holeIdx = [Int]()
var j = 0
var i = 1

while i < N - 1 {
    let idx = i / 2

    let width = corners[i + 1].0 - corners[i].0
    let depth = corners[i].1

    horizons[idx][0] = width
    horizons[idx][1] = depth

    if j < M && corners[i].0 == holes[j][0] && corners[i].1 == holes[j][1] {
        holeIdx.append(idx)
        j += 1
    }

    i += 2
}

var water = Array(repeating: 0, count: N / 2 - 1)

for idx in holeIdx {
    var h = horizons[idx][1]
    water[idx] = h

    // 왼쪽
    if idx - 1 >= 0 {
        for i in stride(from: idx - 1, through: 0, by: -1) {
            h = min(h, horizons[i][1])
            water[i] = max(water[i], h)
        }
    }

    // 오른쪽
    h = horizons[idx][1]
    if idx + 1 < horizons.count {
        for i in idx + 1..<horizons.count {
            h = min(h, horizons[i][1])
            water[i] = max(water[i], h)
        }
    }
}

var ans = 0

for i in 0..<horizons.count {
    ans += (horizons[i][1] - water[i]) * horizons[i][0]
}

print(ans)