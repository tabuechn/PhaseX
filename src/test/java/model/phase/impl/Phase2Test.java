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

public class Phase2Test {

    private Phase2 testee;

    @Before
    public void setUp() throws Exception {
        testee = new Phase2();
    }

    @Test
    public void nextPhaseReturnsPhase3() {
        assertEquals(testee.getNextPhase().getPhaseNumber(),Phase3.PHASE_NUMBER);
    }

    @Test
    public void descriptionTest() {
        assertEquals(testee.getDescription(),"street of six numbers");
    }

    @Test
    public void checkRightPhase() {
        IDeckOfCards rightPhase = fillDeck(createYellowCards(new int[]{1, 2, 3, 4, 5, 6}));
        assertTrue(testee.checkIfDeckFitsToPhase(rightPhase));
        rightPhase = fillDeck(createYellowCards(new int[]{6, 5, 4, 3, 2, 1}));
        assertTrue(testee.checkIfDeckFitsToPhase(rightPhase));
    }

    @Test
    public void checkWrongPhase() {
        IDeckOfCards wrongPhase = fillDeck(createYellowCards(new int[]{1, 2, 3, 3, 5, 6}));
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
    }

    @Test
    public void checkTooLongPhase() {
        IDeckOfCards wrongPhase = fillDeck(createYellowCards(new int[]{1, 2, 3, 4, 5, 6, 7}));
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
    }

    @Test
    public void checkTooShortPhase() {
        IDeckOfCards wrongPhase = fillDeck(createYellowCards(new int[]{1, 2, 3, 4}));
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
    }

    @Test
    public void checkWithOrderedStreet() {
        IDeckOfCards testPhase = fillDeck(createYellowCards(new int[]{1, 2, 3, 4, 5, 6}));
        List<ICardStack> testStacks = testee.splitPhaseIntoStacks(testPhase);
        assertEquals(testStacks.size(), 1);
        IDeckOfCards firstStreet = testStacks.get(0).getList();
        assertEquals(firstStreet.size(), 6);
    }

    @Test
    public void checkWithUnorderedStreet() {
        IDeckOfCards testPhase = fillDeck(createYellowCards(new int[]{3, 2, 4, 6, 1, 5}));
        List<ICardStack> testStacks = testee.splitPhaseIntoStacks(testPhase);
        assertEquals(testStacks.size(), 1);
        IDeckOfCards firstStreet = testStacks.get(0).getList();
        assertEquals(firstStreet.size(), 6);
        for (int i = 0; i < 6; i++) {
            assertEquals(firstStreet.get(i).getNumber(), i + 1);
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