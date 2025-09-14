import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static class Node{
		Map<Character, Node> child;
		boolean endOfName;
		public Node() {
			child = new HashMap<>();
			endOfName = false;
		}
	}
	public static class Trie {
		Node root;
		public Trie(){
			root = new Node();
		}

		public void insert(String name){
			if(name == null || name.isEmpty()) return;

			Node cur = root;
			for(char ch : name.toCharArray()){
				cur.child.putIfAbsent(ch, new Node());
				cur = cur.child.get(ch);
			}
			cur.endOfName = true;
		}

		public boolean startWith(String prefix){
			if(prefix == null || prefix.isEmpty()) return false;
			Node cur = root;
			for(char ch : prefix.toCharArray()){
				Node node = cur.child.get(ch);
				if(node == null) return false;
				cur = node;
			}
			return cur.endOfName || !cur.child.isEmpty();
		}
	}

	static Map<String, Integer> nameCount = new HashMap<>();
	public static void main(String[] args) throws IOException {
		int N = Integer.parseInt(br.readLine());
		Trie trie = new Trie();

		for(int i = 0; i<N; i++){
			String name = br.readLine();
			nameCount.put(name, nameCount.getOrDefault(name, 0) + 1);
			int count = nameCount.get(name);
			String alias;
			//만약 같은 닉네임이 있으면 별명은 x+숫자
			if(nameCount.get(name) >=2){
				alias = name+count;
			}
			//새로 들어온 닉네임이면 겹치지 않는 가장 짧은 접두사
			else{
				alias = name;
				for(int len = 1; len <= name.length(); len++){
					String prefix = name.substring(0, len);
					if(!trie.startWith(prefix)){
						alias = prefix;
						break;
					}
				}
			}
			trie.insert(name);
			System.out.println(alias);
		}

	}
}


//trie 구현
//문자열 추가해가면서 찾다가 이미 만들어진 별명에 포함되지 않는 문자열이면 return
//별명짓기
//정확히 일치하는 닉네임이 있는지
//없다면 이렇게 시작하는 닉네임이 있는지