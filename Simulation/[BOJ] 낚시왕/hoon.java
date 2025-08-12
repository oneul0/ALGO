import java.io.*;
import java.util.*;

public class Main {
	static class Shark {
		// 행, 열, 속력, 방향, 크기
		int r, c, s, d, z;
		public Shark(int r, int c, int s, int d, int z) {
			this.r = r;
			this.c = c;
			this.s = s;
			this.d = d;
			this.z = z;
		}
	}

	static int R, C, M;
	static List<Shark> sharks;
	// 각 위치에 상어 인덱스
	static int[][] map;
	static int[] dr = {0, -1, 1, 0, 0};
	static int[] dc = {0, 0, 0, 1, -1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[R + 1][C + 1];
		for (int i = 0; i <= R; i++) {
			Arrays.fill(map[i], -1);
		}

		sharks = new ArrayList<>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			//(행 또는 열의 길이 - 1) * 2 번 이동하면 처음 자리로 돌아옴
			if (d == 1 || d == 2) s %= (R - 1) * 2;
			else s %= (C - 1) * 2;

			sharks.add(new Shark(r, c, s, d, z));
			map[r][c] = i;
		}

		int total = 0;
		for (int fisher = 1; fisher <= C; fisher++) {
			// 1. 낚시
			for (int r = 1; r <= R; r++) {
				if (map[r][fisher] != -1) {
					total += sharks.get(map[r][fisher]).z;
					// 상어 제거
					sharks.set(map[r][fisher], null);
					map[r][fisher] = -1;
					break;
				}
			}
			// 2. 상어 이동
			moveSharks();
		}

		System.out.println(total);
	}

	static void moveSharks() {
		int[][] newMap = new int[R + 1][C + 1];
		for (int i = 0; i <= R; i++) {
			Arrays.fill(newMap[i], -1);
		}

		for (int i = 0; i < sharks.size(); i++) {
			Shark s = sharks.get(i);
			if (s == null) continue;

			int r = s.r;
			int c = s.c;
			int d = s.d;
			int speed = s.s;

			for (int mv = 0; mv < speed; mv++) {
				if (d == 1 && r == 1) d = 2;
				else if (d == 2 && r == R) d = 1;
				else if (d == 3 && c == C) d = 4;
				else if (d == 4 && c == 1) d = 3;
				r += dr[d];
				c += dc[d];
			}

			s.r = r;
			s.c = c;
			s.d = d;

			if (newMap[r][c] == -1) {
				newMap[r][c] = i;
			} else {
				// 같은 칸에 상어가 이미 있다면 크기 비교
				int otherIdx = newMap[r][c];
				if (sharks.get(otherIdx).z < s.z) {
					// 기존 상어 제거
					sharks.set(otherIdx, null);
					newMap[r][c] = i;
				} else {
					// 현재 상어 제거
					sharks.set(i, null);
				}
			}
		}

		map = newMap;
	}
}
