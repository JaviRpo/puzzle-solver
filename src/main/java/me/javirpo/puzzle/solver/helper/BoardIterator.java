package me.javirpo.puzzle.solver.helper;

import java.util.Iterator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class BoardIterator implements Iterator<Line> {
    @Setter
    private int minLength = 3;
    @Setter
    private int maxLength = Integer.MAX_VALUE;
    private boolean horizontal = true;
    @NonNull
    private int[][] boardNumbers;
    @NonNull
    private char[][] boardLetters;
    @NonNull
    private int rows;
    @NonNull
    private int cols;

    private Line nextLine;
    private int row = 0;
    private int col = -1;

    @Override
    public boolean hasNext() {
        if (nextLine == null) {
            calculateNextLine();
        }
        return nextLine != null;
    }

    @Override
    public Line next() {
        Line returnLine = nextLine;
        nextLine = null;
        return returnLine;
    }

    private void calculateNextLine() {
        if (horizontal) {
            calculateNextRow();
            if (nextLine == null) {
                horizontal = false;
                col = 0;
                row = -1;
            }
        }

        if (!horizontal) {
            calculateNextCol();
        }
    }

    private void calculateNextRow() {
        int indexStart = -1;
        while (indexStart == -1 && row < rows) {
            indexStart = getNextStart(row, col + 1, 0, 1);
            if (indexStart == -1) {
                row++;
                col = -1;
            } else {
                int indexEnd = getEndOfWord(row, indexStart, 0, 1);
                if (indexEnd != -1) {
                    int diffLetters = indexEnd - indexStart;
                    if (diffLetters >= minLength - 1 && diffLetters <= maxLength - 1) {
                        Point from = new Point(row, indexStart);
                        Point to = new Point(row, indexEnd);
                        nextLine = new Line(horizontal, from, to);
                    } else {
                        indexStart = -1;
                    }
                    col = indexEnd;
                } else {
                    row++;
                    col = -1;
                }
            }
        }
    }

    private void calculateNextCol() {
        int indexStart = -1;
        while (indexStart == -1 && col < cols) {
            indexStart = getNextStart(row + 1, col, 1, 0);
            if (indexStart == -1) {
                col++;
                row = -1;
            } else {
                int indexEnd = getEndOfWord(indexStart, col, 1, 0);
                if (indexEnd != -1) {
                    int diffLetters = indexEnd - indexStart;
                    if (diffLetters >= minLength - 1 && diffLetters <= maxLength - 1) {
                        Point from = new Point(indexStart, col);
                        Point to = new Point(indexEnd, col);
                        nextLine = new Line(horizontal, from, to);
                    } else {
                        indexStart = -1;
                    }
                    row = indexEnd;
                } else {
                    col++;
                    row = -1;
                }
            }
        }
    }

    private int getNextStart(int row, int col, int plusRow, int plusCol) {
        boolean hasSpace = false;
        int index = -1;
        for (; row < rows && col < cols; row += plusRow, col += plusCol) {
            if (boardNumbers[row][col] != 0) {
                if (index == -1) {
                    index = plusRow != 0 ? row : col;
                }

                hasSpace |= boardLetters[row][col] == ' ';
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
}
