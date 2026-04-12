import Foundation

let s = Array(readLine()!)
var first = Array(repeating: -1, count: 26)
var second = Array(repeating: -1, count: 26)

for i in 0..<s.count {
    let idx = Int(s[i].asciiValue! - Character("A").asciiValue!)
    if first[idx] == -1 {
        first[idx] = i
    } else {
        second[idx] = i
    }
}

var answer = 0

for i in 0..<26 {
    for j in i+1..<26 {
        let a1 = first[i]
        let a2 = second[i]
        let b1 = first[j]
        let b2 = second[j]

        if (a1 < b1 && b1 < a2 && a2 < b2) ||
           (b1 < a1 && a1 < b2 && b2 < a2) {
            answer += 1
        }
    }
}

print(answer)