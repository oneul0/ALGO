import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import java.util.ArrayDeque
import java.util.HashMap

data class Point(val x: Int, val y: Int)

lateinit var blocked: HashMap<Point, MutableSet<Point>>
lateinit var cows: List<Point>

var N = 0
var K = 0
var R = 0

val dx = intArrayOf(-1, 1, 0, 0)
val dy = intArrayOf(0, 0, -1, 1)

fun bfs(start: Point): Array<BooleanArray> {
    val visited = Array(N) { BooleanArray(N) }
    val q: ArrayDeque<Point> = ArrayDeque()

    visited[start.x][start.y] = true
    q.add(start)

    while (q.isNotEmpty()) {
        val cur = q.removeFirst()

        for (d in 0 until 4) {
            val nx = cur.x + dx[d]
            val ny = cur.y + dy[d]
            val next = Point(nx, ny)

            if (nx !in 0 until N || ny !in 0 until N) continue
            if (visited[nx][ny]) continue

            // 길로 막힌 경우 이동 불가
            if (blocked[cur]?.contains(next) == true) continue

            visited[nx][ny] = true
            q.add(next)
        }
    }
    return visited
}

fun main() {
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())

    N = st.nextToken().toInt()
    K = st.nextToken().toInt()
    R = st.nextToken().toInt()

    blocked = HashMap()

    // 길 입력(여기로는 못 지나감)
    repeat(R) {
        st = StringTokenizer(br.readLine())
        val x1 = st.nextToken().toInt() - 1
        val y1 = st.nextToken().toInt() - 1
        val x2 = st.nextToken().toInt() - 1
        val y2 = st.nextToken().toInt() - 1

        val p1 = Point(x1, y1)
        val p2 = Point(x2, y2)

        blocked.getOrPut(p1) { mutableSetOf() }.add(p2)
        blocked.getOrPut(p2) { mutableSetOf() }.add(p1)
    }

    // 소 위치 입력
    val cowList = mutableListOf<Point>()
    repeat(K) {
        st = StringTokenizer(br.readLine())
        val x = st.nextToken().toInt() - 1
        val y = st.nextToken().toInt() - 1
        cowList.add(Point(x, y))
    }
    cows = cowList

    var answer = 0

    for (i in 0 until K) {
        val visited = bfs(cows[i])

        for (j in i + 1 until K) {
            val other = cows[j]
            if (!visited[other.x][other.y]) {
                answer++
            }
        }
    }

    println(answer)
}
