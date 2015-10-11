package model.phase.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
import model.phase.IPhase;
import model.stack.ICardStack;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 */

/**
 * edited: merged phase checker & getter
 * <p>
 * If everything works right this class was
 * created by Konraifen88 on 30.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */

public class Phase1Test {

    private Phase1 testee;

    @Before
    public void setUp() throws Exception {
        testee = new Phase1();
    }

    @Test
    public void nextPhaseReturnsPhase2() {
        IPhase phase2 = testee.getNextPhase();
        assertEquals(phase2.getPhaseNumber(), Phase2.PHASE_NUMBER);
    }

    @Test
    public void descriptionTest() {
        assertEquals(testee.getDescription(), "two number triples");
    }

    @Test
    public void checkCorrectPhase() {
        ICard[] cardArray = createYellowCards(new int[]{2, 2, 2, 4, 4, 4});
        IDeckOfCards correctPhase = fillDeck(cardArray);
        assertTrue(testee.checkIfDeckFitsToPhase(correctPhase));
    }

    @Test
    public void checkOneNumber() {
        ICard[] cardArray = createYellowCards(new int[]{2, 2, 2, 2, 2, 2});
        IDeckOfCards correctPhase = fillDeck(cardArray);
        assertTrue(testee.checkIfDeckFitsToPhase(correctPhase));
    }

    @Test
    public void checkTooLongPhase() {
        ICard[] cardArray = createYellowCards(new int[]{2, 2, 2, 2, 4, 4, 4});
        IDeckOfCards wrongPhase = fillDeck(cardArray);
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
    }

    @Test
    public void checkTooShortPhase() {
        ICard[] cardArray = createYellowCards(new int[]{2, 2, 4, 4, 4});
        IDeckOfCards wrongPhase = fillDeck(cardArray);
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
    }

    @Test
    public void checkWithTwoDifferentTriples() {
        IDeckOfCards testPhase = fillDeck(createYellowCards(new int[]{1, 1, 1, 6, 6, 6}));
        List<ICardStack> testStacks = testee.splitPhaseIntoStacks(testPhase);
        assertEquals(testStacks.size(), 2);
        IDeckOfCards firstTriple = testStacks.get(0).getList();
        IDeckOfCards secondTriple = testStacks.get(1).getList();
        assertEquals(firstTriple.size(), 3);
        assertEquals(secondTriple.size(), 3);
    }

    @Test
    public void checkWithTwoSameTriples() {
        IDeckOfCards testPhase = fillDeck(createYellowCards(new int[]{1, 1, 1, 1, 1, 1}));
        List<ICardStack> testStacks = testee.splitPhaseIntoStacks(testPhase);
        assertEquals(testStacks.size(), 2);
        IDeckOfCards firstTriple = testStacks.get(0).getList();
        IDeckOfCards secondTriple = testStacks.get(1).getList();
        assertEquals(firstTriple.size(), 3);
        assertEquals(secondTriple.size(), 3);
    }

    private ICard[] createYellowCards(int[] numbers) {
        ICard[] returnValue = new ICard[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            returnValue[i] = new Card(CardValue.byOrdinal(numbers[i]), CardColor.YELLOW);
        }
        return returnValue;
    }

    private IDeckOfCards fillDeck(ICard[] cards) {
        return new DeckOfCards(Arrays.asList(cards));
    }


}