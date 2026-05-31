class Solution {
    func buildArray(_ target: [Int], _ n: Int) -> [String] {
        var answer = [String]()
        var currentNumber = 1
        var currentIndex = 0

        while currentIndex < target.count {
            answer.append("Push")
            if target[currentIndex] == currentNumber {
                currentIndex += 1
            } else {
                answer.append("Pop")
            }
            currentNumber += 1
        }

        return answer
    }

    let calc: [String: (Int, Int) -> Int] = [
        "+": (+),
        "-": (-),
        "*": (*), 
        "/": (/)
        ]

    func evalRPN(_ tokens: [String]) -> Int {
        var stack = [Int]()

        for token in tokens {
            if let n = Int(token) {
                stack.append(n)
            } else {
                let a = stack.removeLast()
                let b = stack.removeLast()
                stack.append(calc[token]!(b, a))
            }
        }

        return stack.last ?? -1
    }

    func exclusiveTime(_ n: Int, _ logs: [String]) -> [Int] {
        var result = Array(repeating: 0, count: n)
        var stack = [Int]()
        var prevTime = 0

        for log in logs {
            let parts = log.split(separator: ":")

            let id = Int(parts[0])!
            let action = String(parts[1])
            let time = Int(parts[2])!

            if action == "start" {
                if let current = stack.last {
                    result[current] += time - prevTime
                }
                stack.append(id)
                prevTime = time
            } else {
                let current = stack.removeLast()
                result[current] += time - prevTime + 1
                prevTime = time + 1
            }
        }
        return result
    }
}