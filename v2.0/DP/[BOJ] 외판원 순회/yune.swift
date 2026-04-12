let n = Int(readLine()!)!
let cost = (0..<n).map { _ in readLine()!.split { $0 == " " }.map { Int(String($0))! } }
let allVisited = (1 << n) - 1

var dp = [[Int]](repeating: [Int](repeating: -1, count: 1 << n), count: n)

func tsp(_ now: Int, _ visited: Int) -> Int {
    if visited == allVisited {
        // 모든 도시를 방문했다면, now에서 1번째 도시로 돌아가는 비용을 반환
        // 즉, 3개의 도시가 있고, now = 3, visited = 111이라면, 이제 모든 도시를 방문한것이므로
        // 3에서 0으로의 비용을 반환
        return cost[now][0] == 0 ? Int.max : cost[now][0]
    }
    if dp[now][visited] != -1 {
        // -1이 아니라면 방문한 것이고, 방문했다면 최소비용(dp)을 계산했을 것 이므로 최소비용 반환
        return dp[now][visited]
    }

    var minCost = Int.max

    for next in 0..<n {
        // next 도시를 방문하지 않았고, 이동 경로가 존재한다면
        guard (visited & (1 << next)) == 0, cost[now][next] != 0 else { continue }
        let newCost = tsp(next, visited | (1 << next))
        if newCost != Int.max {
            minCost = min(minCost, cost[now][next] + newCost)
        }
    }
    // 즉 점화식은
    // dp[현재 도시][방문한 도시] = 현재 도시에서, 방문한 도시를 제외하고 나머지 도시를 순회 후 0으로 돌아오는 최소 비용
    dp[now][visited] = minCost
    return minCost
}

let answer = tsp(0, 1 << 0)
print(answer)