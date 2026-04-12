import Foundation

var game = 1

while true {
    let rc = readLine()!.split(separator: " ").map { Int($0)! }
    let R = rc[0], 
        C = rc[1]
    
    if R == 0 && C == 0 { break }
    
    var board = [[Character]]()
    var px = 0, py = 0
    
    for i in 0..<R {
        let row = Array(readLine()!)
        for j in 0..<C {
            if row[j] == "w" || row[j] == "W" {
                px = i
                py = j
            }
        }
        board.append(row)
    }
    
    let commands = Array(readLine()!)
    
    let dir: [Character: (Int,Int)] = [
        "U":(-1,0), "D":(1,0),
        "L":(0,-1), "R":(0,1)
    ]
    
    func complete() -> Bool {
        for r in board {
            for c in r {
                if c == "b" { return false }
            }
        }
        return true
    }
    
    for cmd in commands {
        if complete() { break }
        
        let (dx,dy) = dir[cmd]!
        let nx = px + dx
        let ny = py + dy
        
        let next = board[nx][ny]
        
        if next == "." || next == "+" {
            if board[px][py] == "w" { board[px][py] = "." }
            else { board[px][py] = "+" }
            
            if next == "." { board[nx][ny] = "w" }
            else { board[nx][ny] = "W" }
            
            px = nx
            py = ny
        }
        
        else if next == "b" || next == "B" {
            let bx = nx + dx
            let by = ny + dy
            let next2 = board[bx][by]
            
            if next2 == "." || next2 == "+" {
                
                if next2 == "." { board[bx][by] = "b" }
                else { board[bx][by] = "B" }
                
                if next == "b" { board[nx][ny] = "w" }
                else { board[nx][ny] = "W" }
                
                if board[px][py] == "w" { board[px][py] = "." }
                else { board[px][py] = "+" }
                
                px = nx
                py = ny
            }
        }
    }
    
    print("Game \(game): \(complete() ? "complete" : "incomplete")")
    
    for r in board {
        print(String(r))
    }
    
    game += 1
}