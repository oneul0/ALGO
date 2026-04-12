import Foundation

while true {
    let first = readLine()!.split(separator: " ")
    let n = Int(first[0])!
    let mStr = first[1]

    if n == 0 && mStr == "0.00" {
        break
    }

    let m = Int(Double(mStr)! * 100 + 0.5)

    var candies = [(cal: Int, price: Int)]()

    for _ in 0..<n {
        let parts = readLine()!.split(separator: " ")
        let c = Int(parts[0])!
        let p = Int(Double(parts[1])! * 100 + 0.5)
        candies.append((c, p))
    }

    var dp = Array(repeating: 0, count: m + 1)

    for candy in candies {
        let price = candy.price
        let cal = candy.cal

        if price > m { continue }

        for money in price...m {
            let candidate = dp[money - price] + cal
            if candidate > dp[money] {
                dp[money] = candidate
            }
        }
    }

    print(dp[m])
}