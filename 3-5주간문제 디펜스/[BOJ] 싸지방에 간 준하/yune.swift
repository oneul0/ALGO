import Foundation

let N = Int(readLine()!)!
var people = [(start: Int, end: Int)]()

for _ in 0..<N {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    people.append((input[0], input[1]))
}

people.sort { $0.start < $1.start }

var usingHeap = MinHeap<(end: Int, seat: Int)> { $0.end < $1.end }

var emptySeats = MinHeap<Int> { $0 < $1 }

var count = [Int]()

var seatCount = 0

for person in people {
    let start = person.start
    let end = person.end
    
    while let top = usingHeap.peek(), top.end <= start {
        _ = usingHeap.pop()
        emptySeats.push(top.seat)
    }
    
    let seat: Int
    
    if let available = emptySeats.pop() {
        seat = available
    } else {
        seat = seatCount
        seatCount += 1
        count.append(0)
    }
    
    count[seat] += 1
    usingHeap.push((end, seat))
}

print(seatCount)
print(count.map { String($0) }.joined(separator: " "))

struct MinHeap<T> {
    private var heap: [T] = []
    private let areSorted: (T, T) -> Bool

    init(_ areSorted: @escaping (T, T) -> Bool) {
        self.areSorted = areSorted
    }

    var isEmpty: Bool { heap.isEmpty }
    
    func peek() -> T? {
        return heap.first
    }

    mutating func push(_ element: T) {
        heap.append(element)
        siftUp(heap.count - 1)
    }

    mutating func pop() -> T? {
        guard !heap.isEmpty else { return nil }
        heap.swapAt(0, heap.count - 1)
        let result = heap.removeLast()
        siftDown(0)
        return result
    }

    private mutating func siftUp(_ index: Int) {
        var child = index
        var parent = (child - 1) / 2
        
        while child > 0 && areSorted(heap[child], heap[parent]) {
            heap.swapAt(child, parent)
            child = parent
            parent = (child - 1) / 2
        }
    }

    private mutating func siftDown(_ index: Int) {
        var parent = index
        
        while true {
            let left = parent * 2 + 1
            let right = parent * 2 + 2
            var candidate = parent
            
            if left < heap.count && areSorted(heap[left], heap[candidate]) {
                candidate = left
            }
            if right < heap.count && areSorted(heap[right], heap[candidate]) {
                candidate = right
            }
            
            if candidate == parent { return }
            
            heap.swapAt(parent, candidate)
            parent = candidate
        }
    }
}