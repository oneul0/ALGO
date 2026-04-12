let ns = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = ns[0],
    s = ns[1]
let numberList = readLine()!.split { $0 == " " }.map { Int(String($0))! }
var sumList = [0]
for i in 0..<numberList.count {
    let curSum = sumList.last! + numberList[i]
    sumList.append(curSum)
}

var start = 1
var end = 1
var answer = Int.max
while true {
    if start >= sumList.count || end >= sumList.count { break }
    let curSum = sumList[end] - sumList[start-1]
    if curSum >= s {
        answer = min(answer, end - start + 1)
        start += 1
        continue
    }
    end += 1
}
print(answer == Int.max ? 0 : answer)