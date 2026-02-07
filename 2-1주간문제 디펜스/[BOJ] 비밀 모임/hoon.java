import java.io.*;
import java.util.*;

public class Main {
    static final int INF = 1_000_000_000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //출발하는 장소 정해짐
        //도착 장소도 정해짐
        //어느 두 노드 사이의 간선의 가중치의 합 중 가장 작은 값은 동일함
        //방의 개수 <=100, 간선의 개수 <=4950, 가중치 <=1000

        int tc = Integer.parseInt(br.readLine());
        while(tc-- > 0){
            StringTokenizer st = new StringTokenizer(br.readLine());

            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            long[][] dists = new long[n+1][n+1];
            for(int i = 0; i<=n; i++){
                Arrays.fill(dists[i],INF);
                dists[i][i] = 0;
            }

            for(int i = 0; i<m; i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                dists[a][b] = c;
                dists[b][a] = c;
            }
            int k = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());

            floyd(dists, n);

            //방을 기준으로 갱신
            //이 방에서 dists[room][f]의 합이 가장 작은 방 구하기
            //비교시에는 미만, 초과로 계산하여 방 번호가 작은 번호가 정답이 되게
            List<Integer> friends = new ArrayList<>();
            for(int i = 0; i<k; i++){
                int f = Integer.parseInt(st.nextToken());
                friends.add(f);
            }
            int answer = 0;
            long totalDist = INF;
            for(int room = 1; room<=n; room++){
                long total = 0;
                for(int f : friends){
                    total+=dists[room][f];
                }
                if(totalDist > total){
                    totalDist = total;
                    answer = room;
                }
            }
            System.out.println(answer);
        }
    }

    //call by reference
    public static void floyd(long[][] dists, int n){
        for(int mid = 1; mid<=n; mid++){
            for(int s = 1; s<=n; s++){
                if(dists[s][mid] == INF) continue;
                for(int e = 1; e<=n; e++){
                    if(dists[mid][e] == INF) continue;
                    if(dists[s][e] > dists[s][mid] + dists[mid][e]){
                        dists[s][e] = dists[s][mid] + dists[mid][e];
                    }
                }
            }
        }
    }
}
