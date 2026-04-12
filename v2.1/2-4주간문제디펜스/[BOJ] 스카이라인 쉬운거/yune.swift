import Foundation

let n = Int(readLine()!)!
var stack = [Int]()
var count = 0

for _ in 0..<n {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    let y = input[1]
    
    while let last = stack.last, last > y {
        stack.removeLast()
    }
    
    if y > 0 {
        if stack.isEmpty || stack.last! < y {
            stack.append(y)
            count += 1
        }
    }
}

print(count)