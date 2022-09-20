package me.javirpo.puzzle.solver.game;

import me.javirpo.puzzle.solver.Unscrambleletters;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LocateWords extends Game {
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private final Set<String> mustHave = new HashSet<>();
    private List<String> lastWords = Collections.emptyList();
    private List<List<String>> crossCols;
    private List<String[]> wordsFound = new ArrayList<>();

    @Override
    protected void run() throws IOException {
        createBoard();
        readLetters();

        String opt;
        while (!isDone()) {
            printBoard();
            System.out.println();
            System.out.println("-1. Solve");
            System.out.println("-2. Put Word");
            if (!lastWords.isEmpty()) {
                System.out.println("#. For any Last Word");
            }
            System.out.print("Write: ");
            opt = sc.nextLine();
            switch (opt) {
                case "-1":
                    solve();
                    break;
                case "-2":
                    //readHelp();
                    printBoard();
                    solve();
                    break;
                default:
                    int nOpt = Integer.parseInt(opt);
                    if (nOpt < lastWords.size()) {
                        String word = lastWords.get(nOpt);
                        //readWord(word);
                        printBoard();
                        solve();
                        break;
                    }
            }
        }
        System.out.println("Finish ...");
        System.out.println();
        printBoard();
    }

    protected void createBoard() {
        super.createBoard();

        crossCols = new ArrayList<>(cols);
        for (int i = 0; i < cols; i++) {
            crossCols.add(new ArrayList<>(rows));
        }
    }

    private void readLetters() {
        System.out.println();
        System.out.print("Reading Rows: ");

        List<String[]> letterRows = new ArrayList<>(rows);

        for (int i = 0; i < rows; i++) {
            System.out.print("Rows #" + (i + 1) + ": ");
            String line = sc.nextLine();
            String[] letters = line.split(",");
            if (letters.length != cols) {
                System.out.println("Only " + letters.length + " cols, " + cols + " is needed.");
                i--;
                continue;
            }
            letterRows.add(letters);
        }
        Collections.reverse(letterRows);
        for (int i = 0; i < rows; i++) {
            String[] letters = letterRows.get(i);
            for (int j = 0; j < cols; j++) {
                crossCols.get(i).add(letters[j]);
            }
        }
    }

    @Override
    protected void printBoard() {
        System.out.println("====");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                List<String> cross = crossCols.get(j);
                if(cross.size() < rows) {
                    System.out.print(cross.get(i));
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println("====");
        System.out.println("Words found:");
        wordsFound.stream().map(Arrays::toString).forEach(System.out::println);
        System.out.println("====");
    }

    private void solve() throws IOException {
        boolean checkAgain = true;
        while (checkAgain) {
            checkAgain = solveIterator();
        }
    }

    private boolean solveIterator() throws IOException {
        boolean checkAgain = false;

        for (int col = 0; col < cols; col++) {
            List<String> cross = crossCols.get(col);
            String letters = cross.stream()
                    .filter(letter -> !"".equals(letter.trim()))
                            .collect(Collectors.joining());

            Map<Integer, Collection<String>> map = Unscrambleletters.getWordsWithCache(letters);
            if (!map.isEmpty()) {
                Map<Integer, Collection<String>> tree = new TreeMap<>(Collections.reverseOrder());
                tree.putAll(map);

                boolean found = false;
            }

            if (!checkAgain) {
                checkAgain = !map.isEmpty();
            }
        }

        return checkAgain;
    }

    private void removeWord(String s, List<String> cross) {
    }

    private boolean matchWord(String word, String letters) {
        return false;
    }

    public static void main(String[] args) throws IOException {
        new LocateWords()._run();
    }
}
