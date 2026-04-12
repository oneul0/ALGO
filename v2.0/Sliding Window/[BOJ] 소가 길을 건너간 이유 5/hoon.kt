import java.util.StringTokenizer

fun main() {
    val br = System.`in`.bufferedReader()
    val st = StringTokenizer(br.readLine())

    val N = st.nextToken().toInt()
    val K = st.nextToken().toInt()
    val B = st.nextToken().toInt()

    val isBroken = BooleanArray(N + 1)

    repeat(B) {
        val idx = br.readLine().toInt()
        isBroken[idx] = true
    }

    var brokenCount = 0
    for (i in 1..K) {
        if (isBroken[i]) brokenCount++
    }

    var answer = brokenCount

    for (i in K + 1..N) {
        if (isBroken[i - K]) brokenCount--
        if (isBroken[i]) brokenCount++
        answer = minOf(answer, brokenCount)
    }

    println(answer)
}
