import Foundation

let N = Int(readLine()!)!
var time = [Int](repeating: 0, count: N + 1)
var graph = [[Int]](repeating: [Int](), count: N + 1)
var indegree = [Int](repeating: 0, count: N + 1)
var dp = [Int](repeating: 0, count: N + 1)

for i in 1...N {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    time[i] = input[0]
    
    var idx = 1
    while input[idx] != -1 {
        let pre = input[idx]
        graph[pre].append(i)
        indegree[i] += 1
        idx += 1
    }
}

var queue = [Int]()

for i in 1...N {
    if indegree[i] == 0 {
        queue.append(i)
        dp[i] = time[i]
    }
}

var index = 0
while index < queue.count {
    let current = queue[index]
    index += 1
    
    for next in graph[current] {
        dp[next] = max(dp[next], dp[current] + time[next])
        indegree[next] -= 1
        
        if indegree[next] == 0 {
            queue.append(next)
        }
    }
}

for i in 1...N {
    print(dp[i])
}