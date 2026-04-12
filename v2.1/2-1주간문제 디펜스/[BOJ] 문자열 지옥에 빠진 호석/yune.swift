import Foundation

let first = readLine()!.split(separator: " ").map { Int($0)! },
    n = first[0],
    m = first[1],
    k = first[2]

var board = [[Character]]()
for _ in 0..<n {
    board.append(Array(readLine()!))
}

let dx = [-1,-1,-1, 0,0, 1,1,1]
let dy = [-1, 0, 1,-1,1,-1,0,1]

var countMap = [String: Int]()

func dfs(_ x: Int, _ y: Int, _ depth: Int, _ str: String) {
    countMap[str, default: 0] += 1
    if depth == 5 { return }

    for d in 0..<8 {
        let nx = (x + dx[d] + n) % n
        let ny = (y + dy[d] + m) % m
        dfs(nx, ny, depth + 1, str + String(board[nx][ny]))
    }
}

for i in 0..<n {
    for j in 0..<m {
        dfs(i, j, 1, String(board[i][j]))
    }
}

for _ in 0..<k {
    let s = readLine()!
    print(countMap[s, default: 0])
}