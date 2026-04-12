import Foundation

let t = Int(readLine()!)!
var answer = ""

testCase: for _ in 0..<t {
    let line = readLine()!
    let count = line.count
    var balance = 0
    var wild = false

    if line.contains("*") {
        for c in line.reversed() {
            if c == "(" {
                balance -= 1
                if balance < 0 {
                    answer += "NO\n"
                    continue testCase
                }
            }
            if c == ")" {
                balance += 1
            }
            if c == "?" {
                balance += 1
            }
            if c == "*" {
                break
            }
        }

        balance = 0
        wild = false

        for c in line {
            if c == "(" {
                balance += 1
            }
            if c == ")" {
                balance -= 1
                if balance < 0 {
                    answer += "NO\n"
                    continue testCase
                }
            }
            if c == "?" {
                balance += 1
            }
            if c == "*" {
                break
            }
        }
    } else {
        if (count % 2) != 0 {
            answer += "NO\n"
            continue testCase
        }
        
        var opn = 0, close = 0
        
        for c in line {
            if c == "(" {
                opn += 1
            } else if c == ")" {
                close += 1
            }
        }
        
        if (opn > (count / 2)) || (close > (count / 2)) {
            answer += "NO\n"
            continue testCase
        }

        for c in line {
            if c == "?" {
                if opn < (count / 2) {
                    balance += 1
                    opn += 1
                } else {
                    balance -= 1
                }
            }
            if c == "(" {
                balance += 1
            }
            if c == ")" {
                balance -= 1
            }
            if balance < 0 {
                answer += "NO\n"
                continue testCase
            }
        }
    }

    answer += "YES\n"
}

print(answer)