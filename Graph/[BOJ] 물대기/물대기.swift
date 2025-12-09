import Foundation

let N = Int(readLine()!)!

var dist = [Int](repeating: Int.max, count: N)

var visited = [Bool](repeating: false, count: N)

// 우물 처리
for i in 0..<N { 
    dist[i] = Int(readLine()!)!
}

var cost = [[Int]]()
for _ in 0..<N {
    let row = readLine()!.split(separator: " ").map { Int($0)! }
    cost.append(row)
}

var answer = 0

for _ in 0..<N {
    var u = -1
    var minValue = Int.max
    
    for i in 0..<N {
        if !visited[i] && dist[i] < minValue {
            minValue = dist[i]
            u = i
        }
    }
    
    visited[u] = true
    answer += dist[u]
    
    for v in 0..<N {
        if !visited[v] && cost[u][v] < dist[v] {
            dist[v] = cost[u][v]
        }
    }
}

print(answer)