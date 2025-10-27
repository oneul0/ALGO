let n = Int(readLine()!)!
var answer = 0

var aList = [Int]()
var bList = [Int]()
var cList = [Int]()
var dList = [Int]()

var abList = [Int]()
var cdList = [Int]()

for _ in 0..<n {
    let abcd = readLine()!.split { $0 == " " }.map { Int($0)! }
    aList.append(abcd[0])
    bList.append(abcd[1])
    cList.append(abcd[2])
    dList.append(abcd[3])
}

for i in 0..<n {
    for j in 0..<n {
        let sumAb = aList[i] + bList[j]
        let sumCd = cList[i] + dList[j]
        abList.append(sumAb)
        cdList.append(sumCd)
    }
}

abList.sort { $0 < $1 }
cdList.sort { $0 < $1 }

for ab in abList {
    let upperBound = upperBound(ab, cdList)
    let lowerBound = lowerBound(ab, cdList)
    
    answer += upperBound - lowerBound
}

print(answer)

func lowerBound(_ ab: Int, _ cdList: [Int]) -> Int {
    var start = 0
    var end = cdList.count
    
    while start < end {
        let mid = (start + end) / 2
        let cd = cdList[mid]
        // 0보다 작음, 즉 덜 빼야함
        if ab + cd < 0 {
            start = mid + 1
        } else {
            end = mid
        }
    }
    
    return start
}

func upperBound(_ ab: Int, _ cd: [Int]) -> Int {
    var start = 0
    var end = cdList.count
    
    while start < end {
        let mid = (start + end) / 2
        let cd = cdList[mid]
        // 0보다 작음, 즉 덜 빼야함
        if ab + cd <= 0 {
            start = mid + 1
        } else {
            end = mid
        }
    }
    
    return start
}
