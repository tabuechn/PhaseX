package controller.impl;

import model.card.CardColor;
import model.card.ICard;
import model.card.impl.Card;
import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
import model.phase.impl.Phase2;
import model.phase.impl.Phase5;
import model.player.impl.Player;
import model.stack.ICardStack;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Tarek on 26.09.2015. Be grateful for this superior Code!
 */
@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    private final int NUMBER_OF_PLAYERS = 2;

    @InjectMocks
    private Controller testee = new Controller(NUMBER_OF_PLAYERS);

    @Mock
    private Player playerInPhase2;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testStartingTheGame() {
        assertEquals(testee.getRoundState().toString(), "StartPhase");
        testee.startGame();
        assertEquals(testee.getRoundState().toString(), "DrawPhase");
        assertEquals(testee.getCurrentPlayer().getPlayerNumber(), 0);
        assertEquals(testee.getCurrentPlayersHand().size(), 10);
        assertFalse(testee.currentPlayerHasNoCards());
        assertEquals(testee.getAllStacks().size(), 0);
    }

    @Test
    public void testCardDecks() {
        testee.startGame();
        assertEquals(testee.getDiscardPile().size(), 0);
        assertEquals(testee.getDrawPile().size(), (9 * 4 * 2) - 10 * NUMBER_OF_PLAYERS);
    }

    @Test
    public void drawTestHiddenTest() {
        testee.startGame();
        testee.drawHidden();
        assertEquals(testee.getCurrentPlayersHand().size(), 11);
        assertEquals(testee.getDrawPile().size(), (9 * 4 * 2) - 10 * NUMBER_OF_PLAYERS - 1);
        assertEquals(testee.getRoundState().toString(), "PlayerTurnNotFinished");
    }

    @Test
    public void drawOpenWithEmptyPileTest() {
        testee.startGame();
        testee.drawOpen();
        assertEquals(testee.getCurrentPlayersHand().size(), 10);
        assertEquals(testee.getDrawPile().size(), (9 * 4 * 2) - 10 * NUMBER_OF_PLAYERS);
        assertEquals(testee.getRoundState().toString(), "DrawPhase");
    }

    @Test
    public void discardTest() {
        testee.startGame();
        testee.drawHidden();
        ICard cardToDiscard = testee.getCurrentPlayersHand().get(0);
        assertEquals(testee.getCurrentPlayer().getPlayerNumber(), 0);
        testee.discard(cardToDiscard);
        assertEquals(testee.getCurrentPlayer().getPlayerNumber(), 1);
        assertEquals(testee.getDiscardPile().size(), 1);

    }

    @Test
    public void drawOpenWithNonEmptyPileTest() {
        testee.startGame();
        testee.drawHidden();
        ICard cardToDiscard = testee.getCurrentPlayersHand().get(0);
        assertEquals(testee.getCurrentPlayer().getPlayerNumber(), 0);
        testee.discard(cardToDiscard);
        assertEquals(testee.getCurrentPlayer().getPlayerNumber(), 1);
        assertEquals(testee.getDiscardPile().size(), 1);
        testee.drawOpen();
        assertEquals(testee.getCurrentPlayersHand().size(), 11);
        assertEquals(testee.getRoundState().toString(), "PlayerTurnNotFinished");
    }

    @Test
    public void testGetRoundState() {
        assertEquals(testee.getRoundState().toString(), "StartPhase");
        testee.startGame();
        assertEquals(testee.getRoundState().toString(), "DrawPhase");
    }

    @Test
    public void playerChangeAfterDiscard() {
        testee.startGame();

        assertEquals(testee.getCurrentPlayer().getPlayerNumber(), 0);
        testee.drawHidden();
        ICard cardToDiscard = testee.getCurrentPlayersHand().get(0);
        testee.discard(cardToDiscard);

        assertEquals(testee.getCurrentPlayer().getPlayerNumber(), 1);
        testee.drawHidden();
        cardToDiscard = testee.getCurrentPlayersHand().get(0);
        testee.discard(cardToDiscard);

        assertEquals(testee.getCurrentPlayer().getPlayerNumber(), 0);

    }

    @Test
    public void playPhase1Test() {
        testee.startGame();
        testee.drawHidden();
        assertEquals(testee.getCurrentPlayersHand().size(), 11);
        testee.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 3, 4, 5, 6})));
        assertEquals(testee.getCurrentPlayersHand().size(), 10);
        testee.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2})));
        assertEquals(testee.getCurrentPlayersHand().size(), 4);
        assertEquals(testee.getAllStacks().size(), 2);
    }

    @Test
    public void playPhase2Test() {
        testee.startGame();
        testee.drawHidden();
        assertEquals(testee.getCurrentPlayersHand().size(), 11);
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 3, 4, 5, 6})));
        assertEquals(testee.getCurrentPlayersHand().size(), 10);
        testee.playPhase(fillDeck(createYellowCards(new int[]{1, 2, 3, 4, 5, 6})));
        assertEquals(testee.getCurrentPlayersHand().size(), 4);
        assertEquals(testee.getAllStacks().size(), 1);
        assertEquals(testee.getRoundState().toString(), "PlayerTurnFinished");
    }

    @Test
    public void playPhase3Test() {
        testee.startGame();
        testee.drawHidden();
        assertEquals(testee.getCurrentPlayersHand().size(), 11);
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 3, 4, 5, 6})));
        assertEquals(testee.getCurrentPlayersHand().size(), 10);
        testee.playPhase(fillDeck(createYellowCards(new int[]{1, 2, 3, 4, 5, 6})));
        assertEquals(testee.getCurrentPlayersHand().size(), 4);
        assertEquals(testee.getAllStacks().size(), 1);
        assertEquals(testee.getRoundState().toString(), "PlayerTurnFinished");
    }

    @Test
    public void playPhase4Test() {
        testee.startGame();
        testee.drawHidden();
        assertEquals(testee.getCurrentPlayersHand().size(), 11);
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 2, 4, 5, 6})));
        assertEquals(testee.getCurrentPlayersHand().size(), 10);
        testee.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 2, 2, 2, 2})));
        assertEquals(testee.getCurrentPlayersHand().size(), 4);
        assertEquals(testee.getAllStacks().size(), 2);
        assertEquals(testee.getRoundState().toString(), "PlayerTurnFinished");
    }

    @Test
    public void playPhase5Test() {
        testee.startGame();
        testee.drawHidden();
        assertEquals(testee.getCurrentPlayersHand().size(), 11);
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 2, 1, 5, 6})));
        assertEquals(testee.getCurrentPlayersHand().size(), 10);
        testee.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 1, 2, 2, 2, 2})));
        assertEquals(testee.getCurrentPlayersHand().size(), 2);
        assertEquals(testee.getAllStacks().size(), 2);
        assertTrue(testee.getCurrentPlayer().isPhaseDone());
        assertEquals(testee.getRoundState().toString(), "PlayerTurnFinished");
    }

    @Test
    public void addToPhase1Test() {
        testee.startGame();
        testee.drawHidden();
        assertEquals(testee.getCurrentPlayersHand().size(), 11);
        testee.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 2, 1, 5, 6})));
        assertEquals(testee.getCurrentPlayersHand().size(), 10);
        testee.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2})));
        assertEquals(testee.getCurrentPlayersHand().size(), 4);
        assertEquals(testee.getAllStacks().size(), 2);
        ICardStack testStack = testee.getAllStacks().get(0);
        int testNumber = testStack.getList().get(0).getNumber();
        assertEquals(testee.getCurrentPlayersHand().size(), 4);
        testee.addToFinishedPhase(new Card(testNumber, CardColor.YELLOW), testStack);
        assertEquals(testee.getAllStacks().get(0).getList().size(), 4);
        assertEquals(testee.getCurrentPlayersHand().size(), 3);
    }

    @Test
    public void addToPhase1TestAndDiscard() {
        testee.startGame();
        testee.drawHidden();
        assertEquals(testee.getCurrentPlayersHand().size(), 11);
        testee.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 2, 1, 5, 6})));
        assertEquals(testee.getCurrentPlayersHand().size(), 10);
        testee.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2})));
        assertEquals(testee.getCurrentPlayersHand().size(), 4);
        assertEquals(testee.getAllStacks().size(), 2);
        ICardStack testStack = testee.getAllStacks().get(0);
        int testNumber = testStack.getList().get(0).getNumber();
        assertEquals(testee.getCurrentPlayersHand().size(), 4);
        testee.addToFinishedPhase(new Card(testNumber, CardColor.YELLOW), testStack);
        assertEquals(testee.getAllStacks().get(0).getList().size(), 4);
        assertEquals(testee.getCurrentPlayersHand().size(), 3);
        testee.discard(new Card(6, CardColor.YELLOW));
    }

    @Test
    public void getAllPlayersMap() {
        testee.startGame();
        testee.drawHidden();
        Map<String, Integer> playersAndCards = testee.getNumberOfCardsForAllPlayers();
        assertTrue(playersAndCards.containsKey("Player2"));
        playersAndCards.containsValue(10);
    }

    @Test
    public void drawAfterPlayPhase() {
        testee.startGame();
        testee.drawHidden();
        testee.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 3, 4, 5, 6})));
        testee.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2})));
        assertEquals(testee.getCurrentPlayersHand().size(), 4);
        testee.discard(new Card(3, CardColor.YELLOW));
        assertEquals(testee.getCurrentPlayer().getPlayerNumber(), 1);
        testee.drawHidden();
        testee.discard(testee.getCurrentPlayersHand().get(0));
        assertEquals(testee.getCurrentPlayer().getPlayerNumber(), 0);
        assertTrue(testee.getCurrentPlayer().isPhaseDone());
        testee.drawHidden();
    }

    @Test
    public void roundEndTest() {
        testee.startGame();
        testee.drawHidden();
        testee.getCurrentPlayer().setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 6})));
        testee.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2})));
        assertEquals(testee.getCurrentPlayersHand().size(), 1);
        testee.discard(new Card(6, CardColor.YELLOW));
        assertEquals(testee.getRoundState().toString(), "DrawPhase");
        assertEquals(testee.getCurrentPlayer().getPhase().getPhaseNumber(), Phase2.PHASE_NUMBER);
    }

    @Test
    public void gameEndTest() {
        testee.startGame();
        testee.drawHidden();
        testee.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 1, 2, 2, 2, 2, 6})));
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer().nextPhase();

        assertEquals(testee.getCurrentPlayer().getPhase().getPhaseNumber(), Phase5.PHASE_NUMBER);
        testee.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 1, 2, 2, 2, 2})));
        assertEquals(testee.getCurrentPlayersHand().size(), 1);
        testee.discard(new Card(6, CardColor.YELLOW));
        assertEquals(testee.getRoundState().toString(), "EndPhase");
        testee.startGame();
        assertEquals(testee.getRoundState().toString(), "DrawPhase");
    }

    @Test
    public void endWithAddToStack() {
        testee.startGame();
        testee.drawHidden();
        testee.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 1, 2, 2, 2, 2, 2})));

        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer().nextPhase();
        testee.getCurrentPlayer().nextPhase();

        assertEquals(testee.getCurrentPlayer().getPhase().getPhaseNumber(), Phase5.PHASE_NUMBER);
        testee.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 1, 2, 2, 2, 2})));
        assertEquals(testee.getCurrentPlayersHand().size(), 1);
        testee.addToFinishedPhase(new Card(2, CardColor.YELLOW), testee.getAllStacks().get(1));
        assertEquals(testee.getRoundState().toString(), "EndPhase");
    }

    @Test
    public void reshuffleTest() {
        testee.startGame();
        testee.drawHidden();
        ICard testCard = testee.getCurrentPlayersHand().get(0);
        testee.discard(testCard);
        assertTrue(testee.getDiscardPile().contains(testCard));
        testee.drawHidden();
        ICard testCard2 = testee.getCurrentPlayersHand().get(0);
        testee.getDiscardPile().addAll(testee.getDrawPile());
        testee.getDrawPile().removeAll(testee.getDrawPile());
        testee.getDrawPile().add(new Card(1, CardColor.GREEN));
        testee.discard(testCard2);
        testee.drawHidden();
        assertEquals(testee.getDiscardPile().size(), 1);
        assertEquals(testee.getDiscardPile().get(0), testCard2);
    }

    @Test
    public void checkIfNumberOfPlayersIsReturnedCorrectly() {
        testee = new Controller(NUMBER_OF_PLAYERS + 2);
        assertTrue(NUMBER_OF_PLAYERS + 2 == testee.getPlayerCount());

    }

    private ICard[] createYellowCards(int[] numbers) {
        ICard[] returnValue = new ICard[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            returnValue[i] = new Card(numbers[i], CardColor.YELLOW);
        }
        return returnValue;
    }

    private IDeckOfCards fillDeck(ICard[] cards) {
        return new DeckOfCards(Arrays.asList(cards));
    }

}