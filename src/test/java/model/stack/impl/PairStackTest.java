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
 * Created by Tarek on 24.09.2015. Be gratefull for this superior Code
 */
public class PairStackTest {

    public static final CardValue CARD_NUMBER_FOUR = CardValue.FOUR;

    PairStack testee;

    @Before
    public void setUp() throws Exception {
        DeckOfCards cardList = new DeckOfCards();
        cardList.add(new Card(CARD_NUMBER_FOUR, CardColor.BLUE));
        cardList.add(new Card(CARD_NUMBER_FOUR, CardColor.GREEN));
        cardList.add(new Card(CARD_NUMBER_FOUR, CardColor.RED));
        testee = new PairStack(cardList);
    }

    @Test
    public void csTest() {
        assertTrue(testee.getStackNumber().equals(CARD_NUMBER_FOUR));
        testee.addCardToStack(new Card(CARD_NUMBER_FOUR, CardColor.BLUE));
        assertTrue(testee.getList().size() == 4);
        assertTrue(testee.checkCardMatching(new Card(CARD_NUMBER_FOUR, CardColor.BLUE)));
        assertFalse(testee.checkCardMatching(new Card(CardValue.EIGHT, CardColor.GREEN)));
    }
}