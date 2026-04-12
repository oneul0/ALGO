import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let N = input[0], M = input[1]

var board = (0..<N).map { _ in
    readLine()!.split(separator: " ").map { Int($0)! }
}

let dr = [0, 1, 0, -1]
let dc = [1, 0, -1, 0]

func bfs() -> Int {
    var visited = Array(
        repeating: Array(
            repeating: Array(
                repeating: Array(repeating: false, count: 20),
                count: 2
            ),
            count: N
        ),
        count: N
    )
    
    var queue: [(Int, Int, Int, Int)] = [(0, 0, 0, 0)]
    var idx = 0
    
    visited[0][0][0][0] = true
    
    while idx < queue.count {
        let (r, c, time, prevBridge) = queue[idx]
        idx += 1
        
        if r == N-1 && c == N-1 {
            return time
        }
        
        let nextTime = time + 1
        let mod = nextTime % 20
        
        if !visited[r][c][prevBridge][mod] {
            visited[r][c][prevBridge][mod] = true
            queue.append((r, c, nextTime, prevBridge))
        }
        
        for d in 0..<4 {
            let nr = r + dr[d]
            let nc = c + dc[d]
            
            if nr < 0 || nr >= N || nc < 0 || nc >= N { continue }
            
            let val = board[nr][nc]
            
            if val == 1 {
                if !visited[nr][nc][0][mod] {
                    visited[nr][nc][0][mod] = true
                    queue.append((nr, nc, nextTime, 0))
                }
            }
            
            else if val >= 2 {
                if prevBridge == 1 { continue }
                
                if nextTime % val == 0 {
                    if !visited[nr][nc][1][mod] {
                        visited[nr][nc][1][mod] = true
                        queue.append((nr, nc, nextTime, 1))
                    }
                }
            }
        }
    }
    
    return Int.max
}

var answer = Int.max

for i in 0..<N {
    for j in 0..<N {
        if board[i][j] != 0 { continue }
        
        let vertical = (i > 0 && i < N-1 && board[i-1][j] == 0 && board[i+1][j] == 0)
        let horizontal = (j > 0 && j < N-1 && board[i][j-1] == 0 && board[i][j+1] == 0)
        
        if vertical && horizontal { continue }
        
        board[i][j] = M
        
        answer = min(answer, bfs())
        
        board[i][j] = 0
    }
}

print(answer)