import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let A = input[0]
let B = input[1]
let C = input[2]

var visited = Array(
    repeating: Array(
        repeating: Array(repeating: false, count: C + 1),
        count: B + 1),
    count: A + 1
)

var result = Set<Int>()

var queue = [(Int, Int, Int)]()
var idx = 0

queue.append((0, 0, C))
visited[0][0][C] = true

let capacities = [A, B, C]

func pour(_ from: Int, _ to: Int, _ state: [Int]) -> [Int] {
    var next = state
    let amount = min(state[from], capacities[to] - state[to])
    next[from] -= amount
    next[to] += amount
    return next
}

while idx < queue.count {
    let (a, b, c) = queue[idx]
    idx += 1

    if a == 0 {
        result.insert(c)
    }

    let cur = [a, b, c]

    for i in 0..<3 {
        for j in 0..<3 {
            if i == j { continue }
            let next = pour(i, j, cur)
            let na = next[0], nb = next[1], nc = next[2]

            if !visited[na][nb][nc] {
                visited[na][nb][nc] = true
                queue.append((na, nb, nc))
            }
        }
    }
}

let answer = result.sorted()
print(answer.map { String($0) }.joined(separator: " "))