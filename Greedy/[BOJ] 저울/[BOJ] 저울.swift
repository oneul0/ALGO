let n = Int(readLine()!)!
var numbers = readLine()!.split { $0 == " " }.map { Int($0)! }.sorted { $0 > $1 }
var expectNumber = 1

while !numbers.isEmpty {
    guard let last = numbers.last else { break }
    guard last <= expectNumber else { break }
    numbers.popLast()
    expectNumber += last
}

print(expectNumber)