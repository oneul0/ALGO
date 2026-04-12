import Foundation

let n = Int(readLine()!)!
var stars = [(Double, Double)]()

for _ in 0..<n {
    let input = readLine()!.split(separator: " ").map { Double($0)! }
    stars.append((input[0], input[1]))
}

if n == 1 {
    print("0.00")
    exit(0)
}

var visited = [Bool](repeating: false, count: n)
var minDist = [Double](repeating: Double.greatestFiniteMagnitude, count: n)

minDist[0] = 0
var result = 0.0

for _ in 0..<n {
    var u = -1
    var minValue = Double.greatestFiniteMagnitude
    
    for i in 0..<n {
        if !visited[i] && minDist[i] < minValue {
            minValue = minDist[i]
            u = i
        }
    }
    
    visited[u] = true
    result += minValue
    
    for v in 0..<n {
        if !visited[v] {
            let dx = stars[u].0 - stars[v].0
            let dy = stars[u].1 - stars[v].1
            let dist = sqrt(dx * dx + dy * dy)
            minDist[v] = min(minDist[v], dist)
        }
    }
}

print(String(format: "%.2f", result))