package model.phase.impl;

import model.card.CardColor;
import model.card.ICard;
import model.card.impl.Card;
import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
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

public class Phase4Test {

    private Phase4 testee;

    @Before
    public void setUp() throws Exception {
        testee = new Phase4();
    }

    @Test
    public void nextPhaseReturnsPhase5() {
        assertEquals(testee.getNextPhase().getPhaseNumber(), Phase5.PHASE_NUMBER);
    }

    @Test
    public void descriptionTest() {
        assertEquals(testee.getDescription(), "number quadruple and number pair");
    }

    @Test
    public void checkRightPhase() {
        IDeckOfCards rightPhase = fillDeck(createYellowCards(new int[]{2, 2, 4, 4, 4, 4}));
        assertTrue(testee.checkIfDeckFitsToPhase(rightPhase));
        rightPhase = fillDeck(createYellowCards(new int[]{2, 2, 2, 2, 4, 4}));
        assertTrue(testee.checkIfDeckFitsToPhase(rightPhase));
        rightPhase = fillDeck(createYellowCards(new int[]{2, 2, 2, 2, 2, 2}));
        assertTrue(testee.checkIfDeckFitsToPhase(rightPhase));
    }

    @Test
    public void checkWrongPhase() {
        IDeckOfCards wrongPhase = fillDeck(createYellowCards(new int[]{1, 2, 3, 4, 5, 6}));
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
    }

    @Test
    public void checkTooLongPhase() {
        IDeckOfCards wrongPhase = fillDeck(createYellowCards(new int[]{1, 2, 3, 4, 5, 6, 7}));
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
    }

    @Test
    public void checkTooShortPhase() {
        IDeckOfCards wrongPhase = fillDeck(createYellowCards(new int[]{1, 2, 3, 4, 5}));
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
    }

    @Test
    public void testWithDifferentNumbers() {
        IDeckOfCards testphase = fillDeck(createYellowCards(new int[]{2, 2, 5, 5, 5, 5}));
        List<ICardStack> testStacks = testee.splitPhaseIntoStacks(testphase);
        assertEquals(testStacks.size(), 2);
        IDeckOfCards firstStack = testStacks.get(0).getList();
        IDeckOfCards secondStack = testStacks.get(1).getList();
        if (firstStack.size() == 2) {
            assertEquals(secondStack.size(), 4);
        } else {
            assertEquals(firstStack.size(), 4);
            assertEquals(secondStack.size(), 2);
        }
    }

    @Test
    public void testWithSameNumbers() {
        IDeckOfCards testphase = fillDeck(createYellowCards(new int[]{5, 5, 5, 5, 5, 5}));
        List<ICardStack> testStacks = testee.splitPhaseIntoStacks(testphase);
        assertEquals(testStacks.size(), 2);
        IDeckOfCards firstStack = testStacks.get(0).getList();
        IDeckOfCards secondStack = testStacks.get(1).getList();
        if (firstStack.size() == 2) {
            assertEquals(secondStack.size(), 4);
        } else {
            assertEquals(firstStack.size(), 4);
            assertEquals(secondStack.size(), 2);
        }
    }

    private ICard[] createYellowCards(int[] numbers) {
        ICard[] returnValue = new ICard[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            returnValue[i] = new Card(numbers[i], CardColor.YELLOW);
        }
        return returnValue;
    }

    private IDeckOfCards fillDeck(ICard[] cards) {
        return new DeckOfCards(Arrays.asList(cards));
    }

}