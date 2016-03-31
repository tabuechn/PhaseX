package model.phase.checker;

import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.phase.IPhaseChecker;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * If everything works right this class was
 * created by Konraifen88 on 11.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class StreetCheckerTest {

    private static final int NUMBER_OF_CARDS = 6;
    private static final CardValue CARD_VALUE_OUT_OF_RANGE = CardValue.byOrdinal(NUMBER_OF_CARDS + 1);

    private final IPhaseChecker testee = new StreetChecker(NUMBER_OF_CARDS);

    private IDeckOfCards deck;

    @Before
    public void setUp() {
        deck = new DeckOfCards();
        createAndAddCards();
    }

    @Test
    public void aCorrectAndSortedDeckShouldReturnTrue() {
        assertTrue(testee.check(deck));
    }

    @Test
    public void aCorrectAndUnsortedDeckShouldReturnTrue() {
        Collections.shuffle(deck);
        assertTrue(testee.check(deck));
    }

    @Test
    public void aSortedDeckWithARemovedCardFromTheMiddleShouldRemoveFalse() {
        deck.add(new Card(CARD_VALUE_OUT_OF_RANGE, CardColor.BLUE));
        deck.remove(NUMBER_OF_CARDS / 2);
        assertTrue(deck.size() == NUMBER_OF_CARDS);
        assertFalse(testee.check(deck));
    }

    @Test
    public void anUnsortedDeckWithARemovedCardFromTheMiddleShouldRemoveFalse() {
        deck.add(new Card(CARD_VALUE_OUT_OF_RANGE, CardColor.BLUE));
        deck.remove(NUMBER_OF_CARDS / 2);
        Collections.shuffle(deck);
        assertTrue(deck.size() == NUMBER_OF_CARDS);
        assertFalse(testee.check(deck));
    }

    @Test
    public void aDeckWhichIsTooShortShouldReturnFalse() {
        deck.removeFirst();
        assertFalse(testee.check(deck));
    }

    @Test
    public void aDeckWhichIsTooLongShouldReturnFalse() {
        deck.add(new Card(CARD_VALUE_OUT_OF_RANGE, CardColor.BLUE));
        assertFalse(testee.check(deck));
    }

    private void createAndAddCards() {
        for (int i = 0; i < NUMBER_OF_CARDS; i++) {
            deck.add(new Card(CardValue.byOrdinal(i), CardColor.BLUE));
        }
    }

}