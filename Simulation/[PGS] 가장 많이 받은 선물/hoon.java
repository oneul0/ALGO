import java.util.*;
class Solution {
    public int solution(String[] friends, String[] gifts) {
        int N = friends.length;
        Map<String, Integer> idxMap = new HashMap<>();
        for(int i = 0; i<N; i++){
            idxMap.put(friends[i], i);
        }
        // 선물 관계 정리
        int[][] giftRelations = new int[N][N];
        int[] gifted = new int[N]; //준 선물
        int[] getGifts = new int[N]; // 받은 선물
        int[] giftScores = new int[N]; // 선물 지수
        for(String gift : gifts){
            String[] rel = gift.split(" ");
            giftRelations[idxMap.get(rel[0])][idxMap.get(rel[1])]++;
            gifted[idxMap.get(rel[0])]++;
            getGifts[idxMap.get(rel[1])]++;
        }

        // 선물 지수 도출
        for(int i = 0; i<N; i++){
            giftScores[i] = gifted[i] - getGifts[i];
        }

        int[] giftCount = new int[N];
        // 선물 받는 수 정리하면서 가장 많이 받은 선물 저장
        for(int i = 0; i<N; i++){
            for(int j = 0; j<N; j++){
                if(i == j) continue;
                // 두 사람 간에 선물 교환이 있었으면 더 많이 준 사람한테 선물을 하나 준다.
                if((giftRelations[i][j] != 0 || giftRelations[j][i] != 0)
                    && giftRelations[i][j] != giftRelations[j][i]){
                    giftCount[i] += (giftRelations[i][j] > giftRelations[j][i] ? 1 : 0);
                }
                // 두 사람이 선물 교환이 없었거나 주고 받은 수가 같다면 선물 지수로 판단(더 작은 사람이 더 큰 사람에게 하나 준다.)
                else {
                    giftCount[i] += (giftScores[i] > giftScores[j] ? 1 : 0);
                }
            }
        }

        return Arrays.stream(giftCount)
            .max().getAsInt();
    }
}



// 선물을 가장 많이 받은 사람이 받는 선물의 수