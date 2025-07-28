let nk = readLine()!.split{ $0 == " " }.map{ Int($0)! }
let n = nk[0]
let k = nk[1]
var w = [Int](repeating: 0, count: n+1)
var v = [Int](repeating: 0, count: n+1)
for i in 1...n{
    let wv = readLine()!.split{ $0 == " " }.map{ Int($0)! }
    w[i] = wv[0]
    v[i] = wv[1]
}
var dp = [[Int]](repeating: [Int](repeating: 0, count: k+1), count: n+1)

//dp[i][j] = i번째 물건까지 살폈을 때 크기가 j인 배낭의 최대 가치

for i in 1...n{
    for j in 1...k{
        if j - w[i] >= 0{ //i번째 물건을 배낭에 넣을 지 말지 결정
            dp[i][j] = max( dp[i-1][j], dp[i-1][j - w[i]] + v[i] )
        }else{
            dp[i][j] = dp[i-1][j]
        }
    }
}
print(dp[n][k])