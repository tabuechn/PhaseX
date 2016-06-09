package model.roundstate.impl;

import model.roundstate.StateEnum;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by tabuechn on 08.06.2016.
 */
public class RoundStateTest {

    private RoundState roundState;

    @Before
    public void setUp() {
        roundState = new RoundState();
    }

    @Test
    public void toStringWorksCorrectly() {
        roundState.setState(StateEnum.START_PHASE);
        assertEquals(roundState.toString(), StateEnum.START_PHASE.toString());
    }
}
