import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IfYouGiveASeedAFertilizer {
    private static final String FILEPATH = "Task5/input.txt";

    public static void main(String[] args) {
        // yerp
        try {
            System.out.println(Arrays.toString(splitInputLines()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static String[] splitInputLines() throws IOException {
        List<String> inp = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILEPATH))) {
            for (String line; (line = br.readLine()) != null;) {
                inp.add(line.trim());
            }
        }
        return Arrays.copyOfRange(String.join("\n", inp.toArray(new String[0])).split("[a-z\\-]* ?[a-z]+:"), 1, 9);
    }

    /**
     * Almanac
     */
    public class Almanac {
        private final List<Integer> seedList;
        private final Map<Integer, Integer> seed2SoilMap;
        private final Map<Integer, Integer> soil2FertMap;
        private final Map<Integer, Integer> fert2WaterMap;
        private final Map<Integer, Integer> water2LightMap;
        private final Map<Integer, Integer> light2TempMap;
        private final Map<Integer, Integer> temp2HumidMap;
        private final Map<Integer, Integer> humid2LocMap;

        public Almanac(String[] inputs) {
            seedList = new ArrayList<>();
            for (String s : inputs[0].trim().split("\\s")) {
                if (s.matches("[0-9]+")) {
                    seedList.add(Integer.valueOf(s));
                }
            }
            seed2SoilMap   = setMap(inputs[1]);
            soil2FertMap   = setMap(inputs[2]);
            fert2WaterMap  = setMap(inputs[3]);
            water2LightMap = setMap(inputs[4]);
            light2TempMap  = setMap(inputs[5]);
            temp2HumidMap  = setMap(inputs[6]);
            humid2LocMap   = setMap(inputs[7]);
        }

        private Map<Integer, Integer> setMap(String line) {
            Map<Integer, Integer> map = new HashMap<>();
            for (String row : line.trim().split("\\n")) {
                setRowRange(map, row.split(" +"));
            }
            return map;
        }

        private void setRowRange(Map<Integer, Integer> map, String[] row) {
            int[] rangeData = new int[row.length];
            for (int i = 0; i < row.length; i++) {
                
            }
        }
    }
}
