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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

public class Phase5Test {

    private Phase5 testee;

    @Before
    public void setUp() throws Exception {
        testee = new Phase5();
    }

    @Test
    public void nextPhaseIsSamePhaseThanCurrent(){
        assertEquals(testee.getNextPhase().getPhaseNumber(),Phase5.PHASE_NUMBER);
    }

    @Test
    public void descriptionTest() {
        assertEquals(testee.getDescription(), "two number quadruples");
    }

    @Test
    public void checkRightPhase() {
        IDeckOfCards rightPhase = fillDeck(createYellowCards(new int[]{2, 2, 2, 2, 1, 1, 1, 1}));
        assertTrue(testee.checkIfDeckFitsToPhase(rightPhase));
        rightPhase = fillDeck(createYellowCards(new int[]{2, 2, 2, 2, 2, 2, 2, 2}));
        assertTrue(testee.checkIfDeckFitsToPhase(rightPhase));
    }

    @Test
    public void checkTooLongPhase() {
        IDeckOfCards wrongPhase = fillDeck(createYellowCards(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
    }


    @Test
    public void checkTooShortPhase() {
        IDeckOfCards wrongPhase = fillDeck(createYellowCards(new int[]{1, 2, 3, 4, 5, 6, 7}));
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
    }

    @Test
    public void checkWithTwoDifferentQuadruples() {
        IDeckOfCards testPhase = fillDeck(createYellowCards(new int[]{1, 1, 1, 1, 6, 6, 6, 6}));
        List<ICardStack> testStacks = testee.splitPhaseIntoStacks(testPhase);
        assertEquals(testStacks.size(), 2);
        IDeckOfCards firstTriple = testStacks.get(0).getList();
        IDeckOfCards secondTriple = testStacks.get(1).getList();
        assertEquals(firstTriple.size(), 4);
        assertEquals(secondTriple.size(), 4);
    }

    @Test
    public void checkWithTwoSameQuadruples() {
        IDeckOfCards testPhase = fillDeck(createYellowCards(new int[]{1, 1, 1, 1, 1, 1, 1, 1}));
        List<ICardStack> testStacks = testee.splitPhaseIntoStacks(testPhase);
        assertEquals(testStacks.size(), 2);
        IDeckOfCards firstTriple = testStacks.get(0).getList();
        IDeckOfCards secondTriple = testStacks.get(1).getList();
        assertEquals(firstTriple.size(), 4);
        assertEquals(secondTriple.size(), 4);
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