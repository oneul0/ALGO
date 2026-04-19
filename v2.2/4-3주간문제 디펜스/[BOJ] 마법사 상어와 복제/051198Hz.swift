import Foundation

let fdx = [0, -1, -1, -1, 0, 1, 1, 1]
let fdy = [-1, -1, 0, 1, 1, 1, 0, -1]

let dx = [-1, 0, 1, 0]
let dy = [0, -1, 0, 1]

let input = readLine()!.split(separator: " ").map { Int($0)! }
let m = input[0], 
    s = input[1]

// graph[x][y] = [물고기 방향]
var graph = Array(
    repeating: Array(repeating: [Int](), count: 4),
    count: 4
)

for _ in 0..<m {
    let f = readLine()!.split(separator: " ").map { Int($0)! }
    graph[f[0]-1][f[1]-1].append(f[2]-1)
}

let sInput = readLine()!.split(separator: " ").map { Int($0)! }
var shark = (sInput[0]-1, sInput[1]-1)

var smell = Array(repeating: Array(repeating: 0, count: 4), count: 4)

var temp = graph
var maxEat = -1
var eat = [(Int, Int)]()

func moveFish() -> [[[Int]]] {
    var res = Array(
        repeating: Array(repeating: [Int](), count: 4),
        count: 4
    )
    
    for x in 0..<4 {
        for y in 0..<4 {
            while !temp[x][y].isEmpty {
                let d = temp[x][y].removeLast()
                var moved = false
                
                for k in 0..<8 {
                    let i = (d - k + 8) % 8
                    let nx = x + fdx[i]
                    let ny = y + fdy[i]
                    
                    if nx >= 0 && nx < 4 && ny >= 0 && ny < 4 &&
                        !(nx == shark.0 && ny == shark.1) &&
                        smell[nx][ny] == 0 {
                        res[nx][ny].append(i)
                        moved = true
                        break
                    }
                }
                
                if !moved {
                    res[x][y].append(d)
                }
            }
        }
    }
    
    return res
}

func dfs(_ x: Int, _ y: Int, _ dep: Int, _ cnt: Int, _ visit: [(Int, Int)]) {
    if dep == 3 {
        if cnt > maxEat {
            maxEat = cnt
            shark = (x, y)
            eat = visit
        }
        return
    }
    
    for d in 0..<4 {
        let nx = x + dx[d]
        let ny = y + dy[d]
        
        if nx >= 0 && nx < 4 && ny >= 0 && ny < 4 {
            var visitNext = visit
            
            if !visit.contains(where: { $0.0 == nx && $0.1 == ny }) {
                visitNext.append((nx, ny))
                dfs(nx, ny, dep + 1, cnt + temp[nx][ny].count, visitNext)
            } else {
                dfs(nx, ny, dep + 1, cnt, visitNext)
            }
        }
    }
}

for _ in 0..<s {
    eat = []
    maxEat = -1
    
    temp = graph
    
    temp = moveFish()
    
    dfs(shark.0, shark.1, 0, 0, [])
    
    for (x, y) in eat {
        if !temp[x][y].isEmpty {
            temp[x][y].removeAll()
            smell[x][y] = 3
        }
    }
    
    for i in 0..<4 {
        for j in 0..<4 {
            if smell[i][j] > 0 {
                smell[i][j] -= 1
            }
        }
    }
    
    for i in 0..<4 {
        for j in 0..<4 {
            graph[i][j] += temp[i][j]
        }
    }
}

var answer = 0
for i in 0..<4 {
    for j in 0..<4 {
        answer += graph[i][j].count
    }
}

print(answer)