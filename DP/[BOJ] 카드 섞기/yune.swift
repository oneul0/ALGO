import Foundation

// 3명의 플레이어는 n개의 카드를 이용한다.
// 지민이는 카드를 섞어 플레이어에게 나눠준다.
// n % 3 번 플레이어에게 n번째 카드를 나눠준다
// s: 카드를섞는 방법

// 몇번째 순서에 가는지는 상관 없고 그 사람한테만 가면 댐

let n = Int(readLine()!)!
var cards = Array(0..<n).map { String($0) }
let p = readLine()!.split(separator: " ").map { String($0) }
let s = readLine()!.split(separator: " ").map { Int(String($0))! }
var visited = Set<String>()
var goal = [String: String]()
for i in 0..<n {
    goal[String(i)] = p[i]
}
var result = 0
while !check() {
    let card = cards.joined()
    if visited.contains(card) {
        result = -1
        break
    }
    visited.insert(cards.joined())
    shuffle()
    result += 1
}

print(result)

func check() -> Bool {
    for i in 0..<n {
        if goal[cards[i]] != String(i % 3) { return false }
    }
    return true
}

func shuffle() {
    var tmp = [String](repeating: "", count: n)
    for i in 0..<n {
        tmp[s[i]] = cards[i]
    }
    cards = tmp
}