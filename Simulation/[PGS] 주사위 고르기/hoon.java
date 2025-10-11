import java.util.*;
class Solution {

    int N;
    public int[] solution(int[][] dice) {
        N = dice.length/2;
        List<Map<Integer, Integer>> pq = new ArrayList<>();
        for(int i = 0; i<6; i++){
            pq.get(i).add(new TreeMap<>(Collections.reverseOrder()));
        }

        for(int i = 0; i<dice.length; i++){
            for(int j = 0; j<6; j++){
                pq.get(i).putIfAbsent(dice[j], 0);
                Map<Integer, Integer> curDice = pq.get(i);
                curDice.put(dice[i][j], curDice.get(dice[i][j])+1);
            }
        }

        Collections.sort(pq);
        int[] answer = new int[N];
        for(int i = 0; i<N; i++){
            answer[i] = pq.get(i).idx;
        }
        return answer;
    }
}