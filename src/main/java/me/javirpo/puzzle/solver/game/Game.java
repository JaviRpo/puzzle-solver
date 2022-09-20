package me.javirpo.puzzle.solver.game;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public abstract class Game {
    protected Scanner sc;
    private boolean defaultBoardPicked;

    protected int rows;
    protected int cols;
    protected char[][] boardLetters;
    protected int[][] boardNumbers;

    public final void _run() throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            sc = scanner;

            run();
        }
    }

    protected abstract void run() throws IOException;

    protected void createBoard() {
        System.out.println();
        System.out.print("Rows: ");
        String strRows = sc.nextLine();
        System.out.print("Cols: ");
        String strCols = sc.nextLine();

        rows = Integer.parseInt(strRows);
        cols = Integer.parseInt(strCols);
        boardLetters = new char[rows][cols];
        boardNumbers = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boardLetters[i][j] = ' ';
            }
        }
    }

    protected void showOptionsBoard() {
        List<int[][]> boards = CrosswordBoards.boards(rows, cols);
        if (!boards.isEmpty()) {
            for (int i = 0; i < boards.size(); i++) {
                System.out.println();
                System.out.println("BOARD #" + i);
                int[][] board = boards.get(i);
                System.out.println(stringBoardBlock(board, new char[rows][cols]));
            }
            System.out.print("(-1 Exit) Board #: ");
            String boardNumber = sc.nextLine();
            if (!"-1".equals(boardNumber)) {
                int number = Integer.parseInt(boardNumber);
                boardNumbers = boards.get(number);
                defaultBoardPicked = true;
            }
        }
    }

    protected boolean isDone() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (boardNumbers[i][j] != 0 && boardLetters[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    protected void readRows() {
        if (defaultBoardPicked) {
            return;
        }

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
            }
        }
    }

    protected void printBoard() {
        Integer my[] = {1, 2, 3};

        System.out.println();
        //System.out.println(⎡' + StringUtils.repeat('‾', cols) + '⎤');
        System.out.println(' ' + StringUtils.repeat('⎽', cols) + ' ');
        System.out.println(stringBoard());
        //System.out.println('⎣' + StringUtils.repeat('⎽', cols) + '⎦');
        System.out.println(' ' + StringUtils.repeat('‾', cols) + ' ');
    }

    protected final String stringBoard() {
        return stringBoardBlock();
    }

    protected final String stringBoardBlock() {
        return stringBoard('■');
    }

    protected final String stringBoardSpace() {
        return stringBoard(' ');
    }

    protected final String stringBoard(char emptyChar) {
        return stringBoard(emptyChar, boardNumbers, boardLetters);
    }

    protected final String stringBoard(char emptyChar, int[][] numbers, char[][] letters) {
        StringBuilder sb = new StringBuilder((5 + numbers.length) * numbers.length);
        for (int i = 0; i < numbers.length; i++) {
            sb.append("│");
            for (int j = 0; j < numbers[i].length; j++) {
                if (numbers[i][j] == 0) {
                    sb.append(emptyChar);
                //} else if (letters[i][j] == ' ') {
                //    sb.append('⏥');
                } else {
                    sb.append(letters[i][j]);
                }
            }
            sb.append("│");
            if (i + 1 < numbers.length) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    protected final String stringBoardBlock(int[][] numbers, char[][] letters) {
        return stringBoard('■', numbers, letters);
    }
}
