package me.javirpo.puzzle.solver.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import me.javirpo.puzzle.solver.Unscrambleletters;
import me.javirpo.puzzle.solver.helper.BoardIterator;
import me.javirpo.puzzle.solver.helper.Line;
import me.javirpo.puzzle.solver.helper.Point;

public class Codeword extends Game {
    private int maxLetters = 1;

    private String currentMissingLetters;
    private Collection<Character> missingLetters;
    private char[] letters;

    @Override
    protected void run() throws IOException {
        createBoard();
        readRows();
        readMissingLetters();

        String opt;
        while (!isDone()) {
            printBoard();
            System.out.println();
            System.out.println("-1. Solve");
            System.out.println("-2. Put");
            System.out.print("Write: ");
            opt = sc.nextLine();
            switch (opt) {
                case "-1":
                    solve();
                    break;
                case "-2":
                    readNumberLetter();
                    populateLetters();
                    break;
            }
        }
        System.out.println("Finish ...");
        System.out.println();
        printBoard();
    }

    protected void readRows() {
        super.readRows();
        System.out.println();
        System.out.println("Reading Rows ...");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maxLetters = Math.max(maxLetters, boardNumbers[i][j]);
            }
        }

        letters = new char[maxLetters + 1];
        for (int i = 0; i <= maxLetters; i++) {
            letters[i] = ' ';
        }
    }

    private void readMissingLetters() {
        System.out.println();
        System.out.print("Reading Missing Letters: ");
        currentMissingLetters = sc.nextLine();

        missingLetters = new LinkedList<>();
        for (int i = 0; i < currentMissingLetters.length(); i++) {
            missingLetters.add(currentMissingLetters.charAt(i));
        }
    }

    private void readNumberLetter() {
        System.out.println();
        System.out.print("Number,Letter: ");

        String[] line = sc.nextLine().split(",");
        int index = Integer.parseInt(line[0]);
        char newChar = line[1].charAt(0);
        char currentChar = letters[index];
        letters[index] = newChar;

        if (newChar == ' ') {
            missingLetters.add(currentChar);
        } else {
            missingLetters.remove(newChar);
        }
        currentMissingLetters = StringUtils.join(missingLetters, "");
    }

    private void populateLetters() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int letterNumber = boardNumbers[i][j];
                boardLetters[i][j] = letters[letterNumber];
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
        boolean checkAgain = false;

        BoardIterator iterator = new BoardIterator(boardNumbers, boardLetters, rows, cols);
        while (iterator.hasNext()) {
            Line line = iterator.next();
            int size = line.length() + 1;
            Map<Integer, Integer> numberRepeated = new HashMap<>(size);
            StringBuilder pattern = new StringBuilder();
            StringBuilder letterSearch = new StringBuilder();
            ArrayList<Integer> numbers = new ArrayList<>(size);

            for (Point p : line) {
                checkLetter(p.getRow(), p.getCol(), numberRepeated, pattern, letterSearch, numbers);
            }

            checkAgain |= search(line, size, numberRepeated, pattern, letterSearch, numbers);

            populateLetters();
            printBoard();
        }

        return checkAgain;
    }

    private void checkLetter(int row, int col, Map<Integer, Integer> numberRepeated, StringBuilder pattern,
        StringBuilder letterSearch, ArrayList<Integer> numbers) {
        int num = boardNumbers[row][col];
        numbers.add(num);

        if (boardLetters[row][col] == ' ') {
            pattern.append('[');
            pattern.append(currentMissingLetters);
            pattern.append(']');

            numberRepeated.compute(num, (number, occurrences) -> {
                if (occurrences == null) {
                    return 1;
                }
                return occurrences + 1;
            });
        } else {
            pattern.append(boardLetters[row][col]);
            letterSearch.append(boardLetters[row][col]);
        }
    }

    private boolean search(Line line, int size, Map<Integer, Integer> numberRepeated, StringBuilder pattern,
        StringBuilder letterSearch, ArrayList<Integer> numbers) throws IOException {
        boolean checkAgain = false;

        int max = numberRepeated.values()
            .stream()
            .max(Integer::compare)
            .orElse(1);
        letterSearch.append(currentMissingLetters.repeat(max));

        Map<Integer, Collection<String>> map = Unscrambleletters.getWordsWithCache(letterSearch.toString());
        Collection<String> words = Unscrambleletters.getAndFilter(map, size, pattern.toString());
        words = filterRepeatedLetters(line, numberRepeated, max, words);

        if (words.size() == 1) {
            checkAgain = solveLetters(size, numbers, words.iterator().next());
        } else if (words.size() > 1) {
            checkAgain = lookForSimilarities(size, numbers, words);
        }
        return checkAgain;
    }

    private Collection<String> filterRepeatedLetters(Line line, Map<Integer, Integer> numberRepeated, int max, Collection<String> words) {
        if (max > 1 && !words.isEmpty()) {
            Set<Integer> checked = new HashSet<>();
            Set<ArrayList<Integer>> repeated = new LinkedHashSet<>();
            for (Entry<Integer, Integer> number : numberRepeated.entrySet()) {
                int num = number.getKey();
                if (number.getValue() > 1 && checked.add(num)) {
                    int j = 0;
                    ArrayList<Integer> rep = new ArrayList<>(number.getValue());
                    for (Point p : line) {
                        if (boardNumbers[p.getRow()][p.getCol()] == num) {
                            rep.add(j);
                        }
                        j++;
                    }
                    repeated.add(rep);
                }
            }
            if (!repeated.isEmpty()) {
                words = words.stream()
                    .filter(word -> {
                        boolean match = true;
                        for (ArrayList<Integer> rep : repeated) {
                            int indexLetter = rep.get(0);
                            char letter = word.charAt(indexLetter);
                            for (Integer r : rep) {
                                if (letter != word.charAt(r)) {
                                    match = false;
                                    break;
                                }
                            }

                            if (!match) {
                                break;
                            }
                        }
                        return match;
                    })
                    .collect(Collectors.toList());
            }
        }
        return words;
    }

    private boolean lookForSimilarities(int size, ArrayList<Integer> numbers, Collection<String> words) {
        boolean checkAgain = false;
        StringBuilder sb = new StringBuilder(size);

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
            checkAgain = solveLetters(size, numbers, sb.toString());
        }

        return checkAgain;
    }

    private boolean solveLetters(int size, ArrayList<Integer> numbers, String word) {
        boolean checkAgain = false;
        for (int k = 0; k < size; k++) {
            char c = word.charAt(k);
            int num = numbers.get(k);
            if (c != ' ' && letters[num] == ' ') {
                letters[num] = c;
                missingLetters.remove(c);
                checkAgain = true;
            }
        }

        currentMissingLetters = StringUtils.join(missingLetters, "");
        return checkAgain;
    }

    public static void main(String[] args) throws IOException {
        new Codeword()._run();
    }
}
