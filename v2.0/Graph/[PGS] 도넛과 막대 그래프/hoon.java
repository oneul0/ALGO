import java.util.*;
class Solution {
    public int[] solution(int[][] edges) {
        Map<Integer, int[]> nodes = new HashMap<>();
        int[] answer = new int[4];

        for(int[] edge : edges){
            int from = edge[0];
            int to = edge[1];

            nodes.putIfAbsent(from, new int[]{0,0});
            nodes.putIfAbsent(to, new int[]{0,0});

            nodes.get(from)[0]++;
            nodes.get(to)[1]++;
        }

        for(int key : nodes.keySet()){
            int[] cnt = nodes.get(key);

            //out 2이상, in 0 -> 생성한 정점\
            if(cnt[0] >= 2 && cnt[1] == 0){
                answer[0] = key;
            }

            //out 0, int >= 1 -> 막대 그래프
            else if(cnt[0] == 0 && cnt[1] > 0){
                answer[2]++;
            }

            //in, out >= 2 -> 8
            else if(cnt[0] >= 2 && cnt[1] >= 2){
                answer[3]++;
            }

        }
        //총 그래프의 개수 = root노드의 진출차수의 수
        //8자 그래프에서 indegree == 2, outdegree == 2 인 노드는 무조건 1개
        //막대 그래프에서 indegree == 1, outdegree == 0 인 노드는 무조건 1개
        //도넛 그래프의 수 = root 노드의 진출 차수 - 막대 그래프의 수 - 8자 그래프의 수
        answer[1] = nodes.get(answer[0])[0] - answer[2] - answer[3];
        return answer;
    }
}