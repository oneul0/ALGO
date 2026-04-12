import Foundation

let first = readLine()!.split(separator: " ").map { Int($0)! },
    N = first[0],
    E = first[1]

var graph = Array(repeating: [Edge](), count: N + 1)

for _ in 0..<E {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    let a = input[0]
    let b = input[1]
    let c = input[2]
    
    graph[a].append(Edge(to: b, cost: c))
    graph[b].append(Edge(to: a, cost: c))
}

let last = readLine()!.split(separator: " ").map { Int($0)! }
let v1 = last[0]
let v2 = last[1]

let INF = Int.max

let distFrom1 = dijkstra(start: 1, graph: graph, n: N)
let distFromV1 = dijkstra(start: v1, graph: graph, n: N)
let distFromV2 = dijkstra(start: v2, graph: graph, n: N)

var answer = INF

// 1 -> v1 -> v2 -> N
if distFrom1[v1] != INF &&
   distFromV1[v2] != INF &&
   distFromV2[N] != INF {
    answer = min(answer,
                 distFrom1[v1] + distFromV1[v2] + distFromV2[N])
}

// 1 -> v2 -> v1 -> N
if distFrom1[v2] != INF &&
   distFromV2[v1] != INF &&
   distFromV1[N] != INF {
    answer = min(answer,
                 distFrom1[v2] + distFromV2[v1] + distFromV1[N])
}

print(answer == INF ? -1 : answer)

func dijkstra(start: Int, graph: [[Edge]], n: Int) -> [Int] {
    let INF = Int.max
    var dist = Array(repeating: INF, count: n + 1)
    dist[start] = 0
    
    var heap = Heap<(node: Int, cost: Int)> { $0.cost < $1.cost }
    heap.push((start, 0))
    
    while !heap.isEmpty {
        guard let current = heap.pop() else { break }
        let node = current.node
        let cost = current.cost
        
        if cost > dist[node] { continue }
        
        for edge in graph[node] {
            let nextCost = cost + edge.cost
            if nextCost < dist[edge.to] {
                dist[edge.to] = nextCost
                heap.push((edge.to, nextCost))
            }
        }
    }
    
    return dist
}

struct Edge {
    let to: Int
    let cost: Int
}

struct Heap<T> {
    private var elements: [T] = []
    private let priority: (T, T) -> Bool
    
    init(priority: @escaping (T, T) -> Bool) {
        self.priority = priority
    }
    
    var isEmpty: Bool { elements.isEmpty }
    
    mutating func push(_ value: T) {
        elements.append(value)
        siftUp(from: elements.count - 1)
    }
    
    mutating func pop() -> T? {
        guard !elements.isEmpty else { return nil }
        elements.swapAt(0, elements.count - 1)
        let value = elements.removeLast()
        siftDown(from: 0)
        return value
    }
    
    private mutating func siftUp(from index: Int) {
        var child = index
        var parent = (child - 1) / 2
        while child > 0 && priority(elements[child], elements[parent]) {
            elements.swapAt(child, parent)
            child = parent
            parent = (child - 1) / 2
        }
    }
    
    private mutating func siftDown(from index: Int) {
        var parent = index
        while true {
            let left = parent * 2 + 1
            let right = parent * 2 + 2
            var candidate = parent
            
            if left < elements.count && priority(elements[left], elements[candidate]) {
                candidate = left
            }
            if right < elements.count && priority(elements[right], elements[candidate]) {
                candidate = right
            }
            if candidate == parent { break }
            
            elements.swapAt(parent, candidate)
            parent = candidate
        }
    }
}