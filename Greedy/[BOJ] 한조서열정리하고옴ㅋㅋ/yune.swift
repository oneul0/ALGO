let n = Int(readLine()!)!
let mountines = readLine()!.split { $0 == " " }.map { Int(String($0))! }

var maxScore = Int.min

for i in 0..<n {
    var currentScore = 0
    for j in stride(from: i+1, to: n, by: 1) {
        guard mountines[j] <= mountines[i] else { break }
        currentScore += 1
    }
    maxScore = maxScore < currentScore ? currentScore : maxScore
}

print(maxScore)