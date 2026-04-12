import Foundation

let SIZE = 5
let INF = 1_000_000

var boards = Array(repeating: Array(repeating: Array(repeating: 0, count: SIZE), count: SIZE), count: SIZE)

for k in 0..<SIZE {
    for i in 0..<SIZE {
        let line = readLine()!.split(separator: " ").map { Int($0)! }
        for j in 0..<SIZE {
            boards[k][i][j] = line[j]
        }
    }
}

func rotate(_ board: [[Int]]) -> [[Int]] {
    var result = Array(repeating: Array(repeating: 0, count: SIZE), count: SIZE)
    for i in 0..<SIZE {
        for j in 0..<SIZE {
            result[j][SIZE - 1 - i] = board[i][j]
        }
    }
    return result
}

var rotated = Array(
    repeating: Array(
        repeating: Array(repeating: Array(repeating: 0, count: SIZE), count: SIZE),
        count: 4
    ),
    count: SIZE
)

for i in 0..<SIZE {
    rotated[i][0] = boards[i]
    for r in 1..<4 {
        rotated[i][r] = rotate(rotated[i][r - 1])
    }
}

let dx = [1, -1, 0, 0, 0, 0]
let dy = [0, 0, 1, -1, 0, 0]
let dz = [0, 0, 0, 0, 1, -1]

func bfs(_ cube: [[[Int]]]) -> Int {
    if cube[0][0][0] == 0 || cube[4][4][4] == 0 {
        return INF
    }

    var visited = Array(
        repeating: Array(
            repeating: Array(repeating: false, count: SIZE),
            count: SIZE),
        count: SIZE
    )

    var queue: [(Int, Int, Int, Int)] = [(0, 0, 0, 0)]
    visited[0][0][0] = true
    var idx = 0

    while idx < queue.count {
        let (z, x, y, dist) = queue[idx]
        idx += 1

        if z == 4 && x == 4 && y == 4 {
            return dist
        }

        for d in 0..<6 {
            let nz = z + dz[d]
            let nx = x + dx[d]
            let ny = y + dy[d]

            if nz < 0 || nx < 0 || ny < 0 || nz >= SIZE || nx >= SIZE || ny >= SIZE {
                continue
            }

            if !visited[nz][nx][ny] && cube[nz][nx][ny] == 1 {
                visited[nz][nx][ny] = true
                queue.append((nz, nx, ny, dist + 1))
            }
        }
    }

    return INF
}

var used = Array(repeating: false, count: SIZE)
var order = Array(repeating: 0, count: SIZE)
var answer = INF

func permute(_ depth: Int) {
    if depth == SIZE {
        for r0 in 0..<4 {
            for r1 in 0..<4 {
                for r2 in 0..<4 {
                    for r3 in 0..<4 {
                        for r4 in 0..<4 {
                            var cube = Array(
                                repeating: Array(
                                    repeating: Array(repeating: 0, count: SIZE),
                                    count: SIZE),
                                count: SIZE
                            )

                            let rotations = [r0, r1, r2, r3, r4]

                            for z in 0..<SIZE {
                                cube[z] = rotated[order[z]][rotations[z]]
                            }

                            let result = bfs(cube)
                            answer = min(answer, result)

                            if answer == 12 {
                                print(12)
                                exit(0)
                            }
                        }
                    }
                }
            }
        }
        return
    }

    for i in 0..<SIZE {
        if !used[i] {
            used[i] = true
            order[depth] = i
            permute(depth + 1)
            used[i] = false
        }
    }
}

permute(0)

print(answer == INF ? -1 : answer)