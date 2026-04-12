import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let N = input[0]
let M = input[1]

var board = Array(repeating: Array(repeating: 0, count: M), count: N)
for i in 0..<N {
    board[i] = readLine()!.split(separator: " ").map { Int($0)! }
}

let dx = [1, -1, 0, 0]
let dy = [0, 0, 1, -1]

var visited = Array(repeating: Array(repeating: false, count: M), count: N)
var answer = 0

func dfs(_ x: Int, _ y: Int, _ depth: Int, _ sum: Int) {
    if depth == 4 {
        answer = max(answer, sum)
        return
    }

    for d in 0..<4 {
        let nx = x + dx[d]
        let ny = y + dy[d]

        if nx < 0 || ny < 0 || nx >= N || ny >= M { continue }
        if visited[nx][ny] { continue }

        visited[nx][ny] = true
        dfs(nx, ny, depth + 1, sum + board[nx][ny])
        visited[nx][ny] = false
    }
}

func checkT(_ x: Int, _ y: Int) {
    let center = board[x][y]
    var wings = [Int]()

    for d in 0..<4 {
        let nx = x + dx[d]
        let ny = y + dy[d]
        if nx < 0 || ny < 0 || nx >= N || ny >= M { continue }
        wings.append(board[nx][ny])
    }

    if wings.count < 3 { return }
    wings.sort(by: >)
    answer = max(answer, center + wings[0] + wings[1] + wings[2])
}

for i in 0..<N {
    for j in 0..<M {
        visited[i][j] = true
        dfs(i, j, 1, board[i][j])
        visited[i][j] = false
        checkT(i, j)
    }
}

print(answer)