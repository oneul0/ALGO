import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let N = input[0]
let K = input[1]
let R = input[2]

let dr = [-1, 1, 0, 0]
let dc = [0, 0, -1, 1]

var blocked = Array(
    repeating: Array(
        repeating: Array(repeating: false, count: 4),
        count: N
    ),
    count: N
)

for _ in 0..<R {
    let road = readLine()!.split(separator: " ").map { Int($0)! - 1 }
    let r1 = road[0], c1 = road[1]
    let r2 = road[2], c2 = road[3]

    if r1 == r2 {
        if c1 < c2 {
            blocked[r1][c1][3] = true
            blocked[r2][c2][2] = true
        } else {
            blocked[r1][c1][2] = true
            blocked[r2][c2][3] = true
        }
    } else {
        if r1 < r2 {
            blocked[r1][c1][1] = true
            blocked[r2][c2][0] = true
        } else {
            blocked[r1][c1][0] = true
            blocked[r2][c2][1] = true
        }
    }
}

var cows: [(Int, Int)] = []
for _ in 0..<K {
    let pos = readLine()!.split(separator: " ").map { Int($0)! - 1 }
    cows.append((pos[0], pos[1]))
}

var component = Array(repeating: Array(repeating: -1, count: N), count: N)
var compId = 0

for r in 0..<N {
    for c in 0..<N {
        if component[r][c] != -1 { continue }

        var queue = [(r, c)]
        component[r][c] = compId
        var idx = 0

        while idx < queue.count {
            let (cr, cc) = queue[idx]
            idx += 1

            for d in 0..<4 {
                if blocked[cr][cc][d] { continue }
                let nr = cr + dr[d]
                let nc = cc + dc[d]

                if nr < 0 || nr >= N || nc < 0 || nc >= N { continue }
                if component[nr][nc] != -1 { continue }

                component[nr][nc] = compId
                queue.append((nr, nc))
            }
        }

        compId += 1
    }
}

var cowCount = Array(repeating: 0, count: compId)
for (r, c) in cows {
    cowCount[component[r][c]] += 1
}

let totalPairs = K * (K - 1) / 2

var sameComponentPairs = 0
for count in cowCount {
    sameComponentPairs += count * (count - 1) / 2
}

print(totalPairs - sameComponentPairs)