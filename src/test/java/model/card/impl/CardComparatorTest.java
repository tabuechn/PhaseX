package model.card.impl;

import model.card.CardColor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Tarek on 24.09.2015. Be gratefull for this superior Code
 */
public class CardComparatorTest {

    CardComparator cc;
    Card compare4;
    Card compare7;

    @Before
    public void setUp() throws Exception {
        cc = new CardComparator();
        compare4 = new Card(4, CardColor.BLUE);
        compare7 = new Card(7,CardColor.BLUE);
    }

    @Test
    public void compareTest() {
        assertTrue(cc.compare(compare4,compare7) != 0);
        assertTrue(cc.compare(compare4,compare7) < 0);
        assertTrue(cc.compare(compare7,compare4) != 0);
        assertTrue(cc.compare(compare7,compare4) > 0);
    }
}