import java.util.StringTokenizer
fun main() {
    val br = System.`in`.bufferedReader()
    val n = br.readLine().toInt()

    var st: StringTokenizer
    var answer = 0
    val dirs = IntArray(n+1)
    dirs.fill(-1)
    repeat(n){
        st = StringTokenizer(br.readLine())
        val cow = st.nextToken().toInt()
        val dir = st.nextToken().toInt()
        if(dirs[cow] == -1){
            dirs[cow] = dir
        }else{
            if(dirs[cow] != dir){
                dirs[cow] = dir
                answer++
            }
        }
    }
    println(answer)

}