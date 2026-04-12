import Foundation

func solution(_ m:Int, _ n:Int, _ h:Int, _ w:Int, _ drops:[[Int]]) -> [Int] {
    
    var order = Array(repeating: Array(repeating: Int.max, count: n), count: m)
    for (i, drop) in drops.enumerated() {
        let (r, c) = (drop[0], drop[1])
        order[r][c] = i + 1
    }

    var rowMin = Array(repeating: [Int](), count: m)
    for r in 0..<m {
        var deque = Deque<Int>(n)
        for c in 0..<n {
            while !deque.isEmpty && order[r][deque.last()] > order[r][c] {
                deque.removeLast()
            }
            deque.append(c)
            if deque.first() <= c - w { deque.removeFirst() }
            if c >= w - 1 {
                rowMin[r].append(order[r][deque.first()])
            }
        }
    }

    var bestTime = -1
    var answer = [0,0]
    for c in 0..<(n - w + 1) {
        var deque = Deque<Int>(m)
        for r in 0..<m {
            while !deque.isEmpty && rowMin[deque.last()][c] > rowMin[r][c] {
                deque.removeLast()
            }
            deque.append(r)
            if deque.first() <= r - h { deque.removeFirst() }
            if r >= h - 1 {
                let time = rowMin[deque.first()][c]
                // 최대값, 동점 시 위쪽 우선, 왼쪽 우선
                if time > bestTime || (time == bestTime && (r-h+1 < answer[0] || (r-h+1 == answer[0] && c < answer[1]))) {
                    bestTime = time
                    answer = [r - h + 1, c]
                }
            }
        }
    }

    return answer
}

struct Deque<T> {
    private var data: [T?]
    private var head: Int = 0
    private var tail: Int = 0
    private var capacity: Int

    init(_ capacity: Int) {
        self.capacity = max(2, capacity)
        data = Array(repeating: nil, count: self.capacity)
    }

    var isEmpty: Bool { head == tail }
    var count: Int { (tail - head + capacity) % capacity }

    mutating func append(_ value: T) {
        if (tail + 1) % capacity == head { resize() }
        data[tail] = value
        tail = (tail + 1) % capacity
    }

    mutating func removeFirst() -> T {
        let val = data[head]!
        data[head] = nil
        head = (head + 1) % capacity
        return val
    }

    mutating func removeLast() -> T {
        tail = (tail - 1 + capacity) % capacity
        let val = data[tail]!
        data[tail] = nil
        return val
    }

    func first() -> T {
        return data[head]!
    }

    func last() -> T {
        return data[(tail - 1 + capacity) % capacity]!
    }

    private mutating func resize() {
        let oldData = data
        capacity *= 2
        data = Array(repeating: nil, count: capacity)
        var i = 0
        var idx = head
        while idx != tail {
            data[i] = oldData[idx]
            i += 1
            idx = (idx + 1) % oldData.count
        }
        head = 0
        tail = i
    }
}