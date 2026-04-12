import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br=  new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException{
		int T = Integer.parseInt(br.readLine());

		while(T-- > 0){
			int N = Integer.parseInt(br.readLine());
			String[] numbers = new String[N];

			for(int i = 0; i<N; i++){
				numbers[i] = br.readLine();
			}

			Arrays.sort(numbers);

			boolean isConsist = true;
			for(int i = 0; i<N-1; i++){
				if(numbers[i+1].startsWith(numbers[i])){
					isConsist = false;
					break;
				}
			}

			System.out.println(isConsist ? "YES" : "NO");
		}
		br.close();
	}
}
