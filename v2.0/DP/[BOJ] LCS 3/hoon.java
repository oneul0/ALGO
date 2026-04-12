import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		String[] strs = new String[3];
		for(int i = 0; i<3; i++){
			strs[i] = br.readLine();
		}
		Arrays.sort(strs, Collections.reverseOrder());
		int firstLen = strs[0].length();
		int secondLen = strs[1].length();
		int thirdLen = strs[2].length();

		int[][][] firstSecondThird = new int[firstLen+1][secondLen+1][thirdLen+1];

		for(int i = 1; i<=firstLen; i++){
			for(int j = 1; j<=secondLen; j++){
				for(int k = 1; k<=thirdLen; k++){
					if(strs[0].charAt(i-1) == strs[1].charAt(j-1) && strs[1].charAt(j-1) == strs[2].charAt(k-1)){
						firstSecondThird[i][j][k] = firstSecondThird[i-1][j-1][k-1]+1;
					}
					else{
						firstSecondThird[i][j][k] = Math.max(firstSecondThird[i-1][j][k], Math.max(firstSecondThird[i][j-1][k], firstSecondThird[i][j][k-1]));
					}
				}
			}
		}
		System.out.println(firstSecondThird[firstLen][secondLen][thirdLen]);
		br.close();
	}
}
