import Foundation

@inline(__always) func readInt() -> Int {
    var c = getchar()
    var number = 0
    var sign = 1
    while c == 10 || c == 32 { c = getchar() }
    if c == 45 { sign = -1; c = getchar() }
    while c >= 48 && c <= 57 {
        number = number * 10 + Int(c - 48)
        c = getchar()
    }
    return number * sign
}

struct PriorityQueue<T> {
    var heap = [T]()
    let compare: (T, T) -> Bool
    
    init(_ compare: @escaping (T, T) -> Bool) {
        self.compare = compare
    }
    
    mutating func push(_ x: T) {
        heap.append(x)
        var i = heap.count - 1
        while i > 0 {
            let p = (i - 1) >> 1
            if compare(heap[i], heap[p]) {
                heap.swapAt(i, p)
                i = p
            } else {
                break
            }
        }
    }
    
    mutating func pop() -> T? {
        guard !heap.isEmpty else { return nil }
        heap.swapAt(0, heap.count - 1)
        let ret = heap.removeLast()
        var i = 0
        while true {
            let l = i * 2 + 1
            let r = l + 1
            var candidate = i
            if l < heap.count && compare(heap[l], heap[candidate]) {
                candidate = l
            }
            if r < heap.count && compare(heap[r], heap[candidate]) {
                candidate = r
            }
            if candidate == i { break }
            heap.swapAt(i, candidate)
            i = candidate
        }
        return ret
    }
    
    var isEmpty: Bool {
        heap.isEmpty
    }
}

struct Edge {
    let to: Int
    let weight: Int
}

let N = readInt()
let M = readInt()

var graph = Array(repeating: [Edge](), count: N + 1)
for _ in 0..<M {
    let a = readInt()
    let b = readInt()
    let d = readInt()
    let w = d * 2
    graph[a].append(Edge(to: b, weight: w))
    graph[b].append(Edge(to: a, weight: w))
}

let INF = Int.max / 4

func dijkstraFox() -> [Int] {
    var dist = Array(repeating: INF, count: N + 1)
    dist[1] = 0
    
    var pq = PriorityQueue<(node: Int, cost: Int)> { $0.cost < $1.cost }
    pq.push((1, 0))
    
    while let cur = pq.pop() {
        let u = cur.node
        let costU = cur.cost
        if costU > dist[u] { continue }
        
        for e in graph[u] {
            let v = e.to
            let w = e.weight
            let newCost = costU + w
            if newCost < dist[v] {
                dist[v] = newCost
                pq.push((v, newCost))
            }
        }
    }
    
    return dist
}

func dijkstraWolf() -> [[Int]] {
    var dist = Array(repeating: [INF, INF], count: N + 1)
    dist[1][0] = 0
    
    var pq = PriorityQueue<(node: Int, cost: Int, state: Int)> { 
        $0.cost < $1.cost 
    }
    pq.push((1, 0, 0))
    
    while let cur = pq.pop() {
        let u = cur.node
        let costU = cur.cost
        let state = cur.state
        if costU > dist[u][state] { continue }
        
        for e in graph[u] {
            let v = e.to
            let w = e.weight
            
            let nextState = 1 - state
            var nextCost: Int
            if state == 0 {
                nextCost = costU + (w / 2)
            } else {
                nextCost = costU + (w * 2)
            }
            
            if nextCost < dist[v][nextState] {
                dist[v][nextState] = nextCost
                pq.push((v, nextCost, nextState))
            }
        }
    }
    
    return dist
}

let foxDist = dijkstraFox()
let wolfDist = dijkstraWolf()

var count = 0
for i in 2...N {
    let wolfBest = min(wolfDist[i][0], wolfDist[i][1])
    if foxDist[i] < wolfBest {
        count += 1
    }
}

print(count)