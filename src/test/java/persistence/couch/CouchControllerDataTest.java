package persistence.couch;

import controller.UIController;
import controller.impl.ActorController;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * If everything works right this class was
 * created by Konraifen88 on 21.04.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
@Ignore
public class CouchControllerDataTest {

    private static final String PLAYER_NAME = "TestPlayer1";

    private CouchControllerData testee;
    private UIController controller;

    @Before
    public void setUp() throws Exception {
        controller = new ActorController();
        controller.startGame(PLAYER_NAME);
        testee = new CouchControllerData();
    }

    @Test
    public void setterAndGetterForControllerWorkingCorrectly() {
        testee.setController(controller);
        assertEquals(PLAYER_NAME, testee.getController().getPlayers()[0].getPlayerName());
    }

    @Test
    public void setterAndGetterForPlayerNameWorkingCorrectly() {
        testee.setPlayerName(PLAYER_NAME);
        assertEquals(PLAYER_NAME, testee.getPlayerName());
    }

    @Test
    public void nameIsCorrectlySetWhenUsingConstructorWithParam() {
        testee = new CouchControllerData(controller);
        assertEquals(PLAYER_NAME, testee.getPlayerName());
    }

}