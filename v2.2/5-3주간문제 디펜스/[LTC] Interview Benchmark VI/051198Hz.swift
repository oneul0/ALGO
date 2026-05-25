class Solution {
    func rearrangeArray(_ nums: [Int]) -> [Int] {
        var positives = [Int]()
        var negatives = [Int]()

        for num in nums {
            if num > 0 {
                positives.append(num)
            } else {
                negatives.append(num)
            }
        }

        var result = [Int]()

        for i in 0..<positives.count {
            result.append(positives[i])
            result.append(negatives[i])
        }
        return result
    }

    func countSubarrays(_ nums: [Int], _ k: Int) -> Int {
        let n = nums.count
        let kIndex = nums.firstIndex(of: k)!
        var map = [Int: Int]()

        map[0] = 1

        var balance = 0

        for i in (kIndex + 1)..<n {
            if nums[i] > k {
                balance += 1
            } else {
                balance -= 1
            }

            map[balance, default: 0] += 1
        }

        var answer = map[0, default: 0] + map[1, default: 0]
        balance = 0

        for i in stride(from: kIndex - 1, through: 0, by: -1) {
            if nums[i] > k {
                balance += 1
            } else {
                balance -= 1
            }

            answer += map[-balance, default: 0]
            answer += map[1 - balance, default: 0]
        }
        return answer
    }
}

class LFUCache {
    class Node {
        var key: Int
        var value: Int
        var freq: Int = 1

        var prev: Node?
        var next: Node?

        init(_ key: Int, _ value: Int) {
            self.key = key
            self.value = value
        }
    }

    class DoublyLinkedList {
        let head = Node(0, 0)
        let tail = Node(0, 0)

        var size = 0

        init() {
            head.next = tail
            tail.prev = head
        }

        func addFirst(_ node: Node) {

            node.next = head.next
            node.prev = head

            head.next?.prev = node
            head.next = node

            size += 1
        }

        func remove(_ node: Node) {

            node.prev?.next = node.next
            node.next?.prev = node.prev

            size -= 1
        }

        func removeLast() -> Node? {

            guard size > 0 else { return nil }

            let node = tail.prev!

            remove(node)

            return node
        }
    }

    let capacity: Int

    var minFreq = 0

    var cache = [Int: Node]()

    var freqMap = [Int: DoublyLinkedList]()

    init(_ capacity: Int) {
        self.capacity = capacity
    }

    func get(_ key: Int) -> Int {
        guard let node = cache[key] else {
            return -1
        }

        updateFreq(node)

        return node.value
    }

    func put(_ key: Int, _ value: Int) {
        guard capacity > 0 else { return }

        if let node = cache[key] {
            node.value = value
            updateFreq(node)

            return
        }

        if cache.count >= capacity {
            let list = freqMap[minFreq]!
            let removed = list.removeLast()!

            cache.removeValue(forKey: removed.key)
        }

        let node = Node(key, value)

        cache[key] = node
        minFreq = 1

        let list = freqMap[1] ?? DoublyLinkedList()
        list.addFirst(node)

        freqMap[1] = list
    }

    func updateFreq(_ node: Node) {
        let oldFreq = node.freq

        let oldList = freqMap[oldFreq]!
        oldList.remove(node)

        if oldFreq == minFreq && oldList.size == 0 {
            minFreq += 1
        }
        node.freq += 1

        let newList = freqMap[node.freq] ?? DoublyLinkedList()
        newList.addFirst(node)

        freqMap[node.freq] = newList
    }
}