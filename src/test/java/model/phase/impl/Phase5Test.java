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
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * If everything works right this class was
 * created by Konraifen88 on 14.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */

public class Phase5Test {
    private static final int QUADRUPLE_INDEX_1 = 0;
    private static final int QUADRUPLE_INDEX_2 = 1;
    private static final ICard CARD_1 = new Card(CardValue.ONE, CardColor.BLUE);
    private static final ICard CARD_2 = new Card(CardValue.TWO, CardColor.GREEN);
    @InjectMocks
    private IPhase testee = new Phase5();
    @Mock(name = "phaseChecker")
    private IPhaseChecker checkerMock;
    @Mock(name = "phaseSplitter")
    private IPhaseSplitter splitterMock;
    private List<IDeckOfCards> splittedDeck;
    private IDeckOfCards cards = new DeckOfCards();
    private IDeckOfCards firstQuadruple;
    private IDeckOfCards secondQuadruple;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void nextPhaseReturnsPhase3() {
        assertEquals(testee.getNextPhase().getPhaseNumber(), 5);
    }

    @Test
    public void descriptionTest() {
        assertThat(testee.getDescription(), containsString("quadruples"));
    }

    @Test
    public void whenCheckIsPassingAStackWithTheCardsShouldBeReturned() throws DeckNotFitException {
        setCheckIsPassingAndFillDeck(true);
        List<ICardStack> tmp = testee.splitAndCheckPhase(cards);
        verify(checkerMock, times(2)).check(any(IDeckOfCards.class));
        //Checking Quadruple
        assertTrue(tmp.get(QUADRUPLE_INDEX_1) instanceof PairStack);
        assertEquals(firstQuadruple, tmp.get(QUADRUPLE_INDEX_1).getList());
        //Checking secondQuadruple
        assertTrue(tmp.get(QUADRUPLE_INDEX_2) instanceof PairStack);
        assertEquals(secondQuadruple, tmp.get(QUADRUPLE_INDEX_2).getList());
    }

    @Test(expected = DeckNotFitException.class)
    public void whenBothChecksFailAnExceptionIsThrown() throws DeckNotFitException {
        setCheckIsPassingAndFillDeck(false);
        testee.splitAndCheckPhase(cards);
    }

    @Test
    public void isNumberPhaseShouldReturnFalse() {
        assertFalse(testee.isNumberPhase());
    }

    private void setCheckIsPassingAndFillDeck(boolean passing) {
        createCardDeck();
        when(splitterMock.split(eq(cards))).thenReturn(splittedDeck);
        when(checkerMock.check(any())).thenReturn(passing);
    }

    private void createCardDeck() {
        splittedDeck = new LinkedList<>();

        firstQuadruple = new DeckOfCards();
        firstQuadruple.addAll(Arrays.asList(CARD_1, CARD_1, CARD_1, CARD_1));
        splittedDeck.add(firstQuadruple);

        secondQuadruple = new DeckOfCards();
        secondQuadruple.addAll(Arrays.asList(CARD_2, CARD_2, CARD_2, CARD_2));
        splittedDeck.add(secondQuadruple);
    }
}