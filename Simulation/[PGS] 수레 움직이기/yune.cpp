#include <bits/stdc++.h>
using namespace std;

const int INF = 1e9;
int dx[4] = {-1,1,0,0};
int dy[4] = {0,0,-1,1};

struct P { int x,y; };

int H, W;
int mp[4][4];
bool visitedPos[4][4][2]; // [x][y][0:red,1:blue]
bool redAtGoal=false, blueAtGoal=false;

P getNext(const P &p, int dir){
    return {p.x + dx[dir], p.y + dy[dir]};
}

bool inBounds(const P &p){
    return p.x >= 0 && p.x < H && p.y >= 0 && p.y < W;
}

bool isPossibleMove(const P &curR, const P &nR, const P &curB, const P &nB){
    // 범위/벽 체크
    if (!inBounds(nR) || !inBounds(nB)) return false;
    if (mp[nR.x][nR.y] == 5 || mp[nB.x][nB.y] == 5) return false;

    // 두 수레가 같은 칸으로 이동하면 안 됨
    if (nR.x == nB.x && nR.y == nB.y) return false;

    // 서로 위치를 바꿀 수 없음 (swap)
    if (curR.x == nB.x && curR.y == nB.y && curB.x == nR.x && curB.y == nR.y) return false;

    // 도착해 있지 않은 수레가 이미 방문한 칸으로 돌아가는 것은 불가
    if (!redAtGoal && visitedPos[nR.x][nR.y][0]) return false;
    if (!blueAtGoal && visitedPos[nB.x][nB.y][1]) return false;

    return true;
}

int backtrack(const P &red, const P &blue, int turns){
    if (redAtGoal && blueAtGoal) return turns;
    int best = INF;

    // 각 수레가 움직일 방향(4) × 4 = 16가지
    for(int dr = 0; dr < 4; ++dr){
        for(int db = 0; db < 4; ++db){
            P nR = red, nB = blue;
            if (!redAtGoal) nR = getNext(red, dr);
            if (!blueAtGoal) nB = getNext(blue, db);

            if (!isPossibleMove(red, nR, blue, nB)) continue;

            // 상태 변경 적용
            bool prevRedGoal = redAtGoal, prevBlueGoal = blueAtGoal;
            visitedPos[nR.x][nR.y][0] = true;
            visitedPos[nB.x][nB.y][1] = true;

            if (mp[nR.x][nR.y] == 3) redAtGoal = true;
            if (mp[nB.x][nB.y] == 4) blueAtGoal = true;

            best = min(best, backtrack(nR, nB, turns + 1));

            // 상태 복구
            redAtGoal = prevRedGoal;
            blueAtGoal = prevBlueGoal;
            visitedPos[nR.x][nR.y][0] = false;
            visitedPos[nB.x][nB.y][1] = false;
        }
    }
    return best;
}

// 프로그래머스 형식: solution 함수
int solution(vector<vector<int>> maze) {
    H = maze.size();
    W = maze[0].size();
    P startR{-1,-1}, startB{-1,-1};

    for(int i=0;i<H;++i){
        for(int j=0;j<W;++j){
            mp[i][j] = maze[i][j];
            if (mp[i][j] == 1) startR = {i,j}; // 빨간 출발
            if (mp[i][j] == 2) startB = {i,j}; // 파란 출발
        }
    }

    // 초기 방문 마크
    memset(visitedPos, 0, sizeof(visitedPos));
    visitedPos[startR.x][startR.y][0] = true;
    visitedPos[startB.x][startB.y][1] = true;

    // 만약 시작이 이미 목표에 있다면 해당 수레는 고정(문제 조건에 따라)
    redAtGoal  = (mp[startR.x][startR.y] == 3);
    blueAtGoal = (mp[startB.x][startB.y] == 4);

    int ans = backtrack(startR, startB, 0);
    return (ans == INF) ? 0 : ans;
}

/* 
// 테스트용 main (플랫폼 제출시에는 필요없음)
int main(){
    vector<vector<int>> maze = {
        {1,0,0,3},
        {0,5,0,0},
        {2,0,0,4},
        {0,0,0,0}
    };
    cout << solution(maze) << "\n";
    return 0;
}
*/

