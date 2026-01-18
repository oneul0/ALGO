import Foundation

let nm = readLine()!.split(separator: " ").map { Int($0)! }
let N = nm[0], M = nm[1]

var grid = Array(repeating: Array(repeating: 0, count: M + 1), count: N + 1)
for i in 1...N {
    let row = readLine()!.split(separator: " ").map { Int($0)! }
    for j in 1...M {
        grid[i][j] = row[j - 1]
    }
}

let info = readLine()!.split(separator: " ").map { Int($0)! }
let H = info[0], W = info[1]
let Sr = info[2], Sc = info[3]
let Fr = info[4], Fc = info[5]

var psum = Array(repeating: Array(repeating: 0, count: M + 1), count: N + 1)
for i in 1...N {
    for j in 1...M {
        psum[i][j] = grid[i][j]
            + psum[i - 1][j]
            + psum[i][j - 1]
            - psum[i - 1][j - 1]
    }
}

func hasWall(_ r: Int, _ c: Int) -> Bool {
    let r2 = r + H - 1
    let c2 = c + W - 1
    let sum = psum[r2][c2]
        - psum[r - 1][c2]
        - psum[r2][c - 1]
        + psum[r - 1][c - 1]
    return sum > 0
}

var visited = Array(repeating: Array(repeating: false, count: M + 1), count: N + 1)
var queue: [(Int, Int, Int)] = []
var head = 0

queue.append((Sr, Sc, 0))
visited[Sr][Sc] = true

let dr = [-1, 1, 0, 0]
let dc = [0, 0, -1, 1]

var answer = -1

while head < queue.count {
    let (r, c, d) = queue[head]
    head += 1

    if r == Fr && c == Fc {
        answer = d
        break
    }

    for k in 0..<4 {
        let nr = r + dr[k]
        let nc = c + dc[k]

        if nr < 1 || nc < 1 || nr > N - H + 1 || nc > M - W + 1 {
            continue
        }
        if visited[nr][nc] { continue }
        if hasWall(nr, nc) { continue }

        visited[nr][nc] = true
        queue.append((nr, nc, d + 1))
    }
}

print(answer)