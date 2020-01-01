package me.javirpo.puzzle.solver.game;

import java.io.IOException;
import java.util.Scanner;

public abstract class Game {
    protected Scanner sc;

    protected int rows;
    protected int cols;
    protected char[][] board;

    public void _run() throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            sc = scanner;

            run();
        }
    }

    protected abstract void run() throws IOException;

    protected void printBoard() {
        System.out.println();
        System.out.println(stringBoard());
    }

    protected String stringBoard() {
        StringBuilder sb = new StringBuilder(rows * cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
