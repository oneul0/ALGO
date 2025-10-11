import java.util.*;
class Solution {
    Set<Integer> hand = new HashSet<>();
    Set<Integer> passedCards = new HashSet<>();
    int N, N3, coins;

    public int solution(int coin, int[] cards) {
        N = cards.length;
        N3 = N/3;
        this.coins = coin;

        for(int i = 0; i<N3; i++){
            hand.add(cards[i]);
        }

        int rounds = 1;
        for(int idx = N3; idx<N; idx+=2){
            passedCards.add(cards[idx]);
            passedCards.add(cards[idx+1]);

            if(makeN1() || getCardsAndMakeN1()){
                rounds++;
            }
            else {
                break;
            }
        }
        return rounds;
    }

    // 손에서 만들 수 있는지
    public boolean makeN1(){
        Iterator<Integer> it = hand.iterator();
        while(it.hasNext()){
            int c = it.next();
            int target = N+1-c;
            if(hand.contains(target) && target != c){
                it.remove(); // 현재 원소 제거
                hand.remove(target); // 짝 제거
                return true;
            }
        }
        return false;
    }

    // 지나친 카드와 내 손에 있는 카드 혹은 지나친 카드들 중에서 가능한지
    public boolean getCardsAndMakeN1(){
        if(passedCards.isEmpty()) return false;

        // 코인 1개 소모, (hand + passed)
        if(coins >= 1){
            Iterator<Integer> it = passedCards.iterator();
            while(it.hasNext()){
                int cur = it.next();
                int target = N+1-cur;
                if(hand.contains(target)){
                    hand.remove(target);
                    it.remove(); // cur 제거
                    coins--;
                    return true;
                }
            }
        }

        // 코인 2개 소모, (passed + passed)
        if(coins >= 2){
            Iterator<Integer> it1 = passedCards.iterator();
            while(it1.hasNext()){
                int cur = it1.next();
                int target = N+1-cur;
                if(passedCards.contains(target) && target != cur){
                    it1.remove(); // cur 제거
                    passedCards.remove(target); // 짝 제거
                    coins -= 2;
                    return true;
                }
            }
        }

        return false;
    }
}
