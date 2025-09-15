import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static class PatternData {
		String pattern;
		int length;

		PatternData(String binaryStr) {
			this.pattern = binaryStr;
			this.length = binaryStr.length();
		}
	}

	public static void main(String[] args) throws IOException {
		int B = Integer.parseInt(br.readLine());
		List<PatternData> lowData = new ArrayList<>();
		List<PatternData> highData = new ArrayList<>();

		for(int i = 0; i < B; i++){
			lowData.add(new PatternData(br.readLine()));
		}
		for(int i = 0; i < B; i++){
			highData.add(new PatternData(br.readLine()));
		}

		int N = Integer.parseInt(br.readLine());
		for(int i = 0; i < N; i++){
			String realTimeStr = br.readLine();

			// 패턴 매칭
			int lowCount = countPatterns(realTimeStr, lowData);
			int highCount = countPatterns(realTimeStr, highData);

			// 현재 차고 계산
			int C = highCount - lowCount;

			if(C == 0){
				System.out.println("GOOD");
			}
			else {
				System.out.println(C > 0 ? "LOW " + C : "HIGH " + Math.abs(C));
			}
		}
		br.close();
	}

	public static int countPatterns(String realTimeData, List<PatternData> patterns) {
		int totalCount = 0;

		for(PatternData pattern : patterns) {
			totalCount += countSinglePattern(realTimeData, pattern);
		}

		return totalCount;
	}

	public static int countSinglePattern(String realTimeData, PatternData pattern) {
		int count = 0;
		String patternStr = pattern.pattern;
		int patternLength = pattern.length;

		// 슬라이딩 윈도우
		for(int pos = 0; pos <= realTimeData.length() - patternLength; pos++) {
			String extracted = realTimeData.substring(pos, pos + patternLength);

			if(extracted.equals(patternStr)) {
				count++;
			}
		}

		return count;
	}
}