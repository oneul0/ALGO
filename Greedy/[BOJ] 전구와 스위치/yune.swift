let n = Int(readLine()!)!
let goal = readLine()!.map { $0 == "1" ? true : false }
let initial = readLine()!.map { $0 == "1" ? true : false }
var current = initial

var answer = Int.max
var count = 0

current[0] = !current[0]
current[1] = !current[1]
count += 1

for i in 1..<n {
    guard current[i-1] != goal[i-1] else { continue }
    for j in i-1...i+1 where j >= 0 && j < n {
        current[j] = !current[j]
    }
    count += 1
}

answer = current == goal ? count : answer

current = initial
count = 0

for i in 1..<n {
    guard current[i - 1] != goal[i - 1] else { continue }
    for j in i - 1...i + 1 where j >= 0 && j < n {
        current[j] = !current[j]
    }
    count += 1
}

answer = current == goal ? count : answer

answer = answer == Int.max ? -1 : answer

print(answer)