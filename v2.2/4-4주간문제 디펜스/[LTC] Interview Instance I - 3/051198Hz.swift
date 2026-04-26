class Solution {
    func rob(_ nums: [Int]) -> Int {
        if nums.count < 2 {
            return nums[0]
        }
        if nums.count < 3 {
            return max(nums[0], nums[1])
        }

        var dp = Array(repeating: 0, count: nums.count)
        dp[0] = nums[0]
        dp[1] = max(nums[0], nums[1])

        for i in stride(from: 2, to: nums.count, by: 1) {
            dp[i] = max(nums[i] + dp[i-2], dp[i-1])
        }

        return dp[nums.count-1]
    }
}
