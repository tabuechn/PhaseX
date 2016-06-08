package model.card.impl;

import model.card.CardColor;
import model.card.CardValue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code
 */
public class CardValueComparatorTest {

    private CardValueComparator testee;
    private Card compare4;
    private Card compare7;

    @Before
    public void setUp() throws Exception {
        testee = new CardValueComparator();
        compare4 = new Card(CardValue.FOUR, CardColor.BLUE);
        compare7 = new Card(CardValue.SEVEN, CardColor.BLUE);
    }

    @Test
    public void compareTest() {
        assertTrue(testee.compare(compare4, compare7) != 0);
        assertTrue(testee.compare(compare4, compare7) < 0);
        assertTrue(testee.compare(compare7, compare4) != 0);
        assertTrue(testee.compare(compare7, compare4) > 0);
    }

    @Test
    public void getValueByStringTest() {
        assertEquals(CardValue.ZERO,CardValue.getValueByString("ZERO"));
        assertEquals(CardValue.ONE,CardValue.getValueByString("ONE"));
        assertEquals(CardValue.TWO,CardValue.getValueByString("TWO"));
        assertEquals(CardValue.THREE,CardValue.getValueByString("THREE"));
        assertEquals(CardValue.FOUR,CardValue.getValueByString("FOUR"));
        assertEquals(CardValue.FIVE,CardValue.getValueByString("FIVE"));
        assertEquals(CardValue.SIX,CardValue.getValueByString("SIX"));
        assertEquals(CardValue.SEVEN,CardValue.getValueByString("SEVEN"));
        assertEquals(CardValue.EIGHT,CardValue.getValueByString("EIGHT"));
        assertEquals(CardValue.NINE,CardValue.getValueByString("NINE"));
        assertEquals(CardValue.TEN,CardValue.getValueByString("TEN"));
        assertEquals(CardValue.ELEVEN,CardValue.getValueByString("ELEVEN"));
        assertEquals(CardValue.TWELVE,CardValue.getValueByString("TWELVE"));
    }
}