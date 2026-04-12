import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		double x=  Double.parseDouble(st.nextToken());
		double y = Double.parseDouble(st.nextToken());
		double c = Double.parseDouble(st.nextToken());

		double left = 0.0;
		double right = Math.min(x,y);
		double mid = 0.0;

		for(int i = 0; i<10000; i++){
			mid = (left+right)/2.0;
			double h1 = Math.sqrt(x*x - mid*mid);
			double h2 = Math.sqrt(y*y - mid*mid);
			double cc = (h1 * h2) / (h1 + h2);
			if(cc < c) right = mid;
			else left = mid;
		}
		System.out.printf("%.3f", mid);
	}
}
