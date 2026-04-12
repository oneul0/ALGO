import Foundation
let first = readLine()!.split(separator: " ").map { Int($0)! },
    N = first[0],
    M = first[1],
    X = first[2]

var graph = Array(repeating: [Edge](), count: N + 1)
var reverseGraph = Array(repeating: [Edge](), count: N + 1)

for _ in 0..<M {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    let from = input[0]
    let to = input[1]
    let cost = input[2]
    
    graph[from].append(Edge(to: to, cost: cost))
    reverseGraph[to].append(Edge(to: from, cost: cost))
}

let distFromX = dijkstra(start: X, graph: graph, n: N)
let distToX = dijkstra(start: X, graph: reverseGraph, n: N)

var answer = 0
for i in 1...N {
    let total = distFromX[i] + distToX[i]
    answer = max(answer, total)
}

print(answer)

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
    
    var isEmpty: Bool {
        return elements.isEmpty
    }
    
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
            
            if candidate == parent { return }
            
            elements.swapAt(parent, candidate)
            parent = candidate
        }
    }
}

func dijkstra(start: Int, graph: [[Edge]], n: Int) -> [Int] {
    var dist = Array(repeating: Int.max, count: n + 1)
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