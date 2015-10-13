package model.stack.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deckOfCards.impl.DeckOfCards;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code
 */
public class StreetStackTest {

    private StreetStack ss;

    @Before
    public void setUp() throws Exception {
        DeckOfCards cardList = new DeckOfCards();
        cardList.add(new Card(CardValue.ONE, CardColor.BLUE));
        cardList.add(new Card(CardValue.TWO, CardColor.BLUE));
        cardList.add(new Card(CardValue.THREE, CardColor.BLUE));
        ss = new StreetStack(cardList);
    }

    @Test
    public void ssTest() {
        assertEquals(ss.getList().size(), 3);
        assertEquals(ss.getHighestCardNumber(), CardValue.THREE);
        assertEquals(ss.getLowestCardNumber(), CardValue.ONE);
        assertTrue(ss.checkCardMatching(new Card(CardValue.FOUR, CardColor.GREEN)));
        assertTrue(ss.checkCardMatching(new Card(CardValue.ZERO, CardColor.GREEN)));
        assertFalse(ss.checkCardMatching(new Card(CardValue.NINE, CardColor.GREEN)));
    }

    @Test
    public void addCardToStackTest() {
        assertEquals(ss.getList().size(), 3);
        assertEquals(ss.getHighestCardNumber(), CardValue.THREE);
        assertEquals(ss.getLowestCardNumber(), CardValue.ONE);
        ss.addCardToStack(new Card(CardValue.FOUR, CardColor.GREEN));
        assertEquals(ss.getList().size(), 4);
        assertEquals(ss.getHighestCardNumber(), CardValue.FOUR);
        assertEquals(ss.getLowestCardNumber(), CardValue.ONE);
        ss.addCardToStack(new Card(CardValue.ZERO, CardColor.GREEN));
        assertEquals(ss.getList().size(), 5);
        assertEquals(ss.getHighestCardNumber(), CardValue.FOUR);
        assertEquals(ss.getLowestCardNumber(), CardValue.ZERO);
    }
}