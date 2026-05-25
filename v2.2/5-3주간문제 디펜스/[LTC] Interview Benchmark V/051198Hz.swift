class Solution {
    func nextBeautifulNumber(_ n: Int) -> Int {
        func isBalanced(_ num: Int) -> Bool {
            var count = Array(repeating: 0, count: 10)
    
            for ch in String(num) {
                let digit = Int(String(ch))!
                count[digit] += 1
            }
    
            for digit in 0...9 {
                if count[digit] > 0 && count[digit] != digit {
                    return false
                }
            }
    
            return true
        }
        
        var num = n + 1

        while true {
            if isBalanced(num) {
                return num
            }

            num += 1
        }
        
        return -1
    }

    let mod = 1_000_000_007

    func numWays(_ steps: Int, _ arrLen: Int) -> Int {
        let maxPos = min(arrLen - 1, steps / 2)

        var dp = Array(repeating: 0, count: maxPos + 1)
        dp[0] = 1

        for _ in 0..<steps {
            var next = Array(repeating: 0, count: maxPos + 1)

            for pos in 0...maxPos {
                next[pos] = (next[pos] + dp[pos]) % mod

                if pos > 0 {
                    next[pos] = (next[pos] + dp[pos - 1]) % mod
                }

                if pos < maxPos {
                    next[pos] = (next[pos] + dp[pos + 1]) % mod
                }
            }

            dp = next
        }

        return dp[0]
    }
        
    var parent = [Int: Int]()

    func gcdSort(_ nums: [Int]) -> Bool {
        func find(_ x: Int) -> Int {

            if parent[x] == nil {
                parent[x] = x
            }

            if parent[x] != x {
                parent[x] = find(parent[x]!)
            }

            return parent[x]!
        }
        func union(_ a: Int, _ b: Int) {
            let pa = find(a)
            let pb = find(b)

            if pa != pb {
                parent[pa] = pb
            }
        }

        for num in nums {
            var x = num
            var factor = 2

            while factor * factor <= x {

                if x % factor == 0 {

                    union(num, factor)

                    while x % factor == 0 {
                        x /= factor
                    }
                }

                factor += 1
            }

            if x > 1 {
                union(num, x)
            }
        }

        let sortedNums = nums.sorted()

        for i in nums.indices {

            if find(nums[i]) != find(sortedNums[i]) {
                return false
            }
        }

        return true
    }
}

class Solution {
    var candidates = [Int]()

    func nextBeautifulNumber(_ n: Int) -> Int {

        generate(1, "")

        candidates.sort()

        for num in candidates {
            if num > n {
                return num
            }
        }

        return -1
    }

    func generate(_ digit: Int, _ current: String) {

        if !current.isEmpty {
            permute(Array(current), 0)
        }

        guard digit <= 9 else { return }

        generate(digit + 1, current)

        let added = current + String(repeating: "\(digit)", count: digit)
        generate(digit + 1, added)
    }

    func permute(_ chars: [Character], _ start: Int) {

        var chars = chars

        if start == chars.count {
            let num = Int(String(chars))!
            candidates.append(num)
            return
        }

        var used = Set<Character>()

        for i in start..<chars.count {

            if used.contains(chars[i]) {
                continue
            }

            used.insert(chars[i])

            chars.swapAt(start, i)

            permute(chars, start + 1)

            chars.swapAt(start, i)
        }
    }
}