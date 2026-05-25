class Solution {
    // 1-1
    func longestConsecutive(_ nums: [Int]) -> Int {
        let set = Set(nums)

        var longest = 0

        for num in set {
            if !set.contains(num - 1) {

                var current = num
                var length = 1

                while set.contains(current + 1) {
                    current += 1
                    length += 1
                }

                longest = max(longest, length)
            }
        }
        return longest
    }
    //1-2
    func candy(_ ratings: [Int]) -> Int {
        var candyList = Array(repeating: 1, count: ratings.count)

        for i in 0..<ratings.count {
            guard i - 1 >= 0 else { continue }
            guard ratings[i-1] < ratings[i] else { continue }
            candyList[i] = max(candyList[i-1] + 1, candyList[i])
        }
        
        for i in stride(from: ratings.count-1, to: -1, by: -1) {
            guard i + 1 < ratings.count else { continue }
            guard ratings[i+1] < ratings[i] else { continue }
            candyList[i] = max(candyList[i+1] + 1, candyList[i])
        }
        return candyList.reduce(0, +)
    }
    // 1-3
    func makeLargestSpecial(_ s: String) -> String {
        let chars = Array(s)

        var count = 0
        var start = 0

        var parts = [String]()

        for i in chars.indices {
            if chars[i] == "1" {
                count += 1
            } else {
                count -= 1
            }

            if count == 0 {
                let inner = String(chars[(start + 1)..<i])
                let processed = makeLargestSpecial(inner)
                
                parts.append("1" + processed + "0")

                start = i + 1
            }
        }

        parts.sort(by: >)

        return parts.joined()
    }
}