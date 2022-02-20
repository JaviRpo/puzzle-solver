package me.javirpo.puzzle.solver.game;

import me.javirpo.puzzle.solver.Unscrambleletters;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Wordy extends Game {
    private static final int LEN = 5;
    private String letters = "abcdefghijklmnopqrstuvwxyz";
    private final Set<String> mustHave = new HashSet<>();
    private Collection<String> lastWords = Collections.emptyList();

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
        cols = 5;

        boardLetters = new char[][]{{' ', ' ', ' ', ' ', ' '}};
        boardNumbers = new int[][]{{1, 1, 1, 1, 1}};
    }

    private void loadDictionary() {
        // TODO: load letter with size
    }

    private void readHelp() {
        System.out.println();
        System.out.print("Word used: ");
        String word = sc.nextLine().toLowerCase();

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
                    letters = letters.replace(letter, "");
                    break;
                case 'C':
                    mustHave.add(letter);
                    putLetter(0, i, word.charAt(i));
                    break;
                case 'P':
                    mustHave.add(letter);
                    break;
                default:
                    System.out.println("WARNING - Option not valid: " + option);
            }
        }
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
            pattern.append(letters);
            pattern.append(']');
        } else {
            pattern.append(boardLetters[0][col]);
        }
    }

    private boolean search(StringBuilder pattern) throws IOException {
        boolean checkAgain = false;

        Map<Integer, Collection<String>> map = Unscrambleletters.getWordsWithCache(mustHave.size() == LEN ? StringUtils.join(mustHave, "") : letters);
        Collection<String> words = Unscrambleletters.getAndFilter(map, LEN, pattern.toString());
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

    private boolean lookForSimilarities(Collection<String> words) {
        boolean checkAgain = false;
        StringBuilder sb = new StringBuilder(LEN);

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
