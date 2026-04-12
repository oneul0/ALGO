import Foundation

let expr = readLine()!
let chars = Array(expr)

var numbers = [Int]()
var ops = [Character]()

var i = 0
let n = chars.count

while i < n {
    if "+-*/".contains(chars[i]) {
        // 맨 앞이거나 이전이 연산자면 음수
        if i == 0 || "+-*/".contains(chars[i - 1]) {
            var j = i
            i += 1
            while i < n && chars[i].isNumber {
                i += 1
            }
            let num = Int(String(chars[j..<i]))!
            numbers.append(num)
        } else {
            ops.append(chars[i])
            i += 1
        }
    } else {
        var j = i
        while i < n && chars[i].isNumber {
            i += 1
        }
        let num = Int(String(chars[j..<i]))!
        numbers.append(num)
    }
}

var left = 0
var right = numbers.count - 1

while left < right {
    let leftOp = ops[left]
    let rightOp = ops[right - 1]
    
    let leftPri = priority(leftOp)
    let rightPri = priority(rightOp)
    
    var chooseLeft = false
    
    if leftPri > rightPri {
        chooseLeft = true
    } else if leftPri < rightPri {
        chooseLeft = false
    } else {
        let leftVal = calc(numbers[left], leftOp, numbers[left + 1])
        let rightVal = calc(numbers[right - 1], rightOp, numbers[right])
        
        if leftVal > rightVal {
            chooseLeft = true
        } else if leftVal < rightVal {
            chooseLeft = false
        } else {
            chooseLeft = true
        }
    }
    
    if chooseLeft {
        numbers[left + 1] = calc(numbers[left], leftOp, numbers[left + 1])
        left += 1
    } else {
        numbers[right - 1] = calc(numbers[right - 1], rightOp, numbers[right])
        right -= 1
    }
}

print(numbers[left])

func priority(_ op: Character) -> Int {
    if op == "*" || op == "/" { return 2 }
    return 1
}

func calc(_ a: Int, _ op: Character, _ b: Int) -> Int {
    switch op {
    case "+": return a + b
    case "-": return a - b
    case "*": return a * b
    case "/": return a / b
    default: return 0
    }
}