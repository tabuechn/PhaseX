package persistence.hibernate;

import model.phase.impl.Phase1;
import model.player.impl.Player;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Created by tabuechn on 19.04.2016.
 */
public class HibernateControllerDataTest {

    private HibernateControllerData hibernateControllerData;

    @Before
    public void setUp() throws Exception {
        hibernateControllerData = new HibernateControllerData();
    }


    @Test
    public void testGetAndSetStringID() throws Exception {
        hibernateControllerData.setStringID(1);
        assertEquals(hibernateControllerData.getStringID(), new Integer(1));

    }


    @Test
    public void testGetAndSetPlayer2() throws Exception {
        Player player2 = new Player(0);
        player2.setName("Herbert");
        hibernateControllerData.setPlayer2(player2);
        assertEquals(hibernateControllerData.getPlayer2().getPlayerName(), "Herbert");
    }


    @Test
    public void testGetAndSetPlayer1() throws Exception {
        Player player1 = new Player(0);
        player1.setName("Herbert");
        hibernateControllerData.setPlayer1(player1);
        assertEquals(hibernateControllerData.getPlayer1().getPlayerName(), "Herbert");
    }


    @Test
    public void testGetAndSetPlayer1Pile() throws Exception {
        hibernateControllerData.setPlayer1Pile("test");
        assertEquals(hibernateControllerData.getPlayer1Pile(), "test");
    }


    @Test
    public void testGetPlayer2Pile() throws Exception {
        hibernateControllerData.setPlayer2Pile("test");
        assertEquals(hibernateControllerData.getPlayer2Pile(), "test");
    }


    @Test
    public void testGetAndSetPlayer1PhaseString() throws Exception {
        Player player1 = new Player(0);
        hibernateControllerData.setPlayer1PhaseString(player1.getPhase().toString());
        assertEquals(hibernateControllerData.getPlayer1PhaseString(), player1.getPhase().toString());
    }


    @Test
    public void testGetAndSetPlayer2PhaseString() throws Exception {
        Player player2 = new Player(0);
        hibernateControllerData.setPlayer2PhaseString(player2.getPhase().toString());
        assertEquals(hibernateControllerData.getPlayer2PhaseString(), player2.getPhase().toString());
    }


    @Test
    public void testGetAndSetDiscardPile() throws Exception {
        hibernateControllerData.setDiscardPile("test");
        assertEquals(hibernateControllerData.getDiscardPile(), "test");
    }


    @Test
    public void testGetAndSetDrawPile() throws Exception {
        hibernateControllerData.setDrawPile("test");
        assertEquals(hibernateControllerData.getDrawPile(), "test");
    }


    @Test
    public void testGetRoundState() throws Exception {
        hibernateControllerData.setRoundState("test");
        assertEquals(hibernateControllerData.getRoundState(), "test");
    }

    @Test
    public void setPlayer1PhaseString() {
        hibernateControllerData.setPlayer1PhaseString(new Phase1());
        assertEquals("Phase1", hibernateControllerData.getPlayer1PhaseString());
    }

    @Test
    public void setPlayer2PhaseString() {
        hibernateControllerData.setPlayer2PhaseString(new Phase1());
        assertEquals("Phase1", hibernateControllerData.getPlayer2PhaseString());
    }

    @Test
    public void testAndGetStatusMessage() {
        hibernateControllerData.setStatusMessage("testnachricht");
        assertEquals("testnachricht", hibernateControllerData.getStatusMessage());
    }

    @Test
    public void testGetAndSetStacks() {
        hibernateControllerData.setStack("stack1", 1);
        hibernateControllerData.setStack("stack2", 2);
        hibernateControllerData.setStack("stack3", 3);
        hibernateControllerData.setStack("stack4", 4);
        assertEquals("stack1", hibernateControllerData.getStack1());
        assertEquals("stack2", hibernateControllerData.getStack2());
        assertEquals("stack3", hibernateControllerData.getStack3());
        assertEquals("stack4", hibernateControllerData.getStack4());
    }

    @Test
    public void setInvalidStack() {
        boolean error = false;
        try {
            hibernateControllerData.setStack("error", 5);
        } catch (IllegalArgumentException iae) {
            error = true;
        }
        assertTrue(error);
    }

    @Test
    public void getControllerTest() {
        /*
        Player player1 = new Player(0);
        Player player2 = new Player(1);

        IDeckOfCards drawPile = CardCreator.giveDeckOfCards();
        for(int i = 0; i < 10;++i) {
            player1.getDeckOfCards().add(drawPile.removeLast());
            player2.getDeckOfCards().add(drawPile.removeLast());
        }
        IDeckOfCards discardPile = new DeckOfCards();
        discardPile.add(drawPile.removeLast());
        int currentPlayerIndex = 0;
        IRoundState state = new RoundState();
        state.setState(StateEnum.DRAW_PHASE);
        StatusMessage statusMessage = new StatusMessage();
        statusMessage.setStatusMessage("start");

        UIController controller = hibernateControllerData.getController();*/
    }
}