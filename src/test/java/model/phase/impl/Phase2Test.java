package model.phase.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
import model.phase.IPhase;
import model.phase.IPhaseChecker;
import model.stack.ICardStack;
import model.stack.impl.StreetStack;
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
public class Phase2Test {

    @InjectMocks
    private IPhase testee = new Phase2();

    @Mock
    private IPhaseChecker checkerMock;

    private IDeckOfCards street = new DeckOfCards();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(checkerMock.check(any())).thenReturn(true);
    }

    @Test
    public void nextPhaseReturnsPhase3() {
        assertEquals(testee.getNextPhase().getPhaseNumber(), 3);
    }

    @Test
    public void descriptionTest() {
        assertThat(testee.getDescription(), containsString("street"));
    }

    @Test
    public void whenCheckIsPassingAStackWithTheCardsShouldBeReturned() {
        setCheckIsPassingAndFillDeck(true);
        ICardStack tmp = testee.splitAndCheckPhase(street).get(0);
        assertTrue(tmp instanceof StreetStack);
        street.forEach(card -> assertTrue(tmp.getList().contains(card)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCheckIsFailingAExceptionIsThrown() {
        setCheckIsPassingAndFillDeck(false);
        testee.splitAndCheckPhase(street);
    }

    private void setCheckIsPassingAndFillDeck(boolean passing) {
        when(checkerMock.check(eq(street))).thenReturn(passing);
        createCardDeck();
    }

    private void createCardDeck() {
        for (int i = 0; i < 6; i++) {
            street.add(new Card(CardValue.byOrdinal(i), CardColor.BLUE));
        }
    }
}