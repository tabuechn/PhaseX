package controller.impl;

import actors.message.DiscardMessage;
import actors.message.MasterMessage;
import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;
import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.player.IPlayer;
import model.state.IRoundState;
import model.state.StateEnum;
import model.state.impl.RoundState;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import scala.concurrent.Await;
import scala.concurrent.duration.FiniteDuration;
import util.CardCreator;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * If everything works right this class was
 * created by konraifen88 on 30.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Patterns.class, Await.class, CardCreator.class})
public class ActorControllerTest {

    private static final String TEST_PLAYER_1 = "Player1";
    private static final int DECK_SIZE = 10;
    private static final Card TEST_CARD = new Card(CardValue.NINE, CardColor.GREEN);
    private static final Card DECK_INIT_CARD = new Card(CardValue.EIGHT, CardColor.BLUE);

    @InjectMocks
    private ActorController testee;

    @Mock
    private IDeckOfCards drawPileMock;

    @Before
    public void setUp() {
        testee = new ActorController();
        mockCardCreator();
    }

    @Test
    public void playersShouldGet10CardsDuringStartGame(){
        testee.startGame(TEST_PLAYER_1);
        assertEquals(DECK_SIZE, testee.getCurrentPlayer().getDeckOfCards().size());
        assertEquals(DECK_SIZE, testee.getOpponentPlayer().getDeckOfCards().size());
    }

    @Test
    public void startGameShouldOnlyWorkIfGameIsInStartPhase(){
        testee.setRoundState(StateEnum.DRAW_PHASE);
        testee.startGame(TEST_PLAYER_1);
        verifyStatic(never());
        CardCreator.giveDeckOfCards();
    }

