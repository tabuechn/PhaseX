package persistence.couchDB;

import controller.UIController;
import controller.impl.Controller;
import model.deck.IDeckOfCards;
import model.player.IPlayer;
import model.player.impl.Player;
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

    public static final String PLAYER_NAME = "player1234";
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
        IDeckOfCards cards = controller.getCurrentPlayersHand();
        testee.saveCardToDB(cards, PLAYER_NAME);
        System.out.println("in: " + cards);
        IDeckOfCards dbCards = testee.getCardFromDB(PLAYER_NAME);
        System.out.println("out: " + dbCards);
        assertEquals(cards, dbCards);
    }

}