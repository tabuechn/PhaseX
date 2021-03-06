package model.phase.checker;

import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
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
public class ColorCheckerTest {

    private static final int NUMBER_OF_CARDS = 6;
    private static final CardValue CARD_NUMBER_OUT_OF_RANGE = CardValue.byOrdinal(NUMBER_OF_CARDS + 2);

    private IPhaseChecker testee;

    private IDeckOfCards cards;

    @Before
    public void setUp() {
        testee = new ColorChecker(NUMBER_OF_CARDS);
        cards = new DeckOfCards();
        addCardsToDeck();
    }

    @Test
    public void aDeckWithCorrectSizeAndOnlyOneColorShouldReturnTrue() {
        assertTrue(testee.check(cards));
    }

    @Test
    public void aDeckWithTooSmallSizeShouldReturnFalse() {
        cards.removeFirst();
        assertFalse(testee.check(cards));
    }

    @Test
    public void aDeckWithTooGreatSizeShouldReturnFalse() {
        cards.add(new Card(CARD_NUMBER_OUT_OF_RANGE, CardColor.BLUE));
        assertFalse(testee.check(cards));
    }

    @Test
    public void aDeckWithCorrectSizeAndMultipleColoursShouldReturnFalse() {
        cards.removeFirst();
        cards.add(new Card(CARD_NUMBER_OUT_OF_RANGE, CardColor.RED));
        assertFalse(testee.check(cards));
    }

    private void addCardsToDeck() {
        for (int i = 0; i < NUMBER_OF_CARDS; i++) {
            cards.add(new Card(CardValue.byOrdinal(i), CardColor.BLUE));
        }
    }

}