    @Test
    public void drawHiddenShouldChangeTheStateIfActorWorkedCorrectly() throws Exception {
        testee.startGame(TEST_PLAYER_1);
        settingUpMocksForDraw(true);
        StateEnum stateBefore = testee.getRoundState();
        testee.drawHidden();
        verifyThatActorIsCalledCorrect();
        assertNotEquals(testee.getRoundState(), stateBefore);
        assertEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    @Test
    @Ignore
    public void drawHiddenShouldNotChangeTheStateIfActorFails() throws Exception {
        testee.startGame(TEST_PLAYER_1);
        settingUpMocksForDraw(false);
        StateEnum stateBefore = testee.getRoundState();
        testee.drawHidden();
        verifyThatActorIsCalledCorrect();
        assertEquals(testee.getRoundState(), stateBefore);
        assertNotEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    @Test
    public void drawHiddenShouldNotChangeTheStateIfActorTrowsException() throws Exception {
        testee.startGame(TEST_PLAYER_1);
        initMocks(this);
        PowerMockito.mockStatic(Patterns.class);
        PowerMockito.when(Patterns.ask(any(ActorRef.class), any(MasterMessage.class), any(Timeout.class))).thenReturn(null);
        PowerMockito.mockStatic(Await.class);
        PowerMockito.when(Await.result(any(), any(FiniteDuration.class))).thenThrow(Exception.class);
        StateEnum stateBefore = testee.getRoundState();
        testee.drawHidden();
        verifyThatActorIsCalledCorrect();
        assertEquals(testee.getRoundState(), stateBefore);
        assertNotEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    @Test
    public void drawOpenShouldChangeTheStateIfActorWorkedCorrectly() throws Exception {
        testee.startGame(TEST_PLAYER_1);
        settingUpMocksForDraw(true);
        StateEnum stateBefore = testee.getRoundState();
        testee.drawOpen();
        verifyThatActorIsCalledCorrect();
        assertNotEquals(testee.getRoundState(), stateBefore);
        assertEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    @Test
    @Ignore
    public void drawOpenShouldNotChangeTheStateIfActorFails() throws Exception {
        testee.startGame(TEST_PLAYER_1);
        settingUpMocksForDraw(false);
        StateEnum stateBefore = testee.getRoundState();
        testee.drawOpen();
        verifyThatActorIsCalledCorrect();
        assertEquals(testee.getRoundState(), stateBefore);
        assertNotEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }


    @Test
    public void drawOpenShouldNotChangeTheStateIfActorTrowsException() throws Exception {
        testee.startGame(TEST_PLAYER_1);
        initMocks(this);
        PowerMockito.mockStatic(Patterns.class);
        PowerMockito.when(Patterns.ask(any(ActorRef.class), any(MasterMessage.class), any(Timeout.class))).thenReturn(null);
        PowerMockito.mockStatic(Await.class);
        PowerMockito.when(Await.result(any(), any(FiniteDuration.class))).thenThrow(Exception.class);
        StateEnum stateBefore = testee.getRoundState();
        testee.drawOpen();
        verifyThatActorIsCalledCorrect();
        assertEquals(testee.getRoundState(), stateBefore);
        assertNotEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    @Test
    public void discardShouldChangeThePlayerIfActorWorkedCorrectlyAndDeckIsNotEmpty() throws Exception {
        testee.startGame(TEST_PLAYER_1);
        settingUpMocksForDraw(true);
        IPlayer playerBefore = testee.getCurrentPlayer();
        testee.discard(TEST_CARD);
        verifyThatActorIsCalledCorrect();
        assertNotEquals(testee.getRoundState(), playerBefore);
        assertEquals(testee.getRoundState(), StateEnum.DRAW_PHASE);
    }

    @Test
    public void discardShouldEndTheGameIfPlayerHasNoCardsLeft() throws Exception {
        testee.startGame(TEST_PLAYER_1);
        IRoundState state = new RoundState();
        state.setState(StateEnum.PLAYER_TURN_FINISHED);
        settingUpMocksForDraw(new DiscardMessage(state, testee.getDiscardPile(), TEST_CARD, testee.getCurrentPlayer()));
        testee.drawHidden();
        testee.getCurrentPlayer().setPhaseDone(true);
        testee.getCurrentPlayer().setDeckOfCards(new DeckOfCards());
        testee.setRoundState(StateEnum.PLAYER_TURN_FINISHED);
        int phaseNumber = testee.getCurrentPlayer().getPhase().getPhaseNumber();
        testee.discard(TEST_CARD);
        assertNotEquals(phaseNumber, testee.getCurrentPlayer().getPhase().getPhaseNumber());
    }

    @Test
    public void discardShouldNotChangeThePlayerIfActorFails() throws Exception {
        testee.startGame(TEST_PLAYER_1);
        settingUpMocksForDraw(false);
        IPlayer playerBefore = testee.getCurrentPlayer();
        testee.discard(TEST_CARD);
        verifyThatActorIsCalledCorrect();
        assertEquals(testee.getCurrentPlayer(), playerBefore);
        assertNotEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    @Test
    public void playerShouldReachThePhaseDonePhaseAfterDrawWithLaidDownPhase() throws Exception {
        testee.startGame(TEST_PLAYER_1);
        settingUpMocksForDraw(true);

    }


    @Test
    public void discardShouldNotChangeTheStateIfActorTrowsException() throws Exception {
        testee.startGame(TEST_PLAYER_1);
        initMocks(this);
        PowerMockito.mockStatic(Patterns.class);
        PowerMockito.when(Patterns.ask(any(ActorRef.class), any(MasterMessage.class), any(Timeout.class))).thenReturn(null);
        PowerMockito.mockStatic(Await.class);
        PowerMockito.when(Await.result(any(), any(FiniteDuration.class))).thenThrow(Exception.class);
        StateEnum stateBefore = testee.getRoundState();
        testee.discard(TEST_CARD);
        verifyThatActorIsCalledCorrect();
        assertEquals(testee.getRoundState(), stateBefore);
        assertNotEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    private void verifyThatActorIsCalledCorrect() throws Exception {
        verifyStatic();
        Patterns.ask(any(ActorRef.class), any(MasterMessage.class), any(Timeout.class));
        verifyStatic();
        Await.result(any(), any(FiniteDuration.class));
    }

    private void settingUpMocksForDraw(Object ret) throws Exception {
        initMocks(this);
        PowerMockito.mockStatic(Patterns.class);
        PowerMockito.when(Patterns.ask(any(ActorRef.class), any(MasterMessage.class), any(Timeout.class))).thenReturn(null);
        PowerMockito.mockStatic(Await.class);
        PowerMockito.when(Await.result(any(), any(FiniteDuration.class))).thenReturn(ret);
    }


    private void mockCardCreator(){
        initMocks(this);
        PowerMockito.mockStatic(CardCreator.class);
        PowerMockito.when(CardCreator.giveDeckOfCards()).thenReturn(new DeckOfCards(addHundredCards()));
    }

    private List<ICard> addHundredCards() {
        List<ICard> cards = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            cards.add(DECK_INIT_CARD);
        }
        return cards;
    }

}