import Foundation

let input = readLine()!.split(separator: " ").map{Int($0)!},
    N = input[0],
    M = input[1],
    K = input[2]

var board = [String]()

for _ in 0..<N {
    board.append(readLine()!)
}

var ps1 = [[Int]](repeating: [Int](repeating: 0, count: M+1), count: N+1)
var ps2 = [[Int]](repeating: [Int](repeating: 0, count: M+1), count: N+1)

for i in 1...N {
    let row = Array(board[i-1])
    for j in 1...M {
        
        let current = row[j-1]
        
        let expectedW = ((i+j) % 2 == 0) ? "W" : "B"
        let expectedB = ((i+j) % 2 == 0) ? "B" : "W"
        
        let mis1 = (String(current) == expectedW) ? 0 : 1
        let mis2 = (String(current) == expectedB) ? 0 : 1
        
        ps1[i][j] = ps1[i-1][j] + ps1[i][j-1] - ps1[i-1][j-1] + mis1
        ps2[i][j] = ps2[i-1][j] + ps2[i][j-1] - ps2[i-1][j-1] + mis2
    }
}

var answer = Int.max

for i in K...N {
    for j in K...M {
        
        let r1 = i-K+1
        let c1 = j-K+1
        
        let s1 = ps1[i][j] - ps1[r1-1][j] - ps1[i][c1-1] + ps1[r1-1][c1-1]
        let s2 = ps2[i][j] - ps2[r1-1][j] - ps2[i][c1-1] + ps2[r1-1][c1-1]
        
        answer = min(answer, s1, s2)
    }
}

print(answer)