package model.stack.impl;

import model.card.CardColor;
import model.card.impl.Card;
import model.deckOfCards.impl.DeckOfCards;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tarek on 24.09.2015. Be gratefull for this superior Code
 */
public class StreetStackTest {

    StreetStack ss;

    @Before
    public void setUp() throws Exception {
        DeckOfCards cardList = new DeckOfCards();
        cardList.add(new Card(1, CardColor.BLUE));
        cardList.add(new Card(2,CardColor.BLUE));
        cardList.add(new Card(3,CardColor.BLUE));
        ss = new StreetStack(cardList);
    }

    @Test
    public void ssTest() {
        assertEquals(ss.getList().size(),3);
        assertEquals(ss.getHighestCardNumber(), 3);
        assertEquals(ss.getLowestCardNumber(), 1);
        assertTrue(ss.checkCardMatching(new Card(4, CardColor.GREEN)));
        assertTrue(ss.checkCardMatching(new Card(0, CardColor.GREEN)));
        assertFalse(ss.checkCardMatching(new Card(9, CardColor.GREEN)));
    }
    
    @Test
    public void addCardToStackTest() {
        assertEquals(ss.getList().size(),3);
        assertEquals(ss.getHighestCardNumber(), 3);
        assertEquals(ss.getLowestCardNumber(), 1);
        ss.addCardToStack(new Card(4, CardColor.GREEN));
        assertEquals(ss.getList().size(), 4);
        assertEquals(ss.getHighestCardNumber(), 4);
        assertEquals(ss.getLowestCardNumber(), 1);
        ss.addCardToStack(new Card(0, CardColor.GREEN));
        assertEquals(ss.getList().size(), 5);
        assertEquals(ss.getHighestCardNumber(), 4);
        assertEquals(ss.getLowestCardNumber(), 0);
    }
}