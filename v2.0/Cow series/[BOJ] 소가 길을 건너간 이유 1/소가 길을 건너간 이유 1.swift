import Foundation

if let firstLine = readLine(),
   let N = Int(firstLine) {

    var position = Array(repeating: -1, count: 11)
    var count = 0

    for _ in 0..<N {
        if let line = readLine() {
            let parts = line.split(separator: " ")
            let cowNum = Int(parts[0])!
            let loc = Int(parts[1])!

            if position[cowNum] != -1 && position[cowNum] != loc {
                count += 1
            }
            position[cowNum] = loc
        }
    }

    print(count)
}