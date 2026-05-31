class Solution {
    func finalPrices(_ prices: [Int]) -> [Int] {
        var answer = prices
        var stack = [Int]()

        for i in prices.indices {
            while let last = stack.last,
                  prices[i] <= prices[last] {

                answer[last] -= prices[i]
                stack.removeLast()
            }
            stack.append(i)
        }
        return answer
    }

    func dailyTemperatures(_ temperatures: [Int]) -> [Int] {
        var answer = Array(
            repeating: 0,
            count: temperatures.count
        )
        var stack = [Int]()

        for i in temperatures.indices {
            while let last = stack.last,
                  temperatures[i] > temperatures[last] {
                answer[last] = i - last
                stack.removeLast()
            }
            stack.append(i)
        }
        return answer
    }
    
    func largestRectangleArea(_ heights: [Int]) -> Int {
        var heights = heights + [0]
        var stack = [Int]()
        var answer = 0

        for i in heights.indices {
            while let last = stack.last,
                  heights[last] > heights[i] {
                let height = heights[stack.removeLast()]
                var width = i

                if let left = stack.last {
                    width = i - left - 1
                }
                answer = max(answer, height * width)
            }
            stack.append(i)
        }
        return answer
    }
}