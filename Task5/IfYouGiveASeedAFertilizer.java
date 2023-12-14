import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IfYouGiveASeedAFertilizer {
    private static final String FILEPATH = "Task5/input.txt";

    public static void main(String[] args) {
        // yerp
        try {
            // System.out.println(Arrays.toString(splitInputLines()));
            Almanac a = new Almanac(splitInputLines());
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
    public static class Almanac {
        private final List<Long> seedList;
        private final FarmMap seed2SoilMap;
        private final FarmMap soil2FertMap;
        private final FarmMap fert2WaterMap;
        private final FarmMap water2LightMap;
        private final FarmMap light2TempMap;
        private final FarmMap temp2HumidMap;
        private final FarmMap humid2LocMap;

        public Almanac(String[] inputs) {
            seedList = new ArrayList<>();
            for (String s : inputs[0].trim().split("\\s")) {
                if (s.matches("[0-9]+")) {
                    seedList.add(Long.valueOf(s));
                }
            }
            seed2SoilMap = new FarmMap(inputs[1].trim().split("\\n"));
            soil2FertMap = new FarmMap(inputs[2].trim().split("\\n"));
            fert2WaterMap = new FarmMap(inputs[3].trim().split("\\n"));
            water2LightMap = new FarmMap(inputs[4].trim().split("\\n"));
            light2TempMap = new FarmMap(inputs[5].trim().split("\\n"));
            temp2HumidMap = new FarmMap(inputs[6].trim().split("\\n"));
            humid2LocMap = new FarmMap(inputs[7].trim().split("\\n"));
        }
    }

    private static class FarmMap {
        private final List<long[]> sourceRangeList; // each element in list is of form {rangeStart, rangeEnd}
        private final List<long[]> destinationRangeList; // same as above

        FarmMap(String[] rows) {
            sourceRangeList = new ArrayList<>();
            destinationRangeList = new ArrayList<>();
            for (String row : rows) {
                if (row.length() != 0) {
                    String[] splitRow = row.split(" ");
                    long detinationStart = Long.valueOf(splitRow[0]);
                    long sourceStart = Long.valueOf(splitRow[1]);
                    long length = Long.valueOf(splitRow[2]);
                    sourceRangeList.add(new long[] { sourceStart, sourceStart + length - 1 });
                    destinationRangeList.add(new long[] { detinationStart, detinationStart + length - 1 });
                }
            }
        }
    }
}
