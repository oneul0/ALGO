import java.util.PriorityQueue
import java.util.StringTokenizer

private const val INF = Int.MAX_VALUE

var N = 0
var T = 0

val dx = intArrayOf(-1, 1, 0, 0)
val dy = intArrayOf(0, 0, -1, 1)

lateinit var arr: Array<IntArray>

data class Point(
    val x: Int,
    val y: Int,
    val time: Int,
    val mod: Int
)

fun dijkstra(): Int {
    val pq = PriorityQueue<Point>(compareBy { it.time })

    val times = Array(N) { Array(N) { IntArray(3) { INF } } }

    pq.offer(Point(0, 0, 0, 0))
    times[0][0][0] = 0

    while (pq.isNotEmpty()) {
        val cur = pq.poll()
        if (cur.time > times[cur.x][cur.y][cur.mod]) continue
        if (cur.x == N - 1 && cur.y == N - 1) {
            return cur.time
        }

        for (i in 0 until 4) {
            val nx = cur.x + dx[i]
            val ny = cur.y + dy[i]

            if (nx !in 0 until N || ny !in 0 until N) continue

            val nextMod = (cur.mod + 1) % 3
            var newTime = cur.time + T

            if (nextMod == 0) {
                newTime += arr[nx][ny]
            }

            if (times[nx][ny][nextMod] > newTime) {
                times[nx][ny][nextMod] = newTime
                pq.offer(Point(nx, ny, newTime, nextMod))
            }
        }
    }

    return INF
}

fun main() {
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())

    N = st.nextToken().toInt()
    T = st.nextToken().toInt()

    arr = Array(N) { IntArray(N) }
    for (i in 0 until N) {
        st = StringTokenizer(br.readLine())
        for (j in 0 until N) {
            arr[i][j] = st.nextToken().toInt()
        }
    }

    //0,0 -> N-1, N-1
    println(dijkstra())
}
