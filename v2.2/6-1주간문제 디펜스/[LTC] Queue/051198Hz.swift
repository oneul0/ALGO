class Solution {
    func countStudents(_ students: [Int], _ sandwiches: [Int]) -> Int {
        var stack = sandwiches
        var stackHead = 0

        var queue = students
        var queueHead = 0

        var unableEaterCount = 0

        while queue.count > queueHead {
            if queue.count - queueHead == unableEaterCount {
                break
            }
            if stack[stackHead] == queue[queueHead] {
                unableEaterCount = 0
                queueHead += 1
                stackHead += 1
                continue
            }
            unableEaterCount += 1
            queue.append(queue[queueHead])
            queueHead += 1
        }
        
        return unableEaterCount
    }

    func timeRequiredToBuy(_ tickets: [Int], _ k: Int) -> Int {
        var queue = tickets
        let ticketAmount = tickets.count
        var cycleIndex = -1
        var timeTaken = 0
        var head = 0
        
        while true {
            let currentTikets = queue[head] - 1

            cycleIndex += 1
            cycleIndex %= ticketAmount

            if cycleIndex == k, currentTikets == 0 {
                timeTaken += 1
                break
            }
            if currentTikets >= 0 {
                timeTaken += 1
            }
            queue.append(max(currentTikets, 0))
            head += 1
        }

        return timeTaken
    }
}

class MyQueue {

    private var outQueue = [Int]()
    private var inQueue = [Int]()

    init() {
        
    }
    
    func push(_ x: Int) {
        inQueue.append(x)
    }
    
    func pop() -> Int {
        if outQueue.isEmpty {
            outQueue = reverse()
        }
        return outQueue.removeLast()
    }
    
    func peek() -> Int {
        guard let peek = outQueue.last else { return inQueue.first ?? 0 }
        return peek
    }
    
    func empty() -> Bool {
        return inQueue.isEmpty && outQueue.isEmpty
    }

    private func reverse() -> [Int] {
        var stack = [Int]()
        while !inQueue.isEmpty {
            stack.append(inQueue.removeLast())
        }
        return stack
    }
}