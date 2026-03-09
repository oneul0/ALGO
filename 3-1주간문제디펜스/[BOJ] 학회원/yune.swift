import Foundation

while true {
    let n = Int(readLine()!)!
    if n == 0 { break }

    var groups = [String: [String]]()
    var firstGroup = ""

    for i in 0..<n {
        let line = readLine()!
        let parts = line.dropLast().split(separator: ":")
        let group = String(parts[0])
        let members = parts[1].split(separator: ",").map { String($0) }

        groups[group] = members

        if i == 0 {
            firstGroup = group
        }
    }

    var visitedGroups = Set<String>()
    var people = Set<String>()

    func dfs(_ group: String) {
        if visitedGroups.contains(group) { return }
        visitedGroups.insert(group)

        guard let members = groups[group] else { return }

        for m in members {
            if groups[m] != nil {
                dfs(m)
            } else {
                people.insert(m)
            }
        }
    }

    dfs(firstGroup)

    print(people.count)
}