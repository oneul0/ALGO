import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) throws IOException {
		Stack<Character> stack = new Stack<>();
		String origin = br.readLine();
		String bomb = br.readLine();
		for(int i = 0; i<origin.length(); i++) {
			stack.push(origin.charAt(i));
			if(stack.size() >= bomb.length()) {
				boolean flag = true;

				for(int j = 0; j<bomb.length(); j++) {
					if(stack.get(stack.size() - bomb.length() + j) != bomb.charAt(j)){
						flag = false;
						break;
					}
				}

				if(flag) {
					for(int j = 0; j<bomb.length(); j++) {
						stack.pop();
					}
				}
			}
		}

		if(stack.isEmpty()) System.out.println("FRULA");
		else{
			StringBuilder sb = new StringBuilder();
			for(char c : stack) {
				sb.append(c);
			}
			System.out.println(sb.toString());
		}
	}
}
