import Foundation

let N = Int(readLine()!)!
var board = (0..<N).map { _ in
    readLine()!.split(separator: " ").map { Int($0)! }
}

var answer = 0

dfs(0, board)
print(answer)

func move(_ dir: Int, _ b: [[Int]]) -> [[Int]] {
    var newBoard = Array(repeating: Array(repeating: 0, count: N), count: N)
    
    for i in 0..<N {
        var line: [Int] = []
        
        for j in 0..<N {
            let val: Int
            switch dir {
            case 0: val = b[j][i]       // up
            case 1: val = b[N-1-j][i]   // down
            case 2: val = b[i][j]       // left
            case 3: val = b[i][N-1-j]   // right
            default: val = 0
            }
            if val != 0 {
                line.append(val)
            }
        }
        
        var merged: [Int] = []
        var idx = 0
        
        while idx < line.count {
            if idx + 1 < line.count && line[idx] == line[idx + 1] {
                merged.append(line[idx] * 2)
                idx += 2
            } else {
                merged.append(line[idx])
                idx += 1
            }
        }
        
        while merged.count < N {
            merged.append(0)
        }
        
        for j in 0..<N {
            switch dir {
            case 0: newBoard[j][i] = merged[j]
            case 1: newBoard[N-1-j][i] = merged[j]
            case 2: newBoard[i][j] = merged[j]
            case 3: newBoard[i][N-1-j] = merged[j]
            default: break
            }
        }
    }
    
    return newBoard
}

func dfs(_ depth: Int, _ board: [[Int]]) {
    if depth == 5 {
        for i in 0..<N {
            for j in 0..<N {
                answer = max(answer, board[i][j])
            }
        }
        return
    }
    
    for dir in 0..<4 {
        let next = move(dir, board)
        dfs(depth + 1, next)
    }
}