class Solution {
    func findErrorNums(_ nums: [Int]) -> [Int] {
        var freqCnt = Array(repeating: 0, count: nums.count+1)
        var missing = 0
        var duple = 0

        for n in nums {
            freqCnt[n] += 1
        }

        for i in freqCnt.indices {
            if freqCnt[i] == 0 { missing = i }
            if freqCnt[i] == 2 { duple = i }
        }

        return [duple, missing]
    }

    func smallerNumbersThanCurrent(_ nums: [Int]) -> [Int] {
        let sortedNums = nums.sorted { $0 < $1 }
        return nums.map { lowerIndex(sortedNums, $0) }
    }

    func lowerIndex(_ nums: [Int], _ target: Int) -> Int {
        var left = 0
        var right = nums.count-1

        while left <= right {
            let mid = (left + right) / 2
            if nums[mid] >= target {
                right = mid-1
                continue
            }
            if nums[mid] < target {
                left = mid + 1
            }
        }
        return left
    }

    func findDisappearedNumbers(_ nums: [Int]) -> [Int] {
        var nums = nums

        for n in nums {
            let index = abs(n) - 1

            if nums[index] > 0 {
                nums[index] *= -1
            }
        }

        return nums.enumerated().filter { $0.1 > 0 }.map { $0.0 + 1 }
    }
}

class Solution {
    func smallerNumbersThanCurrent(_ nums: [Int]) -> [Int] {
        var count = Array(repeating: 0, count: 101)

        for num in nums {
            count[num] += 1
        }

        var prefix = Array(repeating: 0, count: 101) 
        for v in 1 ..< 101 {
            prefix[v] = prefix[v - 1] + count[v - 1]
        }

        return nums.map {
            prefix[$0]
        }
    }
}