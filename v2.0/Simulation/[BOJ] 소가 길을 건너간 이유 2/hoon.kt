fun main () {
    val br = System.`in`.bufferedReader()
    val str = br.readLine()
    val stack = IntArray(53)
    var top = 0
    var answer = 0
    val visited = BooleanArray(27)
    for (c in str) {
        val cow = c - 'A'
        if (!visited[cow]) {
            visited[cow] = true
            stack[top++] = cow
        } else {
            var idx = top - 1
            while (stack[idx] != cow) idx--
            answer += (top - idx - 1)

            for (i in idx until top - 1) {
                stack[i] = stack[i + 1]
            }
            top--
        }
    }
    println(answer)
}