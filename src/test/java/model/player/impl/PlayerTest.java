package model.player.impl;

import model.card.CardColor;
import model.card.CardValue;
import model.card.impl.Card;
import model.deck.impl.DeckOfCards;
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

    public static final String DERP = "Derp";
    private static final String PLAYER_NAME = "John";
    private static final String PHASE = "Phase";

    private Player testee;

    @Before
    public void setUp() {
        testee = new Player(1);
        testee.setName(PLAYER_NAME);
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

    @Test
    public void checkIfPlayerWithEmptyNameIsSetCorrectly() {
        testee = new Player(1);
        assertTrue(testee.getPlayerName().isEmpty());
        testee.setName("John");
        assertEquals("John", testee.getPlayerName());
    }

    @Test
    public void playerNameMustNotSetTwice() {
        testee.setName("John");
        assertEquals("John", testee.getPlayerName());
        testee.setName("Giesela");
        assertEquals("John", testee.getPlayerName());
    }

    @Test
    public void compareWithSamePlayerShouldReturnTrue() {
        assertTrue(testee.equals(testee));
    }

    @Test
    public void comparePlayersWithSameNameShouldReturnTrue() {
        IPlayer secondPlayer = new Player(2);
        secondPlayer.setName(PLAYER_NAME);
        secondPlayer.nextPhase();
        assertTrue(testee.equals(secondPlayer));
    }

    @Test
    public void playersWithSameNameShouldReturnSameHashCode() {
        IPlayer secondPlayer = new Player(2);
        secondPlayer.setName(PLAYER_NAME);
        secondPlayer.nextPhase();
        assertTrue(testee.hashCode() == secondPlayer.hashCode());
    }

    @Test
    public void comparePlayersWithOtherNamesShouldReturnFalse() {
        IPlayer secondPlayer = new Player(2);
        secondPlayer.setName("Udo");
        secondPlayer.nextPhase();
        assertFalse(testee.equals(secondPlayer));
    }

    @Test
    public void playersWithDifferentNameShouldReturnDifferentHashCode() {
        IPlayer secondPlayer = new Player(2);
        secondPlayer.setName("Udo");
        secondPlayer.nextPhase();
        assertFalse(testee.hashCode() == secondPlayer.hashCode());
    }

    @Test
    public void comparePlayersWithSameNameButDifferentCaptionShouldReturnTrue() {
        IPlayer secondPlayer = new Player(2);
        secondPlayer.setName("joHn");
        secondPlayer.nextPhase();
        assertTrue(testee.equals(secondPlayer));
    }

    @Test
    public void setPhaseTest() {

        for(int i = 1; i <= 5;++i) {
            String phaseX = PHASE + i;
            testee.setPhase(phaseX);
            assertEquals(phaseX,testee.getPhase().toString());
        }
    }

    @Test
    public void setIllegalPhaseTest() {
        boolean checker = false;
        try {
            testee.setPhase("YOLO");
        } catch (IllegalStateException ise) {
            checker = true;
        }
        assertTrue(checker);
    }

    @Test
    public void equalsNullShouldReturnFalse() {
        assertFalse(testee.equals(null));
    }

    @Test
    public void getAndSetIDTest() {
        String yolo = "YOLO";
        testee.setId(yolo);
        assertEquals(yolo,testee.getId());
    }

    @Test
    public void emptyConstructorWorksProperly() {
        Player player = new Player();
        assertNotNull(player);
    }

    @Test
    public void sortingWithNumberPhaseSortsProperly() {
        testee.setPhase("Phase3");
        DeckOfCards numberPhase = new DeckOfCards();
        Card one = new Card(CardValue.ONE, CardColor.BLUE);
        Card three = new Card(CardValue.THREE, CardColor.BLUE);
        Card two = new Card(CardValue.TWO, CardColor.BLUE);
        numberPhase.add(two);
        numberPhase.add(three);
        numberPhase.add(one);
        testee.setDeckOfCards(numberPhase);

    }
}