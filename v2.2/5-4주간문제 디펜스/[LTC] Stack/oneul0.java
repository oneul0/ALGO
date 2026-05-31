//Stack
//Q1. Build an Array With Stack Operations
//target으로 만들기 위한 연산 순서 찾기
//처음에는 dfs로 완전탐색 하려다가 싸함을 느낌
//선형 탐색하면서 원하는 숫자라면 push,아니라면 pop으로 바로 빼면 됨
class Solution {
    public List<String> buildArray(int[] target, int n) {
        List<String> result = new ArrayList<>();
        int targetIndex = 0;
        for (int stream = 1; stream <= n; stream++) {
            result.add("Push");
            if (stream == target[targetIndex]) {
                targetIndex++;
                if (targetIndex == target.length) {
                    break;
                }
            } else {
                result.add("Pop");
            }
        }

        return result;
    }
}

//Q2. Evaluate Reverse Polish Notation
//연산자가 나오면 계산하고 그 결과를 넣고
//아니라면 숫자를 스택에 넣어서 계산
class Solution {
    public int evalRPN(String[] tokens) {
        Deque<String> nums = new ArrayDeque<>();
        Set<String> isOps = new HashSet<>();
        isOps.add("+");
        isOps.add("-");
        isOps.add("*");
        isOps.add("/");
        for(int i = 0; i<tokens.length; i++){
            if(isOps.contains(tokens[i])){
                String b = nums.pop();
                String a = nums.pop();
                String val = calc(a, b, tokens[i]);
                nums.push(val);
            }
            else{
                nums.push(tokens[i]);
            }
        }
        return Integer.parseInt(nums.pop());
    }

    public String calc(String a, String b, String op){
        int aVal = Integer.parseInt(a);
        int bVal = Integer.parseInt(b);
        
        switch(op){
            case "+":
                return String.valueOf(aVal+bVal);
            case "-":
                return String.valueOf(aVal-bVal);
            case "*":
                return String.valueOf(aVal*bVal);
            case "/":
                return String.valueOf(aVal/bVal);
            default:
                return "";
        }
    }
}

//Q3. Exclusive Time of Functions
//각 task별 start, end로 실행시간 체크
class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] answer = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        int prevTime = 0;

        for (String log : logs) {
            String[] parts = log.split(":");

            int id = Integer.parseInt(parts[0]);
            String status = parts[1];
            int time = Integer.parseInt(parts[2]);

            if (status.equals("start")) {
                if (!stack.isEmpty()) {
                    int cur = stack.peek();
                    answer[cur] += time-prevTime;
                }

                stack.push(id);
                prevTime = time;
            } else {
                int cur = stack.pop();
                answer[cur] += time-prevTime+1;

                prevTime = time+1;
            }
        }

        return answer;
    }
}