import Foundation

let T = Int(readLine()!)!

for _ in 0..<T {
    let DN = readLine()!.split { $0 == " " }.map { Int(String($0))! },
        D = DN[0],
        N = DN[1]

    var answer = 0
    var remainderList = [Int](repeating: 0, count: D)
    let arr = readLine()!.split { $0 == " " }.map { Int(String($0))! }
    remainderList[0] = 1

    var sum = 0
    for element in arr {
        sum = (sum + element) % D
        remainderList[sum] += 1
    }  

    for i in 0..<D {
        var count = remainderList[i]
        guard count >= 2 else { continue }
        answer += (count * (count - 1)) / 2
    }
    print(answer)
}