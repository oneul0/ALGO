import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let N = input[0], M = input[1]

var board = (0..<N).map { _ in
    readLine()!.split(separator: " ").map { Int($0)! }
}

let dx = [0, 0, 1, -1]
let dy = [1, -1, 0, 0]

var answer = 0

while true {
    var visited = Array(repeating: Array(repeating: false, count: N), count: N)
    
    var bestGroup: [(Int, Int)] = []
    var bestRainbow = 0
    var bestStandard = (r: -1, c: -1)
    
    for i in 0..<N {
        for j in 0..<N {
            if board[i][j] <= 0 || visited[i][j] { continue }
            
            let color = board[i][j]
            var queue = [(i, j)]
            var group = [(i, j)]
            var rainbow = [(Int, Int)]()
            visited[i][j] = true
            
            var idx = 0
            
            while idx < queue.count {
                let (x, y) = queue[idx]
                idx += 1
                
                for d in 0..<4 {
                    let nx = x + dx[d]
                    let ny = y + dy[d]
                    
                    if nx < 0 || ny < 0 || nx >= N || ny >= N { continue }
                    if visited[nx][ny] { continue }
                    
                    if board[nx][ny] == color || board[nx][ny] == 0 {
                        visited[nx][ny] = true
                        queue.append((nx, ny))
                        group.append((nx, ny))
                        
                        if board[nx][ny] == 0 {
                            rainbow.append((nx, ny))
                        }
                    }
                }
            }
            
            for (x, y) in rainbow {
                visited[x][y] = false
            }
            
            if group.count < 2 { continue }
            
            let normalBlocks = group.filter { board[$0.0][$0.1] > 0 }
            let standard = normalBlocks.min {
                if $0.0 == $1.0 { return $0.1 < $1.1 }
                return $0.0 < $1.0
            }!
            
            if group.count > bestGroup.count ||
                (group.count == bestGroup.count && rainbow.count > bestRainbow) ||
                (group.count == bestGroup.count && rainbow.count == bestRainbow && standard.0 > bestStandard.r) ||
                (group.count == bestGroup.count && rainbow.count == bestRainbow && standard.0 == bestStandard.r && standard.1 > bestStandard.c) {
                
                bestGroup = group
                bestRainbow = rainbow.count
                bestStandard = (standard.0, standard.1)
            }
        }
    }
    
    if bestGroup.isEmpty { break }
    
    let B = bestGroup.count
    answer += B * B
    
    for (x, y) in bestGroup {
        board[x][y] = -2
    }
    
    func gravity() {
        for j in 0..<N {
            for i in stride(from: N - 2, through: 0, by: -1) {
                if board[i][j] >= 0 {
                    var x = i
                    while true {
                        let nx = x + 1
                        if nx >= N || board[nx][j] != -2 { break }
                        board[nx][j] = board[x][j]
                        board[x][j] = -2
                        x = nx
                    }
                }
            }
        }
    }
    
    gravity()
    
    var newBoard = Array(repeating: Array(repeating: 0, count: N), count: N)
    for i in 0..<N {
        for j in 0..<N {
            newBoard[N - 1 - j][i] = board[i][j]
        }
    }
    board = newBoard
    
    gravity()
}

print(answer)