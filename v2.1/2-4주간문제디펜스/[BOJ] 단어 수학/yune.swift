import Foundation

let N = Int(readLine()!)!
var weight = [Character: Int]()

for _ in 0..<N {
    let word = readLine()!
    var multiplier = 1
    
    for ch in word.reversed() {
        weight[ch, default: 0] += multiplier
        multiplier *= 10
    }
}

let sorted = weight.sorted { $0.value > $1.value }

var digit = 9
var answer = 0

for (_, w) in sorted {
    answer += w * digit
    digit -= 1
}

print(answer)