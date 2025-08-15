let t = Int(readLine()!)!

for _ in 0..<t {
    let n = Int(readLine()!)!

    let mineCount = readLine()!.map { Int(String($0))! }
    let mineInfo = readLine()!.map { String($0) }

    var answer = mineInfo.filter { $0 == "*" }.count
    var need = mineCount

    for i in 0..<n {
        if mineInfo[i] == "*" { 
            need[i] -= 1
            if i-1 >= 0 {
                need[i-1] -= 1
            }
            if i+1 < n {
                need[i+1] -= 1
            }
        }
    }

    for i in 0..<n {
        if mineInfo[i] == "#" {
            var isAllNotZero = true
            for i in stride(from: i-1, through: i+1, by: 1) where i >= 0 && i < n {
                if need[i] < 1 { isAllNotZero = false }
            }
            if isAllNotZero {
                answer += 1
                for i in stride(from: i - 1, through: i + 1, by: 1) where i >= 0 && i < n {
                    need[i] -= 1
                }
            }
        }
    }

    print(answer)
}