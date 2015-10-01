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

public class Phase3Test {

    private Phase3 testee;

    @Before
    public void setUp() throws Exception {
        testee = new Phase3();
    }

    @Test
    public void nextPhaseReturnsPhase4() {
        assertEquals(testee.getNextPhase().getPhaseNumber(), Phase4.PHASE_NUMBER);
    }

    @Test
    public void descriptionTest() {
        assertEquals(testee.getDescription(), "six cards of one color");
    }

    @Test
    public void correctPhase() {
        IDeckOfCards wrongPhase = fillDeck(createOneCards(
                new CardColor[]{CardColor.BLUE, CardColor.BLUE, CardColor.BLUE, CardColor.BLUE, CardColor.BLUE,
                        CardColor.BLUE}));
        assertTrue(testee.checkIfDeckFitsToPhase(wrongPhase));
    }

    @Test
    public void phaseToShort() {
        IDeckOfCards wrongPhase = fillDeck(createOneCards(
                new CardColor[]{CardColor.BLUE, CardColor.BLUE, CardColor.BLUE, CardColor.BLUE, CardColor.BLUE}));
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
    }

    @Test
    public void phaseToLong() {
        IDeckOfCards wrongPhase = fillDeck(createOneCards(
                new CardColor[]{CardColor.BLUE, CardColor.BLUE, CardColor.BLUE, CardColor.BLUE, CardColor.BLUE,
                        CardColor.BLUE, CardColor.BLUE}));
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
    }

    @Test
    public void phaseWrong() {
        IDeckOfCards wrongPhase = fillDeck(createOneCards(
                new CardColor[]{CardColor.BLUE, CardColor.YELLOW, CardColor.BLUE, CardColor.BLUE, CardColor.BLUE,
                        CardColor.BLUE}));
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
        wrongPhase = fillDeck(createOneCards(
                new CardColor[]{CardColor.YELLOW, CardColor.BLUE, CardColor.BLUE, CardColor.BLUE, CardColor.BLUE,
                        CardColor.BLUE}));
        assertFalse(testee.checkIfDeckFitsToPhase(wrongPhase));
    }

    @Test
    public void addToPlayedPhase() {
        IDeckOfCards testPhase = fillDeck(createOneCards(
                new CardColor[]{CardColor.BLUE, CardColor.BLUE, CardColor.BLUE, CardColor.BLUE, CardColor.BLUE,
                        CardColor.BLUE}));
        List<ICardStack> testStacks = testee.splitPhaseIntoStacks(testPhase);
        assertEquals(testStacks.size(), 1);
        IDeckOfCards colorStack = testStacks.get(0).getList();
        assertEquals(colorStack.size(), 6);
    }

    private ICard[] createOneCards(CardColor[] colors) {
        ICard[] returnValue = new ICard[colors.length];
        for (int i = 0; i < colors.length; i++) {
            returnValue[i] = new Card(1, colors[i]);
        }
        return returnValue;
    }

    private IDeckOfCards fillDeck(ICard[] cards) {
        return new DeckOfCards(Arrays.asList(cards));
    }

}