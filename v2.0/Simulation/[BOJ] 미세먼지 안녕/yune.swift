import Foundation

let rct = readLine()!.split(separator: " ").map{ Int($0)! }
let r = rct[0]
let c = rct[1]
let t = rct[2]

var now = [[Int]](repeating: [Int](repeating: 0, count: c), count: r)
var next = now
var ap = [(Int,Int)]()
var total = 0

for i in 0..<r{
    let line = readLine()!.split(separator: " ").map{Int($0)!}
    for k in 0..<c{
        now[i][k] = line[k]
        if line[k] == -1{
            ap.append((i,k))
        }
    }
}

func spread(x:Int, y:Int){
    let dx = [-1,1,0,0]
    let dy = [0,0,-1,1]
    let dust = now[x][y]/5
    
    for i in 0..<4{
        let nx = x+dx[i]
        let ny = y+dy[i]
        
        guard (0..<r).contains(nx), (0..<c).contains(ny) else { continue }
        guard now[nx][ny] != -1 else { continue }
        
        next[nx][ny] += dust
        now[x][y] -= dust
    }

    next[x][y] += now[x][y]
}

func cleanAntiClock(){
    let x = ap[0].0
    let y = ap[0].1
    
    for nx in stride(from: x-1, through: 1, by: -1){
        now[nx][y] = now[nx-1][y]
    }
    for ny in stride(from: 0, through: c-2, by: +1){
        now[0][ny] = now[0][ny+1]
    }
    for nx in stride(from: 0, through: x-1, by: +1){
        now[nx][c-1] = now[nx+1][c-1]
    }
    for ny in stride(from: c-1, through: 2, by: -1){
        now[x][ny] = now[x][ny-1]
    }
    now[x][y+1] = 0
}

func cleanClock(){
    let x = ap[1].0
    let y = ap[1].1
    
    for nx in stride(from: x+1, through: r-2, by: +1){
        now[nx][y] = now[nx+1][y]
    }
    for ny in stride(from: 0, through: c-2, by: +1){
        now[r-1][ny] = now[r-1][ny+1]
    }
    for nx in stride(from: r-1, through: x+1, by: -1){
        now[nx][c-1] = now[nx-1][c-1]
    }
    for ny in stride(from: c-1, through: 2, by: -1){
        now[x][ny] = now[x][ny-1]
    }
    now[x][y+1] = 0
}

for _ in 0..<t{
    next = [[Int]](repeating: [Int](repeating: 0, count: c), count: r)
    
    for i in 0..<r{
        for k in 0..<c{
            if now[i][k]>0{
                spread(x: i, y: k)
            }
        }
    }
    
    next[ap[0].0][ap[0].1] = -1
    next[ap[1].0][ap[1].1] = -1
    now = next
    
    cleanAntiClock()
    cleanClock()
}

total = now.flatMap{ $0 }.filter { $0>0 }.reduce(0, +)
print(total)