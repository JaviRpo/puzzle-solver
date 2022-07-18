package me.javirpo.puzzle.solver.game;

import me.javirpo.puzzle.solver.Unscrambleletters;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Wordy extends Game {
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private final Set<String> mustHave = new HashSet<>();
    private List<String> lastWords = Collections.emptyList();
    private List<String> crossCols;
    private Map<Integer, Set<String>> dictionary = new HashMap<>();

    @Override
    protected void run() throws IOException {
        createBoard();
        loadDictionary();

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
                    readHelp();
                    printBoard();
                    solve();
                    break;
                default:
                    int nOpt = Integer.parseInt(opt);
                    if (nOpt < lastWords.size()) {
                        String word = lastWords.get(nOpt);
                        readWord(word);
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

    @Override
    protected void printBoard() {
        super.printBoard();
        System.out.println("Must Have: " + mustHave);
        System.out.println("Last Words: " + lastWords);
    }

    @Override
    protected void createBoard() {
        rows = 1;
        System.out.print("Cols: ");
        String strCols = sc.nextLine();
        cols = Integer.parseInt(strCols);

        crossCols = IntStream.range(0, cols)
                .mapToObj(i -> LETTERS)
                .collect(Collectors.toList());

        boardLetters = new char[rows][cols];
        boardNumbers = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boardLetters[i][j] = ' ';
                boardNumbers[i][j] = 1;
            }
        }
    }

    private void loadDictionary() {
        dictionary.put(4, new TreeSet<>(Arrays.asList("home", "star", "time")));
        dictionary.put(5, new TreeSet<>(Arrays.asList("music", "heart")));
        dictionary.put(6, new TreeSet<>(Arrays.asList("solver", "string", "method")));

        System.out.println("Starting help: " + dictionary.getOrDefault(cols, Collections.emptySet()));
    }

    private void readHelp() {
        System.out.println();
        System.out.print("Word used: ");
        String word = sc.nextLine().toLowerCase();

        readWord(word);
    }

    private void readWord(String word) {
        System.out.println("'X' for Not in word");
        System.out.println("'C' for Correct");
        System.out.println("'P' for Possible");
        System.out.println("Example: XPPCX");
        System.out.print("Letter by letter: ");
        String line = sc.nextLine().toUpperCase();
        for (int i = 0; i < line.length(); i++) {
            String letter = word.charAt(i) + "";
            char option = line.charAt(i);
            switch (option) {
                case 'X':
                    removeLetter(letter);
                    break;
                case 'C':
                    mustHave.add(letter);
                    removeLetter(letter);
                    crossCols.set(i, letter);
                    putLetter(0, i, word.charAt(i));
                    break;
                case 'P':
                    mustHave.add(letter);
                    removeLetter(i, letter);
                    break;
                default:
                    System.out.println("WARNING - Option not valid: " + option);
            }
        }
    }

    private void removeLetter(String letter) {
        IntStream.range(0, crossCols.size())
                .filter(i -> crossCols.get(i).length() != 1)
                .forEach(i -> removeLetter(i, letter));
    }

    private void removeLetter(int i, String letter) {
        String letters = crossCols.get(i);
        letters = letters.replace(letter, "");
        crossCols.set(i, letters);
    }

    private void solve() throws IOException {
        boolean checkAgain = true;
        while (checkAgain) {
            checkAgain = solveIterator();
        }
    }

    private boolean solveIterator() throws IOException {
        StringBuilder pattern = new StringBuilder();

        for (int col = 0; col < cols; col++) {
            checkLetter(col, pattern);
        }

        boolean checkAgain = search(pattern);
        printBoard();

        return checkAgain;
    }

    private void checkLetter(int col, StringBuilder pattern) {
        if (boardLetters[0][col] == ' ') {
            pattern.append('[');
            pattern.append(crossCols.get(col));
            pattern.append(']');
        } else {
            pattern.append(boardLetters[0][col]);
        }
    }

    private boolean search(StringBuilder pattern) throws IOException {
        boolean checkAgain = false;

        Map<Integer, Collection<String>> map = Unscrambleletters.getWordsWithCache(mustHave.size() == cols ? StringUtils.join(mustHave, "") : searchLetters());
        List<String> words = Unscrambleletters.getAndFilter(map, cols, pattern.toString());
        // Filter words with MUST_HAVE
        words = words.stream()
                .filter(w -> {
                    for (String letter : mustHave) {
                        if (!w.contains(letter)) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
        lastWords = words;

        if (words.size() == 1) {
            checkAgain = solveLetters(words.iterator().next());
        } else if (words.size() > 1) {
            checkAgain = lookForSimilarities(words);
        }
        return checkAgain;
    }

    private String searchLetters() {
        Set<Character> character = crossCols.stream()
                .map(String::toCharArray)
                .map(ArrayUtils::toObject)
                .map(Arrays::asList)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
        return StringUtils.join(character, "");
    }

    private boolean lookForSimilarities(Collection<String> words) {
        boolean checkAgain = false;
        StringBuilder sb = new StringBuilder(cols);

        String firstWord = words.iterator().next();
        for (int i = 0; i < firstWord.length(); i++) {
            char c = firstWord.charAt(i);
            boolean same = true;
            for (String word : words) {
                if (c != word.charAt(i)) {
                    same = false;
                    break;
                }
            }
            if (same) {
                sb.append(c);
                checkAgain = true;
            } else {
                sb.append(' ');
            }
        }

        if (checkAgain) {
            checkAgain = solveLetters(sb.toString());
        }

        return checkAgain;
    }

    private boolean solveLetters(String word) {
        boolean checkAgain = false;
        int k = 0;
        int row = 0;
        for (int col = 0; col < cols; col++) {
            char c = word.charAt(k);
            if (c != ' ' && boardLetters[row][col] == ' ') {
                putLetter(row, col, c);

                checkAgain = true;
            }
            k++;
        }

        return checkAgain;
    }

    private void putLetter(int row, int col, char c) {
        boardLetters[row][col] = c;
    }

    public static void main(String[] args) throws IOException {
        new Wordy()._run();
    }
}
