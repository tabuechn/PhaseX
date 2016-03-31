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
public class Phase4Test {
    private static final int QUADRUPLE_INDEX = 0;
    private static final int PAIR_INDEX = 1;
    private static final ICard CARD_1 = new Card(CardValue.ONE, CardColor.BLUE);
    private static final ICard CARD_2 = new Card(CardValue.TWO, CardColor.GREEN);
    @InjectMocks
    private IPhase testee = new Phase4();
    @Mock(name = "quadruplePhaseChecker")
    private IPhaseChecker quadrupleCheckerMock;
    @Mock(name = "pairPhaseChecker")
    private IPhaseChecker pairCheckerMock;
    @Mock(name = "phaseSplitter")
    private IPhaseSplitter splitterMock;
    private List<IDeckOfCards> splittedDeck;
    private IDeckOfCards cards = new DeckOfCards();
    private IDeckOfCards quadruple;
    private IDeckOfCards pair;

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
        assertThat(testee.getDescription(), containsString("quadruple"));
        assertThat(testee.getDescription(), containsString("pair"));
    }

    @Test
    public void whenCheckIsPassingAStackWithTheCardsShouldBeReturned() throws DeckNotFitException {
        setCheckIsPassingAndFillDeck(true);
        List<ICardStack> tmp = testee.splitAndCheckPhase(cards);
        //Checking Quadruple
        assertTrue(tmp.get(QUADRUPLE_INDEX) instanceof PairStack);
        assertEquals(quadruple, tmp.get(QUADRUPLE_INDEX).getList());
        //Checking pair
        assertTrue(tmp.get(PAIR_INDEX) instanceof PairStack);
        assertEquals(pair, tmp.get(PAIR_INDEX).getList());
    }

    @Test(expected = DeckNotFitException.class)
    public void whenBothChecksFailAnExceptionIsThrown() throws DeckNotFitException {
        setCheckIsPassingAndFillDeck(false);
        testee.splitAndCheckPhase(cards);
    }

    @Test(expected = DeckNotFitException.class)
    public void whenPairChecksFailsAnExceptionIsThrown() throws DeckNotFitException {
        setCheckIsPassingAndFillDeck(true);
        when(pairCheckerMock.check(any())).thenReturn(false);
        testee.splitAndCheckPhase(cards);
    }

    @Test(expected = DeckNotFitException.class)
    public void whenQuadrupleChecksFailAnExceptionIsThrown() throws DeckNotFitException {
        setCheckIsPassingAndFillDeck(true);
        when(quadrupleCheckerMock.check(any())).thenReturn(false);
        testee.splitAndCheckPhase(cards);
    }

    private void setCheckIsPassingAndFillDeck(boolean passing) {
        createCardDeck();
        when(splitterMock.split(eq(cards))).thenReturn(splittedDeck);
        when(pairCheckerMock.check(any())).thenReturn(passing);
        when(quadrupleCheckerMock.check(any())).thenReturn(passing);
    }

    private void createCardDeck() {
        splittedDeck = new LinkedList<>();

        quadruple = new DeckOfCards();
        quadruple.addAll(Arrays.asList(CARD_1, CARD_1, CARD_1, CARD_1));
        splittedDeck.add(quadruple);

        pair = new DeckOfCards();
        pair.addAll(Arrays.asList(CARD_2, CARD_2));
        splittedDeck.add(pair);
    }

}