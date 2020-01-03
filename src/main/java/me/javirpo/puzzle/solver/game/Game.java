package me.javirpo.puzzle.solver.game;

import java.io.IOException;
import java.util.Scanner;

public abstract class Game {
    protected Scanner sc;

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

    protected final void printBoard() {
        System.out.println();
        System.out.println(stringBoard());
    }

    protected final String stringBoard() {
        StringBuilder sb = new StringBuilder(rows * cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(boardLetters[i][j]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
