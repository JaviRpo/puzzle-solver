package me.javirpo.puzzle.solver.game;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.javirpo.puzzle.solver.Unscrambleletters;
import me.javirpo.puzzle.solver.helper.BoardIterator;
import me.javirpo.puzzle.solver.helper.Line;
import me.javirpo.puzzle.solver.helper.Point;

public class Crossword extends Game {
    private Map<Line, String> cross = new LinkedHashMap<>();
    private Map<Line, String> crossRows = new LinkedHashMap<>();
    private Map<Line, String> crossCols = new LinkedHashMap<>();

    @Override
    protected void run() throws IOException {
        createBoard();
        showOptionsBoard();
        readRows();
        readLetters();

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
                    break;
            }
        }
        System.out.println("Finish ...");
        System.out.println();
        printBoard();
    }

    private void readLetters() {
        System.out.println();
        System.out.println("Reading Letters... ");

        BoardIterator iterator = new BoardIterator(boardNumbers, boardLetters, rows, cols);
        while (iterator.hasNext()) {
            Line line = iterator.next();

            System.out.print("Letters for (" + line.isHorizontal() + ") [" +
                line.getFrom().getRow() + "," + line.getFrom().getCol() + "][" +
                line.getTo().getRow() + "," + line.getTo().getCol() + "]: ");
            String letters = sc.nextLine();
            cross.put(line, letters);
            if (line.isHorizontal()) {
                crossRows.put(line, letters);
            } else {
                crossCols.put(line, letters);
            }
        }
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
        while (iterator.hasNext()) {
            Line line = iterator.next();

            int size = line.length() + 1;
            StringBuilder pattern = new StringBuilder();

            for (Point p : line) {
                checkLetter(line, p, pattern);
            }

            checkAgain |= search(line, size, pattern);
        }

        return checkAgain;
    }

    private void checkLetter(Line line, Point p, StringBuilder pattern) {
        if (boardLetters[p.getRow()][p.getCol()] == ' ') {
            Entry<Line, String> entryCrossLine = searchEntryCrossLine(line, p);
            if (entryCrossLine != null) {
                pattern.append('[');
                pattern.append(entryCrossLine.getValue());
                pattern.append(']');
            } else {
                pattern.append('.');
            }
        } else {
            pattern.append(boardLetters[p.getRow()][p.getCol()]);
        }
    }

    private Entry<Line, String> searchEntryCrossLine(Line line, Point p) {
        if (line.isHorizontal()) {
            for (Entry<Line, String> entryCrossLine : crossCols.entrySet()) {
                Line crossLine = entryCrossLine.getKey();
                if (p.getCol() == crossLine.getFrom().getCol() &&
                    p.getRow() >= crossLine.getFrom().getRow() && p.getRow() <= crossLine.getTo().getRow()) {
                    return entryCrossLine;
                }
            }
        } else {
            for (Entry<Line, String> entryCrossLine : crossRows.entrySet()) {
                Line crossLine = entryCrossLine.getKey();
                if (p.getRow() == crossLine.getFrom().getRow() &&
                    p.getCol() >= crossLine.getFrom().getCol() && p.getCol() <= crossLine.getTo().getCol()) {
                    return entryCrossLine;
                }
            }
        }
        return null;
    }

    private boolean search(Line line, int size, StringBuilder pattern) throws IOException {
        boolean checkAgain = false;

        String letterSearch = cross.get(line);
        Map<Integer, Collection<String>> map = Unscrambleletters.getWordsWithCache(letterSearch);
        Collection<String> words = Unscrambleletters.getAndFilter(map, size, pattern.toString());

        if (words.size() == 1) {
            checkAgain = solveLetters(line, size, words.iterator().next());
        } else if (words.size() > 1) {
            checkAgain = lookForSimilarities(line, size, words);
            if (!checkAgain) {
                System.out.println(line);
                System.out.println(words);
            }
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
    }

    public static void main(String[] args) throws IOException {
        new Crossword()._run();
    }
}
