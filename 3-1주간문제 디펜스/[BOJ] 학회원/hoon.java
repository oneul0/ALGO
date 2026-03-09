import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true){
            String input = br.readLine();
            if(input.equals("0")) break;

            int n = Integer.parseInt(input);
            List<String> lines = new ArrayList<>();
            for(int i = 0; i<n; i++){
                lines.add(br.readLine());
            }

            Map<String, List<String>> gr = new HashMap<>();
            Set<String> insNames = new HashSet<>();
            String targetIns = "";
            for(int i = 0; i<n; i++){

                String str = lines.get(i);
                String[] tmp = splitInstituteName(str);
                String insName = tmp[0];

                // 첫 협회명 저장
                if(targetIns.isEmpty()){
                    targetIns = insName;
                }
                String[] members = splitMemberName(tmp[1]);
                //협회명 그래프에 추가
                gr.putIfAbsent(insName, new ArrayList<>());
                //협회명에 협회원 추가
                gr.get(insName).addAll(Arrays.asList(members));
                //협회명 추가
                insNames.add(insName);
            }

            //첫 협회부터 탐색 시작
            System.out.println(dfs(targetIns, gr, insNames, new HashSet<>()));
        }
    }

    //협회원이 협회인지 체크
    //협회원이 모두 사람인 경우까지 반복
    public static int dfs(String curIns, Map<String, List<String>> gr, Set<String> insNames, Set<String> visited){
        if(visited.contains(curIns)) return 0;
        visited.add(curIns);
        int count = 0;
        //지금 협회의 협회원 중 협회가 있다면
        for(String name : gr.get(curIns)){
            //그 협회의 협회원 탐색(count 누적)
            if(insNames.contains(name)){
                count += dfs(name, gr, insNames, visited);
            }
            else{
                if(!visited.contains(name)){
                    visited.add(name);
                    count++;
                }
            }
        }

        return count;
    }

    //학회 : 학회원(협회) 끊기
    public static String[] splitInstituteName(String str){
        return str.split(":");
    }
    //협회원, 끊기
    public static String[] splitMemberName(String str){
        return str.replace(".","").split(",");
    }
    //idx 0 인 학회명에 대해 회원수가 몇 명인지
    //parent 테이블 이용(map)
    //그래프 형태로 작성해도 되지만 부모가 바뀔 때 오버헤드가 클 것
    //학회명의 인덱스를 고정하고 테이블의 값만 변경하여 이후 값으로 0을 가지는 요소에 대해
    //count 진행?

}
