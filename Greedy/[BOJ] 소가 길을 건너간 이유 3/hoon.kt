import java.util.PriorityQueue
import java.util.StringTokenizer

data class Cow(val arrival: Int, val burst: Int)

fun main() {
    val br = System.`in`.bufferedReader()
    val n = br.readLine().toInt()

    val pq = PriorityQueue<Cow>(compareBy { it.burst })

    val cows = Array(n) {
        val st = StringTokenizer(br.readLine())
        Cow(st.nextToken().toInt(), st.nextToken().toInt())
    }

    cows.sortBy { it.arrival }

    var time = 0
    var idx = 0

    while (idx < n || pq.isNotEmpty()) {

        while (idx < n && cows[idx].arrival <= time) {
            pq.add(cows[idx])
            idx++
        }

        if (pq.isEmpty()) {
            time = cows[idx].arrival
            continue
        }

        val job = pq.poll()
        time += job.burst
    }

    println(time)
}
