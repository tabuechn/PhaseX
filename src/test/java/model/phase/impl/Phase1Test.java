package model.phase.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

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

    public static final int SINGLE_STACK_SIZE = 3;
    private static final ICard CARD_1 = new Card(CardValue.ONE, CardColor.BLUE);
    private static final ICard CARD_2 = new Card(CardValue.TWO, CardColor.GREEN);
    private static final IDeckOfCards DECK_TO_SPLIT = new DeckOfCards();
    @InjectMocks
    private Phase1 testee = new Phase1();
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
        IPhase phase2 = testee.getNextPhase();
        assertEquals(phase2.getPhaseNumber(), Phase2.PHASE_NUMBER);
    }

    @Test
    public void getDescriptionShouldReturnAString() {
        assertEquals(Phase1.DESCRIPTION_PHASE_1, testee.getDescription());
    }

    @Test
    public void checkWithTwoDifferentTriplesShouldReturnTwoPairStacks() {
        createSplittedDeck(Arrays.asList(CARD_1, CARD_1, CARD_1), Arrays.asList(CARD_2, CARD_2, CARD_2));
        List<ICardStack> stacks = testee.splitPhaseIntoStacks(DECK_TO_SPLIT);
        checkCorrectSplittedPhase(stacks);
        assertEquals(CARD_1, stacks.get(0).getList().get(0));
        assertEquals(CARD_2, stacks.get(1).getList().get(0));
    }

    @Test
    public void checkWithTwoSameTriplesShouldReturnTwoPairStacks() {
        createSplittedDeck(Arrays.asList(CARD_1, CARD_1, CARD_1), Arrays.asList(CARD_1, CARD_1, CARD_1));
        List<ICardStack> stacks = testee.splitPhaseIntoStacks(DECK_TO_SPLIT);
        checkCorrectSplittedPhase(stacks);
        assertEquals(CARD_1, stacks.get(0).getList().get(0));
        assertEquals(CARD_1, stacks.get(1).getList().get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkWithOnlyOneCorrectTripleShouldThrowException() {
        createSplittedDeck(Arrays.asList(CARD_1, CARD_1, CARD_1), Arrays.asList(CARD_1, CARD_2, CARD_1));
        when(checkerMock.check(any())).thenReturn(false);
        testee.splitPhaseIntoStacks(DECK_TO_SPLIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkWithoutCorrectTripleShouldThrowException() {
        createSplittedDeck(Arrays.asList(CARD_1, CARD_2, CARD_1), Arrays.asList(CARD_1, CARD_2, CARD_1));
        when(checkerMock.check(any())).thenReturn(false);
        testee.splitPhaseIntoStacks(DECK_TO_SPLIT);
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
        when(splitterMock.split(DECK_TO_SPLIT)).thenReturn(splittedDeck);
        splittedDeck.add(new DeckOfCards(cardsIndex0));
        splittedDeck.add(new DeckOfCards(cardsIndex1));
    }
}