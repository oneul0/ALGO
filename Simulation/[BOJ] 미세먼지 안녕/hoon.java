import java.io.*;
import java.util.*;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int R, C, T;
	static int[][] room;
	static List<Pair> purifier = new ArrayList<>();
	static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
	static class Pair {
		int r, c;
		public Pair(int r, int c){
			this.r = r;
			this.c = c;
		}
	}
	public static void main(String[] args) throws IOException {
		init();

		for(int t=0; t<T; t++){
			//먼지 확산
			diffusion();
			//공청기
			purifying();
		}

		//남은 먼지 세기
		int ans = 0;
		for(int i = 0; i<R; i++){
			for(int j = 0; j<C; j++){
				if(room[i][j] != -1){
					ans += room[i][j];
				}
			}
		}
		System.out.println(ans);
	}

	public static void init() throws IOException {
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		room = new int[R][C];
		for(int i = 0; i<R; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<C; j++){
				room[i][j] = Integer.parseInt(st.nextToken());
				if(room[i][j] == -1) {
					purifier.add(new Pair(i, j));
				}
			}
		}
	}

	public static void diffusion() {
		int[][] temp = new int[R][C];

		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				if(room[i][j] > 0) {
					int amount = room[i][j] / 5;
					int count = 0;
					for(int d = 0; d < 4; d++) {
						int ni = i + dx[d];
						int nj = j + dy[d];
						if(ni >= 0 && ni < R && nj >= 0 && nj < C && room[ni][nj] != -1) {
							temp[ni][nj] += amount;
							count++;
						}
					}
					temp[i][j] -= amount * count;
				}
			}
		}

		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				room[i][j] += temp[i][j];
			}
		}
	}

	public static void purifying(){
		//위
		upside(purifier.get(0).r, purifier.get(0).c);
		//아래
		downside(purifier.get(1).r, purifier.get(1).c);
	}

	public static void upside(int sr, int sc){
		//1. r양수방향
		for(int r = sr - 1; r > 0; r--){
			room[r][0] = room[r-1][0];
		}
		//2. c음수 방향
		for(int c = 0; c < C - 1; c++){
			room[0][c] = room[0][c+1];
		}
		//3. r음수 방향
		for(int r = 0; r < sr; r++){
			room[r][C-1] = room[r+1][C-1];
		}
		//4.c양수 방향
		for(int c = C - 1; c > 1; c--){
			room[sr][c] = room[sr][c-1];
		}
		room[sr][1] = 0;
		room[sr][sc] = -1;
	}
	public static void downside(int sr, int sc){
		//1. r음수방향
		for(int r = sr + 1; r < R - 1; r++){
			room[r][0] = room[r+1][0];
		}
		//2. c음수방향
		for(int c = 0; c < C - 1; c++){
			room[R-1][c] = room[R-1][c+1];
		}
		//3. r양수방향
		for(int r = R - 1; r > sr; r--){
			room[r][C-1] = room[r-1][C-1];
		}
		//4. c양수방향
		for(int c = C - 1; c > 1; c--){
			room[sr][c] = room[sr][c-1];
		}
		room[sr][1] = 0;
		room[sr][sc] = -1;
	}

}
//확산 규칙
//인접한 네 방향으로
//공기청정기나 칸이 없는 곳으로는 확산하지 않음
//확산되는 양은 확산 origin의 미세먼지 양의 / 5
//확산 origin 에 남은 미세먼지 양은 원래 있던 양 - (원래 있던 양/5)*확산된 방향의 개수

//공기청정 규칙
//공기청정기 바람의 방향에 따라 바람이 순환함
//위쪽 공기청정기는 반시계, 아래쪽 공기청정기는 시계방향 순환
//바람이 불면 미세먼지가 바람의 방향대로 모두 한 칸씩 이동
//공기청정기로 들어간 미세먼지는 모두 정화됨