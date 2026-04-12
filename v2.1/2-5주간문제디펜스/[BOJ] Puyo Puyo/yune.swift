import Foundation

let rows = 12
let cols = 6

var field = [[Character]]()
for _ in 0..<rows {
    let line = Array(readLine()!)
    field.append(line)
}

let dr = [-1, 1, 0, 0]
let dc = [0, 0, -1, 1]

var chain = 0

while true {
    var visited = [[Bool]](repeating: [Bool](repeating: false, count: cols), count: rows)
    var exploded = false
    var toRemove = [(Int, Int)]()

    for r in 0..<rows {
        for c in 0..<cols {
            if field[r][c] == "." || visited[r][c] { continue }

            let group = bfs(r, c, &visited)
            if group.count >= 4 {
                exploded = true
                toRemove.append(contentsOf: group)
            }
        }
    }

    if !exploded { break }

    for (r, c) in toRemove {
        field[r][c] = "."
    }

    applyGravity()
    chain += 1
}

print(chain)

func bfs(_ r: Int, _ c: Int, _ visited: inout [[Bool]]) -> [(Int, Int)] {
    let color = field[r][c]
    var queue = [(r, c)]
    var result = [(r, c)]
    visited[r][c] = true

    var index = 0
    while index < queue.count {
        let (cr, cc) = queue[index]
        index += 1

        for d in 0..<4 {
            let nr = cr + dr[d]
            let nc = cc + dc[d]

            if nr < 0 || nr >= rows || nc < 0 || nc >= cols { continue }
            if visited[nr][nc] { continue }
            if field[nr][nc] != color { continue }

            visited[nr][nc] = true
            queue.append((nr, nc))
            result.append((nr, nc))
        }
    }

    return result
}

func applyGravity() {
    for c in 0..<cols {
        var stack = [Character]()

        for r in stride(from: rows - 1, through: 0, by: -1) {
            if field[r][c] != "." {
                stack.append(field[r][c])
            }
        }

        for r in stride(from: rows - 1, through: 0, by: -1) {
            if !stack.isEmpty {
                field[r][c] = stack.removeFirst()
            } else {
                field[r][c] = "."
            }
        }
    }
}