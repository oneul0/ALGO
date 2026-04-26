class Solution {
    func canFinish(_ numCourses: Int, _ prerequisites: [[Int]]) -> Bool {
        var indegree = [Int](repeating: 0, count: numCourses)
        var nextCourses = [[Int]](repeating: [Int](), count: numCourses)
        for prerequisite in prerequisites {
            let a = prerequisite[1],
                b = prerequisite[0] // preceding
            indegree[a] += 1
            nextCourses[b].append(a)
        }
        var numUnfinishedCourse = numCourses
        var queue = [Int]()
        var head = 0

        for i in 0..<numCourses {
            guard indegree[i] == 0 else { continue }
            queue.append(i)
        }
        
        while queue.count > head {
            let courseIndex = queue[head]
            numUnfinishedCourse -= 1
            for nextCourseIndex in nextCourses[courseIndex] {
                indegree[nextCourseIndex] -= 1
                if indegree[nextCourseIndex] == 0 {
                    queue.append(nextCourseIndex)
                }
            }

            head += 1
        }

        return numUnfinishedCourse == 0 ? true : false
    }
}
