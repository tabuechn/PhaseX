package model.stack.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deck.impl.DeckOfCards;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code
 */
public class PairStackTest {

    private static final CardValue CARD_NUMBER_FOUR = CardValue.FOUR;

    private PairStack testee;

    private DeckOfCards cardList;

    @Before
    public void setUp() throws Exception {
        cardList = new DeckOfCards();
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

    @Test
    public void setAndGetIDTest() {
        String yolo = "YOLO";
        testee.setId(yolo);
        assertEquals(yolo,testee.getId());
    }

    @Test
    public void getStackType() {
        assertEquals(testee.getStackType(),StackType.PAIR);
    }

    @Test
    public void hashCodeTest() {
        assertNotNull(testee.hashCode());
    }

    @Test
    public void equalsWithNullShouldReturnFalse() {
        assertFalse(testee.equals(null));
    }

    @Test
    public void equalsWithSelfShouldReturnTrue() {
        assertTrue(testee.equals(testee));
    }

    @Test
    public void equalsWithOtherObjectShouldReturnFalse() {
        DeckOfCards deck = new DeckOfCards();
        deck.add(new Card(CARD_NUMBER_FOUR,CardColor.BLUE));
        assertFalse(testee.equals(new PairStack(deck)));
    }
}