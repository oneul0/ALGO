import Foundation

struct Point {
    let x: Double
    let y: Double
}

@inline(__always)
func distance(_ a: Point, _ b: Point) -> Double {
    hypot(a.x - b.x, a.y - b.y)
}

struct PriorityQueue<T> {
    var elements: [(T, Double)] = []
    let compare: (Double, Double) -> Bool
    
    mutating func push(_ value: T, _ priority: Double) {
        elements.append((value, priority))
        siftUp(elements.count - 1)
    }
    
    mutating func pop() -> (T, Double)? {
        guard !elements.isEmpty else { return nil }
        elements.swapAt(0, elements.count - 1)
        let result = elements.removeLast()
        siftDown(0)
        return result
    }
    
    var isEmpty: Bool { elements.isEmpty }
    
    mutating func siftUp(_ index: Int) {
        var child = index
        while child > 0 {
            let parent = (child - 1) / 2
            if compare(elements[child].1, elements[parent].1) {
                elements.swapAt(child, parent)
                child = parent
            } else { break }
        }
    }
    
    mutating func siftDown(_ index: Int) {
        var parent = index
        while true {
            let left = parent * 2 + 1
            let right = left + 1
            var candidate = parent
            
            if left < elements.count && compare(elements[left].1, elements[candidate].1) {
                candidate = left
            }
            if right < elements.count && compare(elements[right].1, elements[candidate].1) {
                candidate = right
            }
            if candidate == parent { return }
            elements.swapAt(parent, candidate)
            parent = candidate
        }
    }
}

let startInput = readLine()!.split(separator: " ").map { Double($0)! }
let start = Point(x: startInput[0], y: startInput[1])

let endInput = readLine()!.split(separator: " ").map { Double($0)! }
let end = Point(x: endInput[0], y: endInput[1])

let n = Int(readLine()!)!

var points: [Point] = [start]
for _ in 0..<n {
    let arr = readLine()!.split(separator: " ").map { Double($0)! }
    points.append(Point(x: arr[0], y: arr[1]))
}
points.append(end)

let total = points.count
var graph = Array(repeating: Array(repeating: 0.0, count: total), count: total)

for i in 0..<total {
    for j in 0..<total {
        if i == j { continue }
        let d = distance(points[i], points[j])
        
        let walk = d / 5.0
        var best = walk
        
        if i != 0 {
            let cannon = 2.0 + abs(d - 50.0) / 5.0
            best = min(best, cannon)
        }
        
        graph[i][j] = best
    }
}

var dist = Array(repeating: Double.infinity, count: total)
dist[0] = 0

var pq = PriorityQueue<Int>(compare: <)
pq.push(0, 0)

while !pq.isEmpty {
    let (u, time) = pq.pop()!
    if time > dist[u] { continue }
    
    if u == total - 1 { break }
    
    for v in 0..<total {
        if u == v { continue }
        let next = time + graph[u][v]
        if next < dist[v] {
            dist[v] = next
            pq.push(v, next)
        }
    }
}

print(String(format: "%.10f", dist[total - 1]))