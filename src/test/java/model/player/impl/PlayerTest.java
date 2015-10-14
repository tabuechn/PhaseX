package model.player.impl;

import model.deckOfCards.impl.DeckOfCards;
import model.phase.impl.Phase1;
import model.player.IPlayer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 22.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class PlayerTest {

    private static final String PLAYER_NAME = "John";

    private IPlayer testee;

    @Before
    public void setUp() {
        testee = new Player(PLAYER_NAME, 1);
    }

    @Test
    public void checkPlayerNumberGetter() {
        assertEquals(testee.getPlayerNumber(), 1);
    }

    @Test
    public void checkSetDeckOfCards() {
        testee.setDeckOfCards(new DeckOfCards());
        assertEquals(testee.getDeckOfCards().size(), 0);
    }

    @Test
    public void checkIfDeckOfCardIsReturnedCorrectly() throws Exception {
        testee.setDeckOfCards(new DeckOfCards());
        assertNotNull(testee.getDeckOfCards());
    }


    @Test
    public void checkIfPlayerIsInitializedCorrectly() {
        assertEquals(PLAYER_NAME, testee.getPlayerName());
        assertEquals(Phase1.PHASE_NUMBER, testee.getPhase().getPhaseNumber());
        assertFalse(testee.isPhaseDone());
    }

    @Test
    public void checkIfNextStageIsWorkingCorrectly() {
        int tmp = testee.getPhase().getPhaseNumber();
        testee.nextPhase();
        assertEquals(++tmp, testee.getPhase().getPhaseNumber());
    }

    @Test
    public void checkIfPhaseDoneIsSetCorrectly() {
        testee.setPhaseDone(true);
        assertTrue(testee.isPhaseDone());
    }

    @Test
    public void checkIfPointsAreAddedCorrectly() {
        testee.addPoints(10);
        testee.addPoints(3);
        assertEquals(13, testee.getPoints());
    }
}