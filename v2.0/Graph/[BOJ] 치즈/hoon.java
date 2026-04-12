import java.util.*

data class Coord(val x: Int, val y: Int)

var N = 0
var M = 0
val arr = Array(100){ IntArray(100) }
val dx = intArrayOf(-1,1,0,0)
val dy = intArrayOf(0,0,-1,1)

fun isValid(x: Int, y: Int): Boolean{
	return (x in 0 until N && y in 0 until M)
}

fun bfs(): Int{
	var time = 0

	while (true) {
		val isVisited = Array(N){ BooleanArray(M) }
		val q = ArrayDeque<Coord>()
		q.add(Coord(0,0))
		isVisited[0][0] = true

		val melt = Array(N){ IntArray(M) }

		// 외부 공기 BFS
		while (q.isNotEmpty()) {
			val cur = q.removeFirst()
			for (i in 0..3) {
				val nx = cur.x + dx[i]
				val ny = cur.y + dy[i]
				if (!isValid(nx, ny) || isVisited[nx][ny]) continue

				if (arr[nx][ny] == 0) {
					isVisited[nx][ny] = true
					q.add(Coord(nx, ny))
				} else {
					melt[nx][ny]++
				}
			}
		}

		var melted = false
		for (i in 0 until N) {
			for (j in 0 until M) {
				if (melt[i][j] >= 2) {
					arr[i][j] = 0
					melted = true
				}
			}
		}

		if (!melted) break
		time++
	}

	return time
}

fun main() {
	val br = System.`in`.bufferedReader()
	var st = StringTokenizer(br.readLine())
	N = st.nextToken().toInt()
	M = st.nextToken().toInt()

	for (i in 0 until N) {
		st = StringTokenizer(br.readLine())
		for (j in 0 until M) {
			arr[i][j] = st.nextToken().toInt()
		}
	}

	print(bfs())
}
