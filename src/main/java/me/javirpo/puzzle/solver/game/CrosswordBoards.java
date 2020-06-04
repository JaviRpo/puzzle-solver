package me.javirpo.puzzle.solver.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CrosswordBoards {
    private static final Map<String, List<int[][]>> BOARDS = new LinkedHashMap<>();
    static {
        board_9_7_opt1();

        board_9_9_opt1();
        board_9_9_opt2();
        board_9_9_opt3();
        board_9_9_opt4();

        board_11_11_opt1();
        board_11_11_opt2();
        board_11_11_opt3();
        board_11_11_opt4();
        board_11_11_opt5();
        board_11_11_opt6();
        board_11_11_opt7();
        board_11_11_opt8();
        board_11_11_opt9();
        board_11_11_opt10();
        board_11_11_opt11();

        board_13_13_opt1();
        board_13_13_opt2();
        board_13_13_opt3();
        board_13_13_opt4();
    }

    public static List<int[][]> boards(int rows, int cols) {
        return BOARDS.getOrDefault(getKey(rows, cols), Collections.emptyList());
    }

    private static String getKey(int rows, int cols) {
        return rows + "-" + cols;
    }

    private static void put(int rows, int cols, int[][] board) {
        BOARDS.compute(getKey(rows, cols), (k, v) -> {
            if (v == null) {
                v = new ArrayList<>();
            }
            v.add(board);
            return v;
        });
    }

    private static void board_9_7_opt1() {
        int rows = 9, cols = 7;
        int[][] board = new int[rows][];
        board[0] = new int[] { 0, 0, 0, 1, 0, 0, 0 };
        board[1] = new int[] { 0, 0, 0, 1, 0, 0, 0 };
        board[2] = new int[] { 0, 1, 1, 1, 1, 1, 0 };
        board[3] = new int[] { 0, 1, 0, 1, 0, 1, 0 };
        board[4] = new int[] { 1, 1, 1, 1, 1, 1, 1 };
        board[5] = new int[] { 0, 1, 0, 1, 0, 1, 0 };
        board[6] = new int[] { 0, 1, 1, 1, 1, 1, 0 };
        board[7] = new int[] { 0, 0, 0, 1, 0, 0, 0 };
        board[8] = new int[] { 0, 0, 0, 1, 0, 0, 0 };

        put(rows, cols, board);
    }

    private static void board_9_9_opt1() {
        int rows = 9, cols = 9;
        int[][] board = new int[rows][];
        board[0] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };
        board[1] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };
        board[2] = new int[] { 0, 0, 1, 1, 1, 1, 0, 0, 0 };
        board[3] = new int[] { 0, 0, 1, 0, 1, 0, 1, 0, 0 };
        board[4] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        board[5] = new int[] { 0, 0, 1, 0, 1, 0, 1, 0, 0 };
        board[6] = new int[] { 0, 0, 0, 1, 1, 1, 1, 0, 0 };
        board[7] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };
        board[8] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };

        put(rows, cols, board);
    }

    private static void board_9_9_opt2() {
        int rows = 9, cols = 9;
        int[][] board = new int[rows][];
        board[0] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };
        board[1] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };
        board[2] = new int[] { 0, 0, 1, 1, 1, 1, 1, 0, 0 };
        board[3] = new int[] { 0, 0, 1, 0, 1, 0, 1, 0, 0 };
        board[4] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        board[5] = new int[] { 0, 0, 1, 0, 1, 0, 1, 0, 0 };
        board[6] = new int[] { 0, 0, 1, 1, 1, 1, 1, 0, 0 };
        board[7] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };
        board[8] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };

        put(rows, cols, board);
    }

    private static void board_9_9_opt3() {
        int rows = 9, cols = 9;
        int[][] board = new int[rows][];
        board[0] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };
        board[1] = new int[] { 0, 0, 0, 1, 1, 1, 0, 0, 0 };
        board[2] = new int[] { 0, 0, 1, 1, 1, 1, 1, 0, 0 };
        board[3] = new int[] { 0, 1, 1, 1, 0, 1, 0, 1, 0 };
        board[4] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[5] = new int[] { 0, 1, 0, 1, 0, 1, 1, 1, 0 };
        board[6] = new int[] { 0, 0, 1, 1, 1, 1, 1, 0, 0 };
        board[7] = new int[] { 0, 0, 0, 1, 1, 1, 0, 0, 0 };
        board[8] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };

        put(rows, cols, board);
    }

    private static void board_9_9_opt4() {
        int rows = 9, cols = 9;
        int[][] board = new int[rows][];
        board[0] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };
        board[1] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };
        board[2] = new int[] { 0, 0, 1, 1, 1, 1, 1, 0, 0 };
        board[3] = new int[] { 0, 0, 1, 0, 1, 0, 1, 0, 0 };
        board[4] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[5] = new int[] { 0, 0, 1, 0, 1, 0, 1, 0, 0 };
        board[6] = new int[] { 0, 0, 1, 1, 1, 1, 1, 0, 0 };
        board[7] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };
        board[8] = new int[] { 0, 0, 0, 0, 1, 0, 0, 0, 0 };

        put(rows, cols, board);
    }

    private static void board_11_11_opt1() {
        int rows = 11, cols = 11;
        int[][] board = new int[rows][];
        board[0] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[1] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[2] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[3] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[4] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1 };
        board[5] = new int[] { 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1 };
        board[6] = new int[] { 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[7] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[8] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[9] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[10] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };

        put(rows, cols, board);
    }

    private static void board_11_11_opt2() {
        int rows = 11, cols = 11;
        int[][] board = new int[rows][];
        board[0] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[1] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[2] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[3] = new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1 };
        board[4] = new int[] { 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[5] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };
        board[6] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0 };
        board[7] = new int[] { 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 };
        board[8] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[9] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[10] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };

        put(rows, cols, board);
    }

    private static void board_11_11_opt3() {
        int rows = 11, cols = 11;
        int[][] board = new int[rows][];
        board[0] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[1] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0 };
        board[2] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[3] = new int[] { 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[4] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0 };
        board[5] = new int[] { 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1 };
        board[6] = new int[] { 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[7] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0 };
        board[8] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[9] = new int[] { 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[10] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };

        put(rows, cols, board);
    }

    private static void board_11_11_opt4() {
        int rows = 11, cols = 11;
        int[][] board = new int[rows][];
        board[0] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 };
        board[1] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1 };
        board[2] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 };
        board[3] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1 };
        board[4] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[5] = new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
        board[6] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[7] = new int[] { 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[8] = new int[] { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        board[9] = new int[] { 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[10] = new int[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

        put(rows, cols, board);
    }

    private static void board_11_11_opt5() {
        int rows = 11, cols = 11;
        int[][] board = new int[rows][];
        board[0] = new int[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        board[1] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[2] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[3] = new int[] { 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[4] = new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0 };
        board[5] = new int[] { 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0 };
        board[6] = new int[] { 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1 };
        board[7] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0 };
        board[8] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[9] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[10] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 };

        put(rows, cols, board);
    }

    private static void board_11_11_opt6() {
        int rows = 11, cols = 11;
        int[][] board = new int[rows][];
        board[0] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[1] = new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1 };
        board[2] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[3] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[4] = new int[] { 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1 };
        board[5] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        board[6] = new int[] { 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0 };
        board[7] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[8] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[9] = new int[] { 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 };
        board[10] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };

        put(rows, cols, board);
    }

    private static void board_11_11_opt7() {
        int rows = 11, cols = 11;
        int[][] board = new int[rows][];
        board[0] = new int[] { 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 };
        board[1] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0 };
        board[2] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };
        board[3] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0 };
        board[4] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        board[5] = new int[] { 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1 };
        board[6] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        board[7] = new int[] { 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[8] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };
        board[9] = new int[] { 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[10] = new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1 };

        put(rows, cols, board);
    }

    private static void board_11_11_opt8() {
        int rows = 11, cols = 11;
        int[][] board = new int[rows][];
        board[0] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        board[1] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[2] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[3] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[4] = new int[] { 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0 };
        board[5] = new int[] { 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0 };
        board[6] = new int[] { 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0 };
        board[7] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[8] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[9] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[10] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

        put(rows, cols, board);
    }

    private static void board_11_11_opt9() {
        int rows = 11, cols = 11;
        int[][] board = new int[rows][];
        board[0] = new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1 };
        board[1] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1 };
        board[2] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };
        board[3] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1 };
        board[4] = new int[] { 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1 };
        board[5] = new int[] { 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0 };
        board[6] = new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1 };
        board[7] = new int[] { 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[8] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };
        board[9] = new int[] { 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[10] = new int[] { 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 };

        put(rows, cols, board);
    }

    private static void board_11_11_opt10() {
        int rows = 11, cols = 11;
        int[][] board = new int[rows][];
        board[0] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };
        board[1] = new int[] { 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0 };
        board[2] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[3] = new int[] { 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0 };
        board[4] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0 };
        board[5] = new int[] { 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0 };
        board[6] = new int[] { 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[7] = new int[] { 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0 };
        board[8] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[9] = new int[] { 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0 };
        board[10] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };

        put(rows, cols, board);
    }

    private static void board_11_11_opt11() {
        int rows = 11, cols = 11;
        int[][] board = new int[rows][];
        board[0] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };
        board[1] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0 };
        board[2] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        board[3] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0 };
        board[4] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };
        board[5] = new int[] { 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1 };
        board[6] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };
        board[7] = new int[] { 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[8] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        board[9] = new int[] { 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[10] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };

        put(rows, cols, board);
    }

    private static void board_13_13_opt1() {
        int rows = 13, cols = 13;
        int[][] board = new int[rows][];
        board[0] = new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };
        board[1] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[2] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 };
        board[3] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[4] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[5] = new int[] { 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0 };
        board[6] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[7] = new int[] { 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1 };
        board[8] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 };
        board[9] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[10] = new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };
        board[11] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[12] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 };

        put(rows, cols, board);
    }

    private static void board_13_13_opt2() {
        int rows = 13, cols = 13;
        int[][] board = new int[rows][];
        board[0] = new int[] { 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1 };
        board[1] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[2] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[3] = new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };
        board[4] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[5] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0 };
        board[6] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[7] = new int[] { 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[8] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[9] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 };
        board[10] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[11] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[12] = new int[] { 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1 };

        put(rows, cols, board);
    }

    private static void board_13_13_opt3() {
        int rows = 13, cols = 13;
        int[][] board = new int[rows][];
        board[0] = new int[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 };
        board[1] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[2] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[3] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[4] = new int[] { 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 };
        board[5] = new int[] { 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0 };
        board[6] = new int[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 };
        board[7] = new int[] { 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0 };
        board[8] = new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0 };
        board[9] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[10] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 };
        board[11] = new int[] { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 };
        board[12] = new int[] { 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 };

        put(rows, cols, board);
    }

    private static void board_13_13_opt4() {
        int rows = 13, cols = 13;
        int[][] board = new int[rows][];
        board[0] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };
        board[1] = new int[] { 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1 };
        board[2] = new int[] { 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 };
        board[3] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[4] = new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 };
        board[5] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1 };
        board[6] = new int[] { 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0 };
        board[7] = new int[] { 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[8] = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 };
        board[9] = new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
        board[10] = new int[] { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 };
        board[11] = new int[] { 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1 };
        board[12] = new int[] { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 };

        put(rows, cols, board);
    }
}
