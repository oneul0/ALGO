import Foundation

let nm = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nm[0],
    m = nm[1]

var parents = [0] + readLine()!.split { $0 == " " }.map { Int(String($0))! }

var compliments = [Int](repeating: 0, count: n+1)

for _ in 0..<m {
    let iw = readLine()!.split { $0 == " " }.map { Int(String($0))! },
        i = iw[0],
        w = iw[1]
    compliments[i] += w
}

var tree = [[Int]](repeating: [Int](), count: n+1)

for i in 2...n {
    tree[parents[i]].append(i)
}

dfs(1)

let answer = compliments[1...].map { String($0) }.joined(separator: " ")

print(answer)

func dfs(_ currentNode: Int) {
    for childNode in tree[currentNode] {
        compliments[childNode] += compliments[currentNode]
        dfs(childNode)
    }
}