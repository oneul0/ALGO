import Foundation

let T = Int(readLine()!)!

func buildMapping(enc: [Character], dec: [Character]) -> [Character: Character]? {
    var mapE2D: [Character: Character] = [:]
    var mapD2E: [Character: Character] = [:]

    for i in 0..<enc.count {
        let e = enc[i]
        let d = dec[i]

        if let prev = mapE2D[e], prev != d {
            return nil
        }
        if let prev = mapD2E[d], prev != e {
            return nil
        }
        mapE2D[e] = d
        mapD2E[d] = e
    }
    return mapE2D
}

for _ in 0..<T {
    let N = Int(readLine()!)!
    var encList: [[Character]] = []

    for _ in 0..<N {
        encList.append(Array(readLine()!))
    }

    let dec = Array(readLine()!)
    let X = Array(readLine()!)

    var validMappings: [[Character: Character]] = []

    for e in encList {
        if e.count != dec.count { continue }
        if let m = buildMapping(enc: e, dec: dec) {
            validMappings.append(m)
        }
    }

    if validMappings.isEmpty {
        print("IMPOSSIBLE")
        continue
    }

    let L = X.count
    var pos = Array(repeating: Set<Character>(), count: L)

    let allLetters: [Character] = Array("abcdefghijklmnopqrstuvwxyz")

    for i in 0..<L {
        let c = X[i]
        for m in validMappings {
            if let d = m[c] {
                pos[i].insert(d)
            } else {
                pos[i].formUnion(allLetters.filter { !m.values.contains($0) })
            }
        }
    }

    var answer = ""
    for i in 0..<L {
        if pos[i].count == 1 {
            answer.append(pos[i].first!)
        } else {
            answer.append("?")
        }
    }

    print(answer)
}