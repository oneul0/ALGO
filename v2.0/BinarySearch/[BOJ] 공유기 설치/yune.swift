import Foundation

let nc = readLine()!.split(separator: " ").map{ Int($0)! }
let n = nc[0]
let c = nc[1]
var housePositions = [Int]()

for _ in 0..<n {
    housePositions.append(Int(readLine()!)!)
}

housePositions.sort()

var start = 1
var end = housePositions.last! - housePositions.first!
var answer = 0

while start <= end {
    let minimumDist = (start + end) / 2
    
    var count = 1
    var last = housePositions.first!

    for i in 1..<housePositions.count {
        if housePositions[i] - last >= minimumDist {
            count += 1
            if count >= c {
                break
            }
            last = housePositions[i]
        }
    }
    
    if count >= c{
        answer = minimumDist
        start = minimumDist + 1
        continue
    }
    end = minimumDist - 1
}

print(answer)