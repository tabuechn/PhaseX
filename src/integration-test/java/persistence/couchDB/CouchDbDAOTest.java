package persistence.couchDB;

import controller.UIController;
import controller.impl.Controller;
import model.card.ICard;
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

    public static final String PLAYER_NAME = "player1";
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
        testee.saveDeck(controller);
        ICard card = controller.getCurrentPlayersHand().get(0);
        System.out.println("Card: " + card.getColor() + "\t" + card.getNumber());
        ICard dbCard = testee.findCardByUser(player);
        System.out.println("dbCard: " + dbCard.getColor() + "\t" + dbCard.getNumber());
        assertEquals(card, dbCard);
    }

}