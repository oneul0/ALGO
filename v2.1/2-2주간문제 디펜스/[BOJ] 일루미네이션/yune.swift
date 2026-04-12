import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! },
    W = input[0],
    H = input[1]

var grid = Array(repeating: Array(repeating: 0, count: W + 2), count: H + 2)

for y in 1...H {
    let row = readLine()!.split(separator: " ").map { Int($0)! }
    for x in 1...W {
        grid[y][x] = row[x - 1]
    }
}

var visited = Array(repeating: Array(repeating: false, count: W + 2), count: H + 2)

let dyOdd = [-1, -1, 0, 0, 1, 1]
let dxOdd = [0, 1, -1, 1, 0, 1]

let dyEven = [-1, -1, 0, 0, 1, 1]
let dxEven = [-1, 0, -1, 1, -1, 0]

var queue = [(0, 0)]
visited[0][0] = true
var totalLength = 0
var head = 0

while head < queue.count {
    let (y, x) = queue[head]
    head += 1
    
    let dy = (y % 2 != 0) ? dyOdd : dyEven
    let dx = (y % 2 != 0) ? dxOdd : dxEven
    
    for i in 0..<6 {
        let ny = y + dy[i]
        let nx = x + dx[i]
        
        if ny >= 0 && ny <= H + 1 && nx >= 0 && nx <= W + 1 {
            if grid[ny][nx] == 1 {
                totalLength += 1
            } else if !visited[ny][nx] {
                visited[ny][nx] = true
                queue.append((ny, nx))
            }
        }
    }
}

print(totalLength)