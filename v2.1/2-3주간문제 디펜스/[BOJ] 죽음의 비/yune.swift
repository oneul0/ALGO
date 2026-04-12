import Foundation

let dr = [-1, 1, 0, 0]
let dc = [0, 0, -1, 1]
let NHK = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    N = NHK[0],
    H = NHK[1],
    D = NHK[2]

var grid = [[Character]]()
var S = (0, 0)
var E = (0, 0)

for r in 0..<N {
    let line = Array(readLine()!)
    grid.append(line)
    for c in 0..<N {
        if line[c] == "S" {
            S = (r, c)
        }
        if line[c] == "E" {
            E = (r, c)
        }
    }
}

var visited = Array(
    repeating: Array(repeating: -1, count: N),
    count: N
)

func bfs() -> Int {
    var queue = [(r: Int, c: Int, hp: Int, d: Int, move: Int)]()
    var index = 0

    queue.append((S.0, S.1, H, 0, 0))
    visited[S.0][S.1] = H

    while index < queue.count {
        let cur = queue[index]
        index += 1

        for i in 0..<4 {
            let nr = cur.r + dr[i]
            let nc = cur.c + dc[i]

            if !(0..<N ~= nr && 0..<N ~= nc) { continue }

            if nr == E.0 && nc == E.1 {
                return cur.move + 1
            }

            var nextHP = cur.hp
            var nextD = cur.d

            // 이동 후 우산 교체 여부 먼저 처리
            if grid[nr][nc] == "U" {
                nextD = D
            }

            // 비 맞기 처리
            if nextD > 0 {
                nextD -= 1
            } else {
                nextHP -= 1
            }

            if nextHP <= 0 { continue }

            let total = nextHP + nextD

            // 지배(pruning) 조건
            if visited[nr][nc] >= total { continue }
            visited[nr][nc] = total

            queue.append((nr, nc, nextHP, nextD, cur.move + 1))
        }
    }

    return -1
}

print(bfs())