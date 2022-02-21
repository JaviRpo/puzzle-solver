package me.javirpo.puzzle.solver.game;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class WordyTest extends GameTest {
    @Test
    public void test() throws IOException {
        Wordy puzzle = new Wordy();

        ArrayList<String> inputLines = new ArrayList<>();

        runTest(puzzle, inputLines);
        validate(puzzle, "     ");

        inputLines.add("-2");
        inputLines.add("angry");
        inputLines.add("xxxpx");
        runTest(puzzle, inputLines);
        validate(puzzle, "     ");

        inputLines.add("-2");
        inputLines.add("great");
        inputLines.add("xcxxx");
        runTest(puzzle, inputLines);
        validate(puzzle, " r   ");

        inputLines.add("-2");
        inputLines.add("peace");
        inputLines.add("pxxpx");
        runTest(puzzle, inputLines);
        validate(puzzle, " r   ");

        inputLines.add("-2");
        inputLines.add("weird");
        inputLines.add("xxcpx");
        runTest(puzzle, inputLines);
        validate(puzzle, " ri  ");

        inputLines.add("-2");
        inputLines.add("music");
        inputLines.add("PxxPp");
        runTest(puzzle, inputLines, true);
        validate(puzzle, "crimp");
    }

    private void runTest(Wordy puzzle, ArrayList<String> inputLines) throws IOException {
        runTest(puzzle, inputLines, false);
    }

    private void runTest(Wordy puzzle, ArrayList<String> inputLines, boolean finish) throws IOException {
        try {
            configureInputStream(inputLines);
            puzzle._run();
            if (!finish) {
                Assert.fail("Exception not thrown");
            }
        } catch (NoSuchElementException e) {
            if (finish) {
                Assert.fail("Exception not expected");
            }
        }
    }

    private void validate(Wordy puzzle, String expectedWord) {
        String output = puzzle.stringBoard();
        String[] currentBoard = StringUtils.split(output, '\n');

        String[] expectedBoard = {
            "│" + expectedWord + "│"
        };
        Assert.assertArrayEquals(expectedBoard, currentBoard);
    }
}
