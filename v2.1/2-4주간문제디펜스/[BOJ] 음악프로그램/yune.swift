import Foundation

let first = readLine()!.split(separator: " ").map { Int($0)! },
    N = first[0],
    M = first[1]

var graph = Array(repeating: [Int](), count: N + 1)
var indegree = Array(repeating: 0, count: N + 1)

for _ in 0..<M {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    let count = input[0]
    
    if count > 1 {
        for i in 1..<count {
            let from = input[i]
            let to = input[i + 1]
            graph[from].append(to)
            indegree[to] += 1
        }
    }
}

var queue = [Int]()
for i in 1...N {
    if indegree[i] == 0 {
        queue.append(i)
    }
}

var result = [Int]()
var index = 0

while index < queue.count {
    let current = queue[index]
    index += 1
    result.append(current)
    
    for next in graph[current] {
        indegree[next] -= 1
        if indegree[next] == 0 {
            queue.append(next)
        }
    }
}

if result.count != N {
    print(0)
} else {
    for r in result {
        print(r)
    }
}