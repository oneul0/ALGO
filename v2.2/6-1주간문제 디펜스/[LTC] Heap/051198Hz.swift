class Solution {
    func lastStoneWeight(_ stones: [Int]) -> Int {
        var heap = Heap<Int>()
        heap.insert(contentsOf: stones)
        
        while true {
            guard let a = heap.popMax() else { return 0 }
            guard let b = heap.popMax() else { return a }
            heap.insert(abs(a-b))
        }

        return heap.max ?? 0
    }   

    struct SumPair: Comparable {
        let sum: Int
        let i: Int
        let j: Int

        static func < (lhs: SumPair, rhs: SumPair) -> Bool {
            return lhs.sum < rhs.sum
        }
    }

    func kSmallestPairs(_ nums1: [Int], _ nums2: [Int], _ k: Int) -> [[Int]] {
        var pairs = [[Int]]()
        var heap = Heap<SumPair>()
        (0..<nums1.count).forEach {
            heap.insert(SumPair(sum: nums1[$0] + nums2[0], i: $0, j: 0))
        }

        while pairs.count < k {
            guard let minSum = heap.popMin() else { return [[]] }

            let a = nums1[minSum.i]
            let b = nums2[minSum.j]

            pairs.append([a, b])
            guard minSum.j + 1 < nums2.count else { continue }
            heap.insert(SumPair(sum: nums1[minSum.i] + nums2[minSum.j + 1], i: minSum.i, j: minSum.j + 1))
        }

        return pairs
    }
    
    func isPossible(_ target: [Int]) -> Bool {
        var sum = target.reduce(0, +)
        var heap = Heap<Int>()
        heap.insert(contentsOf: target)
        
        while let largest = heap.popMax() {
            let rest = sum - largest

            if rest == 1 { return true }
            if largest == 1 { return true }

            guard rest < largest else { return false }
            guard rest != 0 else { return false }

            let prev = largest % rest

            if prev == 0 { return false }

            sum = rest + prev
            heap.insert(prev)
        }
        
        return false
    }
}