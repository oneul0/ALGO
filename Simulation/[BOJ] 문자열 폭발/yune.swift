var str = readLine()!.map { String($0) }
let boom: [String] = readLine()!.map { String($0) }.reversed()

var stack = [String]()

while !str.isEmpty {
    let char = str.removeLast()
    stack.append(char)
    if stack.count >= boom.count {
        if stack.suffix(boom.count) == boom.suffix(boom.count) {
            (0..<boom.count).forEach { _ in stack.popLast() }
        }
    }
}
if stack.isEmpty {
    print("FRULA")
} else {
    print(stack.reversed().reduce(into: "") { $0.write($1) })
}