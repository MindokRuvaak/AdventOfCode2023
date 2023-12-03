import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CubeConundrum {
    private static String file = "Task2/input.txt";
    private static CubeSet limit = new CubeSet(12, 13, 14);

    public static void main(String[] args) throws FileNotFoundException, IOException {
        List<CubeGame> gamesList = readGamesFromFile();
        int sum = sumPossible(gamesList);
        System.out.println(sum);
        int powSum = sumPowers(gamesList);
        System.out.println(powSum);
    }

    private static int sumPowers(List<CubeGame> gamesList) {
        int sum = 0;
        for (CubeGame cubeGame : gamesList) {
            sum += cubeGame.getPower();
        }
        return sum;
    }

    private static int sumPossible(List<CubeGame> gamesList) {
        int sum = 0;
        for (CubeGame cubeGame : gamesList) {
            if (cubeGame.isPossible()) {
                sum += cubeGame.getId();
            }
        }
        return sum;
    }

    static List<CubeGame> readGamesFromFile()
            throws FileNotFoundException, IOException {
        List<CubeGame> gamesList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line; (line = br.readLine()) != null;) {
                CubeGame game = extractCubeGameFromLine(line);
                gamesList.add(game);
            }
        }
        return gamesList;
    }

    static CubeGame extractCubeGameFromLine(String line) {
        List<String> lineSegments = splitIntoSegments(line);

        int id = Integer.valueOf(lineSegments.get(0));
        List<CubeSet> sets = extractCubeSets(
                lineSegments.subList(1, lineSegments.size()));
        CubeGame game = new CubeGame(id, sets);
        return game;
    }

    private static List<CubeSet> extractCubeSets(List<String> lineSegments) {
        List<CubeSet> setList = new ArrayList<>();
        for (String str : lineSegments) {
            String[] colourSet = str.split(",");
            setList.add(extractSet(colourSet));
        }
        return setList;
    }

    private static CubeSet extractSet(String[] set) {
        int[] colours = new int[3];
        for (String string : set) {
            String[] pair = string.trim().split(" ");
            switch (pair[1].charAt(0)) {
                case 'r':
                    colours[0] = Integer.valueOf(pair[0]);
                    break;
                case 'g':
                    colours[1] = Integer.valueOf(pair[0]);
                    break;
                case 'b':
                    colours[2] = Integer.valueOf(pair[0]);
                    break;
                default:
                    break;
            }
        }
        return new CubeSet(colours);
    }

    private static List<String> splitIntoSegments(String line) {
        List<String> lineSegmetns = new ArrayList<>();
        String[] idSplit = line.split(":");
        // System.out.println(idSplit[0].substring(5));
        lineSegmetns.add(idSplit[0].substring(5));
        String[] setSplit = idSplit[1].split(";");
        for (String string : setSplit) {
            // System.out.println(" " + string.trim());
            lineSegmetns.add(string);
        }
        return lineSegmetns;
    }

    private static class CubeGame {
        final private int id;
        final private List<CubeSet> sets;
        final private int power;

        public CubeGame(int id, List<CubeSet> sets) {
            this.id = id;
            this.sets = sets;
            int[] minNeeded = {0, 0, 0};
            for (CubeSet cubeSet : sets) {
                int[] colourNums = cubeSet.getColourNum();
                for (int i = 0; i < colourNums.length; i++) {
                    if (minNeeded[i] < colourNums[i]) {
                        minNeeded[i] = colourNums[i];
                    }
                }
            }
            int mul = 1;
            for (int n : minNeeded) {
                mul *= n;
            }
            power = mul;
        }

        public int getId() {
            return id;
        }

        public int getPower() {
            return power;
        }

        public boolean isPossible() {
            for (CubeSet cubeSet : sets) {
                if (!cubeSet.isPossible()) {
                    return false;
                }
            }
            return true;
        }



        @Override
        public String toString() {
            return "id = " + id + "; \nsets: \n" + sets;
        }
    }

    private static class CubeSet {
        final private int numRedCubes;
        final private int numGreenCubes;
        final private int numBlueCubes;

        public CubeSet(int numRedCubes, int numGreenCubes, int numBlueCubes) {
            this.numRedCubes = numRedCubes;
            this.numGreenCubes = numGreenCubes;
            this.numBlueCubes = numBlueCubes;
        }

        public CubeSet(int[] set) {
            this(set[0], set[1], set[2]);
        }

        public int[] getColourNum() {
            return new int[] {numRedCubes, numGreenCubes, numBlueCubes};
        }

        public boolean isPossible() {
            boolean redOK = numRedCubes <= limit.numRedCubes;
            boolean greenOK = numGreenCubes <= limit.numGreenCubes;
            boolean blueOK = numBlueCubes <= limit.numBlueCubes;
            return redOK && greenOK && blueOK;
        }

        @Override
        public String toString() {
            return "[Red: " + numRedCubes + "; Green: " + numGreenCubes + "; Blue: "
                    + numBlueCubes + "]";
        }
    }
}