#include <vector>
#include <queue>
#include <set>
#include <algorithm>

using namespace std;

int solution(vector<vector<int>> land) {
    int n = land.size();
    int m = land[0].size();
    vector<vector<bool>> visited(n, vector<bool>(m, false));
    vector<int> oilPerColumn(m, 0);
    int dx[] = {0, 0, 1, -1};
    int dy[] = {1, -1, 0, 0};

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (land[i][j] == 1 && !visited[i][j]) {
                queue<pair<int, int>> q;
                q.push({i, j});
                visited[i][j] = true;

                int oilSize = 0;
                set<int> columns;

                while (!q.empty()) {
                    pair<int, int> current = q.front();
                    q.pop();
                    int x = current.first;
                    int y = current.second;

                    oilSize++;
                    columns.insert(y);

                    for (int k = 0; k < 4; k++) {
                        int nx = x + dx[k];
                        int ny = y + dy[k];

                        if (nx >= 0 && nx < n && ny >= 0 && ny < m && land[nx][ny] == 1 && !visited[nx][ny]) {
                            visited[nx][ny] = true;
                            q.push({nx, ny});
                        }
                    }
                }
                
                for (int col : columns) {
                    oilPerColumn[col] += oilSize;
                }
            }
        }
    }
    
    return *max_element(oilPerColumn.begin(), oilPerColumn.end());
}