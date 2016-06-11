package controller.impl;

import actors.message.*;
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
import model.player.impl.Player;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;
import model.stack.impl.StreetStack;
import model.state.IRoundState;
import model.state.StateEnum;
import model.state.impl.RoundState;
import org.junit.Before;
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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
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

    private static final String TEST_PLAYER_1_NAME = "Player1";
    private static final int DECK_SIZE = 10;
    private static final Card TEST_CARD = new Card(CardValue.NINE, CardColor.GREEN);
    private static final Card DECK_INIT_CARD = new Card(CardValue.EIGHT, CardColor.BLUE);
    private static final IDeckOfCards TEST_PILE = new DeckOfCards(Arrays.asList(TEST_CARD, TEST_CARD, TEST_CARD));
    private static final IDeckOfCards TEST_DECK = new DeckOfCards(Arrays.asList(DECK_INIT_CARD, DECK_INIT_CARD, DECK_INIT_CARD));
    private static final List<ICardStack> TEST_STACK_LIST = Arrays.asList(new ColorStack(TEST_DECK), new StreetStack(TEST_DECK));
    private static final ICardStack TEST_STACK = new ColorStack(TEST_DECK);
    private static final int TEST_STACK_NUMBER = 1;

    private IRoundState testState;
    private IPlayer testPlayer;

    @InjectMocks
    private ActorController testee;

    @Mock
    private IDeckOfCards drawPileMock;

    @Before
    public void setUp() {
        testee = new ActorController();
        testee.startGame(TEST_PLAYER_1_NAME);
        testPlayer = new Player(1);
        testPlayer.setDeckOfCards(new DeckOfCards());
        testState = new RoundState();
        testState.setState(StateEnum.DRAW_PHASE);
        mockCardCreator();
    }

    @Test
    public void playersShouldGet10CardsDuringStartGame(){
        assertEquals(DECK_SIZE, testee.getCurrentPlayer().getDeckOfCards().size());
        assertEquals(DECK_SIZE, testee.getOpponentPlayer().getDeckOfCards().size());
    }

    @Test
    public void startGameShouldOnlyWorkIfGameIsInStartPhase(){
        testee = new ActorController();
        testee.setRoundState(StateEnum.DRAW_PHASE);
        testee.startGame(TEST_PLAYER_1_NAME);
        verifyStatic(never());
        CardCreator.giveDeckOfCards();
    }

    @Test
    public void drawHiddenShouldChangeTheStateIfActorWorkedCorrectly() throws Exception {
        settingUpMocksForActor(buildNewDrawHiddenMessage());
        StateEnum stateBefore = testee.getRoundState();
        testee.drawHidden();
        verifyThatActorIsCalledCorrect();
        assertEquals(testPlayer, testee.getCurrentPlayer());
        assertNotEquals(testee.getRoundState(), stateBefore);
        assertEquals(TEST_PILE, testee.getDrawPile());
        assertEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    @Test
    public void drawHiddenShouldNotChangeTheStateIfActorFails() throws Exception {
        settingUpMocksForActor(false);
        StateEnum stateBefore = testee.getRoundState();
        testee.drawHidden();
        verifyThatActorIsCalledCorrect();
        assertEquals(testee.getRoundState(), stateBefore);
        assertNotEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    @Test
    public void drawHiddenShouldNotChangeTheStateIfActorTrowsException() throws Exception {
        settingUpActorMockThrowsException();
        StateEnum stateBefore = testee.getRoundState();
        testee.drawHidden();
        verifyThatActorIsCalledCorrect();
        assertEquals(testee.getRoundState(), stateBefore);
        assertNotEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    @Test
    public void drawOpenShouldChangeTheStateIfActorWorkedCorrectly() throws Exception {
        settingUpMocksForActor(buildNewDrawOpenMessage());
        StateEnum stateBefore = testee.getRoundState();
        testee.drawOpen();
        verifyThatActorIsCalledCorrect();
        assertEquals(testPlayer, testee.getCurrentPlayer());
        assertEquals(TEST_PILE, testee.getDiscardPile());
        assertNotEquals(testee.getRoundState(), stateBefore);
        assertEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    @Test
    public void drawOpenShouldNotChangeTheStateIfActorFails() throws Exception {
        settingUpMocksForActor(false);
        testee.drawOpen();
        verifyThatActorIsCalledCorrect();
        assertNotEquals(StateEnum.PLAYER_TURN_NOT_FINISHED, testee.getRoundState());
    }

    @Test
    public void drawOpenForPlayerWithDonePhaseShouldChangeToPlayerTurnFinishedPhase() throws Exception {
        testPlayer.setPhaseDone(true);
        settingUpMocksForActor(buildNewDrawOpenMessage());
        testee.drawOpen();
        verifyThatActorIsCalledCorrect();
        assertEquals(StateEnum.PLAYER_TURN_FINISHED, testee.getRoundState());
    }

    @Test
    public void drawOpenShouldNotChangeTheStateIfActorTrowsException() throws Exception {
        settingUpActorMockThrowsException();
        StateEnum stateBefore = testee.getRoundState();
        testee.drawOpen();
        verifyThatActorIsCalledCorrect();
        assertEquals(testee.getRoundState(), stateBefore);
        assertNotEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    @Test
    public void discardShouldChangeThePlayerIfActorWorkedCorrectlyAndDeckIsNotEmpty() throws Exception {
        settingUpMocksForActor(buildNewDiscardMessageContainingPlayerWithoutEmptyDeck());
        IPlayer playerBefore = testee.getCurrentPlayer();
        testee.discard(TEST_CARD);
        verifyThatActorIsCalledCorrect();
        assertEquals(TEST_PILE, testee.getDiscardPile());
        assertNotEquals(testPlayer, testee.getCurrentPlayer());
        assertNotEquals(testee.getRoundState(), playerBefore);
        assertEquals(testee.getRoundState(), StateEnum.DRAW_PHASE);
    }


    @Test
    public void discardShouldEndTheRoundIfPlayerHasNoCardsLeft() throws Exception {
        testPlayer.setPhaseDone(true);
        settingUpMocksForActor(buildNewDiscardMessage());
        int phaseNumber = testee.getCurrentPlayer().getPhase().getPhaseNumber();
        testee.discard(TEST_CARD);
        assertNotEquals(phaseNumber, testee.getCurrentPlayer().getPhase().getPhaseNumber());
        assertEquals(StateEnum.DRAW_PHASE, testee.getRoundState());
    }

    @Test
    public void discardShouldEndTheGameIfPlayerHasNoCardsLeftAndInPhase5() throws Exception {
        testPlayer.setPhaseDone(true);
        testPlayer.setPhase("Phase5");
        settingUpMocksForActor(buildNewDiscardMessage());
        testee.setRoundState(StateEnum.PLAYER_TURN_FINISHED);
        int phaseNumber = testee.getCurrentPlayer().getPhase().getPhaseNumber();
        testee.discard(TEST_CARD);
        assertNotEquals(phaseNumber, testee.getCurrentPlayer().getPhase().getPhaseNumber());
        assertEquals(StateEnum.END_PHASE, testee.getRoundState());
        assertEquals(testPlayer, testee.getWinner());
    }

    @Test
    public void discardShouldNotChangeThePlayerIfActorFails() throws Exception {
        settingUpMocksForActor(new Object());
        IPlayer playerBefore = testee.getCurrentPlayer();
        testee.discard(TEST_CARD);
        verifyThatActorIsCalledCorrect();
        assertEquals(testee.getCurrentPlayer(), playerBefore);
        assertNotEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    @Test
    public void discardShouldNotChangeTheStateIfActorTrowsException() throws Exception {
        settingUpActorMockThrowsException();
        StateEnum stateBefore = testee.getRoundState();
        testee.discard(TEST_CARD);
        verifyThatActorIsCalledCorrect();
        assertEquals(testee.getRoundState(), stateBefore);
        assertNotEquals(testee.getRoundState(), StateEnum.PLAYER_TURN_NOT_FINISHED);
    }

    @Test
    public void checkIfDrawPileIsSuccessfullyRefilledIfEmpty() throws Exception {
        settingUpMocksForActor(new DrawHiddenMessage(new DeckOfCards(), TEST_DECK, testPlayer, testState));
        testee.setDiscardPile(new DeckOfCards(Arrays.asList(TEST_CARD, DECK_INIT_CARD)));
        testee.drawHidden();
        verifyThatActorIsCalledCorrect();
        assertEquals(1, testee.getDrawPile().getCards().size());
        assertEquals(1, testee.getDiscardPile().getCards().size());
        assertEquals(TEST_CARD, testee.getDrawPile().get(0));
    }

    @Test
    public void playPhaseShouldSetPlayerAndStacksIfResultIsSuccessful() throws Exception {
        settingUpMocksForActor(new PlayPhaseMessage(testState, TEST_DECK, testPlayer, TEST_STACK_LIST));
        testee.playPhase(TEST_DECK);
        verifyThatActorIsCalledCorrect();
        assertEquals(TEST_STACK_LIST, testee.getAllStacks());
        assertEquals(testPlayer, testee.getCurrentPlayer());
    }

    @Test
    public void playPhaseShouldNotChangeTheStateIfActorTrowsException() throws Exception {
        settingUpActorMockThrowsException();
        testee.playPhase(TEST_DECK);
        verifyThatActorIsCalledCorrect();
        assertTrue(testee.getAllStacks().isEmpty());
        assertNotEquals(testPlayer, testee.getCurrentPlayer());
    }

    @Test
    public void addingSingleCardToPhaseShouldSetPlayerAndStack() throws Exception {
        testPlayer.setDeckOfCards(TEST_DECK);
        settingUpMocksForActor(new AddToPhaseMessage(testState, TEST_PILE, TEST_STACK, testPlayer));
        testee.setAllStacks(TEST_STACK_LIST);
        testee.addToFinishedPhase(TEST_CARD, TEST_STACK_NUMBER);
        verifyThatActorIsCalledCorrect();
        assertTrue(testee.getAllStacks().get(TEST_STACK_NUMBER) instanceof ColorStack);
        assertEquals(testPlayer, testee.getCurrentPlayer());
    }

    @Test
    public void addingMultipleCardsToPhaseShouldSetPlayerAndStack() throws Exception {
        testPlayer.setDeckOfCards(TEST_DECK);
        settingUpMocksForActor(new AddToPhaseMessage(testState, TEST_PILE, TEST_STACK, testPlayer));
        testee.setAllStacks(TEST_STACK_LIST);
        testee.addMultipleCardsToFinishedPhase(Arrays.asList(TEST_CARD, TEST_CARD), TEST_STACK_NUMBER);
        verifyThatActorIsCalledCorrect();
        assertTrue(testee.getAllStacks().get(TEST_STACK_NUMBER) instanceof ColorStack);
        assertEquals(testPlayer, testee.getCurrentPlayer());
    }


    @Test
    public void addToPhaseShouldNotChangeTheStateIfActorTrowsException() throws Exception {
        settingUpActorMockThrowsException();
        testee.setAllStacks(TEST_STACK_LIST);
        testee.addMultipleCardsToFinishedPhase(TEST_DECK, TEST_STACK_NUMBER);
        verifyThatActorIsCalledCorrect();
        assertEquals(TEST_DECK, testee.getAllStacks().get(TEST_STACK_NUMBER).getList());
        assertNotEquals(testPlayer, testee.getCurrentPlayer());
    }

    private void verifyThatActorIsCalledCorrect() throws Exception {
        verifyStatic();
        Patterns.ask(any(ActorRef.class), any(MasterMessage.class), any(Timeout.class));
        verifyStatic();
        Await.result(any(), any(FiniteDuration.class));
    }

    private void settingUpMocksForActor(Object ret) throws Exception {
        initMocks(this);
        PowerMockito.mockStatic(Patterns.class);
        PowerMockito.when(Patterns.ask(any(ActorRef.class), any(MasterMessage.class), any(Timeout.class))).thenReturn(null);
        PowerMockito.mockStatic(Await.class);
        PowerMockito.when(Await.result(any(), any(FiniteDuration.class))).thenReturn(ret);
    }


    private void settingUpActorMockThrowsException() throws Exception {
        initMocks(this);
        PowerMockito.mockStatic(Patterns.class);
        PowerMockito.when(Patterns.ask(any(ActorRef.class), any(MasterMessage.class), any(Timeout.class))).thenReturn(null);
        PowerMockito.mockStatic(Await.class);
        PowerMockito.when(Await.result(any(), any(FiniteDuration.class))).thenThrow(Exception.class);
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


    private DiscardMessage buildNewDiscardMessageContainingPlayerWithoutEmptyDeck() {
        testPlayer.setDeckOfCards(TEST_DECK);
        return buildNewDiscardMessage();
    }

    private DiscardMessage buildNewDiscardMessage() {
        //Change state, because after run state should be changed to drawPhase
        testState.setState(StateEnum.PLAYER_TURN_NOT_FINISHED);
        return new DiscardMessage(testState, TEST_PILE, TEST_CARD, testPlayer);
    }

    private DrawOpenMessage buildNewDrawOpenMessage() {
        return new DrawOpenMessage(TEST_PILE, TEST_DECK, testPlayer, testState);
    }

    private DrawHiddenMessage buildNewDrawHiddenMessage() {
        return new DrawHiddenMessage(TEST_PILE, TEST_DECK, testPlayer, testState);
    }

}