package persistence.couch;

import controller.UIController;
import controller.impl.ActorController;
import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.player.IPlayer;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;
import model.state.StateEnum;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 21.04.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CouchDbDAOIntegrationTest {

    private static final String PLAYER_NAME = "test_player1";
    private static final String SECOND_PLAYER_NAME = "test_player2";
    private static final int STATE_ENUM_SIZE = 4;
    private static final int MAX_RANDOM_PHASE_INCREASE = 3;
    private static final int MIN_RANDOM = 1;
    private static final int MAX_RANDOM_REMOVE_FROM_PLAYERS_DECK = 6;
    private static final int MAX_RANDOM_STACK_SIZE = 6;
    private static CouchDbDAO testee;

    private UIController controller;
    private Random random;

    @BeforeClass
    public static void beforeClass() {
        testee = new CouchDbDAO();
    }

    @Before
    public void setUp() {
        controller = new ActorController();
        random = new Random();
    }

    private UIController initController(String playerName) {
        UIController controller = new ActorController();
        controller.startGame(playerName);
        return controller;
    }

    @Test
    public void saveAndLoadFromDatabaseWorksCorrectly() {
        controller = initController(PLAYER_NAME);
        changeStateOfController(controller);
        testee.saveGame(controller);
        UIController dbController = testee.loadGame(controller.getCurrentPlayer());
        checkControllerStateShouldEqual(dbController, controller);
    }

    @Test
    public void saveAndLoadAnotherPlayerWorksCorrectly() {
        testee.saveGame(initController(PLAYER_NAME));
        controller = initController(SECOND_PLAYER_NAME);
        changeStateOfController(controller);
        testee.saveGame(controller);
        UIController dbController = testee.loadGame(controller.getCurrentPlayer());
        checkControllerStateShouldEqual(controller, dbController);
    }

    @Test
    public void dbShouldUpdateExistingControllerInDb() {
        controller = initController(PLAYER_NAME);
        UIController secondController = initController(PLAYER_NAME);
        changeStateOfController(secondController);
        testee.saveGame(controller);
        UIController dbController1 = testee.loadGame(controller.getCurrentPlayer());
        testee.saveGame(secondController);
        UIController dbController2 = testee.loadGame(controller.getCurrentPlayer());
        checkControllerStateShouldNotEqual(dbController1, dbController2);
        checkControllerStateShouldEqual(secondController, dbController2);
    }

    private void checkControllerStateShouldEqual(UIController expected, UIController actual) {
        assertFalse(expected == actual);
        assertEquals(expected.getAllStacks(), actual.getAllStacks());
        assertEquals(expected.getCurrentPlayer(), actual.getCurrentPlayer());
        assertEquals(expected.getRoundState(), actual.getRoundState());
        assertEquals(expected.getDiscardPile(), actual.getDiscardPile());
        assertEquals(expected.getDrawPile(), actual.getDrawPile());
    }

    private void checkControllerStateShouldNotEqual(UIController notExpected, UIController actual) {
        assertFalse(notExpected == actual);
        assertEquals(notExpected.getCurrentPlayer(), actual.getCurrentPlayer());
        assertNotEquals(notExpected.getAllStacks(), actual.getAllStacks());
        assertNotEquals(notExpected.getDiscardPile(), actual.getDiscardPile());
        assertNotEquals(notExpected.getDrawPile(), actual.getDrawPile());
    }

    private void changeStateOfController(UIController controller) {
        addStacksToController(controller);
        IPlayer[] p = controller.getPlayersArray();
        p[0] = removeCardsOfPlayersHand(p[0]);
        p[1] = removeCardsOfPlayersHand(p[1]);
        controller.setRoundState(StateEnum.byOrdinal(random.nextInt(STATE_ENUM_SIZE)));

    }

    private IPlayer removeCardsOfPlayersHand(IPlayer player) {
        for (int i = 0; i < random.nextInt(MAX_RANDOM_PHASE_INCREASE - MIN_RANDOM) + MIN_RANDOM; i++) {
            player.nextPhase();
        }
        IDeckOfCards tmp = player.getDeckOfCards();
        for (int i = 0; i < random.nextInt(MAX_RANDOM_REMOVE_FROM_PLAYERS_DECK - MIN_RANDOM) + MIN_RANDOM; i++) {
            tmp.removeFirst();
        }
        player.setDeckOfCards(tmp);
        return player;
    }

    private void addStacksToController(UIController controller) {
        List<ICardStack> stacks = new LinkedList<>();
        stacks.add(new ColorStack(getDeckOfCards()));
        stacks.add(new ColorStack(getDeckOfCards()));
        stacks.add(new ColorStack(getDeckOfCards()));
        controller.setAllStacks(stacks);
    }

    private IDeckOfCards getDeckOfCards() {
        IDeckOfCards cards = new DeckOfCards();
        for (int i = 0; i < random.nextInt(MAX_RANDOM_STACK_SIZE - MIN_RANDOM) + MIN_RANDOM; i++) {
            cards.add(new Card(CardValue.EIGHT, CardColor.GREEN));
        }
        return cards;
    }
}