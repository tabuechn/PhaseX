package controller.states;

import controller.states.impl.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tarek on 29.09.2015. Be grateful for this superior Code!
 */
public class AbstractStateTest {

    private static final String UNKNOWN_PHASE = "supaDupaPhaseName";
    private AbstractState testee;

    @Before
    public void setUp() throws Exception {
        testee = new StartPhase();
    }

    @Test
    public void testAllButStart() {
        testee.addToFinishedPhase(null, null, null);
        testee.discard(null, null);
        testee.drawHidden(null,null,null);
        testee.drawOpen(null,null,null);
        testee.playPhase(null, null);
        assertEquals(testee.toString(), "StartPhase");
    }

    @Test
    public void testStart() {
        testee = new DrawPhase();
        testee.start(null);
    }

    @Test
    public void startPhaseAsStringShouldReturnNewStartPhase() {
        assertTrue(AbstractState.getStateFromString(AbstractState.START_PHASE) instanceof StartPhase);
    }

    @Test
    public void drawPhaseAsStringShouldReturnNewDrawPhase() {
        assertTrue(AbstractState.getStateFromString(AbstractState.DRAW_PHASE) instanceof DrawPhase);

    }

    @Test
    public void endPhaseAsStringShouldReturnNewEndPhase() {
        assertTrue(AbstractState.getStateFromString(AbstractState.END_PHASE) instanceof EndPhase);

    }

    @Test
    public void playerTurnNotFinishedAsStringShouldReturnNewPlayerTurnNotFinishedPhase() {
        assertTrue(AbstractState.getStateFromString(AbstractState.PLAYER_TURN_NOT_FINISHED) instanceof PlayerTurnNotFinished);

    }

    @Test
    public void playerTurnFinishedPhaseAsStringShouldReturnNewPlayerTurnFinishedPhase() {
        assertTrue(AbstractState.getStateFromString(AbstractState.PLAYER_TURN_FINISED) instanceof PlayerTurnFinished);

    }

    @Test(expected = IllegalArgumentException.class)
    public void unknownStringShouldThrowAnException() {
        AbstractState.getStateFromString(UNKNOWN_PHASE);
    }
}