import Foundation

let N = Int(readLine()!)!

var adj = Array(repeating: [Int](), count: N + 1)
var hasEdge = Array(repeating: Array(repeating: false, count: N + 1), count: N + 1)

for _ in 0..<(N - 1) {
    let parts = readLine()!.split(separator: " ").map { Int($0)! }
    let u = parts[0], v = parts[1]
    adj[u].append(v)
    adj[v].append(u)
    hasEdge[u][v] = true
    hasEdge[v][u] = true
}

// N <= 4, 지름 = 1
if N <= 4 {
    var added: [(Int, Int)] = []
    for i in 1...N {
        if i + 1 > N { continue }
        for j in (i + 1)...N {
            if !hasEdge[i][j] {
                added.append((i, j))
            }
        }
    }

    print(added.count)
    print(1)
    for (u, v) in added {
        print(u, v)
    }
    exit(0)
}

// N >= 5, 지름 = 2
func bfs(_ start: Int) -> ([Int], [Int]) {
    var dist = Array(repeating: -1, count: N + 1)
    var parent = Array(repeating: -1, count: N + 1)
    var queue = [Int]()
    var idx = 0
    
    dist[start] = 0
    queue.append(start)
    
    while idx < queue.count {
        let cur = queue[idx]
        idx += 1
        for nxt in adj[cur] {
            if dist[nxt] == -1 {
                dist[nxt] = dist[cur] + 1
                parent[nxt] = cur
                queue.append(nxt)
            }
        }
    }
    return (dist, parent)
}

let (d1, _) = bfs(1)
var A = 1
for i in 1...N {
    if d1[i] > d1[A] {
        A = i
    }
}

let (d2, parent) = bfs(A)
var B = A
for i in 1...N {
    if d2[i] > d2[B] {
        B = i
    }
}

var path = [Int]()
var cur = B
while cur != -1 {
    path.append(cur)
    cur = parent[cur]
}

let D = path.count - 1
let center = path[D / 2]

var added: [(Int, Int)] = []
for v in 1...N {
    if v == center { continue }
    if !hasEdge[center][v] {
        added.append((center, v))
    }
}

print(added.count)
print(2)
for (u, v) in added {
    print(u, v)
}