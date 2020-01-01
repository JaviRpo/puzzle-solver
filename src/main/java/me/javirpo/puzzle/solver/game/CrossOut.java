package me.javirpo.puzzle.solver.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import any.Unscrambleletters;

public class CrossOut extends Game {
    private String[] patternRows;
    private String[] patternCols;
    private Collection<String>[] crossRows;
    private Collection<String>[] crossCols;

    @Override
    protected void run() throws IOException {
        createBoard();
        readRows();
        readCols();
        solve();

        printBoard();
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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = ' ';
            }
        }

        patternRows = new String[rows];
        patternCols = new String[cols];
    }

    private Collection<String>[] read(String type, int size, String[] patternLines) throws IOException {
        Collection<String>[] crossLines = new Collection[size];

        System.out.println();
        System.out.println("Reading " + type + " ...");

        for (int i = 0; i < size; i += 2) {
            System.out.print(type + " #" + (i + 1) + ": ");
            String line = sc.nextLine();

            String pattern = createPattern(line);
            patternLines[i] = pattern;

            Map<Integer, Collection<String>> map = Unscrambleletters.getWords(line);
            Collection<String> words = Unscrambleletters.getAndFilter(map, size, pattern);
            crossLines[i] = words;
        }

        return crossLines;
    }

    private String createPattern(String line) {
        StringBuilder sb = new StringBuilder(line.length() * 2);
        for (int i = 0; i < line.length(); i += 2) {
            sb.append('[');
            sb.append(line.charAt(i));
            sb.append(line.charAt(i + 1));
            sb.append(']');
        }
        return sb.toString();
    }

    private void readRows() throws IOException {
        crossRows = read("Rows", rows, patternRows);
    }

    private void readCols() throws IOException {
        crossCols = read("Cols", cols, patternCols);
    }

    private void solve() {
        boolean checkAgain = true;
        while (checkAgain) {
            checkAgain = solveRows();
            checkAgain |= solveCols();
        }
    }

    private boolean solveRows() {
        boolean checkAgain = false;
        
        for (int i = 0; i < crossRows.length; i += 2) {
            if (crossRows[i] != null) {
                patternRows[i] = checkPattern(i, 0, 0, 2, cols, patternRows[i]);
                Map<Integer, Collection<String>> map = new LinkedHashMap<>();
                map.put(rows, crossRows[i]);

                Collection<String> words = Unscrambleletters.getAndFilter(map, rows, patternRows[i]);
                if (words.size() == 1) {
                    String word = words.iterator().next();
                    for (int j = 0; j < word.length(); j++) {
                        board[i][j] = word.charAt(j);
                    }

                    crossRows[i] = null;
                    checkAgain = true;
                } else {
                    if (crossRows[i].size() != words.size()) {
                        checkAgain = true;
                    }
                    crossRows[i] = words;
                }
            }
        }
        return checkAgain;
    }

    private boolean solveCols() {
        boolean checkAgain = false;
        for (int i = 0; i < crossCols.length; i += 2) {
            if (crossCols[i] != null) {
                patternCols[i] = checkPattern(0, i, 2, 0, rows, patternCols[i]);
                Map<Integer, Collection<String>> map = new LinkedHashMap<>();
                map.put(cols, crossCols[i]);

                Collection<String> words = Unscrambleletters.getAndFilter(map, cols, patternCols[i]);
                if (words.size() == 1) {
                    String word = words.iterator().next();
                    for (int j = 0; j < word.length(); j++) {
                        board[j][i] = word.charAt(j);
                    }

                    crossCols[i] = null;
                    checkAgain = true;
                } else {
                    if (crossCols[i].size() != words.size()) {
                        checkAgain = true;
                    }
                    crossCols[i] = words;
                }
            }
        }
        return checkAgain;
    }

    private String checkPattern(int row, int col, int plusRow, int plusCol, int len, String pattern) {
        if (pattern.indexOf('[') == -1) {
            return pattern;
        }

        String[] parts = splitPattern(pattern);
        for (int i = row, j = col, k = 0; i < len && j < len; i += plusRow, j += plusCol, k += 2) {
            if (board[i][j] != ' ') {
                parts[k] = "" + board[i][j];
            }
        }

        StringBuilder sb = new StringBuilder(pattern.length());
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].length() == 1) {
                sb.append(parts[i]);
            } else {
                sb.append('[');
                sb.append(parts[i]);
                sb.append(']');
            }
        }
        return sb.toString();
    }

    private String[] splitPattern(String pattern) {
        ArrayList<String> parts = new ArrayList<>(pattern.length());

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (c == '[') {
                parts.add(pattern.charAt(i + 1) + "" + pattern.charAt(i + 2));
                i += 3;
            } else {
                parts.add("" + c);
            }
        }

        return parts.toArray(new String[parts.size()]);
    }

    public static void main(String[] args) throws IOException {
        new CrossOut()._run();
    }
}
