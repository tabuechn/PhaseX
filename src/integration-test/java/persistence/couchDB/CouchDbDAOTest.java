package persistence.couchDB;

import controller.UIController;
import controller.impl.Controller;
import model.deck.IDeckOfCards;
import model.player.IPlayer;
import model.player.impl.Player;
import model.stack.ICardStack;
import model.stack.impl.ColorStack;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * If everything works right this class was
 * created by Konraifen88 on 21.04.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CouchDbDAOTest {

    private static final String PLAYER_NAME = "player1234";
    private static CouchDbDAO testee;

    private UIController controller;
    private IPlayer player;

    @BeforeClass
    public static void beforeClass() {
        testee = new CouchDbDAO();
    }

    @Before
    public void setUp() {
        controller = new Controller(2);
        controller.startGame(PLAYER_NAME);
        player = new Player(0);
        player.setName(PLAYER_NAME);
    }

    @Test
    @Ignore
    public void saveAndLoadFromDatabaseWorksCorrectly() {
        testee.saveGame(controller);
        assertEquals(PLAYER_NAME, testee.loadGame(player).getPlayers()[0].getPlayerName());
    }

    @Test
    public void saveDeckOfCards() {
        IDeckOfCards play = controller.getCurrentPlayer().getDeckOfCards();
        ICardStack stack = new ColorStack(play);
        testee.saveCardToDB(stack, PLAYER_NAME);
        System.out.println("in: " + stack);
        System.out.println("in type: " + stack.getStackType());
        System.out.println("in cards: " + stack.getList().toString());
        ICardStack dbStack = testee.getCardFromDB(PLAYER_NAME);
        System.out.println("out: " + dbStack);
        System.out.println("out type: " + dbStack.getStackType());
        System.out.println("out: " + dbStack.getList().toString());
        assertEquals(stack, dbStack);
    }

}