let T = Int(readLine()!)!
let inf = 987654321

for _ in 0..<T {
    // 첫 번째 줄에 3개의 정수 n, m, t (2 ≤ n ≤ 2 000, 1 ≤ m ≤ 50 000 and 1 ≤ t ≤ 100)가 주어진다. 
    // 각 교차로, 도로, 목적지 후보의 개수이다. 즉, 교차로가 정점의 갯수, 1-base 그래프
    let nmt = readLine()!.split { $0 == " " }.map { Int(String($0))! },
        n = nmt[0],
        m = nmt[1],
        t = nmt[2]
    let sgh = readLine()!.split { $0 == " " }.map { Int(String($0))! },
        s = sgh[0],
        g = sgh[1],
        h = sgh[2]
    var expectations = [Int]()
    var graph = [[Edge]](repeating: [Edge](), count: n+1)
    var w = 0
    var answer = [Int]()

    for _ in 0..<m {
        let abd = readLine()!.split { $0 == " " }.map { Int(String($0))! },
            a = abd[0],
            b = abd[1],
            d = abd[2]
        if (a == g && b == h) || (a == h && b == g) {
            w = d
        }
        graph[a].append(Edge(to: b, cost: d))
        graph[b].append(Edge(to: a, cost: d))
    }

    for _ in 0..<t {
        let x = Int(readLine()!)!
        expectations.append(x)
    }
    expectations.sort { $0 < $1 }
    let distS = djst(s, graph)
    let distH = djst(h, graph)
    let distG = djst(g, graph)

    for expectation in expectations {
        if distS[expectation] == distS[g] + w + distH[expectation] { 
            answer.append(expectation)
            continue
        }
        if distS[expectation] == distS[h] + w + distG[expectation] {
            answer.append(expectation)
            continue
        }
    }
    print(answer.map { "\($0) "}.joined())
}

func djst(_ start: Int, _ graph: [[Edge]]) -> [Int] {
    var heap = Heap<(Int, Int)>(sort: { $0.1 < $1.1 })
    var dist = [Int](repeating: inf, count: graph.count)
    dist[start] = 0
    heap.insert((start, 0))

    while !heap.isEmpty {
        let (currentNode, currentCost) = heap.pop()!
        if currentCost > dist[currentNode] { continue }
        
        for edge in graph[currentNode] {
            let nextCost = currentCost + edge.cost

            if nextCost < dist[edge.to] {
                dist[edge.to] = nextCost
                heap.insert((edge.to, nextCost))
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