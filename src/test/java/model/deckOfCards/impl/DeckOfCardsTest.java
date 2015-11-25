package model.deckOfCards.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.deckOfCards.IDeckOfCards;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * If everything works right this class was
 * created by Konraifen88 on 24.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class DeckOfCardsTest {

    private static final ICard CARD_1 = new Card(CardValue.ONE, CardColor.BLUE);
    private static final ICard CARD_2 = new Card(CardValue.TWO, CardColor.GREEN);
    private static final ICard CARD_3 = new Card(CardValue.THREE, CardColor.YELLOW);
    private static final ICard CARD_4 = new Card(CardValue.FOUR, CardColor.RED);
    private static final String JSON = "[{\"number\":\"ONE\",\"color\":\"BLUE\"}," +
            "{\"number\":\"TWO\",\"color\":\"GREEN\"}," +
            "{\"number\":\"THREE\",\"color\":\"YELLOW\"}," +
            "{\"number\":\"FOUR\",\"color\":\"RED\"}]";
    private IDeckOfCards testee;

    @Before
    public void setUp() throws Exception {
        createAFilledDeckOfCards();
    }

    @Test
    public void removeCardFromDeck() {
        assertTrue(testee.contains(CARD_1));
        testee.remove(CARD_1);
        assertTrue(!testee.contains(CARD_1));
    }

    @Test
    public void checkIfFirstCardIsRemovedOnCallingRemoveFirst() {
        testee.removeFirst();
        assertFalse(testee.contains(CARD_1));
    }

    @Test
    public void checkIfLastCardIsRemovedOnCallingRemoveLast() {
        testee.removeLast();
        assertFalse(testee.contains(CARD_4));
    }

    @Test
    public void onlyOneCardShouldBeRemovedIfMoreThanOneOfTheSameCardIsInTheDeck() {
        testee.add(CARD_1);
        testee.remove(CARD_1);
        assertTrue(testee.contains(CARD_1));
    }

    @Test
    public void aJsonFormattedStringShouldBeReturnedForGetJson() {
        String tmp = testee.getJSon();
        assertEquals(JSON, tmp);
    }

    @Test
    public void emptyConstructorCanBeCalled(){
        testee = new DeckOfCards();
        assertTrue(testee instanceof IDeckOfCards);
    }

    private void createAFilledDeckOfCards() {
        List<ICard> cards = new LinkedList<>();
        cards.add(CARD_1);
        cards.add(CARD_2);
        cards.add(CARD_3);
        cards.add(CARD_4);
        testee = new DeckOfCards(cards);
    }

}