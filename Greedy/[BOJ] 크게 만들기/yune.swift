let nk = readLine()!.split { $0 == " " }.map { Int($0)! },
    n = nk[0],
    k = nk[1]
var numbers: [Int] = readLine()!.map { Int(String($0))! }.reversed()

var stack = [Int]()
var popCount = 0

while popCount < k, !numbers.isEmpty {
    guard let number = numbers.popLast() else { break }
    while popCount < k, let last = stack.last, last < number {
        stack.popLast()
        popCount += 1
    }
    stack.append(number)
}

while !numbers.isEmpty {
    stack.append(numbers.popLast()!)
}

while popCount < k {
    stack.popLast()
    popCount += 1
}

print(stack.map { "\($0)"}.joined())