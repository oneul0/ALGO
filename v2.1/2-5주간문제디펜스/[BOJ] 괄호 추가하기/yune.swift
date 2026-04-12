import Foundation

let N = Int(readLine()!)!
let input = Array(readLine()!)

var nums = [Int]()
var ops = [Character]()

for i in 0..<N {
    if i % 2 == 0 {
        nums.append(Int(String(input[i]))!)
    } else {
        ops.append(input[i])
    }
}

func calc(_ a: Int, _ op: Character, _ b: Int) -> Int {
    switch op {
    case "+": return a + b
    case "-": return a - b
    case "*": return a * b
    default: return 0
    }
}

var answer = Int.min

func dfs(_ idx: Int, _ current: Int) {
    if idx >= ops.count {
        answer = max(answer, current)
        return
    }
    
    let next = calc(current, ops[idx], nums[idx + 1])
    dfs(idx + 1, next)
    
    if idx + 1 < ops.count {
        let bracket = calc(nums[idx + 1], ops[idx + 1], nums[idx + 2])
        let next2 = calc(current, ops[idx], bracket)
        dfs(idx + 2, next2)
    }
}

dfs(0, nums[0])
print(answer)