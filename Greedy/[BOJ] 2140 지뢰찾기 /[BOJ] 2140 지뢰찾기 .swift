let n = Int(readLine()!)!

var answer = 0
var mineMap = [[String]](repeating: [String](repeating: " ", count: n), count: n)
var countMap = [[Int]](repeating: [Int](repeating: -1, count: n), count: n)

for i in 0..<n {
    let line = readLine()!.map { String($0) }
    for j in line.indices {
        if line[j] == "#" {
            mineMap[i][j] = "#"
            continue
        }
        if let number = Int(line[j]) {
            countMap[i][j] = number
            continue
        }
    }
}

if !(1...2).contains(n) {
    for i in stride(from: 1, to: n-1, by: n-3 > 0 ? n-3 : 1) {
        for j in 1..<n-1 {
            if mineMap[i][j] == "#" {
                var flag = true
                for row in stride(from: i - 1, through: i + 1, by: 1) where row >= 0 && row < n {
                    for col in stride(from: j - 1, through: j + 1, by: 1) where col >= 0 && col < n {
                        if countMap[row][col] == -1 { continue }
                        if countMap[row][col] <= 0 {
                            flag = false
                            break
                        }
                    }
                    if flag == false { break }
                }
                guard flag else {
                    mineMap[i][j] = " "
                    continue
                }
                for row in stride(from: i-1, through: i+1, by: 1) where row >= 0 && row < n {
                    for col in stride(from: j-1, through: j+1, by: 1) where col >= 0 && col < n {
                        if countMap[row][col] == -1 { continue }
                        countMap[row][col] = countMap[row][col] - 1 >= 0 ? countMap[row][col] - 1 : 0
                    }
                }
                answer += 1
                mineMap[i][j] = " "
            }
        }
    }
    for i in stride(from: 1, to: n - 1, by: n-3 > 0 ? n-3 : 1) {
        for j in 1..<n - 1 {
            if mineMap[j][i] == "#" {
                var flag = true
                for row in stride(from: j - 1, through: j + 1, by: 1) where row >= 0 && row < n {
                    for col in stride(from: i - 1, through: i + 1, by: 1) where col >= 0 && col < n {
                        if countMap[row][col] == -1 { continue }
                        if countMap[row][col] <= 0 {
                            flag = false
                            break
                        }
                    }
                    if flag == false {
                        mineMap[j][i] = " "
                        break
                    }
                }
                guard flag else { continue }
                for row in stride(from: j - 1, through: j + 1, by: 1) where row >= 0 && row < n {
                    for col in stride(from: i - 1, through: i + 1, by: 1) where col >= 0 && col < n {
                        if countMap[row][col] == -1 { continue }
                        countMap[row][col] = countMap[row][col] - 1 >= 0 ? countMap[row][col] - 1 : 0
                    }
                }
                answer += 1
                mineMap[j][i] = " "
            }
        }
    }
    for i in 0..<n {
        for j in 0..<n {
            if mineMap[i][j] == "#" { answer += 1}
        }
    }
}

print(answer)