class Solution {
    func containsNearbyDuplicate(_ nums: [Int], _ k: Int) -> Bool {
        guard nums.count > 1 else { return false }
        var windowSize = 1
        while windowSize <= k {
            guard 0 <= nums.count - windowSize else { break }
            for i in 0..<nums.count - windowSize {
                guard nums[i] == nums[i+windowSize] else { continue }
                return true
            }
            windowSize += 1
        }
        return false
    }
}

class Answer {
    // 집합의 크기를 유지하면서 집합 내 같은 숫자가 있는지 검사한다.
    func containsNearbyDuplicate(_ nums: [Int], _ k: Int) -> Bool {
        var window = Set<Int>()

        for i in 0..<nums.count {
            if window.contains(nums[i]) {
                return true
            }

            window.insert(nums[i])

            if window.count > k {
                window.remove(nums[i - k])
            }
        }

        return false
    }

    func numberOfSubstrings(_ s: String) -> Int {
        let s = Array(s)
        var count = 0
        var window: [Character: Int] = ["a": 0, "b": 0, "c": 0]
        var left = 0
        var right = 0
        
        while right < s.count {
            window[s[right]]! += 1

            while window["a"]! > 0 &&
                window["b"]! > 0 &&
                window["c"]! > 0 {
                    count += s.count - right
                    window[s[left]]! -= 1
                    left += 1
            }

            right += 1
        }
        return count
    }

    func characterReplacement(_ s: String, _ k: Int) -> Int {
        let s = Array(s)
        var answer = 0

        var freq = [Character: Int]()
        var maxFreq = 0

        var left = 0

        for right in 0..<s.count {
            freq[s[right], default: 0] += 1
            maxFreq = max(maxFreq, freq[s[right]]!)

            while (right - left + 1) - maxFreq > k {
                freq[s[left]]! -= 1
                left += 1
            }

            answer = max(answer, right - left + 1)
        }

        return answer
    }
}