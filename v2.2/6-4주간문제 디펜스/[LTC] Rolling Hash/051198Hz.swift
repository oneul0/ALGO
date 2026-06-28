class Solution {
    func shortestPalindrome(_ s: String) -> String {
        let chars = Array(s)
        let n = chars.count

        if n <= 1 {
            return s
        }

        let base: UInt64 = 131

        var forward: UInt64 = 0
        var backward: UInt64 = 0
        var power: UInt64 = 1

        var longest = 0

        for i in 0..<n {
            let value = UInt64(chars[i].asciiValue!)

            forward = forward &* base &+ value
            backward = backward &+ value &* power

            if forward == backward {
                longest = i
            }

            power = power &* base
        }

        let suffix = chars[(longest + 1)...]
        return String(suffix.reversed()) + s
    }

    func longestPrefix(_ s: String) -> String {
        let chars = Array(s)
        let n = chars.count

        var leftHash: UInt64 = 0
        var rightHash: UInt64 = 0
        let base: UInt64 = 131
        var power: UInt64 = 1
        var longest = 0

        for i in 0..<n-1 {
            let value = UInt64(chars[i].asciiValue!)

            leftHash = leftHash &* base &+ UInt64(chars[i].asciiValue!)
            rightHash = rightHash &+ UInt64(chars[n-1-i].asciiValue!) &* power

            if leftHash == rightHash {
                longest = i+1
            }

            power = power &* base
        }

        return String(chars.prefix(longest))
    }

    func sumScores(_ s: String) -> Int {
        let chars = Array(s)
        let n = chars.count

        let base: UInt64 = 911382323

        var power = [UInt64](repeating: 1, count: n + 1)
        var hash = [UInt64](repeating: 0, count: n + 1)

        for i in 0..<n {
            power[i + 1] = power[i] &* base
            hash[i + 1] = hash[i] &* base &+ UInt64(chars[i].asciiValue!)
        }

        func getHash(_ l: Int, _ r: Int) -> UInt64 {
            return hash[r + 1] &- hash[l] &* power[r - l + 1]
        }

        var ans = 0

        for start in 0..<n {

            var lo = 0
            var hi = n - start

            while lo < hi {
                let mid = (lo + hi + 1) / 2

                let prefixHash = getHash(0, mid - 1)
                let suffixHash = getHash(start, start + mid - 1)

                if prefixHash == suffixHash {
                    lo = mid
                } else {
                    hi = mid - 1
                }
            }

            ans += lo
        }

        return ans
    }
}