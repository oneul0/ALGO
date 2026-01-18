import Foundation

let chars = Array(readLine()!)
var stack: [(Int, Int)] = []

var curLen = 0
var i = 0

while i < chars.count {
    let c = chars[i]

    if c.isNumber {
        let k = Int(String(c))!
        if i + 1 < chars.count && chars[i + 1] == "(" {
            stack.append((curLen, k))
            curLen = 0
            i += 2
            continue
        } else {
            curLen += 1
        }
    }
    else if c == "(" { }
    else if c == ")" {
        if let (prevLen, k) = stack.popLast() {
            curLen = prevLen + curLen * k
        }
    }
    else {
        curLen += 1
    }
    i += 1
}

print(curLen)