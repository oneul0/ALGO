import Foundation

let DP = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    D = DP[0],
    P = DP[1]

var pipeList = [Pipe]()
for _ in 0..<P {
    let LC = readLine()!.split { $0 == " " }.map { Int(String($0))! },
        L = LC[0],
        C = LC[1]
    pipeList.append(Pipe(length: L, capacity: C))
}
pipeList.sort { $0.capacity > $1.capacity }

var dp = [Bool](repeating: false, count: D+1)
dp[0] = true

for pipe in pipeList {
    let L = pipe.length
    let C = pipe.capacity

    if L <= D {
        for l in stride(from: D, through: L, by: -1) {
            if dp[l-L] {
                dp[l] = true
            }
        }
    }

    if dp[D] {
        print(C)
        break
    }
}

struct Pipe {
    let length: Int
    let capacity: Int
}