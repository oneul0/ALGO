class Solution {
    func getConcatenation(_ nums: [Int]) -> [Int] {
        return nums + nums
    }

    func shuffle(_ nums: [Int], _ n: Int) -> [Int] {
        return nums[..<n].enumerated().map { [$1, nums[$0 + n]] }.flatMap { $0 }
    }

    func findMaxConsecutiveOnes(_ nums: [Int]) -> Int {
        var maxCon = 0
        var currentCon = 0

        for n in nums {
            if n == 0 { currentCon = 0 }
            if n == 1 { 
                currentCon += 1
                maxCon = max(currentCon, maxCon)
            }
        }
        
        return maxCon
    }
}