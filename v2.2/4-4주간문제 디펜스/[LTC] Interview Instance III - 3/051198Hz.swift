class Solution {
    func canSeePersonsCount(_ heights: [Int]) -> [Int] {
        let n = heights.count
        var result = Array(repeating: 0, count: n)
        var stack = [Int]()
        
        for i in stride(from: n - 1, through: 0, by: -1) {
            var count = 0
            
            while let last = stack.last, heights[last] < heights[i] {
                stack.removeLast()
                count += 1
            }
            
            if !stack.isEmpty {
                count += 1
            }
            
            result[i] = count
            stack.append(i)
        }
        
        return result
    }
}
