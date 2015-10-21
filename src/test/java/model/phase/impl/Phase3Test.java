package model.phase.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
import model.phase.DeckNotFitException;
import model.phase.IPhase;
import model.phase.IPhaseChecker;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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

public class Phase3Test {
    @InjectMocks
    private IPhase testee = new Phase3();

    @Mock
    private IPhaseChecker checkerMock;

    private IDeckOfCards cards = new DeckOfCards();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(checkerMock.check(any())).thenReturn(true);
    }

    @Test
    public void nextPhaseReturnsPhase3() {
        assertEquals(testee.getNextPhase().getPhaseNumber(), 4);
    }

    @Test
    public void descriptionTest() {
        assertThat(testee.getDescription(), containsString("color"));
    }

    @Test
    public void whenCheckIsPassingAStackWithTheCardsShouldBeReturned() throws DeckNotFitException {
        setCheckIsPassingAndFillDeck(true);
        ICardStack tmp = testee.splitAndCheckPhase(cards).get(0);
        assertTrue(tmp instanceof ColorStack);
        cards.forEach(card -> assertTrue(tmp.getList().contains(card)));
    }

    @Test(expected = DeckNotFitException.class)
    public void whenCheckIsFailingAExceptionIsThrown() throws DeckNotFitException {
        setCheckIsPassingAndFillDeck(false);
        testee.splitAndCheckPhase(cards);
    }

    private void setCheckIsPassingAndFillDeck(boolean passing) {
        when(checkerMock.check(eq(cards))).thenReturn(passing);
        createCardDeck();
    }

    private void createCardDeck() {
        for (int i = 0; i < 6; i++) {
            cards.add(new Card(CardValue.byOrdinal(i), CardColor.BLUE));
        }
    }
}