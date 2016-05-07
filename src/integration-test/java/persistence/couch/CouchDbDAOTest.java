package persistence.couch;

import controller.UIController;
import controller.impl.Controller;
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

    private static final String PLAYER_NAME = "player1";
    private static final String SECOND_PLAYER_NAME = "player2";
    private static CouchDbDAO testee;

    private UIController controller1;
    private UIController controller2;

    @BeforeClass
    public static void beforeClass() {
        testee = new CouchDbDAO();
    }

    @Before
    public void setUp() {
        controller1 = initController(PLAYER_NAME);
        controller2 = initController(SECOND_PLAYER_NAME);
    }

    private UIController initController(String playerName) {
        UIController controller = new Controller(2);
        controller.startGame(playerName);
        return controller;
    }

    @Test
    public void saveAndLoadFromDatabaseWorksCorrectly() {
        System.out.println("in: " + controller1.toString());
        testee.saveGame(controller1);
        UIController dbController = testee.loadGame(controller1.getCurrentPlayer());
        System.out.println("out: " + dbController.toString());
        assertEquals(PLAYER_NAME, dbController.getPlayers()[0].getPlayerName());
        assertEquals(controller1.getCurrentPlayersHand(), dbController.getCurrentPlayersHand());
    }

    @Test
    public void saveAndLoadAnotherPlayerWorksCorrectly() {
        testee.saveGame(controller2);
        UIController dbController = testee.loadGame(controller2.getCurrentPlayer());
        assertEquals(controller2.getCurrentPlayer().getPlayerName(), dbController.getCurrentPlayer().getPlayerName());
        assertEquals(controller2.getCurrentPlayersHand(), dbController.getCurrentPlayersHand());
    }


}