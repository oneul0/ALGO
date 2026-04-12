struct Queue {
    var queue: [Int?] = []
    var head = 0
    var isEmpty: Bool {
        queue.count - head == 0
    }
    var front: Int? {
        isEmpty ? nil : queue[head]
    }
    mutating func enqueue(_ element: Int) {
        queue.append(element)
    }
    mutating func dequeue() -> Int? {
        if isEmpty { return nil }
        let ret = queue[head]
        queue[head] = nil
        head += 1
        return ret
    }
}
func topologyOrder(_ vertexList: inout [[Int]], _ inDegreeList: inout [Int]) -> [Int] {
    var orderdVertexList = [Int]()
    var queue = Queue()
    for i in 1...inDegreeList.count-1 {
        if inDegreeList[i] == 0 { queue.enqueue(i) }
    }
    while !queue.isEmpty {
        let cur = queue.dequeue()!
        let nextVertexList = vertexList[cur]
        for nextVertex in nextVertexList {
            inDegreeList[nextVertex] -= 1
            if inDegreeList[nextVertex] == 0 { queue.enqueue(nextVertex) }
        }
        orderdVertexList.append(cur)
    }
    return orderdVertexList
}

let nm = readLine()!.split { $0 == " " }.map { Int(String($0))! }
var vertexList = [[Int]](repeating: [Int](), count: nm[0]+1)
var inDegreeList = [Int](repeating: 0, count: nm[0]+1)
(0..<nm[1]).forEach { _ in
    let ab = readLine()!.split { $0 == " " }.map { Int(String($0))! }
    vertexList[ab[0]].append(ab[1])
    inDegreeList[ab[1]] += 1
}
let answer = topologyOrder(&vertexList, &inDegreeList).map { String($0) }.joined(separator: " ")
print(answer)