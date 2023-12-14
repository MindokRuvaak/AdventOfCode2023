import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


public class Scratchcards {
    private static final String FILEPATH = "Task4/input.txt";//controllInput.txt";

    public static void main(String[] args) {
        // yeerp
        try {
            List<Scratchcard> scratchCards = loadScratchcards();
            // System.out.println(/* String.join("\n", */ scratchCards.toString()/* .split("\\], \\[")) */);
            int pointSum = sumPoints(scratchCards);
            // System.out.println(pointSum);
            countWonCopies(scratchCards);
            System.out.println(sumAmount(scratchCards));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void countWonCopies(List<Scratchcard> cards) {
        for (Scratchcard sc : cards) {
            for (int i = sc.getCardNumber(); i < sc.getCardNumber()+sc.setNumWinningHave(); i++) {
                cards.get(i).addCopies(sc.getCopies());
            }
        }
    }

    private static int sumAmount(List<Scratchcard> scratchCards) {
        int amount = 0;
        for (Scratchcard scratchcard : scratchCards) {
            amount += scratchcard.getCopies();
        }
        return amount;
    }

    private static int sumPoints(List<Scratchcard> scratchCards) {
        List<Integer> pointList = new ArrayList<>();
        pointList.add(0);
        scratchCards.forEach(x -> pointList.set(0, pointList.get(0) + x.getPoints()));
        return pointList.get(0);
    }

    /**
     * @return
     * @throws IOException
     */
    private static List<Scratchcard> loadScratchcards() throws IOException {
        List<Scratchcard> scs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILEPATH))) {
            for (String line; (line = br.readLine()) != null;) {
                scs.add(new Scratchcard(line.split("[:|]")));
            }
        }
        return scs;
    }

    private static int[] toIntArr(String lineSegment) {
        String[] numberStrings = lineSegment.trim().split(" +");
        int[] intArr = new int[numberStrings.length];
        for (int i = 0; i < numberStrings.length; i++) {
            intArr[i] = Integer.valueOf(numberStrings[i]);
        }
        return intArr;
    }

    /**
     * scratchcard
     */
    public static class Scratchcard {
        private final int cardNumber;
        private final int[] winningNumbers;
        private final int[] haveNumbers;
        private final int points;
        private final int numWinningHave;
        private int copies;
        
        public Scratchcard(int cardNumber, int[] winningNumbers, int[] haveNumbers) {
            this.cardNumber = cardNumber;
            this.winningNumbers = winningNumbers;
            this.haveNumbers = haveNumbers;
            this.numWinningHave = setNumWinningHave();
            points = setPoints();
            copies = 1;
        }

        public void addCopies(int n) {
            copies+=n;
        }

        public int getCopies() {
            return  copies;
        }

        public int getCardNumber() {
            return cardNumber;
        }

        public int getNumWinningHave() {
            return numWinningHave;
        }

        private int setNumWinningHave() {
            int numberOfWinningNumbersInHaveNumbers = 0;
            for (int n : winningNumbers) {
                if (IntStream.of(haveNumbers).anyMatch(x -> x == n)) {
                    numberOfWinningNumbersInHaveNumbers++;
                }
            }
            return numberOfWinningNumbersInHaveNumbers;
        }

        public int getPoints() {
            return points;
        }

        private int setPoints() {
            return (int) Math.pow(2, numWinningHave - 1);
        }

        public Scratchcard(String[] splitLine) {
            this(Integer.valueOf(splitLine[0].substring(4).trim()), toIntArr(splitLine[1].trim()),
                    toIntArr(splitLine[2].trim()));
        }

        @Override
        public String toString() {
            return "[" + Arrays.toString(winningNumbers) + " | "
                    + Arrays.toString(haveNumbers) + "] : " + points + "\n";
        }

    }
}
