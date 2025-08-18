import java.io.*;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		int N = Integer.parseInt(br.readLine()), ans = -1;
		String[] st = br.readLine().split(" ");
		int[] arr = Arrays.stream(st).mapToInt(Integer::parseInt).toArray();
		int count = 0, cur = arr[0];

		for(int i = 1; i<N; i++){
			if(arr[i] < cur){
				count++;
			}
			else{
				ans = Math.max(ans, count);
				count = 0;
				cur = arr[i];
			}
		}

		ans = Math.max(ans, count);
		System.out.print(ans);
	}
}