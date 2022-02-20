package me.javirpo.puzzle.solver.game;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class CrossOutTest extends GameTest {
    @Test
    public void test() throws IOException {
        int rows = 5;

        ArrayList<String> inputLines = new ArrayList<>();
        // Board
        inputLines.add("" + rows);
        inputLines.add("5");
        inputLines.add("2,3,1,6,2");
        inputLines.add("0,0,8,0,1");
        inputLines.add("5,2,6,6,10");
        inputLines.add("3,0,6,0,0");
        inputLines.add("9,5,4,7,2");

        // Missing Letters
        inputLines.add("aglmotyu");

        // Some Letters
        inputLines.add("-2");
        inputLines.add("2,e");
        inputLines.add("-2");
        inputLines.add("5,h");
        inputLines.add("-2");
        inputLines.add("6,l");

        // Solve
        inputLines.add("-1");
        inputLines.add("-1");
        inputLines.add("-1");

        configureInputStream(inputLines);

        Codeword puzzle = new Codeword();
        puzzle._run();

        String output = puzzle.stringBoard();
        String[] currentBoard = StringUtils.split(output, '\n');

        String[] expectedBoard = {
                "eagle",
                "■■u■g",
                "hello",
                "a■l■■",
                "thyme"
        };
        Assert.assertArrayEquals(expectedBoard, currentBoard);
    }
}
