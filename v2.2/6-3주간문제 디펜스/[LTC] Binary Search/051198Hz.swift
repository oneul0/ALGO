class Solution {
    func peakIndexInMountainArray(_ arr: [Int]) -> Int {
        func highNumberIndex(_ arr: [Int]) -> Int {
            var left = 0
            var right = arr.count - 1
            
            while left < right {
                let mid = (left + right) / 2
                if arr[mid] < arr[mid+1] {
                    left = mid + 1
                } else {
                    right = mid
                }
            }

            return right
        }
        let i = highNumberIndex(arr)

        return i
    }

    func search(_ nums: [Int], _ target: Int) -> Int {
        var left = 0
        var right = nums.count - 1
        while left < right {
            let mid = (left + right) / 2
            if nums[mid] < target {
                left = mid + 1
            } else {
                right = mid
            }
        }
        return nums[left] == target ? left : -1
    }

    func judgeSquareSum(_ c: Int) -> Bool {
        func binSearch(_ b: Int, _ low: Int, _ high: Int) -> Int {
            var low = low
            var high = high

            while low < high {
                let a = (low + high) / 2
                if a*a + b*b < c {
                    low = a + 1
                } else {
                    high = a
                }
            }
            return high
        }

        var b = 0

        while b <= Int(sqrt(Double(c))) {
            let a = binSearch(b, 0, b)
            if a*a + b*b == c {
                return true
            }
            b += 1
        }
        return false
    }

    func search(_ nums: [Int], _ target: Int) -> Int {
        var rotated = 0
        for i in stride(from: nums.count - 1, through: 1, by: -1){
            if nums[i] < nums[i-1] {
                rotated = nums.count - i
                break
            }
        }
        if rotated == nums.count { 
            rotated = 0 
        }
        var nums = nums.sorted { $0 < $1 }
        var left = 0
        var right = nums.count - 1
        while left < right {
            let mid = (left + right) / 2
            if nums[mid] < target {
                left = mid + 1
            } else {
                right = mid
            }
        }
        guard nums[left] == target else { return -1 }
        return left - rotated >= 0 ? left - rotated : nums.count - abs(left-rotated)
    }
}