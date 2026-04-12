import kotlin.math.abs

fun main(){
    val br = System.`in`.bufferedReader()
    val N = br.readLine().toInt()
    val dp = Array(N+1){IntArray(N+1)}
    val left = IntArray(N+1)
    val right = IntArray(N+1)

    for(i in 1..N){
        left[i] = br.readLine().toInt()
    }
    for(i in 1..N){
        right[i] = br.readLine().toInt()
    }

    for(i in 1..N){
        for(j in 1..N){
            dp[i][j] = maxOf(dp[i-1][j], dp[i][j-1])
            if(abs(left[i] - right[j]) <= 4){
                dp[i][j] = maxOf(dp[i][j], dp[i-1][j-1]+1)
            }
        }
    }
    println(dp[N][N])

}