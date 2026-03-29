import Foundation

let firstLine = readLine()!
let T = Int(firstLine.trimmingCharacters(in: .whitespaces))!

for t in 1...T {
    var rawCommands = ""

    while let line = readLine() {
        let trimmed = line.trimmingCharacters(in: .whitespaces)
        if trimmed == "end" { break }

        let codeOnly = line.components(separatedBy: "%")[0]
        rawCommands += codeOnly
    }

    let validChars: Set<Character> = [">", "<", "+", "-", ".", "[", "]"]
    let commands = rawCommands.filter { validChars.contains($0) }.map { $0 }

    print("PROGRAM #\(t):")

    var bracketMap = [Int: Int]()
    var stack = [Int]()
    var isCompileError = false

    for (i, char) in commands.enumerated() {
        if char == "[" {
            stack.append(i)
        } else if char == "]" {
            if stack.isEmpty {
                isCompileError = true
                break
            }
            let start = stack.removeLast()
            bracketMap[start] = i
            bracketMap[i] = start
        }
    }

    if isCompileError || !stack.isEmpty {
        print("COMPILE ERROR")
        continue
    }

    var memory = [UInt8](repeating: 0, count: 32768)
    var ptr = 0
    var ip = 0
    var output = ""

    while ip < commands.count {
        let cmd = commands[ip]

        switch cmd {
        case ">":
            ptr = (ptr + 1) % 32768
        case "<":
            ptr = (ptr - 1 + 32768) % 32768
        case "+":
            // UInt8은 255에서 +1하면 0이 되도록 wrapping 연산(&+) 사용
            memory[ptr] &+= 1
        case "-":
            memory[ptr] &-= 1
        case ".":
            let char = Character(UnicodeScalar(memory[ptr]))
            output.append(char)
        case "[":
            if memory[ptr] == 0 {
                ip = bracketMap[ip]!
            }
        case "]":
            if memory[ptr] != 0 {
                ip = bracketMap[ip]!
            }
        default:
            break
        }
        ip += 1
    }

    print(output)
}