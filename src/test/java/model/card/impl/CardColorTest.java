package model.card.impl;

import model.card.CardColor;
import org.junit.Before;
import org.junit.Test;

import javax.smartcardio.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/**
 * Created by tabuechn on 08.06.2016.
 */
public class CardColorTest {

    @Test
    public void invalidNumberShouldReturnNull() {
        assertNull(CardColor.byOrdinal(6));
    }

    @Test
    public void getColorByStringTest() {
        assertEquals(CardColor.YELLOW,CardColor.getColorByString("YELLOW"));
        assertEquals(CardColor.RED,CardColor.getColorByString("RED"));
        assertEquals(CardColor.BLUE,CardColor.getColorByString("BLUE"));
        assertEquals(CardColor.GREEN,CardColor.getColorByString("GREEN"));
        assertEquals(CardColor.BACK,CardColor.getColorByString("BACK"));
        assertEquals(CardColor.BLANK,CardColor.getColorByString("BLANK"));
    }
}
