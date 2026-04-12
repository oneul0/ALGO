import Foundation

let n = Int(readLine()!)!
var cows: [(Int, Int)] = []

for _ in 0..<n {
    if let line = readLine() {
        let parts = line.split(separator: " ")
        let arrive = Int(parts[0])!
        let checkTime = Int(parts[1])!
        cows.append((arrive, checkTime))
    }
}

cows.sort { a, b in
    if a.0 == b.0 {
        return a.1 < b.1
    }
    return a.0 < b.0
}

var currentTime = 0

for cow in cows {
    let arrive = cow.0
    let check = cow.1
    
    if currentTime < arrive {
        currentTime = arrive
    }
    
    currentTime += check
}

print(currentTime)