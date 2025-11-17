let inf = 987654321
let nm = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nm[0],
    m = nm[1]
var graph = [[Edge]](repeating: [Edge](), count: n + 1)

for _ in 0..<m {
    let abc = readLine()!.split { $0 == " " }.map { Int(String($0))! },
        a = abc[0],
        b = abc[1],
        c = abc[2]
    graph[a].append(Edge(to: b, cost: c))
    graph[b].append(Edge(to: a, cost: c))
}


func djst(_ start: Int) -> [[Int]] {
    var dist = [Int](repeating: inf, count: n+1)
    var parent = [Int](repeating: -1, count: n + 1)
    var repairEdge = [[Int]]()
    var heap = Heap<(Int, Int)>(sort: { $0.1 < $1.1 })
    heap.insert((start, 0))
    
    while !heap.isEmpty {
        let (currentNode, currentCost)  = heap.pop()!

        if currentCost > dist[currentNode] { continue }

        for edge in graph[currentNode] {
            let nextCost = currentCost + edge.cost

            if dist[edge.to] > nextCost {
                // 어떤 노드로부터 진입해야 이 노드로 올 때 최소인지 기록
                parent[edge.to] = currentNode
                dist[edge.to] = nextCost
                heap.insert((edge.to, nextCost))
            }
        }
    }
    for i in 2...n {
        if parent[i] != -1 {
            repairEdge.append([i, parent[i]])
        }
    }
    return repairEdge
}

let edges = djst(1)

print(edges.count)
for edge in edges {
    print(edge[0], edge[1])
}


struct Edge {
    let to: Int
    let cost: Int
}

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