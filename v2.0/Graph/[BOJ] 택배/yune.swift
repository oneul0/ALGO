import Foundation

struct Heap<T> {
    private var elements: [T]
    private let priority: (T, T) -> Bool
    
    init(sort: @escaping (T, T) -> Bool) {
        self.elements = []
        self.priority = sort
    }
    
    var isEmpty: Bool { elements.isEmpty }
    
    mutating func insert(_ value: T) {
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
            let left = 2 * parent + 1
            let right = 2 * parent + 2
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

func dijkstra(start: Int, graph: [[(Int, Int)]], n: Int) -> [Int] {
    let INF = Int.max
    var dist = Array(repeating: INF, count: n + 1)
    var firstHop = Array(repeating: -1, count: n + 1)
    var pq = Heap<(Int, Int, Int)>(sort: { $0.0 < $1.0 })
    
    dist[start] = 0
    
    for (next, cost) in graph[start] {
        dist[next] = cost
        firstHop[next] = next
        pq.insert((cost, next, next))
    }
    
    while !pq.isEmpty {
        let (curDist, cur, first) = pq.pop()!
        if curDist > dist[cur] { continue }
        
        for (next, cost) in graph[cur] {
            let newDist = curDist + cost
            if newDist < dist[next] {
                dist[next] = newDist
                firstHop[next] = firstHop[cur] == -1 ? next : firstHop[cur]
                pq.insert((newDist, next, firstHop[next]))
            }
        }
    }
    
    return firstHop
}

let nm = readLine()!.split(separator: " ").map { Int($0)! }
let (n, m) = (nm[0], nm[1])
var graph = Array(repeating: [(Int, Int)](), count: n + 1)

for _ in 0..<m {
    let line = readLine()!.split(separator: " ").map { Int($0)! }
    let (a, b, c) = (line[0], line[1], line[2])
    graph[a].append((b, c))
    graph[b].append((a, c))
}

for i in 1...n {
    let firstHop = dijkstra(start: i, graph: graph, n: n)
    var row = ""
    for j in 1...n {
        if i == j {
            row += "- "
        } else {
            row += "\(firstHop[j]) "
        }
    }
    print(row.trimmingCharacters(in: .whitespaces))
}
