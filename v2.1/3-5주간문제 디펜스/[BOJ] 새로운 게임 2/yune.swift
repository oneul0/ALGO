import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let N = input[0]
let K = input[1]

var color = [[Int]]()
for _ in 0..<N {
    color.append(readLine()!.split(separator: " ").map { Int($0)! })
}

struct Piece {
    var r: Int
    var c: Int
    var d: Int
}

var pieces = [Piece]()
var board = Array(
    repeating: Array(repeating: [Int](), count: N),
    count: N
)

for i in 0..<K {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    let r = input[0] - 1
    let c = input[1] - 1
    let d = input[2]
    
    pieces.append(Piece(r: r, c: c, d: d))
    board[r][c].append(i)
}

let dr = [0, 0, 0, -1, 1]
let dc = [0, 1, -1, 0, 0]

func reverse(_ d: Int) -> Int {
    if d == 1 { return 2 }
    if d == 2 { return 1 }
    if d == 3 { return 4 }
    return 3
}

func move(_ i: Int) -> Bool {
    let p = pieces[i]
    let r = p.r
    let c = p.c
    let d = p.d
    
    let idx = board[r][c].firstIndex(of: i)!
    let moving = Array(board[r][c][idx...])
    board[r][c] = Array(board[r][c][..<idx])
    
    var nd = d
    var nr = r + dr[nd]
    var nc = c + dc[nd]
    
    // 파란색
    // 아웃바운드
    if nr < 0 || nr >= N || nc < 0 || nc >= N || color[nr][nc] == 2 {
        nd = reverse(nd)
        pieces[i].d = nd
        
        nr = r + dr[nd]
        nc = c + dc[nd]
        
        if nr < 0 || nr >= N || nc < 0 || nc >= N || color[nr][nc] == 2 {
            board[r][c] += moving
            return false
        }
    }
    
    // 빨간색
    if color[nr][nc] == 1 {
        let reversed = moving.reversed()
        for m in reversed {
            board[nr][nc].append(m)
        }
    } else {
        for m in moving {
            board[nr][nc].append(m)
        }
    }
    
    for m in moving {
        pieces[m].r = nr
        pieces[m].c = nc
    }
    
    return board[nr][nc].count >= 4
}

for turn in 1...1000 {
    for i in 0..<K {
        if move(i) {
            print(turn)
            exit(0)
        }
    }
}

print(-1)