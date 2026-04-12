import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let N = input[0]
let L = input[1]

var sticks = [(l: Int, d: Int)]()

for _ in 0..<N {
    let row = readLine()!.split(separator: " ").map { Int($0)! }
    sticks.append((row[0], row[1]))
}

var time = 0

for i in 0..<(N - 1) {
    let (l1, d1) = sticks[i]
    let (l2, d2) = sticks[i + 1]

    while true {
        let (s1, e1) = getPosition(time, l1, d1)
        let (s2, e2) = getPosition(time, l2, d2)

        if max(s1, s2) <= min(e1, e2) {
            break
        }

        time += 1
    }
}

print(time)

func getPosition(_ t: Int, _ l: Int, _ d: Int) -> (Int, Int) {
    if l == L {
        return (0, L)
    }

    let period = 2 * (L - l)
    var pos = t % period

    if pos > (L - l) {
        pos = period - pos
    }

    if d == 1 {
        pos = (L - l) - pos
    }

    return (pos, pos + l)
}