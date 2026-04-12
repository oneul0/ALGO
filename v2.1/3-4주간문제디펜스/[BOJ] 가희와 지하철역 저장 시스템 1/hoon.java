import java.io.*;
import java.util.*;

public class Main {
    static int n, r;
    static int fIdx = 0;
    static Map<String, Integer> featureIdx = new HashMap<>(); //get feature bit idx
    static Map<String, Integer> stationFeature = new HashMap<>();
    static int[] maskCounts = new int[512];

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        for(int i = 0; i < n; i++){
            String station = br.readLine();
            stationFeature.put(station, 0);
            maskCounts[0]++;
        }

        r = Integer.parseInt(br.readLine());
        for(int i = 0; i<r; i++){
            String[] line = br.readLine().split(" ");
            String cmd = line[0];

            switch(cmd){
                case "U": updateStation(line[1], line[2].split(",")); break;
                case "G": System.out.println(getStation(line[1].split(","))); break;
            }
        }
    }

    public static void updateStation(String name, String[] features) {
        if (stationFeature.containsKey(name)) {
            int oldMask = stationFeature.get(name);
            maskCounts[oldMask]--;
        }

        int newMask = 0;
        for (String feature : features) {
            if (!featureIdx.containsKey(feature)) {
                featureIdx.put(feature, fIdx++);
            }
            //addFeature
            newMask |= (1 << featureIdx.get(feature));
        }

        maskCounts[newMask]++;
        stationFeature.put(name, newMask);
    }
    public static int getStation(String[] features) {
        int queryMask = 0;
        for (String feature : features) {
            if (!featureIdx.containsKey(feature)) return 0;
            queryMask |= (1 << featureIdx.get(feature));
        }

        int totalCount = 0;
        for (int i = 0; i < 512; i++) {
            //isMatched
            if ((i & queryMask) == queryMask) {
                totalCount += maskCounts[i];
            }
        }
        return totalCount;
    }
}