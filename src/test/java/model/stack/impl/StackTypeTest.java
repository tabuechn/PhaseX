package model.stack.impl;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/**
 * Created by tabuechn on 08.06.2016.
 */
public class StackTypeTest {
    @Test
    public void ordinalTestWithInvalidInput() {
        assertNull(StackType.byOrdinal(3));
    }

    @Test
    public void ordinalTestWithValidInput() {
        assertEquals(StackType.PAIR,StackType.byOrdinal(0));
        assertEquals(StackType.STREET,StackType.byOrdinal(1));
        assertEquals(StackType.COLOR,StackType.byOrdinal(2));
    }
}
