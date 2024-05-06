package com.example.cst338project2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CheckGuessUnitTest {
    @Test
    public void checkGuess() {
        assertEquals(0,numberGuessingGameActivity.checkGuess("1",1));
    }
    @Test
    public void checkGuess1() {
        assertEquals(1,numberGuessingGameActivity.checkGuess("1",2));
    }
    @Test
    public void checkGuess2() {
        assertEquals(2,numberGuessingGameActivity.checkGuess("2",1));
    }


}

