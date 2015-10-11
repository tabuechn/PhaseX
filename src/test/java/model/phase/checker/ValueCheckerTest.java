package model.phase.checker;

import model.card.CardColor;
import model.card.impl.Card;
import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
import model.phase.IPhaseChecker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * If everything works right this class was
 * created by Konraifen88 on 11.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class ValueCheckerTest {

    public static final int TESTEE_NUMBER = 1;
    private static final int NUMBER_OF_CARDS = CardColor.values().length;
    private IPhaseChecker testee;

    private IDeckOfCards cards;

    @Before
    public void setUp() {
        testee = new ValueChecker(NUMBER_OF_CARDS);
        cards = new DeckOfCards();
        addCardsToDeck();
    }

    @Test
    public void aDeckWithCorrectSizeAndOnlyOneValueShouldReturnTrue() {
        assertTrue(testee.check(cards));
    }

    @Test
    public void aDeckWithTooSmallSizeShouldReturnFalse() {
        cards.removeFirst();
        assertFalse(testee.check(cards));
    }

    @Test
    public void aDeckWithTooGreatSizeShouldReturnFalse() {
        cards.add(new Card(TESTEE_NUMBER, CardColor.BLUE));
        assertFalse(testee.check(cards));
    }

    @Test
    public void aDeckWithCorrectSizeAndMultipleValuesShouldReturnFalse() {
        cards.removeFirst();
        cards.add(new Card(TESTEE_NUMBER + 1, CardColor.RED));
        assertFalse(testee.check(cards));
    }


    private void addCardsToDeck() {
        for (CardColor color : CardColor.values()) {
            cards.add(new Card(TESTEE_NUMBER, color));
        }
    }

}