package controller.playerContainer.impl;

import model.player.IPlayer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Tarek on 31.05.2016. Be grateful for this superior Code!
 */
public class PlayerContainerTest {
    private PlayerContainer playerContainer;
    private String player1Name;

    @Before
    public void setUp() {
        player1Name = "Peter";
        playerContainer = new PlayerContainer(player1Name);
    }

    @Test
    public void playerTest() {
        assertEquals(player1Name, playerContainer.getCurrentPlayer().getPlayerName());
        assertEquals(0, playerContainer.getCurrentPlayerIndex());
        IPlayer[] tmp = playerContainer.getPlayers();
        playerContainer.setPlayers(tmp);
        playerContainer.setCurrentPlayer(tmp[0]);
        playerContainer.setOtherPlayer(tmp[1]);
        playerContainer.setCurrentPlayerIndex(0);
        assertEquals(0, playerContainer.getCurrentPlayerIndex());
        assertNotEquals(player1Name, playerContainer.getOtherPlayer().getPlayerName());
    }

    @Test
    public void nextPlayerTest() {
        assertEquals(0, playerContainer.getCurrentPlayerIndex());
        playerContainer.nextPlayer();
        assertEquals(1, playerContainer.getCurrentPlayerIndex());
        assertNotEquals(player1Name, playerContainer.getCurrentPlayer().getPlayerName());
        assertEquals(player1Name, playerContainer.getOtherPlayer().getPlayerName());
        playerContainer.nextPlayer();
        assertEquals(player1Name, playerContainer.getCurrentPlayer().getPlayerName());

    }
}
