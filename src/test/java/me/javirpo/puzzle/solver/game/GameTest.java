package me.javirpo.puzzle.solver.game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.powermock.reflect.internal.WhiteboxImpl;

public class GameTest {
    private static InputStream SYSTEM_IN;

    @BeforeClass
    public static void beforeClass() {
        SYSTEM_IN = System.in;
    }

    @AfterClass
    public static void afterClass() {
        WhiteboxImpl.setInternalState(System.class, "in", SYSTEM_IN);
    }

    protected void configureInputStream(Collection<String> inputLines) {
        String input = StringUtils.join(inputLines, '\n');
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        WhiteboxImpl.setInternalState(System.class, "in", inputStream);
    }

    @After
    public void after() throws IOException {
        Assert.assertEquals("There is bytes available in the InputStream.", 0, System.in.available());
    }
}
