import java.io.*;
import java.util.*;

public class Main {
    static class Transaction {
        long amount, deposit;
        Transaction(long amount, long deposit){
            this.amount = amount;
            this.deposit = deposit;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        Transaction[] transactions = new Transaction[N];
        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            transactions[i] = new Transaction(
                Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken()));
        }
        List<Long> transactionSet = new ArrayList<>();
        long maxBalance = 0;
        //트랜잭션 추리기
        long y = 0;
        for(int i = 0; i < N; i++) {
            long m = transactions[i].amount;
            long t = transactions[i].deposit;

            if (y < -m) {
                transactionSet.add(t - m - y);
                maxBalance = Math.max(maxBalance, t);
            }
            else{
                if(y + m != t) {
                    System.out.println(-1);
                    return;
                }
            }
            y = t;
        }

        //트랜잭션의 GCD구하기
        long x = solve(transactionSet);
        if (transactionSet.isEmpty()) {
            // 충전이 한 번도 없었을 때
            System.out.println(1);
        } else {
            // 충전이 있었을 때
            if (x <= maxBalance) {
                System.out.println(-1);
            } else {
                System.out.println(x);
            }
        }
    }

    public static long getGcd(long a, long b){
        while (b>0){
            long tmp = (a%b);
            a = b;
            b = tmp;
        }
        return a;
    }

    public static long solve(List<Long> charged){
        long x = 0;
        for(long d : charged){
            if(x == 0){
                x = d;
            }
            else{
                x = getGcd(x, d);
            }
        }
        return x;
    }
}
//최소 출금 단위 x 가 존재할 때, 충전 횟수 n,
//입출금 금액 m, 카카오페이 잔고t 가 주어진다.
//이때, 이번 트랜잭션 이전의 잔고 y라고 할 때

// 1500 1500
//-17000 4500

// 4500 = (1500 + x*n) + -17000
// t = (y + x*n) + m
// t-m = y + x*n
//(t-m-y)/n = x 를 충족해야함
//(t-m-y)/x = n
//따라서 이전의 x1*n1 과 xi-1*ni-1간의 공약수 중 아무거나 구하기

//트랜잭션 추리기 : y+m >= 0인 것들로만 충전이 필요한 것들로만
//구한 트랜잭션 세트(t-m-y)들의 GCD(최대공약수) 구하기