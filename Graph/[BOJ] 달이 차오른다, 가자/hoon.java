import java.io.*;
import java.util.*;

public class Main {
	public static class Point{
		int x, y, dist;
		int status;
		Point(int x, int y, int dist, int status){
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.status = status;
		}
	}
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int n, m;
	static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
	static char[][] maze;

	public static int addKey(int idx, int mask){
		return mask | (1<<idx);
	}

	public static boolean hasKey(int idx, int mask){
		return ((mask & (1<<idx)) != 0);
	}

	public static boolean isValid(int x, int y){
		return x>=0 && y>=0 && x<n && y<m;
	}

	public static int alphabetToIdx(char c){
		int idx = -1;
		if(c >= 'a' && c <='f'){
			idx = c - 'a';
		}
		else if(c >= 'A' && c <='F'){
			idx = c - 'A';
		}
		return idx;
	}

	//현재 갈 수 있는 모든 방향으로 탐색
	public static int bfs(Point start){
		Deque<Point> q = new ArrayDeque<>();
		boolean[][][] visited = new boolean[n][m][1<<6];
		q.offer(start);
		visited[start.x][start.y][0] = true;
		int result = Integer.MAX_VALUE;
		while(!q.isEmpty()){
			Point cur = q.poll();

			for(int i = 0; i<4; i++){
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				if(!isValid(nx, ny) || visited[nx][ny][cur.status] || maze[nx][ny] == '#') continue;
				int status = cur.status;
				if(maze[nx][ny] == '1'){
					result = Math.min(cur.dist+1, result);
					break;
				}
				else if(maze[nx][ny] == '.'){
					visited[nx][ny][cur.status] = true;
					q.offer(new Point(nx, ny, cur.dist+1, status));
				}
				else if(maze[nx][ny] >= 'A' && maze[nx][ny] <='F'){
					if(hasKey(alphabetToIdx(maze[nx][ny]), status)){
						visited[nx][ny][status] = true;
						q.offer(new Point(nx, ny, cur.dist+1, status));
					}
				}
				else {
					int idx = alphabetToIdx(maze[nx][ny]);
					status = addKey(idx, status);
					visited[nx][ny][status] = true;
					q.offer(new Point(nx, ny, cur.dist+1, status));
				}

			}
		}

		return result == Integer.MAX_VALUE ? -1 : result;
	}

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		maze = new char[n][m];

		Point start = new Point(0,0,0,0);
		for(int i  = 0; i<n; i++){
			String line = br.readLine();
			for(int j = 0; j<m; j++){
				maze[i][j] = line.charAt(j);
				if(maze[i][j] == '0'){
					start = new Point(i, j, 0, 0);
					maze[i][j] = '.';
				}
			}
		}
		br.close();
		System.out.println(bfs(start));
	}
}