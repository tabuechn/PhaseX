package model.stack.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deckOfCards.impl.DeckOfCards;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code
 */
public class ColorStackTest {

    private ColorStack testee;

    @Before
    public void setUp() throws Exception {
        DeckOfCards cardList = new DeckOfCards();
        cardList.add(new Card(CardValue.FOUR, CardColor.BLUE));
        cardList.add(new Card(CardValue.SIX, CardColor.BLUE));
        cardList.add(new Card(CardValue.ONE, CardColor.BLUE));
        testee = new ColorStack(cardList);
    }

    @Test
    public void csTest() {
        assertTrue(testee.getList().size() == 3);
        assertTrue(testee.getStackColor() == CardColor.BLUE);
        testee.addCardToStack(new Card(CardValue.THREE, CardColor.BLUE));
        assertTrue(testee.getList().size() == 4);
        assertTrue(testee.checkCardMatching(new Card(CardValue.EIGHT, CardColor.BLUE)));
        assertFalse(testee.checkCardMatching(new Card(CardValue.EIGHT, CardColor.GREEN)));
    }


}