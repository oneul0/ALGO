class Solution {
    func largestAltitude(_ gain: [Int]) -> Int {
        var highAltd = 0
        var currentAltd = 0

        for da in gain {
            currentAltd += da
            highAltd = max(currentAltd, highAltd)
        }
        
        return highAltd
    }

    func minSubarray(_ nums: [Int], _ p: Int) -> Int {
        let total = nums.reduce(0, +)
        print(total)
        let k = total % p
        var rangeLength = Int.max

        if k == 0 { return 0 }

        var sumMap = [Int: [Int]]()
        var prefixSum = Array(repeating: 0, count: nums.count+1)

        for i in 1...nums.count {
            prefixSum[i] = prefixSum[i-1] + nums[i-1]
            sumMap[prefixSum[i] % p, default: []].append(i)
        }

        for i in 0...nums.count {
            guard let indexs = sumMap[((prefixSum[i] % p) + k) % p] else { continue }
            for index in indexs {
                guard index > i else { continue }
                guard index - i != nums.count else { continue }
                rangeLength = min(rangeLength, index - i)
            }
        }

        return rangeLength == Int.max ? -1 : rangeLength
    }

    func waysToMakeFair(_ nums: [Int]) -> Int {
        var answer = 0
        let totalSum = nums.reduce(0, +)
        var evenSum = [Int](repeating: 0, count: nums.count+1)
        var oddSum = [Int](repeating: 0, count: nums.count+1)

        for i in 1...nums.count {
            if (i-1) % 2 == 0 {
                evenSum[i] = evenSum[i-1] + nums[i-1]
                oddSum[i] = oddSum[i-1]
            } else {
                evenSum[i] = evenSum[i-1]
                oddSum[i] = oddSum[i-1] + nums[i-1]
            }
        }

        for i in 0..<nums.count {
            let leftEvenSum = evenSum[i]
            let rightEvenSum = oddSum[nums.count] - oddSum[i+1]
            let newEvenSum = leftEvenSum + rightEvenSum

            let leftOddSum = oddSum[i]
            let rightOddSum = evenSum[nums.count] - evenSum[i+1]
            let newOddSum = leftOddSum + rightOddSum

            guard newEvenSum == newOddSum else { continue }
            answer += 1
        }
        return answer
    }
}