#include <iostream>
#include <memory.h>
using namespace std;

const int INF = 987654321;
const int MAX = 500;


int cache[MAX + 1][MAX + 1], files[MAX], pSum[MAX];
// pSum[i] = 0번째 파일부터 i번째 파일까지의 누적 합

int dp(int l, int r) {
	if (l == r)
		return files[l];

	int& ret = cache[l][r];
	if (ret != -1)	return ret;

	ret = INF;

	int p1 = pSum[r];
	int p2 = l > 0 ? pSum[l - 1] : 0;
	int sum = p1 - p2;

	for (int i = l; i < r; i++)
		ret = min(ret, dp(l, i) + dp(i + 1, r) + sum);

	return ret;
}

int main() {
	ios_base::sync_with_stdio(0);
	cin.tie(0);
	cout.tie(0);

	int T;
	cin >> T;

	for(int t = 0; t<T; t++) {
		memset(cache, -1, sizeof(cache));
		int K;
		cin >> K;

		for (int i = 0; i < K; i++) {
			cin >> files[i];
			pSum[i] = files[i];

			if (i > 0)
				pSum[i] += pSum[i - 1];
		}

		int ans = INF;
		for (int i = 0; i < K; i++)
			ans = min(ans, dp(0, i) + dp(i + 1, K - 1));

		cout << ans << '\n';
	}

	return 0;
}