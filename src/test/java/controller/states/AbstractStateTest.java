package controller.states;

import controller.states.impl.DrawPhase;
import controller.states.impl.StartPhase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tarek on 29.09.2015. Be grateful for this superior Code!
 */
public class AbstractStateTest {

    AbstractState abstractTest;

    @Before
    public void setUp() throws Exception {
        abstractTest = new StartPhase();
    }

    @Test
    public void testAllButStart() {
        abstractTest.addToFinishedPhase(null, null, null);
        abstractTest.discard(null, null);
        abstractTest.drawHidden(null);
        abstractTest.drawOpen(null);
        abstractTest.playPhase(null, null);
        assertEquals(abstractTest.toString(), "StartPhase");
    }

    @Test
    public void testStart() {
        abstractTest = new DrawPhase();
        abstractTest.start(null);
    }
}