import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let N = input[0], 
    M = input[1], 
    K = input[2]

var board = [[Int]]()
for _ in 0..<N {
    board.append(readLine()!.split(separator: " ").map { Int($0)! })
}

let dr = [0, 1, 0, -1]
let dc = [1, 0, -1, 0]

var dice = [1, 6, 2, 5, 4, 3]

func roll(_ dir: Int) {
    let t = dice
    switch dir {
    case 0: // 동
        dice[0] = t[4]
        dice[1] = t[5]
        dice[4] = t[1]
        dice[5] = t[0]
    case 2: // 서
        dice[0] = t[5]
        dice[1] = t[4]
        dice[4] = t[0]
        dice[5] = t[1]
    case 1: // 남
        dice[0] = t[2]
        dice[1] = t[3]
        dice[2] = t[1]
        dice[3] = t[0]
    case 3: // 북
        dice[0] = t[3]
        dice[1] = t[2]
        dice[2] = t[0]
        dice[3] = t[1]
    default:
        break
    }
}

func bfs(_ r: Int, _ c: Int) -> Int {
    let value = board[r][c]
    var visited = [[Bool]](repeating: [Bool](repeating: false, count: M), count: N)
    
    var queue = [(r, c)]
    visited[r][c] = true
    var count = 1
    
    var idx = 0
    while idx < queue.count {
        let (cr, cc) = queue[idx]
        idx += 1
        
        for d in 0..<4 {
            let nr = cr + dr[d]
            let nc = cc + dc[d]
            
            if nr < 0 || nr >= N || nc < 0 || nc >= M { continue }
            if visited[nr][nc] { continue }
            if board[nr][nc] != value { continue }
            
            visited[nr][nc] = true
            queue.append((nr, nc))
            count += 1
        }
    }
    
    return count * value
}

var r = 0, c = 0
var dir = 0 // 시작: 동쪽
var answer = 0

for _ in 0..<K {
    var nr = r + dr[dir]
    var nc = c + dc[dir]
    
    if nr < 0 || nr >= N || nc < 0 || nc >= M {
        dir = (dir + 2) % 4
        nr = r + dr[dir]
        nc = c + dc[dir]
    }
    
    r = nr
    c = nc
    roll(dir)
    
    answer += bfs(r, c)
    
    let A = dice[1]
    let B = board[r][c]
    
    if A > B {
        dir = (dir + 1) % 4
    } else if A < B {
        dir = (dir + 3) % 4
    }
}

print(answer)