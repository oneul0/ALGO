import Foundation

@inline(__always) func readInt() -> Int {
    var c = getchar()
    var n = 0
    while c == 10 || c == 32 { c = getchar() }
    var sign = 1
    if c == 45 { sign = -1; c = getchar() }
    while c >= 48 {
        n = n &* 10 &+ Int(c - 48)
        c = getchar()
    }
    return n * sign
}

let N = readInt()
let M = readInt()

var graph = Array(repeating: [(to: Int, cost: Int)](), count: N + 1)

var edges: [(u: Int, v: Int, w: Int)] = []
edges.reserveCapacity(M)

for _ in 0..<M {
    let u = readInt()
    let v = readInt()
    let w = readInt()
    graph[u].append((v, w))
    graph[v].append((u, w))
    edges.append((u, v, w))
}

struct PQ<T> {
    var heap: [T] = []
    let cmp: (T, T) -> Bool
    
    mutating func push(_ x: T) {
        heap.append(x)
        var i = heap.count - 1
        while i > 0 {
            let p = (i - 1) >> 1
            if cmp(heap[i], heap[p]) {
                heap.swapAt(i, p)
                i = p
            } else { break }
        }
    }
    
    mutating func pop() -> T? {
        if heap.isEmpty { return nil }
        heap.swapAt(0, heap.count - 1)
        let ret = heap.removeLast()
        var i = 0
        
        while true {
            let l = i * 2 + 1
            let r = i * 2 + 2
            var smallest = i
            if l < heap.count && cmp(heap[l], heap[smallest]) { smallest = l }
            if r < heap.count && cmp(heap[r], heap[smallest]) { smallest = r }
            if smallest == i { break }
            heap.swapAt(i, smallest)
            i = smallest
        }
        return ret
    }
    
    var isEmpty: Bool { heap.isEmpty }
}

let INF = Int.max

func dijkstra(blockedEdge: (Int, Int)?) -> ([Int], [Int]) {
    var dist = Array(repeating: INF, count: N + 1)
    var prev = Array(repeating: -1, count: N + 1)
    
    var pq = PQ<(Int, Int)>(heap: [], cmp: { $0.1 < $1.1 })
    dist[1] = 0
    pq.push((1, 0))
    
    while !pq.isEmpty {
        let (x, cost) = pq.pop()!
        if dist[x] < cost { continue }
        
        if x == N { break }
        
        for e in graph[x] {
            let y = e.to
            let w = e.cost
            
            // 차단 간선이면 skip
            if let block = blockedEdge {
                if (x == block.0 && y == block.1) || (x == block.1 && y == block.0) {
                    continue
                }
            }
            
            let nc = cost + w
            if nc < dist[y] {
                dist[y] = nc
                prev[y] = x
                pq.push((y, nc))
            }
        }
    }
    
    return (dist, prev)
}

let (dist0, prev0) = dijkstra(blockedEdge: nil)
let base = dist0[N]

if base == INF {
    print(-1)
    exit(0)
}

var pathEdges: [(Int, Int)] = []
var cur = N
while prev0[cur] != -1 {
    pathEdges.append((prev0[cur], cur))
    cur = prev0[cur]
}

var answer = 0
for (a, b) in pathEdges {
    let (distX, _) = dijkstra(blockedEdge: (a, b))
    if distX[N] == INF {
        print(-1)
        exit(0)
    }
    let delay = distX[N] - base
    if delay > answer { answer = delay }
}

print(answer)
