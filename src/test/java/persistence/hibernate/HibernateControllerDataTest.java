package persistence.hibernate;

import model.player.impl.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        assertEquals(hibernateControllerData.getStringID(),new Integer(1));

    }


    @Test
    public void testGetAndSetPlayer2() throws Exception {
        Player player2 = new Player(0);
        player2.setName("Herbert");
        hibernateControllerData.setPlayer2(player2);
        assertEquals(hibernateControllerData.getPlayer2().getPlayerName(),"Herbert");
    }


    @Test
    public void testGetAndSetPlayer1() throws Exception {
        Player player1 = new Player(0);
        player1.setName("Herbert");
        hibernateControllerData.setPlayer1(player1);
        assertEquals(hibernateControllerData.getPlayer1().getPlayerName(),"Herbert");
    }



    @Test
    public void testGetAndSetPlayer1Pile() throws Exception {
        hibernateControllerData.setPlayer1Pile("test");
        assertEquals(hibernateControllerData.getPlayer1Pile(),"test");
    }



    @Test
    public void testGetPlayer2Pile() throws Exception {
        hibernateControllerData.setPlayer2Pile("test");
        assertEquals(hibernateControllerData.getPlayer2Pile(),"test");
    }


    @Test
    public void testGetAndSetPlayer1PhaseString() throws Exception {
        Player player1 = new Player(0);
        hibernateControllerData.setPlayer1PhaseString(player1.getPhase().toString());
        assertEquals(hibernateControllerData.getPlayer1PhaseString(),player1.getPhase().toString());
    }



    @Test
    public void testGetAndSetPlayer2PhaseString() throws Exception {
        Player player2 = new Player(0);
        hibernateControllerData.setPlayer2PhaseString(player2.getPhase().toString());
        assertEquals(hibernateControllerData.getPlayer2PhaseString(),player2.getPhase().toString());
    }


    @Test
    public void testGetAndSetDiscardPile() throws Exception {
        hibernateControllerData.setDiscardPile("test");
        assertEquals(hibernateControllerData.getDiscardPile(),"test");
    }


    @Test
    public void testGetAndSetDrawPile() throws Exception {
        hibernateControllerData.setDrawPile("test");
        assertEquals(hibernateControllerData.getDrawPile(),"test");
    }


    @Test
    public void testGetRoundState() throws Exception {
        hibernateControllerData.setRoundState("test");
        assertEquals(hibernateControllerData.getRoundState(),"test");
    }

}