import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		String line;
		while((line = br.readLine()) != null){
			if(line.equals("#")) break;

			if(isValid(line)){
				System.out.println("legal");
			}
			else{
				System.out.println("illegal");
			}
		}
		br.close();
	}

	public static boolean isValid(String html){
		Deque<String> stack = new ArrayDeque<>();
		int i = 0;
		while(i<html.length()){
			if(html.charAt(i) == '<'){
				//가장 가까운 닫힌 괄호 찾기
				int endIdx = html.indexOf('>', i);
				if(endIdx == -1){
					return false;
				}

				String tag = html.substring(i+1, endIdx);

				if(tag.endsWith("/")){
					//자체 닫힌 태그는 아무것도 안함
				}
				else if(tag.startsWith("/")){
					//닫힌 태그
					//시작 인덱스 1
					String tagName = tag.substring(1).trim();
					int spaceIdx = tagName.indexOf(' ');
					if(spaceIdx != -1){
						tagName = tagName.substring(0, spaceIdx);
					}

					if(stack.isEmpty()){
						return false;
					}

					String openTag = stack.pop();
					if(!openTag.equals(tagName)){
						return false;
					}

				}
				else{
					//열린 태그
					String tagName = tag.trim();
					int spaceIdx = tagName.indexOf(' ');
					if(spaceIdx != -1){
						tagName = tagName.substring(0, spaceIdx);
					}
					stack.push(tagName);
				}
				i = endIdx +1;
			}
			else{
				i++;
			}
		}
		//모든 태그가 닫혔는지
		return stack.isEmpty();
	}
}

// 각 줄을 순회하면서 <와 > 사이의 내용을 태그로 인식
// 자체 닫힌 태그는 스택 추가 x
// 열린 태그 : 태그명만 추출해서 스택에 넣기
// 닫힌태그 스택에서 꺼내서 매칭

// 열린 태그에서 속성이 있을 수 있으므로 첫 번째 공백 전까지만 태그명으로 인식하기
//닫힌 태그가 나왔을 때 스택이 비어있으면 illegal
// 닫힌 태그와 스택의 top이 매칭되지 않으면 illegal
// 모든 처리가 끝났을 때 스택이 비어있지 않으면 illegal
