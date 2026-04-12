import Foundation

let dx = [-1,-1,0,1,1,1,0,-1]
let dy = [0,-1,-1,-1,0,1,1,1]

var answer = 0

var board = Array(repeating: Array(repeating: (0,0), count: 4), count: 4)
var fish = Array(repeating: (-1,-1,-1), count: 17)

for i in 0..<4 {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    for j in 0..<4 {
        let num = input[j*2]
        let dir = input[j*2+1] - 1
        board[i][j] = (num, dir)
        fish[num] = (i, j, dir)
    }
}

let first = board[0][0]
fish[first.0] = (-1, -1, -1)

dfs(board, fish, 0, 0, first.1, first.0)

print(answer)

func dfs(_ board: [[(Int, Int)]], _ fish: [(Int, Int, Int)], _ sx: Int, _ sy: Int, _ sdir: Int, _ sum: Int) {
    answer = max(answer, sum)
    
    var board = board
    var fish = fish
    
    for i in 1...16 {
        let (x, y, dir) = fish[i]
        if x == -1 { continue }
        
        for k in 0..<8 {
            let nd = (dir + k) % 8
            let nx = x + dx[nd]
            let ny = y + dy[nd]
            
            if nx < 0 || nx >= 4 || ny < 0 || ny >= 4 { continue }
            if nx == sx && ny == sy { continue }
            
            let (targetNum, targetDir) = board[nx][ny]
            
            // 물고기 위치 교환
            board[x][y] = (targetNum, targetDir)
            board[nx][ny] = (i, nd)
            
            fish[i] = (nx, ny, nd)
            
            if targetNum != 0 {
                fish[targetNum] = (x, y, targetDir)
            }
            
            break
        }
    }
    
    for step in 1...3 {
        let nx = sx + dx[sdir] * step
        let ny = sy + dy[sdir] * step
        
        if nx < 0 || nx >= 4 || ny < 0 || ny >= 4 { break }
        
        let (num, dir) = board[nx][ny]
        if num == 0 { continue }
        
        var newBoard = board
        var newFish = fish
        
        newBoard[sx][sy] = (0, 0)
        
        newBoard[nx][ny] = (0, 0)
        newFish[num] = (-1, -1, -1)
        
        dfs(newBoard, newFish, nx, ny, dir, sum + num)
    }
}