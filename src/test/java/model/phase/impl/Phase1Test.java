package model.phase.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.phase.DeckNotFitException;
import model.phase.IPhase;
import model.phase.IPhaseChecker;
import model.phase.IPhaseSplitter;
import model.stack.ICardStack;
import model.stack.impl.PairStack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * If everything works right this class was
 * created by Konraifen88 on 14.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */

public class Phase1Test {

    public static final int SINGLE_STACK_SIZE = 3;
    private static final ICard CARD_1 = new Card(CardValue.ONE, CardColor.BLUE);
    private static final ICard CARD_2 = new Card(CardValue.TWO, CardColor.GREEN);
    private static final IDeckOfCards DECK_TO_SPLIT = new DeckOfCards();
    @InjectMocks
    private IPhase testee = new Phase1();
    @Mock
    private IPhaseChecker checkerMock;
    @Mock
    private IPhaseSplitter splitterMock;
    private List<IDeckOfCards> splittedDeck;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        splittedDeck = new LinkedList<>();
        when(checkerMock.check(any())).thenReturn(true);
    }

    @Test
    public void nextPhaseReturnsPhase2() {
        assertEquals(testee.getNextPhase().getPhaseNumber(), 2);
    }

    @Test
    public void thisPhaseReturnsCorrectPhaseNumber() {
        assertEquals(testee.getPhaseNumber(), 1);
    }

    @Test
    public void getDescriptionShouldReturnAString() {
        assertThat(testee.getDescription(), containsString("triple"));
    }

    @Test
    public void checkWithTwoDifferentTriplesShouldReturnTwoPairStacks() throws DeckNotFitException {
        createSplittedDeck(Arrays.asList(CARD_1, CARD_1, CARD_1), Arrays.asList(CARD_2, CARD_2, CARD_2));
        List<ICardStack> stacks = testee.splitAndCheckPhase(DECK_TO_SPLIT);
        checkCorrectSplittedPhase(stacks);
        assertEquals(CARD_1, stacks.get(0).getList().get(0));
        assertEquals(CARD_2, stacks.get(1).getList().get(0));
    }

    @Test
    public void checkWithTwoSameTriplesShouldReturnTwoPairStacks() throws DeckNotFitException {
        createSplittedDeck(Arrays.asList(CARD_1, CARD_1, CARD_1), Arrays.asList(CARD_1, CARD_1, CARD_1));
        List<ICardStack> stacks = testee.splitAndCheckPhase(DECK_TO_SPLIT);
        checkCorrectSplittedPhase(stacks);
        assertEquals(CARD_1, stacks.get(0).getList().get(0));
        assertEquals(CARD_1, stacks.get(1).getList().get(0));
    }

    @Test(expected = DeckNotFitException.class)
    public void checkWithOnlyOneCorrectTripleShouldThrowException() throws DeckNotFitException {
        createSplittedDeck(Arrays.asList(CARD_1, CARD_1, CARD_1), Arrays.asList(CARD_1, CARD_2, CARD_1));
        when(checkerMock.check(any())).thenReturn(false);
        testee.splitAndCheckPhase(DECK_TO_SPLIT);
    }

    @Test(expected = DeckNotFitException.class)
    public void checkWithoutCorrectTripleShouldThrowException() throws DeckNotFitException {
        createSplittedDeck(Arrays.asList(CARD_1, CARD_2, CARD_1), Arrays.asList(CARD_1, CARD_2, CARD_1));
        when(checkerMock.check(any())).thenReturn(false);
        testee.splitAndCheckPhase(DECK_TO_SPLIT);
    }

    private void checkCorrectSplittedPhase(List<ICardStack> stacks) {
        assertEquals(2, stacks.size());
        //Verifying first stack
        assertTrue(stacks.get(0) instanceof PairStack);
        assertEquals(SINGLE_STACK_SIZE, stacks.get(0).getList().size());
        //Verifying second stack
        assertTrue(stacks.get(1) instanceof PairStack);
        assertEquals(SINGLE_STACK_SIZE, stacks.get(0).getList().size());
    }

    private void createSplittedDeck(List<ICard> cardsIndex0, List<ICard> cardsIndex1) {
        when(splitterMock.split(eq(DECK_TO_SPLIT))).thenReturn(splittedDeck);
        splittedDeck.add(new DeckOfCards(cardsIndex0));
        splittedDeck.add(new DeckOfCards(cardsIndex1));
    }
}