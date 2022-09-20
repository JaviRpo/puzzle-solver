package me.javirpo.challenge;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Map;

public class MinesWeeper {
    public static void main(String[] args) {

        char[][] output = minesweeper(new String[]{"XOO", "OOO", "XXO"});
        print(output);

        output = minesweeper(new String[]{"XOOXXXOO", "OOOOXOXX", "XXOXXOOO", "OXOOOXXX", "OOXXXXOX", "XOXXXOXO", "OOOXOXOX", "XOXXOXOX"});
        print(output);

        output = minesweeper(new String[]{"OOOXXXOXX", "XXXXXXOXX", "XOOXXXXXX", "OOXXOXOXX", "XXXXXXXXX"});
        print(output);
    }
    private static void print(char[][] output) {
        for (char[] out: output) {
            for (char o:out) {
                if(o == 'X') {
                    System.out.print(o);
                } else {
                    System.out.print((int)o);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static char[][] minesweeper(String[] input) {
        int M = input.length;
        int N = input[0].length();
        char[][] output = new char[M][N];

        for (int i = 0; i < input.length; i++) {
            String line = input[i];
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == 'X') {
                    output[i][j] = 'X';
                } else {
                    output[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                if (output[i][j] != 'X') {

                    if (i > 0 && j > 0 && output[i-1][j-1] == 'X') {
                        output[i][j]++;
                    }
                    if (i > 0 && output[i-1][j] == 'X') {
                        output[i][j]++;
                    }
                    if (i > 0 && j < output[i].length - 1 && output[i-1][j+1] == 'X') {
                        output[i][j]++;
                    }


                    if (j > 0 && output[i][j-1] == 'X') {
                        output[i][j]++;
                    }
                    if (j < output[i].length - 1 && output[i][j+1] == 'X') {
                        output[i][j]++;
                    }


                    if (i < output.length - 1 && j > 0 && output[i+1][j-1] == 'X') {
                        output[i][j]++;
                    }
                    if (i < output.length - 1 && output[i+1][j] == 'X') {
                        output[i][j]++;
                    }
                    if (i < output.length - 1 && j < output[i].length - 1 && output[i+1][j+1] == 'X') {
                        output[i][j]++;
                    }

                }
            }
        }

        return output;
    }
}
