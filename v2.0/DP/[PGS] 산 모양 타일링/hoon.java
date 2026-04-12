class Solution {
    int MOD = 10007;

    public int solution(int n, int[] tops) {
        //이전 경우에서 역삼각형을 기준으로 왼쪽 삼각형을 사용했는지
        //사용하지 않았는지 경우의 수를 구분
        int[] a = new int[n+1], b = new int[n+1];
        b[0] = 1;
        for(int i = 1; i<=n; i++){
            if(tops[i-1] == 1){
                a[i] = (a[i-1] + b[i-1])%MOD;
                b[i] = (a[i-1]*2 + b[i-1]*3) % MOD;
            }
            else{
                a[i] = (a[i-1] + b[i-1])%MOD;
                b[i] = (a[i-1] + b[i-1]*2) % MOD;
            }
        }
        return (a[n]+b[n])%MOD;
    }
}
//오른쪽 아래 삼각형을 사용했을 경우
//이전 경우에 
//아닌 경우