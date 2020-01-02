package me.javirpo.puzzle.solver.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

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
    private int[][] boardNumbers;

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
            System.out.println("-2. Put number-letter");
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

    private boolean isDone() {
        for (int i = 1; i < letters.length; i++) {
            if (letters[i] == ' ') {
                return false;
            }
        }
        return true;
    }

    private void createBoard() {
        System.out.println();
        System.out.print("Rows: ");
        String strRows = sc.nextLine();
        System.out.print("Cols: ");
        String strCols = sc.nextLine();

        rows = Integer.parseInt(strRows);
        cols = Integer.parseInt(strCols);
        board = new char[rows][cols];
        boardNumbers = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private void readRows() {
        System.out.println();
        System.out.println("Reading Rows ...");

        for (int i = 0; i < rows; i++) {
            System.out.print("Rows #" + (i + 1) + ": ");
            String line = sc.nextLine();
            String[] numbers = line.split(",");
            if (numbers.length != cols) {
                System.out.println("Only " + numbers.length + " cols, " + cols + " is needed.");
                i--;
                continue;
            }
            for (int j = 0; j < cols; j++) {
                boardNumbers[i][j] = Integer.parseInt(numbers[j]);
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
                board[i][j] = letters[letterNumber];
            }
        }
    }

    private void solve() throws IOException {
        boolean checkAgain = true;
        while (checkAgain) {
            checkAgain = solveIterator();
            // checkAgain = solveRows();
            // checkAgain |= solveCols();
        }
    }

    private boolean solveIterator() throws IOException {
        boolean checkAgain = false;

        BoardIterator iterator = new BoardIterator(boardNumbers, board, rows, cols);
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

            checkAgain |= search(size, numberRepeated, pattern, letterSearch, numbers);
            
            populateLetters();
        }

        return checkAgain;
    }

    private boolean solveRows() throws IOException {
        boolean checkAgain = false;

        for (int row = 0; row < rows; row++) {
            int indexStart = getNextStart(row, 0, 0, 1);
            int indexEnd = getEndOfWord(row, indexStart, 0, 1);
            while (indexEnd != -1) {
                int diffLetters = indexEnd - indexStart;
                if (diffLetters >= 2) {
                    int size = diffLetters + 1;
                    Map<Integer, Integer> numberRepeated = new HashMap<>(size);
                    StringBuilder pattern = new StringBuilder();
                    StringBuilder letterSearch = new StringBuilder();
                    ArrayList<Integer> numbers = new ArrayList<>(size);

                    for (int col = indexStart; col <= indexEnd; col++) {
                        checkLetter(row, col, numberRepeated, pattern, letterSearch, numbers);
                    }

                    checkAgain |= search(size, numberRepeated, pattern, letterSearch, numbers);
                }
                populateLetters();

                indexStart = getNextStart(row, indexEnd + 1, 0, 1);
                indexEnd = getEndOfWord(row, indexStart, 0, 1);
            }
        }
        return checkAgain;
    }

    private boolean solveCols() throws IOException {
        boolean checkAgain = false;

        for (int col = 0; col < cols; col++) {
            int indexStart = getNextStart(0, col, 1, 0);
            int indexEnd = getEndOfWord(indexStart, col, 1, 0);
            while (indexEnd != -1) {
                int diffLetters = indexEnd - indexStart;
                if (diffLetters >= 2) {
                    int size = diffLetters + 1;
                    Map<Integer, Integer> numberRepeated = new HashMap<>(size);
                    StringBuilder pattern = new StringBuilder();
                    StringBuilder letterSearch = new StringBuilder();
                    ArrayList<Integer> numbers = new ArrayList<>(size);

                    for (int row = indexStart; row <= indexEnd; row++) {
                        checkLetter(row, col, numberRepeated, pattern, letterSearch, numbers);
                    }

                    checkAgain |= search(size, numberRepeated, pattern, letterSearch, numbers);
                }
                populateLetters();

                indexStart = getNextStart(indexEnd + 1, col, 1, 0);
                indexEnd = getEndOfWord(indexStart, col, 1, 0);
            }
        }
        return checkAgain;
    }

    private void checkLetter(int row, int col, Map<Integer, Integer> numberRepeated, StringBuilder pattern,
        StringBuilder letterSearch, ArrayList<Integer> numbers) {
        int num = boardNumbers[row][col];
        numbers.add(num);

        if (board[row][col] == ' ') {
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
            pattern.append(board[row][col]);
            letterSearch.append(board[row][col]);
        }
    }

    private boolean search(int size, Map<Integer, Integer> numberRepeated, StringBuilder pattern,
        StringBuilder letterSearch, ArrayList<Integer> numbers) throws IOException {
        boolean checkAgain = false;

        int max = numberRepeated.entrySet()
            .stream()
            .map(Entry::getValue)
            .max(Integer::compare)
            .orElse(1);
        for (int k = 0; k < max; k++) {
            letterSearch.append(currentMissingLetters);
        }

        Map<Integer, Collection<String>> map = Unscrambleletters.getWords(letterSearch.toString());
        Collection<String> words = Unscrambleletters.getAndFilter(map, size, pattern.toString());

        if (words.size() == 1) {
            String word = words.iterator().next();
            for (int k = 0; k < size; k++) {
                int num = numbers.get(k);
                char c = word.charAt(k);

                letters[num] = c;
                missingLetters.remove(c);
            }
            checkAgain = true;

            currentMissingLetters = StringUtils.join(missingLetters, "");
        }
        return checkAgain;
    }

    private int getNextStart(int row, int col, int plusRow, int plusCol) {
        boolean hasSpace = false;
        int index = -1;
        for (; row < rows && col < cols; row += plusRow, col += plusCol) {
            if (boardNumbers[row][col] != 0) {
                if (index == -1) {
                    index = plusRow != 0 ? row : col;
                }

                hasSpace |= board[row][col] == ' ';
                if (hasSpace) {
                    break;
                }
            } else {
                index = -1;
            }
        }
        return hasSpace ? index : -1;
    }

    private int getEndOfWord(int row, int col, int plusRow, int plusCol) {
        if (row == -1 || col == -1) {
            return -1;
        }

        int index = -1;
        for (; row < rows && col < cols; row += plusRow, col += plusCol) {
            if (boardNumbers[row][col] == 0) {
                break;
            }

            index = plusRow != 0 ? row : col;
        }

        return index;
    }

    public static void main(String[] args) throws IOException {
        new Codeword()._run();
    }
}
