package me.javirpo.puzzle.solver.game;

import java.io.IOException;

public class QuoteSlide extends WordSlide {
    public QuoteSlide() {
        setOmitColsProcessing(true);
        setMinLength(2);
    }

    public static void main(String[] args) throws IOException {
        new QuoteSlide()._run();
    }
}
