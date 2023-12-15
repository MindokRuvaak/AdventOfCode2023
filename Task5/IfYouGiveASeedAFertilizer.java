import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IfYouGiveASeedAFertilizer {
    private static final String FILEPATH = "Task5/input.txt";
    // private static final String FILEPATH = "Task5/controllInput.txt";

    public static void main(String[] args) {
        try {
            // System.out.println(Arrays.toString(splitInputLines()));
            Almanac a = new Almanac(splitInputLines());
            System.out.println(a.findShortestDistanceFromSeedRange());
            // MMMM
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

        public long findShortestDistanceFromSeedRange() {
            long shortest = Long.MAX_VALUE; // guarantee equal or longer lenght than corresponding seeds
            List<long[]> seedRanges = convertSeedsToRanges();
            for (long[] seedRange : seedRanges) {
                for (long seed = seedRange[0]; seed < seedRange[1]; seed++) {
                    long newVal = findLocationForSeed(seed);
                    if (newVal < shortest) {
                        shortest = newVal;
                    }
                }
            }
            return shortest;
        }

        private List<long[]> convertSeedsToRanges() {
            List<long[]> rangeList = new ArrayList<>();
            for (int i = 0; i < seedList.size() - 1; i++) {
                rangeList.add(new long[] { seedList.get(i), seedList.get(i) + seedList.get(i + 1) });
                i++;
            }
            return rangeList;
        }

        public long findShortestDistance() {
            long shortest = Long.MAX_VALUE; // guarantee equal or longer lenght than corresponding seeds
            for (Long seed : seedList) {
                long newVal = findLocationForSeed(seed);
                if (newVal < shortest) {
                    shortest = newVal;
                }
            }
            return shortest;
        }

        public long findLocationForSeed(long seed) {
            long soil = seed2SoilMap.getDestination(seed);
            long fertelizer = soil2FertMap.getDestination(soil);
            long water = fert2WaterMap.getDestination(fertelizer);
            long light = water2LightMap.getDestination(water);
            long temperature = light2TempMap.getDestination(light);
            long humidity = temp2HumidMap.getDestination(temperature);
            long location = humid2LocMap.getDestination(humidity);
            return location;
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
                    // OBS : half open range! the range start is inclusive and range end exclusive
                    // => [start, start+length)
                    sourceRangeList.add(new long[] { sourceStart, sourceStart + length });
                    destinationRangeList.add(new long[] { detinationStart, detinationStart + length });
                }
            }
        }

        public long getDestination(long source) {
            for (int i = 0; i < sourceRangeList.size(); i++) {
                long[] r = sourceRangeList.get(i);
                if (inRange(r, source)) {
                    // if source in range "nr" i, then destination in respective range
                    // distance between source and sourceRangeStart is equal to
                    // distance between destination and destinationRangeStart
                    // => source - sourceRangeStart = x
                    // && destination = destinationRangeStart + x
                    // => destination = destinationRangeStart + (source - sourceRangeStart)
                    return destinationRangeList.get(i)[0] + (source - r[0]);
                }
            }
            // if source is not in any of the provided ranges: source == destination
            return source;
        }
    }

    public static boolean inRange(long[] r, long source) {
        return source >= r[0] && source < r[r.length - 1];
    }
}
