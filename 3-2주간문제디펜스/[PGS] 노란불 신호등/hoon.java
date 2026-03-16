class Solution {
    public int solution(int[][] signals) {
        int answer = 0;
        int n = signals.length;
        int maxTime = 1;
        for(int i = 0; i<n; i++){
            int c = signals[i][0]+signals[i][1]+signals[i][2];
            maxTime = lcm(maxTime, c);
        }

        for(int t = 1; t<=maxTime; t++){
            boolean isAllYellow = true;
            for(int i = 0; i< n; i++){
                int g = signals[i][0];
                int y = signals[i][1];
                int r = signals[i][2];
                int C = g+y+r;

                int remain = (t-1) %C;

                if(!(g <= remain && remain < g+y)){
                    isAllYellow = false;
                    break;
                }
            }
            if(isAllYellow){
                return t;
            }
        }


        return -1;
    }

    public int lcm(int a, int b){
        return a / gcd(a,b) * b;
    }
    public int gcd(int a, int b){
        while(b>0){
            int r = a%b;
            a = b;
            b = r;
        }
        return a;
    }
}
//노란불이 시작하는 시점을 정수로 관리?
//주기의 최소공배수를 구하는 문제일까
//(초록-노란-빨간) 주기 - 빨간?
//빨강을 시작으로 주기를 시작하면 끝지점이 노랑색이 됨
/*
[[2, 1, 2], [5, 1, 1]]
일때, 다시 노란색이 돌아오는 주기는
2+1 = 3
2+1+2+2+1 = 8
2+1+2+2+1+2+2+1 = 13
반복 시간이 t, 어떤 신호등의 주기가 g, y, r라고 했을 때, 다음 노란색이 돌아오는 주기 x는
x = ((g+y+r)*t)-r
이때, 주기 g+y+r이 C라고 했을 때
x % C = (-r)%C = (C-r) % C = (g+y) % C
즉, 
x % C = (g+y) % C 를 만족하는 경우를 찾으면 됨


*/