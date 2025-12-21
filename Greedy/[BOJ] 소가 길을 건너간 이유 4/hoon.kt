import java.util.PriorityQueue
import java.util.StringTokenizer

data class Cow(val s: Int, val e: Int)

fun main() {
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    val C = st.nextToken().toInt()
    val N = st.nextToken().toInt()

    val chickens = IntArray(C){ 0 }
    for (i in 0 until C){
        chickens[i] = br.readLine().toInt()
    }
    val cows = ArrayList<Cow>(N)
    repeat(N) {
        st = StringTokenizer(br.readLine())
        cows.add(Cow(st.nextToken().toInt(), st.nextToken().toInt()))
    }

    chickens.sort()
    cows.sortBy { it.s }

    val pq = PriorityQueue<Cow>(compareBy { it.e })
    var cowIdx = 0
    var count = 0

    for (time in chickens) {
        while (cowIdx < N && cows[cowIdx].s <= time) {
            pq.offer(cows[cowIdx++])
        }

        while (pq.isNotEmpty() && pq.peek().e < time) {
            pq.poll()
        }

        if (pq.isNotEmpty()) {
            pq.poll()
            count++
        }
    }
    print(count)
}

//끝나는 시간이 빠른 것부터(소)
//도착 시간이 빠른 순서부터(닭)