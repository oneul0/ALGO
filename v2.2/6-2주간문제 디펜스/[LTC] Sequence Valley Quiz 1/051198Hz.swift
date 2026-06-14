import Collections

struct Apple: Comparable {
    let expire: Int
    var count: Int

    static func < (lhs: Apple, rhs: Apple) -> Bool {
        lhs.expire < rhs.expire
    }
}

class Solution {
    func eatenApples(_ apples: [Int], _ days: [Int]) -> Int {
        var heap = Heap<Apple>()

        var day = 0
        var answer = 0

        while day < apples.count || !heap.isEmpty {

            if day < apples.count, apples[day] > 0 {
                heap.insert(
                    Apple(
                        expire: day + days[day],
                        count: apples[day]
                    )
                )
            }

            while let apple = heap.min,
                  apple.expire <= day {
                heap.removeMin()
            }

            if var apple = heap.popMin() {
                answer += 1

                apple.count -= 1

                if apple.count > 0 {
                    heap.insert(apple)
                }
            }

            day += 1
        }

        return answer
    }
}

class MyCircularQueue {

    private let capacity: Int
    private var queue: [Int]

    private var head = 0
    private var tail = 0
    private var count = 0

    init(_ k: Int) {
        capacity = k
        queue = Array(repeating: 0, count: k)
    }

    func enQueue(_ value: Int) -> Bool {
        guard !isFull() else {
            return false
        }

        queue[tail] = value
        tail = (tail + 1) % capacity
        count += 1

        return true
    }

    func deQueue() -> Bool {
        guard !isEmpty() else {
            return false
        }

        head = (head + 1) % capacity
        count -= 1

        return true
    }

    func Front() -> Int {
        isEmpty() ? -1 : queue[head]
    }

    func Rear() -> Int {
        guard !isEmpty() else {
            return -1
        }

        let index = (tail - 1 + capacity) % capacity
        return queue[index]
    }

    func isEmpty() -> Bool {
        count == 0
    }

    func isFull() -> Bool {
        count == capacity
    }
}