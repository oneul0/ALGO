import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int N, L, F;
    static Map<String, Map<String, Long>> acountBook = new HashMap<>(); //name, <product_name, borrowed_time>

    static int[] monthDays = {
        0,
        31, 28, 31, 30, 31, 30,
        31, 31, 30, 31, 30, 31
    };

    //대여기간
    public static int parseDayHourToMin(String period){
        int result = 0;
        String[] parts = period.split("[/:]");
        result += (Integer.parseInt(parts[0])*24*60);
        result += (Integer.parseInt(parts[1])*60);
        result += Integer.parseInt(parts[2]);
        return result;
    }

    //대여기간
    public static void parseNLF(String line){
        String[] tmp = line.split(" ");
        N = Integer.parseInt(tmp[0]);
        L = parseDayHourToMin(tmp[1]);
        F = Integer.parseInt(tmp[2]);
    }

    //빌린 시각(연도는 항상 2021년도이므로 할 필요 없을지도)
    public static int dateToMin(String date) {
        String[] tmp = date.split("-");
        int month = Integer.parseInt(tmp[1]);
        int day = Integer.parseInt(tmp[2]);

        int totalDays = 0;
        for(int i = 1; i < month; i++) {
            totalDays += monthDays[i];
        }
        totalDays += (day - 1);

        return totalDays * 24 * 60;
    }

    public static int timeToMin(String time){
        int result = 0;
        String[] tmp = time.split(":");
        result += (Integer.parseInt(tmp[0])*60);
        result += Integer.parseInt(tmp[1]);
        return result;
    }

    public static boolean hasBorrowed(String name, String product){
        //반납일 때(이미 빌렸으면)
        return acountBook.containsKey(name) && acountBook.get(name).containsKey(product);
    }

    public static long calcTimeGap(String name, String product, long returnTime){
        long borrowed = acountBook.get(name).get(product);
        return returnTime - borrowed - L;
    }

    public static void main(String[] args) throws IOException {
        parseNLF(br.readLine());
        Map<String, Long> fineBook = new HashMap<>();
        for(int i=0; i<N; i++){
            String[] parts = br.readLine().split(" ");
            String name = parts[3];
            String product = parts[2];
            long curTime = dateToMin(parts[0]) + timeToMin(parts[1]);
            if(hasBorrowed(name, product)){
                //반납 절차
                long timeGap = calcTimeGap(name, product, curTime);
                if(timeGap > 0){
                    //벌금 계산
                    long curFine = timeGap * F;
                    //사람 추가
                    fineBook.put(name, fineBook.getOrDefault(name, 0L)+curFine);
                }
                acountBook.get(name).remove(product);
            }
            else{
                acountBook.putIfAbsent(name, new HashMap<>());
                acountBook.get(name).put(product, curTime);
            }
        }

        if(fineBook.isEmpty()){
            System.out.println(-1);
        }
        else{
            fineBook.keySet()
                .stream()
                .sorted()
                .forEach(key -> {
                    System.out.println(key + " " + fineBook.get(key));
                });
        }
    }
}
