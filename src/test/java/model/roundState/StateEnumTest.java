package model.roundstate;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by tabuechn on 08.06.2016.
 */
public class StateEnumTest {

    @Test
    public void byOrdinalWithValidNumberShouldReturnEnum() {
        assertEquals(StateEnum.START_PHASE,StateEnum.byOrdinal(0));
    }

    @Test
    public void byOrdinalWithInvalidNumberSHouldReturnNull() {
        assertNull(StateEnum.byOrdinal(9));
    }

    @Test
    public void getRoundNameByStringTest() {
        assertEquals(StateEnum.START_PHASE,StateEnum.getRoundNameByString("START_PHASE"));
        assertEquals(StateEnum.END_PHASE,StateEnum.getRoundNameByString("END_PHASE"));
        assertEquals(StateEnum.DRAW_PHASE,StateEnum.getRoundNameByString("DRAW_PHASE"));
        assertEquals(StateEnum.PLAYER_TURN_FINISHED,StateEnum.getRoundNameByString("PLAYER_TURN_FINISHED"));
        assertEquals(StateEnum.PLAYER_TURN_NOT_FINISHED,StateEnum.getRoundNameByString("PLAYER_TURN_NOT_FINISHED"));
        boolean tmp = false;
        try {
            StateEnum.getRoundNameByString("YOLO");
        } catch (IllegalArgumentException iae) {
            tmp = true;
        }
        assertTrue(tmp);
    }

}
