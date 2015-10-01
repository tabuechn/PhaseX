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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Tarek on 26.09.2015. Be grateful for this superior Code!
 */
@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    private final int NUMBEROFPLAYERS = 2;

    @InjectMocks
    private Controller testController = new Controller(NUMBEROFPLAYERS);

    @Mock
    private Player playerInPhase2;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void testStartingTheGame() {
        assertEquals(testController.getRoundState().toString(), "StartPhase");
        testController.startGame();
        assertEquals(testController.getRoundState().toString(), "DrawPhase");
        assertEquals(testController.getCurrentPlayer().getPlayerNumber(), 0);
        assertEquals(testController.getCurrentPlayersHand().size(), 10);
        assertFalse(testController.currentPlayerHasNoCards());
        assertEquals(testController.getAllStacks().size(), 0);
    }

    @Test
    public void testCardDecks() {
        testController.startGame();
        assertEquals(testController.getDiscardPile().size(), 0);
        assertEquals(testController.getDrawPile().size(), (9 * 4 * 2) - 10 * NUMBEROFPLAYERS);
    }

    @Test
    public void drawTestHiddenTest() {
        testController.startGame();
        testController.drawHidden();
        assertEquals(testController.getCurrentPlayersHand().size(), 11);
        assertEquals(testController.getDrawPile().size(), (9 * 4 * 2) - 10 * NUMBEROFPLAYERS - 1);
        assertEquals(testController.getRoundState().toString(), "PlayerTurnNotFinished");
    }

    @Test
    public void drawOpenWithEmptyPileTest() {
        testController.startGame();
        testController.drawOpen();
        assertEquals(testController.getCurrentPlayersHand().size(), 10);
        assertEquals(testController.getDrawPile().size(), (9 * 4 * 2) - 10 * NUMBEROFPLAYERS);
        assertEquals(testController.getRoundState().toString(), "DrawPhase");
    }

    @Test
    public void discardTest() {
        testController.startGame();
        testController.drawHidden();
        ICard cardToDiscard = testController.getCurrentPlayersHand().get(0);
        assertEquals(testController.getCurrentPlayer().getPlayerNumber(), 0);
        testController.discard(cardToDiscard);
        assertEquals(testController.getCurrentPlayer().getPlayerNumber(), 1);
        assertEquals(testController.getDiscardPile().size(), 1);

    }

    @Test
    public void drawOpenWithNonEmptyPileTest() {
        testController.startGame();
        testController.drawHidden();
        ICard cardToDiscard = testController.getCurrentPlayersHand().get(0);
        assertEquals(testController.getCurrentPlayer().getPlayerNumber(), 0);
        testController.discard(cardToDiscard);
        assertEquals(testController.getCurrentPlayer().getPlayerNumber(), 1);
        assertEquals(testController.getDiscardPile().size(), 1);
        testController.drawOpen();
        assertEquals(testController.getCurrentPlayersHand().size(), 11);
        assertEquals(testController.getRoundState().toString(), "PlayerTurnNotFinished");
    }

    @Test
    public void testGetRoundState() {
        assertEquals(testController.getRoundState().toString(), "StartPhase");
        testController.startGame();
        assertEquals(testController.getRoundState().toString(), "DrawPhase");
    }

    @Test
    public void playerChangeAfterDiscard() {
        testController.startGame();

        assertEquals(testController.getCurrentPlayer().getPlayerNumber(), 0);
        testController.drawHidden();
        ICard cardToDiscard = testController.getCurrentPlayersHand().get(0);
        testController.discard(cardToDiscard);

        assertEquals(testController.getCurrentPlayer().getPlayerNumber(), 1);
        testController.drawHidden();
        cardToDiscard = testController.getCurrentPlayersHand().get(0);
        testController.discard(cardToDiscard);

        assertEquals(testController.getCurrentPlayer().getPlayerNumber(), 0);

    }

    @Test
    public void playPhase1Test() {
        testController.startGame();
        testController.drawHidden();
        assertEquals(testController.getCurrentPlayersHand().size(), 11);
        testController.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 3, 4, 5, 6})));
        assertEquals(testController.getCurrentPlayersHand().size(), 10);
        testController.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2})));
        assertEquals(testController.getCurrentPlayersHand().size(), 4);
        assertEquals(testController.getAllStacks().size(), 2);
    }

    @Test
    public void playPhase2Test() {
        testController.startGame();
        testController.drawHidden();
        assertEquals(testController.getCurrentPlayersHand().size(), 11);
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 3, 4, 5, 6})));
        assertEquals(testController.getCurrentPlayersHand().size(), 10);
        testController.playPhase(fillDeck(createYellowCards(new int[]{1, 2, 3, 4, 5, 6})));
        assertEquals(testController.getCurrentPlayersHand().size(), 4);
        assertEquals(testController.getAllStacks().size(), 1);
        assertEquals(testController.getRoundState().toString(), "PlayerTurnFinished");
    }

    @Test
    public void playPhase3Test() {
        testController.startGame();
        testController.drawHidden();
        assertEquals(testController.getCurrentPlayersHand().size(), 11);
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 3, 4, 5, 6})));
        assertEquals(testController.getCurrentPlayersHand().size(), 10);
        testController.playPhase(fillDeck(createYellowCards(new int[]{1, 2, 3, 4, 5, 6})));
        assertEquals(testController.getCurrentPlayersHand().size(), 4);
        assertEquals(testController.getAllStacks().size(), 1);
        assertEquals(testController.getRoundState().toString(), "PlayerTurnFinished");
    }

    @Test
    public void playPhase4Test() {
        testController.startGame();
        testController.drawHidden();
        assertEquals(testController.getCurrentPlayersHand().size(), 11);
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 2, 4, 5, 6})));
        assertEquals(testController.getCurrentPlayersHand().size(), 10);
        testController.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 2, 2, 2, 2})));
        assertEquals(testController.getCurrentPlayersHand().size(), 4);
        assertEquals(testController.getAllStacks().size(), 2);
        assertEquals(testController.getRoundState().toString(), "PlayerTurnFinished");
    }

    @Test
    public void playPhase5Test() {
        testController.startGame();
        testController.drawHidden();
        assertEquals(testController.getCurrentPlayersHand().size(), 11);
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 2, 1, 5, 6})));
        assertEquals(testController.getCurrentPlayersHand().size(), 10);
        testController.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 1, 2, 2, 2, 2})));
        assertEquals(testController.getCurrentPlayersHand().size(), 2);
        assertEquals(testController.getAllStacks().size(), 2);
        assertTrue(testController.getCurrentPlayer().isPhaseDone());
        assertEquals(testController.getRoundState().toString(), "PlayerTurnFinished");
    }

    @Test
    public void addToPhase1Test() {
        testController.startGame();
        testController.drawHidden();
        assertEquals(testController.getCurrentPlayersHand().size(), 11);
        testController.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 2, 1, 5, 6})));
        assertEquals(testController.getCurrentPlayersHand().size(), 10);
        testController.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2})));
        assertEquals(testController.getCurrentPlayersHand().size(), 4);
        assertEquals(testController.getAllStacks().size(), 2);
        ICardStack testStack = testController.getAllStacks().get(0);
        int testNumber = testStack.getList().get(0).getNumber();
        assertEquals(testController.getCurrentPlayersHand().size(), 4);
        testController.addToFinishedPhase(new Card(testNumber, CardColor.YELLOW), testStack);
        assertEquals(testController.getAllStacks().get(0).getList().size(), 4);
        assertEquals(testController.getCurrentPlayersHand().size(), 3);
    }

    @Test
    public void addToPhase1TestAndDiscard() {
        testController.startGame();
        testController.drawHidden();
        assertEquals(testController.getCurrentPlayersHand().size(), 11);
        testController.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 2, 1, 5, 6})));
        assertEquals(testController.getCurrentPlayersHand().size(), 10);
        testController.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2})));
        assertEquals(testController.getCurrentPlayersHand().size(), 4);
        assertEquals(testController.getAllStacks().size(), 2);
        ICardStack testStack = testController.getAllStacks().get(0);
        int testNumber = testStack.getList().get(0).getNumber();
        assertEquals(testController.getCurrentPlayersHand().size(), 4);
        testController.addToFinishedPhase(new Card(testNumber, CardColor.YELLOW), testStack);
        assertEquals(testController.getAllStacks().get(0).getList().size(), 4);
        assertEquals(testController.getCurrentPlayersHand().size(), 3);
        testController.discard(new Card(6, CardColor.YELLOW));
    }

    @Test
    public void getAllPlayersMap() {
        testController.startGame();
        testController.drawHidden();
        Map<String, Integer> playersAndCards = testController.getNumberOfCardsForAllPlayers();
        assertTrue(playersAndCards.containsKey("Player2"));
        playersAndCards.containsValue(10);
    }

    @Test
    public void drawAfterPlayPhase() {
        testController.startGame();
        testController.drawHidden();
        testController.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 3, 4, 5, 6})));
        testController.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2})));
        assertEquals(testController.getCurrentPlayersHand().size(), 4);
        testController.discard(new Card(3, CardColor.YELLOW));
        assertEquals(testController.getCurrentPlayer().getPlayerNumber(), 1);
        testController.drawHidden();
        testController.discard(testController.getCurrentPlayersHand().get(0));
        assertEquals(testController.getCurrentPlayer().getPlayerNumber(), 0);
        assertTrue(testController.getCurrentPlayer().isPhaseDone());
        testController.drawHidden();
    }

    @Test
    public void roundEndTest() {
        testController.startGame();
        testController.drawHidden();
        testController.getCurrentPlayer().setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2, 6})));
        testController.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 2, 2, 2})));
        assertEquals(testController.getCurrentPlayersHand().size(), 1);
        testController.discard(new Card(6, CardColor.YELLOW));
        assertEquals(testController.getRoundState().toString(), "DrawPhase");
        assertEquals(testController.getCurrentPlayer().getPhase().getPhaseNumber(), Phase2.PHASE_NUMBER);
    }

    @Test
    public void gameEndTest() {
        testController.startGame();
        testController.drawHidden();
        testController.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 1, 2, 2, 2, 2, 6})));
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer().nextPhase();

        assertEquals(testController.getCurrentPlayer().getPhase().getPhaseNumber(), Phase5.PHASE_NUMBER);
        testController.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 1, 2, 2, 2, 2})));
        assertEquals(testController.getCurrentPlayersHand().size(), 1);
        testController.discard(new Card(6, CardColor.YELLOW));
        assertEquals(testController.getRoundState().toString(), "EndPhase");
        testController.startGame();
        assertEquals(testController.getRoundState().toString(), "DrawPhase");
    }

    @Test
    public void endWithAddToStack() {
        testController.startGame();
        testController.drawHidden();
        testController.getCurrentPlayer()
                .setDeckOfCards(fillDeck(createYellowCards(new int[]{1, 1, 1, 1, 2, 2, 2, 2, 2})));

        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer().nextPhase();
        testController.getCurrentPlayer().nextPhase();

        assertEquals(testController.getCurrentPlayer().getPhase().getPhaseNumber(), Phase5.PHASE_NUMBER);
        testController.playPhase(fillDeck(createYellowCards(new int[]{1, 1, 1, 1, 2, 2, 2, 2})));
        assertEquals(testController.getCurrentPlayersHand().size(), 1);
        testController.addToFinishedPhase(new Card(2, CardColor.YELLOW), testController.getAllStacks().get(1));
        assertEquals(testController.getRoundState().toString(), "EndPhase");
    }

    @Test
    public void reshuffleTest() {
        testController.startGame();
        testController.drawHidden();
        ICard testCard = testController.getCurrentPlayersHand().get(0);
        testController.discard(testCard);
        assertTrue(testController.getDiscardPile().contains(testCard));
        testController.drawHidden();
        ICard testCard2 = testController.getCurrentPlayersHand().get(0);
        testController.getDiscardPile().addAll(testController.getDrawPile());
        testController.getDrawPile().removeAll(testController.getDrawPile());
        testController.getDrawPile().add(new Card(1, CardColor.GREEN));
        testController.discard(testCard2);
        testController.drawHidden();
        assertEquals(testController.getDiscardPile().size(), 1);
        assertEquals(testController.getDiscardPile().get(0), testCard2);
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