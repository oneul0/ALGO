class LRUCache {
    private final class Node {
        let key: Int
        var value: Int
        var prev: Node?
        var next: Node?
        
        init(key: Int, value: Int) {
            self.key = key
            self.value = value
        }
    }

    private var capacity: Int
    private var dict: [Int: Node] = [:]
    private var head: Node?
    private var tail: Node?

    init(_ capacity: Int) {
        if capacity < 0 {
            self.capacity = 0
        }
        self.capacity = capacity
    }
    
    func get(_ key: Int) -> Int {
        guard let node = dict[key] else { return -1 }
        moveToHead(node)

        return node.value
    }
    
    func put(_ key: Int, _ value: Int) {
        if let node = dict[key] {
            node.value = value
            moveToHead(node)
            return
        }
        
        let node = Node(key: key, value: value)
        dict[key] = node
        addToHead(node)
        
        if dict.count > capacity {
            removeLRU()
        }
    }

    private func addToHead(_ node: Node) {
        node.next = head
        node.prev = nil
        
        head?.prev = node
        head = node
        
        if tail == nil {
            tail = node
        }
    }
    
    private func removeNode(_ node: Node) {
        if node === head {
            head = node.next
        }
        if node === tail {
            tail = node.prev
        }
        
        node.prev?.next = node.next
        node.next?.prev = node.prev
    }
    
    private func moveToHead(_ node: Node) {
        removeNode(node)
        addToHead(node)
    }
    
    private func removeLRU() {
        guard let tail = tail else { return }
        dict.removeValue(forKey: tail.key)
        removeNode(tail)
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * let obj = LRUCache(capacity)
 * let ret_1: Int = obj.get(key)
 * obj.put(key, value)
 */
