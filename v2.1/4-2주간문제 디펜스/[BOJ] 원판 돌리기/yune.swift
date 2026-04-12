import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let N = input[0], M = input[1], T = input[2]

var board = (0..<N).map { _ in
    readLine()!.split(separator: " ").map { Int($0)! }
}

let dx = [0,0,1,-1]
let dy = [1,-1,0,0]

for _ in 0..<T {
    let cmd = readLine()!.split(separator: " ").map { Int($0)! }
    let x = cmd[0], d = cmd[1], k = cmd[2]
    
    for i in stride(from: x-1, to: N, by: x) {
        rotate(i, d, k)
    }
    
    if !removeSame() {
        adjust()
    }
}

let result = board.flatMap { $0 }.reduce(0, +)
print(result)

func rotate(_ i: Int, _ d: Int, _ k: Int) {
    let k = k % M
    if d == 0 { // 시계
        board[i] = Array(board[i][M-k..<M] + board[i][0..<M-k])
    } else { // 반시계
        board[i] = Array(board[i][k..<M] + board[i][0..<k])
    }
}

func removeSame() -> Bool {
    var visited = Array(repeating: Array(repeating: false, count: M), count: N)
    var removed = false
    
    for i in 0..<N {
        for j in 0..<M {
            if board[i][j] == 0 || visited[i][j] { continue }
            
            var queue = [(i,j)]
            var group = [(i,j)]
            visited[i][j] = true
            
            let val = board[i][j]
            var idx = 0
            
            while idx < queue.count {
                let (x,y) = queue[idx]
                idx += 1
                
                for d in 0..<4 {
                    let nx = x + dx[d]
                    var ny = y + dy[d]
                    
                    if ny < 0 { ny = M-1 }
                    if ny >= M { ny = 0 }
                    
                    if nx < 0 || nx >= N { continue }
                    if visited[nx][ny] { continue }
                    if board[nx][ny] != val { continue }
                    
                    visited[nx][ny] = true
                    queue.append((nx,ny))
                    group.append((nx,ny))
                }
            }
            
            if group.count > 1 {
                removed = true
                for (x,y) in group {
                    board[x][y] = 0
                }
            }
        }
    }
    
    return removed
}

func adjust() {
    var sum = 0
    var count = 0
    
    for i in 0..<N {
        for j in 0..<M {
            if board[i][j] != 0 {
                sum += board[i][j]
                count += 1
            }
        }
    }
    
    if count == 0 { return }
    
    let avg = Double(sum) / Double(count)
    
    for i in 0..<N {
        for j in 0..<M {
            if board[i][j] == 0 { continue }
            
            if Double(board[i][j]) > avg {
                board[i][j] -= 1
            } else if Double(board[i][j]) < avg {
                board[i][j] += 1
            }
        }
    }
}