import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GearRatios {
    public static void main(String[] args) {
        try {
            Schematic sch = new Schematic("Task3/input.txt");
            int sum = sumNumbers(sch);
            System.out.println(sum);
            int sumGR = sumGearRatio(sch.getGears());
            System.out.println(sumGR);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static int sumGearRatio(List<Gear> grs) {
        int sum = 0;
        for (Gear gear : grs) {
            sum += gear.getRatio();
        }
        return sum;
    }

    private static int sumNumbers(Schematic sch) {
        List<Integer> nums = getNumbers(sch);
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        return sum;
    }

    private static List<Integer> getNumbers(Schematic sch) {
        List<Integer> numbers = new ArrayList<>();
        List<int[][]> numberPos = sch.getNumberPos();
        for (int[][] nPs : numberPos) {
            int row = nPs[0][0];
            int num = nPs[2][0];
            if (sch.isAnyOfIndsAdjacentToSymbol(row, nPs[1])) {
                numbers.add(num);
            }
        }
        return numbers;
    }

    public static boolean isNotDigitOrDot(char c) {
        return !Character.isDigit(c) && c != '.';
    }

    public static class Schematic {
        private final char[][] schematic;
        private final int nRows;
        private final int nCols;
        private final List<int[][]> numberPos; // = {row}{ind1, ind2, ...}{val}
        private final List<Gear> gears;

        Schematic(String filePath) throws FileNotFoundException, IOException {
            schematic = setSchematic(filePath);
            numberPos = setNumberPos();
            nRows = schematic.length;
            nCols = schematic[0].length;
            gears = findGears();
        }

        public List<int[][]> getNumberPos() {
            return numberPos;
        }

        public List<Gear> getGears() {
            return gears;
        }

        public List<Gear> findGears() {
            List<Gear> grs = new ArrayList<>();
            for (int i = 0; i < schematic.length; i++) {
                for (int j = 0; j < schematic[i].length; j++) {
                    List<Integer> adjNum = adjacentNums(i, j);
                    if (schematic[i][j] == '*' && adjNum.size() == 2) {

                        grs.add(new Gear(new int[] { i, j }, adjNum.get(0) * adjNum.get(1)));
                    }
                }
            }
            return grs;
        }

        public List<Integer> adjacentNums(int i, int j) {
            List<Integer> adjNumber = new ArrayList<>();
            for (int[][] nP : numberPos) {
                int numSize = nP[1].length;
                int numIndL = nP[1][0];
                int numIndR = nP[1][numSize-1];
                if ((nP[0][0] == i
                        && (numIndR == j - 1 || numIndL == j + 1))
                        || (Math.abs(nP[0][0] - i) == 1
                                && (Math.abs(numIndR - j) <= 1 || Math.abs(numIndL - j) <= 1))) {
                    adjNumber.add(nP[2][0]);
                }
            }
            return adjNumber;
        }
        // X X X X X X X
        // 0 1 2 * 0 1 2

        public char[][] setSchematic(String filePath) throws FileNotFoundException, IOException {
            List<char[]> tmp = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                for (String line; (line = br.readLine()) != null;) {
                    tmp.add(line.toCharArray());
                }
            }
            return tmp.toArray(new char[0][]);
        }

        public List<int[][]> setNumberPos() {
            List<int[][]> nPs = new ArrayList<>();
            for (int i = 0; i < schematic.length; i++) {
                for (int j = 0; j < schematic[i].length; j++) {
                    List<Integer> rowInds = new ArrayList<>();
                    while (j < schematic[i].length && Character.isDigit(schematic[i][j])) {
                        rowInds.add(j);
                        j++;
                    }
                    int[] rowi = new int[rowInds.size()];
                    String numChars = "";
                    for (int k = 0; k < rowi.length; k++) {
                        rowi[k] = rowInds.get(k);
                        numChars += schematic[i][j - rowInds.size() + k];
                    }
                    if (rowi.length != 0) {
                        nPs.add(new int[][] { { i }, rowi, { Integer.valueOf(numChars) } });
                    }
                }
            }
            return nPs;
        }

        public boolean isAnyOfIndsAdjacentToSymbol(int row, int[] inds) {
            for (int i : inds) {
                if (isNumAtIndsAdjacentToSymbol(row, i)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isNumAtIndsAdjacentToSymbol(int row, int col) {
            for (int i = Math.max(row - 1, 0); i <= Math.min(row + 1, nRows - 1); i++) {
                for (int j = Math.max(col - 1, 0); j <= Math.min(col + 1, nCols - 1); j++) {
                    if (isNotDigitOrDot(schematic[i][j])) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static class Gear {
        private final int[] inds;
        private final int ratio;

        Gear(int[] inds, int ratio) {
            this.inds = inds;
            this.ratio = ratio;
        }

        public int getRatio() {
            return ratio;
        }
    }
}