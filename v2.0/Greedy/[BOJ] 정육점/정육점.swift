import Foundation

let first = readLine()!.split(separator: " ").map { Int($0)! }
let N = Int(first[0])
let M = first[1]

var meats = [(price: Int, weight: Int)]()

for _ in 0..<N {
    let line = readLine()!.split(separator: " ").map { Int($0)! }
    let weight = line[0]
    let price = line[1]
    meats.append((price, weight))
}

meats.sort {
    if $0.price == $1.price {
        return $0.weight > $1.weight
    }
    return $0.price < $1.price
}

var freeWeight = 0
var answer = Int.max

var i = 0
while i < N {
    let currentPrice = meats[i].price
    var samePrices = [Int]()

    // 같은 가격 그룹 수집
    while i < N && meats[i].price == currentPrice {
        samePrices.append(meats[i].weight)
        i += 1
    }

    samePrices.sort(by: >)

    var paidWeight = 0
    var paidCount = 0

    // 같은 가격에서 k개 샀을 때를 전부 확인
    for w in samePrices {
        paidWeight += w
        paidCount += 1

        if freeWeight + paidWeight >= M {
            let cost = paidCount * currentPrice
            answer = min(answer, cost)
            break
        }
    }

    for w in samePrices {
        freeWeight += w
    }
}

if answer == Int64.max {
    print(-1)
} else {
    print(answer)
}