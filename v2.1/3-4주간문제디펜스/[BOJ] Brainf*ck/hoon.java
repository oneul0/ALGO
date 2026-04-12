import java.util.Stack

fun main(){
    val br = System.`in`.bufferedReader();
    val T = br.readLine().toInt()
    var n = 0
    repeat(T){
        val sb = StringBuilder()
        while (true) {
            val line = br.readLine() ?: break
            if (line == "end") break

                // % 이전까지 자르고 그 중에서 명령어만 필터링해서 합치기
            val filteredLine = line.substringBefore('%')
                .filter { it in "><+-.[ ]" }
            sb.append(filteredLine)
        }
        val code = sb.toString()
        val pair = IntArray(code.length)

        if(!findPairs(code, pair)){
            println("PROGRAM #" + ++n + ":")
            println("COMPILE ERROR")
        }
        else{
            println("PROGRAM #" + (++n) + ":")
            println(execute(code, pair))
        }
    }
}

fun findPairs(code:String, pair: IntArray) : Boolean {
    val stack = ArrayDeque<Int>()
    for(i in 0 until code.length){
        if(code[i] == '['){
            stack.add(i)
        }
        else if(code[i] == ']'){
            if(stack.isEmpty()) return false;
            val openIdx = stack.removeLast()
            pair[openIdx] = i
            pair[i] = openIdx
        }
    }
    return stack.isEmpty()
}

fun execute(code: String, pair: IntArray): String{
    val memory = IntArray(32768)
    var ptr = 0
    var ip = 0
    val result = StringBuilder()

    while(ip < code.length){
        val cmd = code[ip]

        when (cmd) {
            '>' -> ptr = (ptr + 1) % 32768
            '<' -> ptr = (ptr + 32767) % 32768
            '+' -> memory[ptr] = (memory[ptr] + 1) % 256
            '-' -> memory[ptr] = (memory[ptr] + 255) % 256
            '.' -> result.append(memory[ptr].toChar())
            '[' -> if (memory[ptr] == 0) ip = pair[ip]
            ']' -> if (memory[ptr] != 0) ip = pair[ip]
        }
        ip++
    }
    return result.toString()
}