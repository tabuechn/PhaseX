package util;

import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deckOfCards.IDeckOfCards;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * If everything works right this class was
 * created by Konraifen88 on 25.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CardCreatorTest {
    private IDeckOfCards testee;

    @Before
    public void setUp() {
        testee = CardCreator.giveDeckOfCards();
    }

    @Test
    public void checkIfEnoughCardsAreCreated() {
        assertEquals(getAmountOfCards(), testee.size());
    }

    @Test
    public void cardLowerThanMinValueShouldNotExist() {
        for (CardColor color : CardColor.values()) {
            assertFalse(testee.contains(new Card(CardValue.byOrdinal(CardCreator.LOWEST_CARD - 1), color)));
        }
    }

    @Test
    public void cardHigherThanMaxValueShouldNotExist() {
        for (CardColor color : CardColor.values()) {
            assertFalse(testee.contains(new Card(CardValue.byOrdinal(CardCreator.HIGHEST_CARD + 1), color)));
        }
    }

    private int getAmountOfCards() {
        return (CardCreator.HIGHEST_CARD - CardCreator.LOWEST_CARD + 1) * CardCreator.SIZE_OF_DECK_MULTIPLIER * 4;
    }
}