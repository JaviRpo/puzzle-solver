package me.javirpo.puzzle.solver.game;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class WordSlideTest extends GameTest {
    @Test
    public void test() throws IOException {
        int rows = 6;

        ArrayList<String> inputLines = new ArrayList<>();
        // Board
        inputLines.add("" + rows);
        inputLines.add("6");
        inputLines.add("1,1,1,1,1,0");
        inputLines.add("1,0,1,0,0,0");
        inputLines.add("1,1,1,1,1,1");
        inputLines.add("0,1,0,0,1,0");
        inputLines.add("0,1,0,0,1,0");
        inputLines.add("0,1,1,1,1,1");

        // Row Letters
        inputLines.add("pa");
        inputLines.add("di");
        inputLines.add("cd");
        inputLines.add("od");
        inputLines.add("ia");
        inputLines.add("nr");

        // Col Letters
        inputLines.add("");
        inputLines.add("et");
        inputLines.add("ut");
        inputLines.add("al");
        inputLines.add("ym");
        inputLines.add("ps");

        // Solve
        inputLines.add("-1");

        configureInputStream(inputLines);

        WordSlide puzzle = new WordSlide();
        puzzle._run();

        String output = puzzle.stringBoard();
        String[] currentBoard = StringUtils.split(output, '\n');

        String[] expectedBoard = {
                "aptly ",
                "d i   ",
                "decamp",
                " d  o ",
                " i  a ",
                " turns"
        };
        Assert.assertArrayEquals(expectedBoard, currentBoard);
    }
}
