let n = Int(readLine()!)!
let s = readLine()!.split { $0 == " " }.map { Int(String($0))! }

var parent = [Int](0..<s.count)
var count = [Int](repeating: 1, count: s.count)

var maxLengthIndex = 0
var maxLength = 1

for i in stride(from: 1, to: s.count, by: 1) {
    for j in stride(from: 0, to: i, by: 1) {
        if s[j] < s[i], count[j]+1 > count[i] {
            count[i] = count[j] + 1
            parent[i] = j
            if count[i] > maxLength {
                maxLength = count[i]
                maxLengthIndex = i
            }
        }
    }
}

var answer = [Int]()
var i = maxLengthIndex

while parent[i] != i {
    answer.append(s[i])
    i = parent[i]
}

answer.append(s[i])
print(maxLength)
print(answer.reversed().map { "\($0)"}.joined(separator: " "))