package me.javirpo.puzzle.solver.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import lombok.Setter;
import me.javirpo.puzzle.solver.Unscrambleletters;
import me.javirpo.puzzle.solver.helper.BoardIterator;
import me.javirpo.puzzle.solver.helper.Line;
import me.javirpo.puzzle.solver.helper.Point;

public class WordSlide extends Game {
    @Setter
    private boolean omitColsProcessing = false;
    @Setter
    private int minLength = 3;

    private ArrayList<String> crossRows;
    private ArrayList<String> crossCols;

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
                    readHelp();
                    printBoard();
                    break;
            }
        }
        System.out.println("Finish ...");
        System.out.println();
        printBoard();
    }

    private void readMissingLetters() {
        System.out.println();
        System.out.println("Reading Missing Letters... ");

        if (!omitColsProcessing) {
            crossRows = readMissingLetters("Row", rows);
        }
        crossCols = readMissingLetters("Col", cols);
    }

    private ArrayList<String> readMissingLetters(String type, int size) {
        ArrayList<String> cross = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            System.out.print("Letters per " + type + " #" + (i + 1) + ": ");
            String line = sc.nextLine();
            cross.add(line);
        }

        return cross;
    }

    private void readHelp() {
        System.out.println();
        System.out.print("Row,Col,Letter: ");

        String[] line = sc.nextLine().split(",");
        int row = Integer.parseInt(line[0]);
        int col = Integer.parseInt(line[1]);
        char newChar = line[2].charAt(0);
        putLetter(row, col, newChar);
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
        iterator.setMinLength(minLength);
        while (iterator.hasNext()) {
            Line line = iterator.next();
            if (omitColsProcessing && !line.isHorizontal()) {
                continue;
            }

            int size = line.length() + 1;
            StringBuilder pattern = new StringBuilder();
            StringBuilder letterSearch = new StringBuilder();

            for (Point p : line) {
                checkLetter(p.getRow(), p.getCol(), pattern, letterSearch);
            }

            checkAgain |= search(line, size, pattern, letterSearch);
        }

        return checkAgain;
    }

    private void checkLetter(int row, int col, StringBuilder pattern, StringBuilder letterSearch) {
        if (boardLetters[row][col] == ' ') {
            Set<Character> set = new LinkedHashSet<>();
            if (!omitColsProcessing) {
                for (char c : crossRows.get(row).toCharArray()) {
                    set.add(c);
                }
            }
            for (char c : crossCols.get(col).toCharArray()) {
                set.add(c);
            }
            String letters = StringUtils.join(set, "");

            pattern.append('[');
            pattern.append(letters);
            pattern.append(']');

            letterSearch.append(letters);
        } else {
            pattern.append(boardLetters[row][col]);
            letterSearch.append(boardLetters[row][col]);
        }
    }

    private boolean search(Line line, int size, StringBuilder pattern, StringBuilder letterSearch) throws IOException {
        boolean checkAgain = false;

        Map<Integer, Collection<String>> map = Unscrambleletters.getWordsWithCache(letterSearch.toString());
        Collection<String> words = Unscrambleletters.getAndFilter(map, size, pattern.toString());

        if (words.size() == 1) {
            checkAgain = solveLetters(line, size, words.iterator().next());
        } else if (words.size() > 1) {
            checkAgain = lookForSimilarities(line, size, words);
        }
        return checkAgain;
    }

    private boolean lookForSimilarities(Line line, int size, Collection<String> words) {
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
            checkAgain = solveLetters(line, size, sb.toString());
        }

        return checkAgain;
    }

    private boolean solveLetters(Line line, int size, String word) {
        boolean checkAgain = false;
        int k = 0;
        for (Point p : line) {
            char c = word.charAt(k);
            int row = p.getRow();
            int col = p.getCol();
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

        String lettersRow = omitColsProcessing ? "" : crossRows.get(row);
        String lettersCol = crossCols.get(col);
        int foundInRow = lettersRow.indexOf(c);
        int foundInCol = lettersCol.indexOf(c);
        if (foundInRow != -1 && foundInCol != -1) {
            // omit to remove
        } else if (foundInRow != -1) {
            lettersRow = lettersRow.substring(0, foundInRow) + lettersRow.substring(foundInRow + 1);
            crossRows.set(row, lettersRow);
        } else if (foundInCol != -1) {
            lettersCol = lettersCol.substring(0, foundInCol) + lettersCol.substring(foundInCol + 1);
            crossCols.set(col, lettersCol);
        }
    }

    public static void main(String[] args) throws IOException {
        new WordSlide()._run();
    }
}
