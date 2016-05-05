package persistence.couchDB;

import controller.UIController;
import controller.impl.Controller;
import model.player.IPlayer;
import model.player.impl.Player;
import org.junit.Before;
import org.junit.BeforeClass;
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
    public void saveAndLoadFromDatabaseWorksCorrectly() {
        System.out.println("in: " + controller.toString());
        testee.saveGame(controller);
        UIController dbController = testee.loadGame(player);
        System.out.println("out: " + dbController.toString());
        assertEquals(PLAYER_NAME, dbController.getPlayers()[0].getPlayerName());
    }


}