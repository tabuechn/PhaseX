package controller.impl;

import controller.IController;
import controller.states.AbstractState;
import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.player.IPlayer;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import util.Event;
import util.IObserver;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * If everything works right this class was
 * created by Konraifen88 on 15.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 *
 * NOTE: Be careful using some methods will override the mocked objects!
 */
public class ControllerTest implements IObserver {
    private static final ICard TEST_CARD_1 = new Card(CardValue.ONE, CardColor.BLUE);

    private static final ICard TEST_CARD_2 = new Card(CardValue.TWO, CardColor.GREEN);
    private static final IDeckOfCards TEST_DECK_SORTED = new DeckOfCards();
    private static final IDeckOfCards TEST_DECK_UNSORTED = new DeckOfCards();
    private static final ICardStack TEST_STACK
            = new ColorStack(new DeckOfCards(Arrays.asList(TEST_CARD_1, TEST_CARD_1)));
    @InjectMocks
    private IController testee;
    @Mock(name = "roundState")
    private AbstractState stateMock;
    @Mock(name = "currentPlayer")
    private IPlayer playerMock;
    @Mock(name = "drawPile")
    private IDeckOfCards drawPileMock;
    @Mock(name = "discardPile")
    private IDeckOfCards discardPileMock;
    private boolean observed;
    private ArgumentCaptor<ICard> cardCaptor = ArgumentCaptor.forClass(ICard.class);
    @Before
    public void setUp() {
        testee = new Controller(2);
        initMocks(this);
        testee.addObserver(this);
        observed = false;
    }


    @Test
    public void drawOpenShouldCallTheStateToDrawACardFromTheOpenPile() {
        //doNothing().when(stateMock).drawOpen(any(Controller.class));
        doNothing().when(stateMock).drawOpen(any(IDeckOfCards.class),any(IDeckOfCards.class),any(IPlayer.class));
        testee.drawOpen();
        verify(stateMock, times(1)).drawOpen(eq(testee.getDiscardPile()),eq(testee.getDrawPile()),eq(testee.getCurrentPlayer()));
        checkIfObserverWasNotified();
    }

    @Test
    public void drawHiddenShouldCallTheStateToDrawACardFromTheHiddenPile() {
        doNothing().when(stateMock).drawHidden(any(IDeckOfCards.class),any(IDeckOfCards.class),any(IPlayer.class));
        testee.drawHidden();
        verify(stateMock, times(1)).drawHidden(eq(testee.getDiscardPile()),eq(testee.getDrawPile()),eq(testee.getCurrentPlayer()));
        checkIfObserverWasNotified();
    }

    @Test
    public void discardShouldCallTheStateToDiscardTheCard() {
        doNothing().when(stateMock).discard(any(Controller.class), any(ICard.class));
        testee.discard(TEST_CARD_1);
        verify(stateMock, times(1)).discard(eq(testee), eq(TEST_CARD_1));
        checkIfObserverWasNotified();
    }

    @Test
    public void playPhaseShouldCallTheStateToPlayThePhase() {
        doNothing().when(stateMock).playPhase(any(Controller.class), any(IDeckOfCards.class));
        testee.playPhase(TEST_DECK_SORTED);
        verify(stateMock, times(1)).playPhase(eq(testee), eq(TEST_DECK_SORTED));
        checkIfObserverWasNotified();
    }

    @Test
    public void addToFinishedPhaseShouldCallTheStateAddTheCards() {
        doNothing().when(stateMock).addToFinishedPhase(
                any(Controller.class), any(ICard.class), any(ICardStack.class));
        testee.addToFinishedPhase(TEST_CARD_1, TEST_STACK);
        verify(stateMock, times(1)).addToFinishedPhase(eq(testee), eq(TEST_CARD_1), eq(TEST_STACK));
        checkIfObserverWasNotified();
    }

    @Test
    public void addingMultipleCardsShouldSortTheGivenList() {
        fillDecks();
        doNothing().when(stateMock).addToFinishedPhase(
                any(Controller.class), any(ICard.class), any(ICardStack.class));
        testee.addMultipleCardsToFinishedPhase(new DeckOfCards(TEST_DECK_UNSORTED), TEST_STACK);
        verify(stateMock, times(2)).addToFinishedPhase(eq(testee), cardCaptor.capture(), eq(TEST_STACK));
        List<ICard> captured = cardCaptor.getAllValues();
        for (int i = 0; i < TEST_DECK_SORTED.size(); i++) {
            assertThat(captured.get(i), equalTo(TEST_DECK_SORTED.get(i)));
        }
        checkIfObserverWasNotified();
    }

    @Test
    public void getCurrentPlayersHandShouldReturnTheDeckForTheCurrentPlayer() {
        doReturn(TEST_DECK_SORTED).when(playerMock).getDeckOfCards();
        testee.getCurrentPlayersHand();
        verify(playerMock).getDeckOfCards();
    }

    @Test
    public void getNumberOfCardsForNextPlayerShouldReturnAMapWithoutCurrentPlayer() {
        //Creating new players and adding some Cards.
        testee.initGame();
        testee.newRound();
        Map<Integer, Integer> tmp = testee.getNumberOfCardsForNextPlayer();
        assertFalse(tmp.containsKey(testee.getCurrentPlayer().getPlayerNumber()));
    }

    //TODO: test all after getNumberOfCardsForNextPlayer

    private void checkIfObserverWasNotified() {
        assertTrue(observed);
    }

    private void fillDecks() {
        TEST_DECK_SORTED.add(TEST_CARD_1);
        TEST_DECK_SORTED.add(TEST_CARD_2);

        TEST_DECK_UNSORTED.add(TEST_CARD_2);
        TEST_DECK_UNSORTED.add(TEST_CARD_1);
    }

    @Override
    public void update(Event e) {
        observed = true;
    }

    @After
    public void tearDown() {
        testee.removeAllObservers();
    }
}